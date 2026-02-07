package com.azusafish.cards;

import basemod.abstracts.CustomCard;
import com.azusafish.GGEnums;
import com.azusafish.powers.CurrentMarkPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DetonationProtocol extends CustomCard {
    public static final String ID = "AzusaFish:DetonationProtocol";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = "images/cards/DetonationProtocol.png";

    private static final int COST = 1;
    private static final int MULTIPLIER = 8;
    private static final int UPGRADE_PLUS_MULTIPLIER = 2;

    public DetonationProtocol() {
        super(ID, cardStrings.NAME, IMG_PATH, COST, cardStrings.DESCRIPTION,
                CardType.SKILL,
                GGEnums.GG_CARD_COLOR,
                CardRarity.RARE,
                CardTarget.ENEMY);

        this.baseMagicNumber = MULTIPLIER;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null && m.hasPower(CurrentMarkPower.POWER_ID)) {
            int stacks = m.getPower(CurrentMarkPower.POWER_ID).amount;
            int damageAmount = stacks * this.magicNumber;
            
            if (damageAmount > 0) {
                addToBot(new DamageAction(m, 
                    new DamageInfo(p, damageAmount, DamageInfo.DamageType.THORNS), 
                    AbstractGameAction.AttackEffect.FIRE));
                
                // 清除印记
                addToBot(new RemoveSpecificPowerAction(m, p, CurrentMarkPower.POWER_ID));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MULTIPLIER);
            initializeDescription();
        }
    }
}
