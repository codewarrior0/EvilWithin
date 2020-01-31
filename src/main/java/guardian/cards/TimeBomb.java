package guardian.cards;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.actions.PlaceActualCardIntoStasis;
import guardian.patches.AbstractCardEnum;

public class TimeBomb extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("TimeBomb");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/explodeProtocol.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 2;

    //TUNING CONSTANTS
    private static final int DAMAGE = 20;
    private static final int UPGRADE_DAMAGE = 10;
    private static final int TURNS = 3;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;
    public static String UPGRADED_DESCRIPTION;

    //END TUNING CONSTANTS

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public TimeBomb() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseMagicNumber = this.magicNumber = DAMAGE;
        this.tags.add(GuardianMod.VOLATILE);
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        AbstractDungeon.actionManager.addToBottom(new PlaceActualCardIntoStasis(this));
    }

    public AbstractCard makeCopy() {
        return new TimeBomb();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_DAMAGE);
        }
    }

    public void updateDescription() {

        if (this.socketCount > 0) {
            if (upgraded && UPGRADED_DESCRIPTION != null) {
                this.rawDescription = this.updateGemDescription(UPGRADED_DESCRIPTION, true);
            } else {
                this.rawDescription = this.updateGemDescription(DESCRIPTION, true);
            }
        }
        this.initializeDescription();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean result;
        if (GuardianMod.canSpawnStasisOrb()) {
            result = true;
        } else {
            this.cantUseMessage = EXTENDED_DESCRIPTION[0];
            result = false;
        }
        return result;
    }
}


