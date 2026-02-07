package com.azusafish.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;

public class SparkOrb extends AbstractOrb {
    public static final String ORB_ID = "AzusaFish:Spark";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESC = orbString.DESCRIPTION;
    private static final String IMG_PATH = "images/orbs/spark.png";
    private static Texture img;

    public SparkOrb() {
        this.ID = ORB_ID;
        this.name = orbString.NAME;
        
        if (img == null) {
            img = ImageMaster.loadImage(IMG_PATH);
        }
        
        this.basePassiveAmount = 3;
        this.passiveAmount = this.basePassiveAmount;
        this.baseEvokeAmount = 8;
        this.evokeAmount = this.baseEvokeAmount;
        
        this.updateDescription();
        
        // 👇 改动 1: 固定角度，不要旋转
        this.angle = 0.0F; 
        
        this.channelAnimTimer = 0.5F;
    }

    @Override
    public void updateDescription() {
        this.applyFocus();
        this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[2];
    }

    @Override
    public void onEvoke() {
        AbstractDungeon.actionManager.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
        AbstractDungeon.actionManager.addToTop(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1F));
        DamageInfo info = new DamageInfo(AbstractDungeon.player, this.evokeAmount, DamageInfo.DamageType.THORNS);
        AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(info, AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    @Override
    public void onEndOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1F));
        DamageInfo info = new DamageInfo(AbstractDungeon.player, this.passiveAmount, DamageInfo.DamageType.THORNS);
        AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(info, AbstractGameAction.AttackEffect.LIGHTNING));
    }

    @Override
    public AbstractOrb makeCopy() { return new SparkOrb(); }

    @Override
    public void playChannelSFX() { CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F); }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, this.c.a / 2.0F));
        
        // 👇 改动 2: 视觉调整
        // 我在 cY 后面加了一个 + 40.0F，这会让球比实际位置看起来高 40 像素
        // bobEffect.y 是球自带的上下浮动动画，保留它会更生动
        float heightOffset = 40.0F; 
        
        sb.draw(img, 
                this.cX - 48.0F, 
                this.cY - 48.0F + this.bobEffect.y + heightOffset, // 在这里加高
                48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, this.angle, 0, 0, 96, 96, false, false);
        
        // 渲染文字 (伤害数值)
        // 文字也需要跟着抬高，不然会和球分离
        this.renderText(sb);
        this.hb.render(sb);
    }
}