package com.azusafish.cards;

import basemod.abstracts.CustomCard;
import com.azusafish.GGEnums;
import com.azusafish.powers.CurrentMarkPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class InductiveChain extends CustomCard {
    public static final String ID = "AzusaFish:InductiveChain";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "images/cards/InductiveChain.png"; 

    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int MAGIC_NUMBER = 2; // Apply 2 stacks
    private static final int UPGRADE_MAGIC = 1; // +1 -> 3 stacks

    public InductiveChain() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, 
              CardType.ATTACK, 
              GGEnums.GG_CARD_COLOR, 
              CardRarity.COMMON, 
              CardTarget.ENEMY);

        this.baseDamage = DAMAGE;
        this.baseMagicNumber = MAGIC_NUMBER;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Deal damage
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        
        // Logic: If target has Current Mark, apply to ALL OTHER enemies
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                // Check if target is still alive and has the power
                if (m != null && !m.isDeadOrEscaped() && m.hasPower(CurrentMarkPower.POWER_ID)) {
                    // Iterate all monsters
                    for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        // Apply to others (not dead, not the target)
                        if (!mo.isDeadOrEscaped() && mo != m) {
                            addToTop(new ApplyPowerAction(mo, p, new CurrentMarkPower(mo, magicNumber), magicNumber));
                        }
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
