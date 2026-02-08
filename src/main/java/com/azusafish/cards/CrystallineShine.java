package com.azusafish.cards;

import basemod.abstracts.CustomCard;
import com.azusafish.GGEnums;
import com.azusafish.orbs.SparkOrb;
import com.azusafish.powers.CurrentMarkPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class CrystallineShine extends CustomCard {
    public static final String ID = "AzusaFish:CrystallineShine";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "images/cards/CrystallineShine.png"; 

    private static final int COST = 3;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 4; // 8 -> 12
    private static final int MAGIC_NUMBER = 3;
    private static final int UPGRADE_MAGIC = 1; // 3 -> 4

    public CrystallineShine() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, 
              CardType.ATTACK, 
              GGEnums.GG_CARD_COLOR, 
              CardRarity.RARE, 
              CardTarget.ALL_ENEMY);

        this.baseDamage = DAMAGE;
        this.baseMagicNumber = MAGIC_NUMBER;
        this.magicNumber = this.baseMagicNumber;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 1. Deal damage to all enemies
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        
        // 2. Apply Current Mark to ALL enemies
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(mo, p, new CurrentMarkPower(mo, this.magicNumber), this.magicNumber));
            }
        }

        // 3. Trigger passive effect of all Sparks
        // We use an Action to ensure it happens after damage/power application if needed, 
        // or just standard sequential execution.
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractOrb o : p.orbs) {
                    if (o instanceof SparkOrb) {
                        o.onEndOfTurn(); // Spark's passive effect is defined in onEndOfTurn
                    }
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
