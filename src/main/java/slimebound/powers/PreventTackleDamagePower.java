package slimebound.powers;


import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;


public class PreventTackleDamagePower extends AbstractPower {
    public static final String POWER_ID = "Slimebound:PreventTackleDamagePower";
    public static final String NAME = "Potency";
    public static final String IMG = "powers/tackleProtection.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;
    private AbstractCreature source;


    public PreventTackleDamagePower(AbstractCreature owner, AbstractCreature source, int amount) {

        this.name = NAME;

        this.ID = POWER_ID;


        this.owner = owner;

        this.source = source;


        this.img = new com.badlogic.gdx.graphics.Texture(SlimeboundMod.getResourcePath(IMG));

        this.type = POWER_TYPE;

        this.amount = amount;
        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(SlimeboundMod.TACKLE)) {
            flash();
            if (this.amount == 0) {// 46
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));// 47
            } else {
                this.addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));// 49
            }
        }
    }
}




