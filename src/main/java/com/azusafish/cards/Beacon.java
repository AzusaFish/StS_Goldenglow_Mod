package com.azusafish.cards;

import basemod.abstracts.CustomCard;
import com.azusafish.GGEnums;
import com.azusafish.powers.CurrentMarkPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Beacon extends CustomCard {
    public static final String ID = "AzusaFish:Beacon";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "images/cards/Beacon.png"; 

    private static final int COST = 0;
    private static final int MAGIC_NUMBER = 3;
    private static final int UPGRADE_MAGIC = 2; // +2 means total 5

    public Beacon() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, 
              CardType.SKILL, 
              GGEnums.GG_CARD_COLOR, 
              CardRarity.COMMON, 
              CardTarget.ENEMY);

        this.baseMagicNumber = MAGIC_NUMBER;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Apply Current Mark
        addToBot(new ApplyPowerAction(m, p, new CurrentMarkPower(m, this.magicNumber), this.magicNumber));
        // Draw 1 card
        addToBot(new DrawCardAction(p, 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
