package com.azusafish.cards;

import basemod.abstracts.CustomCard;
import com.azusafish.GGEnums;
import com.azusafish.powers.MeltdownPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Meltdown extends CustomCard {
    public static final String ID = "AzusaFish:Meltdown";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "images/cards/Meltdown.png"; 

    private static final int COST = 1;

    public Meltdown() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, 
              CardType.SKILL, 
              GGEnums.GG_CARD_COLOR, 
              CardRarity.RARE, 
              CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Gain 2 Energy
        addToBot(new GainEnergyAction(2));
        
        // Apply Meltdown Power (doubles Spark damage, punish next turn)
        addToBot(new ApplyPowerAction(p, p, new MeltdownPower(p), 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}
