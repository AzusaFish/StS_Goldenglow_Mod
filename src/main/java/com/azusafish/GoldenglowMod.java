package com.azusafish;

import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;

import com.azusafish.cards.StaticRelease;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public class GoldenglowMod implements EditCharactersSubscriber, EditCardsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber {

    // 定义澄闪的粉色：R, G, B, A
    public static final Color PINK_COLOR = new Color(1.0f, 0.46f, 0.66f, 1.0f);

    // 资源路径常量
    private static final String ATTACK_BG_512 = "images/512/bg_attack_pink.png";
    private static final String SKILL_BG_512 = "images/512/bg_skill_pink.png";
    private static final String POWER_BG_512 = "images/512/bg_power_pink.png";
    private static final String ORB_512 = "images/512/orb_small.png";
    
    private static final String ATTACK_BG_1024 = "images/1024/bg_attack_pink.png";
    private static final String SKILL_BG_1024 = "images/1024/bg_skill_pink.png";
    private static final String POWER_BG_1024 = "images/1024/bg_power_pink.png";
    private static final String ORB_1024 = "images/1024/orb_large.png";
    private static final String ORB_TEXT = "images/1024/orb_text.png";

    // 角色选人界面图片
    private static final String CHAR_BUTTON = "images/char/goldenglow/button.png";
    private static final String CHAR_PORTRAIT = "images/char/goldenglow/portrait.png";

    // 构造函数
    public GoldenglowMod() {
        BaseMod.subscribe(this);

        // 1. 注册颜色
        BaseMod.addColor(GGEnums.GG_CARD_COLOR, PINK_COLOR, PINK_COLOR, PINK_COLOR,
                PINK_COLOR, PINK_COLOR, PINK_COLOR, PINK_COLOR,
                ATTACK_BG_512, SKILL_BG_512, POWER_BG_512, ORB_512,
                ATTACK_BG_1024, SKILL_BG_1024, POWER_BG_1024, ORB_1024, ORB_TEXT);
    }

    // 初始化入口
    public static void initialize() {
        new GoldenglowMod();
    }

    // 2. 注册角色
    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new GoldenglowCharacter("Goldenglow"),
                CHAR_BUTTON,
                CHAR_PORTRAIT,
                GGEnums.GOLDENGLOW);
    }

    // 3. 注册卡牌
    @Override
    public void receiveEditCards() {
        BaseMod.addCard(new com.azusafish.cards.Strike());
        BaseMod.addCard(new com.azusafish.cards.Defend());
        BaseMod.addCard(new StaticRelease());
    }
    
    // 4. 加载语言文本 (JSON)
    @Override
    public void receiveEditStrings() {
        // 加载卡牌和Orb文本
        BaseMod.loadCustomStringsFile(CardStrings.class, "localization/eng/CardStrings.json");
        BaseMod.loadCustomStringsFile(com.megacrit.cardcrawl.localization.OrbStrings.class, "localization/eng/OrbStrings.json");
    }

    // 5. 加载关键字 (Keywords)
    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal("localization/eng/KeywordStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        
        // 使用下面自定义的 Keyword 类来解析，不再依赖 StSLib
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                // 注册关键字
                BaseMod.addKeyword("azusafish", keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }
    
    static class Keyword {
        public String PROPER_NAME;
        public String[] NAMES;
        public String DESCRIPTION;
    }
}