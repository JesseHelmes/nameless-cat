package com.example.nameless_cat.entity;

import java.util.UUID;

import javax.annotation.Nullable;

import com.example.nameless_cat.NamelessCatMod;
import com.example.nameless_cat.init.EntityInit;
import com.example.nameless_cat.entity.goal.CatTemptGoal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.fluids.FluidType;

public class NamelessCatEntity extends Cat implements NeutralMob {
	// this adds extra food items and does not override its parent
	public static final Ingredient TEMPT_INGREDIENT = Ingredient.of(Items.TROPICAL_FISH, Items.COOKED_COD, Items.COOKED_SALMON);
	private static final EntityDataAccessor<Boolean> IS_NATURAL_SPAWNED = SynchedEntityData.defineId(NamelessCatEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_SOUL_BABY = SynchedEntityData.defineId(NamelessCatEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(NamelessCatEntity.class, EntityDataSerializers.INT);

	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	@Nullable
	private UUID persistentAngerTarget;
	@Nullable
	private TemptGoal temptGoal;

	public NamelessCatEntity(EntityType<? extends Cat> entity, Level level) {
		super(entity, level);
	}

	protected void registerGoals() {
		super.registerGoals();
		this.temptGoal = new CatTemptGoal(this, 0.6D, TEMPT_INGREDIENT, true);
		this.goalSelector.addGoal(3, this.temptGoal);
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
	}

	public static AttributeSupplier.Builder getEntityAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 25.0D)
				// MOVEMENT_SPEED 0D causing entity not move at all, not even falling, or.. it was because i was missing super.aiStep()
				.add(Attributes.MOVEMENT_SPEED, (double)0.3F)
				.add(Attributes.ATTACK_DAMAGE, 3.0D);
	}

	public static boolean canSpawn(EntityType<NamelessCatEntity> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return checkAnimalSpawnRules(entityType, level, spawnType, pos, random);
	}

	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_29533_, DifficultyInstance difficulty, MobSpawnType type, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
		if (spawnGroupData == null) {
			// make her able to spawn as a child
			spawnGroupData = new AgeableMob.AgeableMobGroupData(0.2F);//0.2
		}

