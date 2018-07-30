package blackrusemod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import blackrusemod.BlackRuseMod;

public class ReturningBladePower extends AbstractPower {
	public static final String POWER_ID = "ReturningBladePower";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static TextureAtlas powerAltas = BlackRuseMod.getPowerTextureAtlas();
	private AbstractCreature source;
	private AbstractMonster target;
	private boolean prediction;
	private AbstractCard itself;
	private static int idOffset;
	
	public ReturningBladePower(AbstractCreature owner, AbstractCreature source, int amount, boolean prediction, AbstractCard c) {
		this.name = NAME;
		this.ID = ("ReturningBladePower" + idOffset);
		idOffset += 1;
		this.amount = amount;
		this.owner = owner;
		this.source = source;
		this.target = (AbstractMonster)this.source;
		this.amount = amount;
		this.prediction = prediction;
		this.itself = c;
		this.type = AbstractPower.PowerType.BUFF;
		updateDescription();
		this.region48 = powerAltas.findRegion("returning_blade48");
		this.region128 = powerAltas.findRegion("returning_blade128");
	}
	
	public void atStartOfTurnPostDraw() {
		if (!this.prediction && !(this.target.intent == AbstractMonster.Intent.ATTACK) && !(this.target.intent == AbstractMonster.Intent.ATTACK_BUFF) && !(this.target.intent == AbstractMonster.Intent.ATTACK_DEBUFF) && !(this.target.intent == AbstractMonster.Intent.ATTACK_DEFEND))
		{
			this.flash();
			AbstractDungeon.actionManager.addToBottom(new DamageAction(this.target, new DamageInfo(this.owner, this.amount, DamageType.NORMAL),
					AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(this.itself.makeStatEquivalentCopy(), false));
			if (AbstractDungeon.player.hasRelic("OldScarf")) AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
			if (this.owner.hasPower("TrueSightPower")) 
				for (int i = 0; i < this.owner.getPower("TrueSightPower").amount; i++)
				{
					AbstractDungeon.actionManager.addToBottom(new DamageAction(this.target, new DamageInfo(this.owner, this.amount, DamageType.NORMAL),
							AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
					AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(this.itself.makeStatEquivalentCopy(), false));
					if (AbstractDungeon.player.hasRelic("OldScarf")) AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
				}
		}
		else if (this.prediction && ((this.target.intent == AbstractMonster.Intent.ATTACK) || (this.target.intent == AbstractMonster.Intent.ATTACK_BUFF) || (this.target.intent == AbstractMonster.Intent.ATTACK_DEBUFF) || (this.target.intent == AbstractMonster.Intent.ATTACK_DEFEND)))
		{
			this.flash();
			AbstractDungeon.actionManager.addToBottom(new DamageAction(this.target, new DamageInfo(this.owner, this.amount, DamageType.NORMAL),
					AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(this.itself.makeStatEquivalentCopy(), false));
			if (AbstractDungeon.player.hasRelic("OldScarf")) AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
			if (this.owner.hasPower("TrueSightPower")) 
				for (int i = 0; i < this.owner.getPower("TrueSightPower").amount; i++)
				{
					AbstractDungeon.actionManager.addToBottom(new DamageAction(this.target, new DamageInfo(this.owner, this.amount, DamageType.NORMAL),
							AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
					AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(this.itself.makeStatEquivalentCopy(), false));
					if (AbstractDungeon.player.hasRelic("OldScarf")) AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
				}
		}
		AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 99));
	}

	public void updateDescription()
	{
		if (this.prediction) this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
		else this.description = (DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3]);
	}
}