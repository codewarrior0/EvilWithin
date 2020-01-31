package guardian.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import guardian.GuardianMod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


public class RandomProtocolAction extends AbstractGameAction {
    public boolean upgradeCard;

    public RandomProtocolAction(boolean upgraded) {
        this.upgradeCard = upgraded;


    }


    public void update() {

        ArrayList<String> tmp = new ArrayList();
        Iterator var3 = CardLibrary.cards.entrySet().iterator();

        while (var3.hasNext()) {
            Map.Entry<String, AbstractCard> c = (Map.Entry) var3.next();
            if (c.getValue().hasTag(GuardianMod.PROTOCOL)) {
                tmp.add(c.getKey());
            }
        }


        AbstractCard cStudy = CardLibrary.cards.get(tmp.get(AbstractDungeon.cardRng.random(0, tmp.size() - 1)));
        if (this.upgradeCard) {
            cStudy.upgrade();
        }
        cStudy.modifyCostForCombat(-9);

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(cStudy));

        this.isDone = true;
    }

}



