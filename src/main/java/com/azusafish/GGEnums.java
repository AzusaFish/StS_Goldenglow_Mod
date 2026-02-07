package com.azusafish;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary; 

public class GGEnums {
    // 1. 角色 ID
    @SpireEnum
    public static AbstractPlayer.PlayerClass GOLDENGLOW;

    // 2. 卡牌颜色 ID
    @SpireEnum(name = "GG_PINK") 
    public static AbstractCard.CardColor GG_CARD_COLOR;
    
    // 3. 图鉴分类 ID (就是它导致了崩溃！必须和颜色名字一样)
    @SpireEnum(name = "GG_PINK")
    public static CardLibrary.LibraryType GG_LIBRARY_COLOR;
    
    // 4. 卡牌标签 (这个其实暂时不需要，可以删掉，也可以留着)
    @SpireEnum(name = "GG_PINK") 
    public static AbstractCard.CardTags GG_CARD_TAG;
}