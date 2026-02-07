package com.azusafish.cards;

import basemod.abstracts.CustomCard;
import com.azusafish.GGEnums;
import com.azusafish.orbs.SparkOrb;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class StaticShield extends CustomCard {
    public static final String ID = "AzusaFish:StaticShield";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = "images/cards/StaticShield.png";

    private static final int COST = 2;
    private static final int BLOCK = 10;
    private static final int UPGRADE_PLUS_BLOCK = 4;
    private static final int BONUS_BLOCK = 3;

    public StaticShield() {
        super(ID, cardStrings.NAME, IMG_PATH, COST, cardStrings.DESCRIPTION,
                CardType.SKILL,
                GGEnums.GG_CARD_COLOR,
                CardRarity.UNCOMMON,
                CardTarget.SELF);

        this.baseBlock = BLOCK;
        this.baseMagicNumber = BONUS_BLOCK;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int sparkCount = 0;
        for (AbstractOrb orb : p.orbs) {
            if (orb instanceof SparkOrb) {
                sparkCount++;
            }
        }
        
        int totalBlock = this.block + (sparkCount * this.magicNumber);
        addToBot(new GainBlockAction(p, p, totalBlock));
    }
    
    // 动态更新卡牌描述中的格挡数值（可选，让玩家看到当前实际能加多少防）
    @Override
    public void applyPowers() {
        int sparkCount = 0;
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb instanceof SparkOrb) {
                sparkCount++;
            }
        }
        
        int originalBaseBlock = this.baseBlock;
        this.baseBlock = originalBaseBlock + (sparkCount * this.magicNumber);
        super.applyPowers();
        this.baseBlock = originalBaseBlock;
        this.isBlockModified = (this.block != this.baseBlock);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
