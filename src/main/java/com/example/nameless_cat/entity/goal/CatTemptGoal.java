package com.example.nameless_cat.entity.goal;

import javax.annotation.Nullable;

import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;

public class CatTemptGoal extends TemptGoal {
	@Nullable
	private Player selectedPlayer;
	private final Cat cat;

	public CatTemptGoal(Cat p_28219_, double p_28220_, Ingredient p_28221_, boolean p_28222_) {
		super(p_28219_, p_28220_, p_28221_, p_28222_);
		this.cat = p_28219_;
	}

	public void tick() {
		super.tick();
		if (this.selectedPlayer == null && this.mob.getRandom().nextInt(this.adjustedTickDelay(600)) == 0) {
			this.selectedPlayer = this.player;
		} else if (this.mob.getRandom().nextInt(this.adjustedTickDelay(500)) == 0) {
			this.selectedPlayer = null;
		}
	}

	protected boolean canScare() {
		return this.selectedPlayer != null && this.selectedPlayer.equals(this.player) ? false : super.canScare();
	}

	public boolean canUse() {
		return super.canUse() && !this.cat.isTame();
	}
}