package com.example.nameless_cat.events;

import com.example.nameless_cat.NamelessCatMod;
import com.example.nameless_cat.entity.NamelessCatEntity;
import com.example.nameless_cat.init.EntityInit;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Cat;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NamelessCatMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeCommonEvents {

	@SubscribeEvent
	public static void catStruckByLightning(EntityStruckByLightningEvent event) {
		if (!(event.getEntity() instanceof Cat) ||
				event.getEntity() instanceof NamelessCatEntity) {
			return;
		}

		Cat cat = (Cat) event.getEntity();
		if (!(cat.level instanceof ServerLevel)) return;

		EntityInit.NAMELESS_CAT.get().create(cat.level)
			.sacrificeCat((ServerLevel) cat.level, cat);
	}
}