package com.azusafish.cards;

import basemod.abstracts.CustomCard;
import com.azusafish.GGEnums;
import com.azusafish.orbs.SparkOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DiffusedCurrent extends CustomCard {
    public static final String ID = "AzusaFish:DiffusedCurrent";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "images/cards/DiffusedCurrent.png"; 

    private static final int COST = 1;
    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DMG = 3;

    public DiffusedCurrent() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, 
            CardType.ATTACK, 
            GGEnums.GG_CARD_COLOR, 
            CardRarity.COMMON, 
            CardTarget.ALL_ENEMY);

        this.baseDamage = DAMAGE;
        this.isMultiDamage = true; // 声明是群体攻击
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 对所有敌人造成伤害
        // 使用 LIGHTNING 特效比较符合这张卡的设定（电流）
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.LIGHTNING));
        
        // 生成 1 个 Spark
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
