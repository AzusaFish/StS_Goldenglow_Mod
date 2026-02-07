package com.azusafish;

import basemod.BaseMod;
import basemod.helpers.RelicType;
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
import com.megacrit.cardcrawl.localization.OrbStrings;

import basemod.interfaces.EditRelicsSubscriber;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.azusafish.relics.Sparkles;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public class GoldenglowMod implements EditCharactersSubscriber, EditCardsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, EditRelicsSubscriber {

    public static final Color PINK_COLOR = new Color(1.0f, 0.46f, 0.66f, 1.0f);

    private static final String ATTACK_BG_512 = "images/512/bg_attack_pink.png";
    private static final String SKILL_BG_512 = "images/512/bg_skill_pink.png";
    private static final String POWER_BG_512 = "images/512/bg_power_pink.png";
    private static final String ORB_512 = "images/512/orb_small.png";
    
    private static final String ATTACK_BG_1024 = "images/1024/bg_attack_pink.png";
    private static final String SKILL_BG_1024 = "images/1024/bg_skill_pink.png";
    private static final String POWER_BG_1024 = "images/1024/bg_power_pink.png";
    private static final String ORB_1024 = "images/1024/orb_large.png";
    private static final String ORB_TEXT = "images/1024/orb_text.png";

    private static final String CHAR_BUTTON = "images/char/goldenglow/button.png";
    private static final String CHAR_PORTRAIT = "images/char/goldenglow/portrait.png";

    public GoldenglowMod() {
        BaseMod.subscribe(this);
        BaseMod.addColor(GGEnums.GG_CARD_COLOR, PINK_COLOR, PINK_COLOR, PINK_COLOR,
                PINK_COLOR, PINK_COLOR, PINK_COLOR, PINK_COLOR,
                ATTACK_BG_512, SKILL_BG_512, POWER_BG_512, ORB_512,
                ATTACK_BG_1024, SKILL_BG_1024, POWER_BG_1024, ORB_1024, ORB_TEXT);
    }

    public static void initialize() {
        new GoldenglowMod();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new GoldenglowCharacter("Goldenglow"),
                CHAR_BUTTON,
                CHAR_PORTRAIT,
                GGEnums.GOLDENGLOW);
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addCard(new com.azusafish.cards.Strike());
        BaseMod.addCard(new com.azusafish.cards.Defend());
        BaseMod.addCard(new StaticRelease());
    }
    
    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, "localization/eng/CardStrings.json");
        BaseMod.loadCustomStringsFile(OrbStrings.class, "localization/eng/OrbStrings.json");
        BaseMod.loadCustomStringsFile(com.megacrit.cardcrawl.localization.PowerStrings.class, "localization/eng/PowerStrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "localization/eng/RelicStrings.json");
    }

    @Override
    public void receiveEditKeywords() {
        // 1. 打印：证明方法被调用了
        System.out.println("========= GGLMOD: KEYWORD METHOD CALLED =========");
        
        Gson gson = new Gson();
        String path = "localization/eng/KeywordStrings.json";
        
        try {
            // 2. 打印：尝试读取文件
            String json = Gdx.files.internal(path).readString(String.valueOf(StandardCharsets.UTF_8));
            System.out.println("GGLMOD: JSON Content read: " + json); // 看看读到了什么

            Keyword[] keywords = gson.fromJson(json, Keyword[].class);

            if (keywords != null) {
                for (Keyword keyword : keywords) {
                    System.out.println("GGLMOD: Registering Keyword -> " + keyword.PROPER_NAME);
                    // 使用 null 作为 modId 以注册全局关键字，确保 "Spark" 能被正确识别
                    BaseMod.addKeyword(null, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                }
            } else {
                System.out.println("GGLMOD: Keywords array is NULL!");
            }
        } catch (Exception e) {
            // 3. 打印：如果有报错，直接打印出来
            System.out.println("GGLMOD: ERROR loading keywords!");
            e.printStackTrace();
        }
    }

    public static class Keyword {
        public String PROPER_NAME;
        public String[] NAMES;
        public String DESCRIPTION;
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new Sparkles(), GGEnums.GG_CARD_COLOR);
    }
}