package BattleTowers.monsters.chess.queen.powers;

import BattleTowers.BattleTowers;
import BattleTowers.monsters.chess.queen.vfx.FlexibleWrathParticleEffect;
import BattleTowers.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;

import static BattleTowers.BattleTowers.makeID;
import static BattleTowers.BattleTowers.makePowerPath;

public class FactionChange extends AbstractPower {
    public static final String POWER_ID = makeID(FactionChange.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public int currentStance = 0;
    private float particleTimer = 0f;

    public FactionChange(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.updateDescription();
        this.type = PowerType.BUFF;
        BattleTowers.LoadPowerImage(this);
    }

    public void onUseCard(AbstractCard c, UseCardAction action) {
        if (c.type == AbstractCard.CardType.ATTACK) {
            if(currentStance != 0){
                flash();
                Texture tex84 = TextureLoader.getTexture(makePowerPath("Black" + "84.png"));
                Texture tex32 = TextureLoader.getTexture(makePowerPath("Black" + "32.png"));
                this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
                this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
                updateDescription();
                currentStance = 0;
                AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.BLACK, true));
            }
        }
        else {
            if (c.type == AbstractCard.CardType.SKILL || c.type == AbstractCard.CardType.POWER) {
                if(currentStance != 1){
                    flash();
                    Texture tex84 = TextureLoader.getTexture(makePowerPath("White" + "84.png"));
                    Texture tex32 = TextureLoader.getTexture(makePowerPath("White" + "32.png"));
                    this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
                    this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
                    updateDescription();
                    currentStance = 1;
                    AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.WHITE, true));
                }
            }
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[currentStance];
    }

    @Override
    public void updateParticles() {
        super.updateParticles();
        this.particleTimer -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer < 0.0F) {
            this.particleTimer = 0.04F;
            if(currentStance == 0){AbstractDungeon.effectsQueue.add(new FlexibleWrathParticleEffect(owner, Color.BLACK.cpy()));}
            else {AbstractDungeon.effectsQueue.add(new FlexibleWrathParticleEffect(owner, Color.WHITE.cpy()));}
        }
    }
}
