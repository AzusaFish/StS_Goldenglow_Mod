package com.azusafish.cards;

import basemod.abstracts.CustomCard;
import com.azusafish.GGEnums;
import com.azusafish.powers.PrecisionPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ArtsStorm extends CustomCard {
    public static final String ID = "AzusaFish:ArtsStorm";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "images/cards/ArtsStorm.png"; 

    private static final int COST = 2;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DMG = 3;

    public ArtsStorm() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, 
              CardType.ATTACK, 
              GGEnums.GG_CARD_COLOR, 
              CardRarity.COMMON, 
              CardTarget.ENEMY);

        this.baseDamage = DAMAGE;
        this.baseMagicNumber = 2; // Extra damage per stack
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Calculate total damage
        int precisionStacks = 0;
        if (p.hasPower(PrecisionPower.POWER_ID)) {
            precisionStacks = p.getPower(PrecisionPower.POWER_ID).amount;
        }
        
        int totalDamage = this.damage + (precisionStacks * this.magicNumber);
        
        addToBot(new DamageAction(m, new DamageInfo(p, totalDamage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        // 先计算基础伤害 (受力量、虚弱等影响)
        super.calculateCardDamage(mo);
        
        // 加上额外的 Precision 伤害
        // 注意：Spire 的 calculateCardDamage 更新的是 this.damage 字段，用于在卡面上显示最终数值
        if (AbstractDungeon.player.hasPower(PrecisionPower.POWER_ID)) {
            int precisionStacks = AbstractDungeon.player.getPower(PrecisionPower.POWER_ID).amount;
            this.damage += precisionStacks * this.magicNumber;
            this.isDamageModified = true;
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
