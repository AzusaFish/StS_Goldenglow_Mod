package com.azusafish.cards;

import basemod.abstracts.CustomCard;
import com.azusafish.GGEnums;
import com.azusafish.powers.PrecisionPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PrecisionGuidance extends CustomCard {
    public static final String ID = "AzusaFish:PrecisionGuidance";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "images/cards/PrecisionGuidance.png"; 

    private static final int COST = 1;

    public PrecisionGuidance() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, 
              CardType.POWER, 
              GGEnums.GG_CARD_COLOR, 
              CardRarity.UNCOMMON, 
              CardTarget.SELF);

        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new PrecisionPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