		return super.finalizeSpawn(p_29533_, difficulty, type, spawnGroupData, tag);
	}

	public void aiStep() {
		super.aiStep();

		if(!this.getCombatTracker().isInCombat())
			if (this.getHealth() < this.getMaxHealth() && this.tickCount % 50 == 0)
				this.heal(0.2F);

//		if(this.tickCount % 87 == 0)
//			NamelessCatMod.LOGGER.info("natural spawned = " +this.blockPosition() + ", " + this.position());

		if(this.tickCount % 87 == 0)
			this.level.addParticle(ParticleTypes.GLOW, this.getRandomX(0.3D), this.getRandomY(), this.getRandomZ(0.3D), 0.0D, 0.0D, 0.0D);
		if(this.tickCount % 56 == 0) // i wanted to see how it looks like with SOUL
			this.level.addParticle(ParticleTypes.GLOW, this.getRandomX(0.3D), this.getRandomY(), this.getRandomZ(0.3D), 0.0D, 0.0D, 0.0D);

		if (!this.level.isClientSide)
			this.updatePersistentAnger((ServerLevel)this.level, true);

		// a way to show that this cat can't be breed (i will maybe come up with something else in the futures)
		if(this.tickCount % 200 == 0 && !this.isNaturalSpawned()) {
			this.level.addParticle(ParticleTypes.ELECTRIC_SPARK, this.getRandomX(0.3D), this.getRandomY(), this.getRandomZ(0.3D), 0.0D, 0.0D, 0.0D);
			this.level.addParticle(ParticleTypes.ELECTRIC_SPARK, this.getRandomX(0.3D), this.getRandomY(), this.getRandomZ(0.3D), 0.0D, 0.0D, 0.0D);
		}
	}

	public void tick() {
		super.tick();
		if (this.temptGoal != null && this.temptGoal.isRunning() && !this.isTame() && this.tickCount % 100 == 0)
			this.playSound(SoundEvents.CAT_BEG_FOR_FOOD, 1.0F, 1.0F);

		if(!this.getCombatTracker().isInCombat() &&
			(this.getHealth() / this.getMaxHealth()) <= 50 &&
			this.tickCount % 100 == 0)
			this.playSound(SoundEvents.CAT_BEG_FOR_FOOD, 1.0F, 1.0F);
	}

	public boolean canSpawnSoulSpeedParticle() { 
		return this.tickCount % 40 == 0 && this.getDeltaMovement().x != 0.0D && this.getDeltaMovement().z != 0.0D && !this.isSpectator() && this.onSoulSpeedBlock();
	}

	public NamelessCatEntity getBreedOffspring(ServerLevel level, AgeableMob mob) {
		NamelessCatEntity cat = EntityInit.NAMELESS_CAT.get().create(level);
		if (mob instanceof NamelessCatEntity) {
			if (this.isTame()) {
				cat.setOwnerUUID(this.getOwnerUUID());
				cat.setTame(true);
				if (this.random.nextBoolean()) {
					cat.setCollarColor(this.getCollarColor());
				} else {
					cat.setCollarColor(((NamelessCatEntity)mob).getCollarColor());
				}
			}
		}
		return cat;
	}

	public boolean canMate(Animal animal) {
		if (!(animal instanceof NamelessCatEntity)) return false;
		if(!this.isNaturalSpawned()) return false;
		NamelessCatEntity cat = (NamelessCatEntity)animal;
		if(!cat.isNaturalSpawned()) return false;
		return super.canMate(animal);
	}

	public boolean isBaby() {
		if(this.isSoulBaby()) return true;
		return super.isBaby();
	}

	public boolean isFood(ItemStack stack) {
		return TEMPT_INGREDIENT.test(stack) || super.isFood(stack);
	}

	public boolean canDrownInFluidType(FluidType type) {
		return false;
	}

	public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
		if (!(target instanceof Ghast)) {
			if (target instanceof NamelessCatEntity) {
				NamelessCatEntity cat = (NamelessCatEntity)target;
				return !cat.isTame() || cat.getOwner() != owner;
			} else if (target instanceof Player && owner instanceof Player && !((Player)owner).canHarmPlayer((Player)target)) {
				return false;
			} else if (target instanceof AbstractHorse && ((AbstractHorse)target).isTamed()) {
				return false;
			} else {
				return !(target instanceof TamableAnimal) || !((TamableAnimal)target).isTame();
			}
		} else {
			return false;
		}
	}

	public void sacrificeCat(ServerLevel level, Cat cat) {
		this.setNaturalSpawned(false);
		this.setSoulBaby(cat.isBaby());

		// clones everything from cat so it will look like nothing happened
		this.setBaby(cat.isBaby());
		this.copyPosition(cat);
		this.yHeadRot = cat.yHeadRot;
		this.yHeadRotO = cat.yHeadRotO;
		this.yBodyRot = cat.yBodyRot;
		this.yBodyRotO = cat.yBodyRotO;
		this.setInSittingPose(cat.isInSittingPose());
		this.setPose(cat.getPose());
		this.setLying(cat.isLying());
		if(cat.isTame()) {
			this.setOrderedToSit(cat.isOrderedToSit());
			this.setOwnerUUID(cat.getOwnerUUID());
			this.setTame(cat.isTame());
		}
		this.setCollarColor(cat.getCollarColor());
		this.setTarget(cat.getTarget());

		level.addFreshEntity(this);
		cat.discard();
	}

	// NETWORK AND DATA ENTITY NEEDS TO SAVE

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(IS_NATURAL_SPAWNED, true);
		this.entityData.define(IS_SOUL_BABY, false);
		this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
	}

	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("isNaturalSpawned", this.isNaturalSpawned());
		tag.putBoolean("isSoulBaby", this.isSoulBaby());
		this.addPersistentAngerSaveData(tag);
	}

	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setNaturalSpawned(tag.getBoolean("isNaturalSpawned"));
		this.setSoulBaby(tag.getBoolean("isSoulBaby"));
		this.readPersistentAngerSaveData(this.level, tag);
	}

	public void setNaturalSpawned(boolean isNaturalSpawned) {
		this.entityData.set(IS_NATURAL_SPAWNED, isNaturalSpawned);
	}

	public boolean isNaturalSpawned() {
		return this.entityData.get(IS_NATURAL_SPAWNED);
	}

	public void setSoulBaby(boolean isSoulBaby) {
		this.entityData.set(IS_SOUL_BABY, isSoulBaby);
	}

	public boolean isSoulBaby() {
		return this.entityData.get(IS_SOUL_BABY);
	}

	public int getRemainingPersistentAngerTime() {
		return this.entityData.get(DATA_REMAINING_ANGER_TIME);
	}

	public void setRemainingPersistentAngerTime(int time) {
		this.entityData.set(DATA_REMAINING_ANGER_TIME, time);
	}

	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	@Nullable
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	public void setPersistentAngerTarget(UUID target) {
		this.persistentAngerTarget = target;
	}
}