package com.azusafish.relics;

import basemod.abstracts.CustomRelic;
import com.azusafish.orbs.SparkOrb;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class Sparkles extends CustomRelic {
    public static final String ID = "AzusaFish:Sparkles";
    // 只保留这一张存在的图片
    private static final String IMG = "images/relics/Sparkles.png";

    public Sparkles() {
        super(ID, ImageMaster.loadImage(IMG), RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        boolean hasSpark = false;
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb instanceof SparkOrb) {
                hasSpark = true;
                break;
            }
        }

        if (!hasSpark) {
            flash();
            addToBot(new ChannelAction(new SparkOrb()));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}