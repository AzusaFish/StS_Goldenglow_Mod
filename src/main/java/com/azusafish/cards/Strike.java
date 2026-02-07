package com.azusafish.cards;

import basemod.abstracts.CustomCard;
import com.azusafish.GGEnums;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Strike extends CustomCard {
    public static final String ID = "AzusaFish:Strike";
    // 暂时用静电释放的图，或者你可以再复制一份改名叫 Strike.png
    public static final String IMG_PATH = "images/cards/Strike.png"; 

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 3;

    public Strike() {
        super(ID, "Strike", IMG_PATH, COST, "Deal !D! damage.", 
              CardType.ATTACK, 
              GGEnums.GG_CARD_COLOR, // ✅ 重点：绑定粉色
              CardRarity.BASIC,      // ✅ 稀有度：基础
              CardTarget.ENEMY);

        this.baseDamage = DAMAGE;
        
        // 添加标签：这是打击牌，也是初始打击
        this.tags.add(CardTags.STRIKE);
        this.tags.add(CardTags.STARTER_STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}