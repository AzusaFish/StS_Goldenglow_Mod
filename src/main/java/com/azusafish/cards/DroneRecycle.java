package com.azusafish.cards;

import basemod.abstracts.CustomCard;
import com.azusafish.GGEnums;
import com.azusafish.orbs.SparkOrb;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.defect.RemoveNextOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class DroneRecycle extends CustomCard {
    public static final String ID = "AzusaFish:DroneRecycle";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "images/cards/DroneRecycle.png"; 

    private static final int COST = 0;

    public DroneRecycle() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, 
              CardType.SKILL, 
              GGEnums.GG_CARD_COLOR, 
              CardRarity.UNCOMMON, 
              CardTarget.SELF);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
         boolean canUse = super.canUse(p, m);
         if (!canUse) {
             return false;
         }
         
         // 检查是否有 Sphere (Orb)
         // 虽然描述是 Remove first Spark，但通常实现上就是移除第一个 Orb
         // 为了严格符合 "Spark"，可以加上类型判断，但通用实现通常是移除最前面的 Orb
         // 这里我们先检查有没有 Orb
         if (p.orbs.isEmpty() || p.maxOrbs <= 0) {
             this.cantUseMessage = "No Orbs to recycle!";
             return false;
         }
         
         // 至少要有一个非 EmptyOrb
         boolean hasOrb = false;
         for (AbstractOrb o : p.orbs) {
             if (!(o instanceof com.megacrit.cardcrawl.orbs.EmptyOrbSlot)) {
                 hasOrb = true;
                 break;
             }
         }
         
         if (!hasOrb) {
             this.cantUseMessage = "No Orbs to recycle!";
             return false;
         }

         return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Remove without Evoking (使用 RemoveNextOrbAction 即可，它就是直接移除)
        // 注意：RemoveNextOrbAction 移除的是 "Channel" 队列里的第一个 (即最旧的那个，或者说最前面的)
        // 在 Spire 里，RemoveNextOrbAction 实际上是触发 Evoke 还是直接移除？
        // 原版没有直接移除最前 Orb 的 Action (除了 EvokeOrbAction)。
        // 我们需要手动移除。
        
        if (!p.orbs.isEmpty()) {
            // 手动实现移除第一个非空 Orb
             AbstractOrb orbToRemove = null;
             // 查找第一个非空 Orb (从 0 开始是最新生成的吗？通常 filledOrbs 是按顺序的)
             // 按照 Spire 逻辑，Channel 会挤掉第 0 个 (如果有)，或者填入空位。
             // 玩家看到的“最前面”通常是指即将被 Evoke 的那个。
             // 我们可以使用 p.removeNextOrb() 方法，但这会触发 Evoke。
             // 如果要不触发 Evoke，直接操作 list。
             
             // 这里使用一个简单的 Action 来保证时序
             addToBot(new com.megacrit.cardcrawl.actions.AbstractGameAction() {
                 @Override
                 public void update() {
                     if (!p.orbs.isEmpty()) {
                         // 找到第一个非空 Orb
                         // 在 loop 中，orbs[0] 是最旧的 (即将被推出)。
                         if (!p.orbs.get(0).ID.equals("Empty")) {
                             p.orbs.set(0, new com.megacrit.cardcrawl.orbs.EmptyOrbSlot());
                             // 视觉更新：重新排列
                             for (int i = 0; i < p.orbs.size(); ++i) {
                                ((AbstractOrb)p.orbs.get(i)).setSlot(i, p.maxOrbs);
                             }
                         }
                     }
                     this.isDone = true;
                 }
             });
        }

        // Gain 2 Energy
        addToBot(new GainEnergyAction(2));
        
        // Upgrade logic: Draw 1 card
        if (upgraded) {
            addToBot(new DrawCardAction(p, 1));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
