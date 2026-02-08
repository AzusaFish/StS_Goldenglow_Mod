package com.azusafish;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
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

    // 动画相关
    private Texture[] animationFrames;
    private float animationTimer = 0.0f;
    private float frameDuration = 1.0f / 20.0f; // 20 FPS

    public GoldenglowCharacter(String name) {
        super(name, GGEnums.GOLDENGLOW, null, null, null, (String)null);
        
        initializeClass(MY_CHARACTER_SHOULDER_2,
                MY_CHARACTER_SHOULDER_2,
                MY_CHARACTER_SHOULDER_1,
                MY_CHARACTER_CORPSE,
                getLoadout(),
                0.0F, 0.0F, 220.0F, 290.0F,
                new EnergyManager(3)); // 初始 3 费

        // 加载呼吸动画帧
        this.animationFrames = new Texture[159];
        for (int i = 0; i < 159; i++) {
            // 文件名为 ezgif-frame-001.png 到 ezgif-frame-159.png
            String path = "images/char/goldenglow/video/ezgif-frame-" + String.format("%03d", i + 1) + ".png";
            this.animationFrames[i] = ImageMaster.loadImage(path);
        }
    }
    
    @Override
    public void update() {
        super.update();
        // 更新呼吸动画
        if (this.animationFrames != null && this.animationFrames.length > 0) {
            this.animationTimer += Gdx.graphics.getDeltaTime();
            int frameIndex = (int) (this.animationTimer / this.frameDuration) % this.animationFrames.length;
            this.img = this.animationFrames[frameIndex];
        }
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
        
        retVal.add("AzusaFish:StaticRelease"); 
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