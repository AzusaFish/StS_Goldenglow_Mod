package com.azusafish.powers;

import com.azusafish.orbs.SparkOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class MeltdownPower extends AbstractPower {
    public static final String POWER_ID = "AzusaFish:Meltdown";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public MeltdownPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1; // No stack amount really needed
        this.type = PowerType.BUFF; // It has a downside next turn
        this.isTurnBased = true; // It happens at start of turn

        String unpath = "images/powers/meltdown.png"; 
        Texture img = ImageMaster.loadImage(unpath);
        this.region128 = new TextureAtlas.AtlasRegion(img, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(img, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        AbstractPlayer p = AbstractDungeon.player;
        this.flash();

        // Lose 1 Energy
        addToBot(new LoseEnergyAction(1));

        // Lose all Sparks (no evoke)
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (!p.orbs.isEmpty()) {
                    boolean removed = false;
                    for (int i = 0; i < p.orbs.size(); ++i) {
                        if (p.orbs.get(i) instanceof SparkOrb) {
                             p.orbs.set(i, new EmptyOrbSlot());
                             removed = true;
                        }
                    }
                    if (removed) {
                        // Re-align slots
                        for (int i = 0; i < p.orbs.size(); ++i) {
                            ((AbstractOrb)p.orbs.get(i)).setSlot(i, p.maxOrbs);
                        }
                    }
                }
                this.isDone = true;
            }
        });

        // Remove this power
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
