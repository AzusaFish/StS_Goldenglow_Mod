package com.azusafish.cards;

import basemod.abstracts.CustomCard;
import com.azusafish.GGEnums;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Defend extends CustomCard {
    public static final String ID = "AzusaFish:Defend";
    public static final String IMG_PATH = "images/cards/Defend.png"; // 暂时借用图片

    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    public Defend() {
        super(ID, "Defend", IMG_PATH, COST, "Gain !B! Block.", 
              CardType.SKILL, 
              GGEnums.GG_CARD_COLOR, // ✅ 绑定粉色
              CardRarity.COMMON,     // 暂时改为 COMMON 以防止奖励池死循环
              CardTarget.SELF);

        this.baseBlock = BLOCK;
        
        // 添加标签：这是初始防御
        this.tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}