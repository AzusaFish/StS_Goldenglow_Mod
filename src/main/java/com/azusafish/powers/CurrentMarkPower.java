package com.azusafish.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CurrentMarkPower extends AbstractPower {
    public static final String POWER_ID = "AzusaFish:CurrentMark";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final String IMG_PATH = "images/powers/currentmark.png";
    
    // 默认上限为 5
    public int maxAmount = 5;

    public CurrentMarkPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF; // 这是一个负面效果
        
        // 加载图片
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_PATH), 0, 0, 48, 48);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_PATH), 0, 0, 48, 48);
        
        // 确保初始层数不超过 maxAmount
        if (this.amount > maxAmount) {
            this.amount = maxAmount;
        }
        
        updateDescription();
    }
    
    // 接口：允许外部修改层数上限
    public void setMaxAmount(int newMax) {
        this.maxAmount = newMax;
        if (this.amount > this.maxAmount) {
            this.amount = this.maxAmount;
        }
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount > maxAmount) {
            this.amount = maxAmount;
        }
    }

    @Override
    public void updateDescription() {
        // 描述：当前积累了 !A! 层静电. (上限 !M!) NL Spark 爆炸时将引爆这些层数。
        if (DESCRIPTIONS.length >= 3) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.maxAmount + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
    }
}