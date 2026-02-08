package com.azusafish.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class FullPowerModePower extends AbstractPower {
    public static final String POWER_ID = "AzusaFish:FullPowerMode";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int amountToRemove;

    public FullPowerModePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.amountToRemove = amount;
        this.type = PowerType.BUFF; 
        this.isTurnBased = true;

        String unpath = "images/powers/full_power.png"; 
        Texture img = ImageMaster.loadImage(unpath);
        this.region128 = new TextureAtlas.AtlasRegion(img, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(img, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            flash();
            // 移除 Precision
            this.addToBot(new ReducePowerAction(this.owner, this.owner, PrecisionPower.POWER_ID, this.amount));
            // 移除自己 (一次性效果)
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
