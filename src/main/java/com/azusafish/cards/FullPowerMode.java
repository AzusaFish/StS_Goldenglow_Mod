package com.azusafish.cards;

import basemod.abstracts.CustomCard;
import com.azusafish.GGEnums;
import com.azusafish.powers.FullPowerModePower;
import com.azusafish.powers.PrecisionPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FullPowerMode extends CustomCard {
    public static final String ID = "AzusaFish:FullPowerMode";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "images/cards/FullPowerMode.png"; 

    private static final int COST = 1;

    public FullPowerMode() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
            CardType.SKILL,
            GGEnums.GG_CARD_COLOR,
            CardRarity.RARE,
            CardTarget.SELF);

        this.baseMagicNumber = 10;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new FullPowerModePower(p, 1), 1));
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
