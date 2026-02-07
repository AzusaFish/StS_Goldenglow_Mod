package com.azusafish.orbs;

import com.azusafish.powers.CurrentMarkPower; 
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;

public class SparkOrb extends AbstractOrb {
    // Orb 的 ID
    public static final String ORB_ID = "AzusaFish:Spark";
    // 读取文本
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESC = orbString.DESCRIPTION;
    
    // 图片路径
    private static final String IMG_PATH = "images/orbs/spark.png";
    private static Texture img;
    
    // 锁定的目标变量
    private AbstractMonster target;

    public SparkOrb() {
        this.ID = ORB_ID;
        this.name = orbString.NAME;
        
        if (img == null) {
            img = ImageMaster.loadImage(IMG_PATH);
        }
        
        // --- 数值设定 ---
        this.basePassiveAmount = 1;  // 回合结束伤害
        this.passiveAmount = this.basePassiveAmount;
        
        this.baseEvokeAmount = 6;   // 爆炸基础伤害
        this.evokeAmount = this.baseEvokeAmount;
        
        this.updateDescription();
        
        // 视觉修正：固定角度，不旋转
        this.angle = 0.0F; 
        this.channelAnimTimer = 0.5F;
    }

    // 核心逻辑：每一帧都在运行，负责锁定和转火
    @Override
    public void update() {
        super.update(); // 必须保留父类调用
        
        // 检查：如果 1. 刚生成还没目标 OR 2. 当前目标死掉了/逃跑了
        if (this.target == null || this.target.isDeadOrEscaped()) {
            
            // 尝试找一个活着的随机敌人
            AbstractMonster newTarget = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            
            // 如果找到了新目标 (战场上还有活人)
            if (newTarget != null) {
                this.target = newTarget;
                
                // 转火/锁定特效：立刻给新目标上 1 层印记，读取动态上限
                boolean canApply = !this.target.hasPower(CurrentMarkPower.POWER_ID);
                if (!canApply) {
                    com.megacrit.cardcrawl.powers.AbstractPower p = this.target.getPower(CurrentMarkPower.POWER_ID);
                    if (p instanceof CurrentMarkPower && p.amount < ((CurrentMarkPower)p).maxAmount) {
                        canApply = true;
                    }
                }

                if (canApply) {
                    AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(this.target, AbstractDungeon.player, 
                        new CurrentMarkPower(this.target, 1), 1)
                    );
                }
            }
        }
    }

    @Override
    public void updateDescription() {
        this.applyFocus();
        // 描述需要匹配 OrbStrings.json
        this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[2];
    }

    // 激发 (Evoke)：引爆伤害
    @Override
    public void onEvoke() {
        if (this.target != null) {
            int totalDamage = this.evokeAmount;
            
            // 检查易伤层数
            if (this.target.hasPower(CurrentMarkPower.POWER_ID)) {
                int marks = this.target.getPower(CurrentMarkPower.POWER_ID).amount;
                // 伤害加成公式：每层 +3 伤害 (可自行调整)
                totalDamage += marks * 3;
                
                // 消耗所有易伤
                AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.target, AbstractDungeon.player, CurrentMarkPower.POWER_ID));
            }

            // 特效与音效
            AbstractDungeon.actionManager.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1F));
            
            // 造成最终伤害
            DamageInfo info = new DamageInfo(AbstractDungeon.player, totalDamage, DamageInfo.DamageType.THORNS);
            AbstractDungeon.actionManager.addToBottom(new DamageAction(this.target, info, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }
    }

    // 回合结束 (Passive)：伤害 + 叠层
    @Override
    public void onEndOfTurn() {
        if (this.target != null) {
            // 特效
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1F));
            
            // 1. 造成伤害
            if (this.target.hasPower(CurrentMarkPower.POWER_ID)) {
                int marks = this.target.getPower(CurrentMarkPower.POWER_ID).amount;
                DamageInfo info = new DamageInfo(AbstractDungeon.player, this.passiveAmount + marks, DamageInfo.DamageType.THORNS);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(this.target, info, AbstractGameAction.AttackEffect.LIGHTNING));
            }
            else
            {
                DamageInfo info = new DamageInfo(AbstractDungeon.player, this.passiveAmount, DamageInfo.DamageType.THORNS);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(this.target, info, AbstractGameAction.AttackEffect.LIGHTNING));
            }
            // 2. 增加 1 层印记，读取动态上限
            boolean canApply = !this.target.hasPower(CurrentMarkPower.POWER_ID);
            if (!canApply) {
                com.megacrit.cardcrawl.powers.AbstractPower p = this.target.getPower(CurrentMarkPower.POWER_ID);
                if (p instanceof CurrentMarkPower && p.amount < ((CurrentMarkPower)p).maxAmount) {
                    canApply = true;
                }
            }
            
            if(canApply) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, AbstractDungeon.player,
                new CurrentMarkPower(this.target, 1), 1));
            }

        }
    }

    @Override
    public AbstractOrb makeCopy() { return new SparkOrb(); }

    @Override
    public void playChannelSFX() { CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F); }

    // 渲染逻辑 (包含位置抬高修正)
    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, this.c.a / 2.0F));
        
        // 视觉修正：把球抬高 10 像素，避免离头顶太近
        float heightOffset = 10.0F; 
        
        sb.draw(img, 
                this.cX - 48.0F, 
                this.cY - 48.0F + this.bobEffect.y + heightOffset, 
                48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, this.angle, 0, 0, 96, 96, false, false);
        
        this.renderText(sb);
        this.hb.render(sb);
    }
}