package BattleTowers.monsters.chess.queen.customintents;

import BattleTowers.RazIntent.CustomIntent;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static BattleTowers.BattleTowers.makeID;
import static BattleTowers.BattleTowers.makeUIPath;

public class QueenMarchIntent extends CustomIntent {

    public static final String ID = makeID("MassAttackIntent");

    private static final UIStrings uiStrings;
    private static final String[] TEXT;


    public QueenMarchIntent() {
        super(IntentEnums.QUEEN_MARCH, TEXT[0],
                makeUIPath("areaIntent_L.png"),
                makeUIPath("areaIntent.png"));
    }

    @Override
    public String description(AbstractMonster mo) {
        String result = TEXT[1];
        result += mo.getIntentDmg();
        result += TEXT[2];
        int hitCount;
        if ((Boolean) ReflectionHacks.getPrivate(mo, AbstractMonster.class, "isMultiDmg")) {
            hitCount = (Integer) ReflectionHacks.getPrivate(mo, AbstractMonster.class, "intentMultiAmt");
            result += " #b" + hitCount + TEXT[4];
        } else {
            result += TEXT[3];
        }

        return result;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = uiStrings.TEXT;
    }
}