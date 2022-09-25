package com.example.nameless_cat.events;

import com.example.nameless_cat.NamelessCatMod;
import com.example.nameless_cat.entity.NamelessCatEntity;
import com.example.nameless_cat.init.EntityInit;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = NamelessCatMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCommonEvents {
	@SubscribeEvent
	public static void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			SpawnPlacements.register(EntityInit.NAMELESS_CAT.get(), SpawnPlacements.Type.ON_GROUND,
					Heightmap.Types.WORLD_SURFACE, NamelessCatEntity::canSpawn);
		});
	}

	@SubscribeEvent
	public static void entityAttributes(EntityAttributeCreationEvent event) {
		event.put(EntityInit.NAMELESS_CAT.get(), NamelessCatEntity.getEntityAttributes().build());
	}
}
