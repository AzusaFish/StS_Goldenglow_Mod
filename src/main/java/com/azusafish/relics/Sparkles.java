package com.azusafish.relics;

import basemod.abstracts.CustomRelic;
import com.azusafish.orbs.SparkOrb;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class Sparkles extends CustomRelic {
    // 遗物ID
    public static final String ID = "AzusaFish:Sparkles";
    // 图片路径 (你需要准备这张图)
    private static final String IMG = "images/relics/Sparkles.png";
    // 轮廓图 (可选，没有就用同一个)
    private static final String OUTLINE = "images/relics/Sparkles.png";

    public Sparkles() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
    }

    // 如果你没有做 outline 图片，可以用这个简单的构造函数：
    /*
    public Sparkles() {
        super(ID, ImageMaster.loadImage(IMG), RelicTier.STARTER, LandingSound.MAGICAL);
    }
    */

    @Override
    public void atTurnStart() {
        // 检查是否有 Spark
        boolean hasSpark = false;
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb instanceof SparkOrb) {
                hasSpark = true;
                break;
            }
        }

        // 如果没有，生成一个
        if (!hasSpark) {
            flash(); // 遗物闪烁一下
            addToBot(new ChannelAction(new SparkOrb()));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}