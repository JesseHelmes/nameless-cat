package com.example.nameless_cat.events;

import com.example.nameless_cat.NamelessCatMod;
import com.example.nameless_cat.client.renderer.NamelessCatEntityRenderer;
import com.example.nameless_cat.init.EntityInit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NamelessCatMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {

	@SubscribeEvent
	public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(EntityInit.NAMELESS_CAT.get(), NamelessCatEntityRenderer::new);
	}
}
