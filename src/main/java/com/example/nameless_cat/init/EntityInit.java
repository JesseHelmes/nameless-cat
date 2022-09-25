package com.example.nameless_cat.init;

import com.example.nameless_cat.NamelessCatMod;
import com.example.nameless_cat.entity.NamelessCatEntity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
			NamelessCatMod.MODID);

	public static final RegistryObject<EntityType<NamelessCatEntity>> NAMELESS_CAT = ENTITIES.register("nameless_cat",
			() -> EntityType.Builder.of(NamelessCatEntity::new, MobCategory.CREATURE).sized(0.6F, 0.7F).clientTrackingRange(8)
			.immuneTo(Blocks.SWEET_BERRY_BUSH, Blocks.WITHER_ROSE, Blocks.CACTUS).fireImmune().build(NamelessCatMod.MODID + ":nameless_cat"));
}
