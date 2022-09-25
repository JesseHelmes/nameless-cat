package com.example.nameless_cat;

import com.example.nameless_cat.init.EntityInit;
import com.example.nameless_cat.init.ItemInit;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(NamelessCatMod.MODID)
public class NamelessCatMod {
	public static final String MODID = "nameless_cat";
	// Directly reference a slf4j logger
	public static final Logger LOGGER = LogUtils.getLogger();

	// TODO add transparency, see NamelessCatRenederer

	// TODO add a way to show that the cat can be breed or not

	// TODO add structure spawn someday when there is more info about it because i don't know yet how to use it
	// it spawns once like the village cats
	// https://github.com/MinecraftForge/MinecraftForge/blob/1.19.x/src/generated_test/resources/data/structure_modifiers_test/forge/structure_modifier/modify_stronghold.json#L6

	// ACCEPT MY SOUL KITTY!!
	// nameless_cat, night and rain and lighting spawn soul kitties

	public NamelessCatMod() {
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		ItemInit.ITEMS.register(bus);
		EntityInit.ENTITIES.register(bus);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}
}
