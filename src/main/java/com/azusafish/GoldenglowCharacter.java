package com.azusafish;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

public class GoldenglowCharacter extends CustomPlayer {
    
    // 角色素材路径
    // 确保这几张图是小的 (300px左右)，并且背景透明
    private static final String MY_CHARACTER_SHOULDER_1 = "images/char/goldenglow/shoulder.png";
    private static final String MY_CHARACTER_SHOULDER_2 = "images/char/goldenglow/shoulder.png";
    private static final String MY_CHARACTER_CORPSE = "images/char/goldenglow/corpse.png"; 
    
    // 选人界面的大立绘 (1920x1200)
    private static final String MY_CHARACTER_PORTRAIT = "images/char/goldenglow/portrait.png";

    public GoldenglowCharacter(String name) {
        super(name, GGEnums.GOLDENGLOW, null, null, null, (String)null);
        
        initializeClass(MY_CHARACTER_SHOULDER_2, 
                MY_CHARACTER_SHOULDER_2,
                MY_CHARACTER_SHOULDER_1,
                MY_CHARACTER_CORPSE,
                getLoadout(), 
                0.0F, 0.0F, 220.0F, 290.0F, 
                new EnergyManager(3)); // 初始 3 费
    }

    // 角色面板信息
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                "Goldenglow",
                "A Caster from Rhodes Island.",
                75, 75, 3, 99, 5,
                this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        
        retVal.add("AzusaFish:StaticRelease"); // 你的静电释放
        retVal.add("AzusaFish:StaticRelease"); 
        
        retVal.add("AzusaFish:Strike");
        retVal.add("AzusaFish:Strike");
        retVal.add("AzusaFish:Strike");
        retVal.add("AzusaFish:Strike");
        
        retVal.add("AzusaFish:Defend");
        retVal.add("AzusaFish:Defend");
        retVal.add("AzusaFish:Defend");
        retVal.add("AzusaFish:Defend");
        
        return retVal;
    }

    // 初始遗物
    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        // 暂时使用铁甲战士的燃烧之血
        retVal.add("AzusaFish:Sparkles");
        return retVal;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return GGEnums.GG_CARD_COLOR;
    }

    @Override
    public Color getCardRenderColor() { return GoldenglowMod.PINK_COLOR; }
    
    @Override
    public AbstractCard getStartCardForEvent() { return new com.azusafish.cards.StaticRelease(); }
    
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) { return "Goldenglow"; }
    
    @Override
    public Color getCardTrailColor() { return GoldenglowMod.PINK_COLOR; }
    
    @Override
    public int getAscensionMaxHPLoss() { return 4; }
    
    @Override
    public BitmapFont getEnergyNumFont() { return FontHelper.energyNumFontRed; }
    
    @Override
    public void doCharSelectScreenSelectEffect() { 
        com.megacrit.cardcrawl.core.CardCrawlGame.sound.playA("ATTACK_MAGIC_BEAM_SHORT", 1.0f); 
    }
    
    @Override
    public String getCustomModeCharacterButtonSoundKey() { return "ATTACK_MAGIC_BEAM_SHORT"; }
    
    @Override
    public String getLocalizedCharacterName() { return "Goldenglow"; }
    
    @Override
    public AbstractPlayer newInstance() { return new GoldenglowCharacter(this.name); }
    
    @Override
    public String getSpireHeartText() { return "Start Operation!"; }
    
    @Override
    public Color getSlashAttackColor() { return GoldenglowMod.PINK_COLOR; }
    
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{ AbstractGameAction.AttackEffect.LIGHTNING };
    }
    
    @Override
    public String getVampireText() { return "Static electricity protection engaged..."; }
}