package com.example.nameless_cat.init;

import com.example.nameless_cat.NamelessCatMod;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.common.ForgeSpawnEggItem;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NamelessCatMod.MODID);

	public static final RegistryObject<ForgeSpawnEggItem> NAMELESS_CAT_SPAWN_EGG = ITEMS.register("nameless_cat_spawn_egg",
			() -> new ForgeSpawnEggItem(EntityInit.NAMELESS_CAT, 0x2A404B, 0x6CEDDB,
					new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(16)));
}
