package com.azusafish.cards;

import basemod.abstracts.CustomCard;
import com.azusafish.GGEnums;
import com.azusafish.orbs.SparkOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class StaticRelease extends CustomCard {
    public static final String ID = "AzusaFish:StaticRelease";
    public static final String IMG_PATH = "images/cards/StaticRelease.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 3;

    public StaticRelease() {
        super(ID, cardStrings.NAME, IMG_PATH, COST, cardStrings.DESCRIPTION, 
                CardType.ATTACK, 
                GGEnums.GG_CARD_COLOR, // 绑定粉色
                CardRarity.COMMON, 
                CardTarget.ENEMY);

        this.baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 造成伤害 (带闪电特效)
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.LIGHTNING));
        
        addToBot(new ChannelAction(new SparkOrb()));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}