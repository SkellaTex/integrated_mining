package net.skellatex.integrated_mining.registry;

import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.skellatex.integrated_mining.IntegratedMining;
import net.skellatex.integrated_mining.content.item.PalladiumUpgradeItem;


public class IMItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(IntegratedMining.MOD_ID);

    // Spelunkery Compat
    public static final DeferredItem<Item> ROUGH_SPINEL = ITEMS.register("rough_spinel", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ROUGH_SPINEL_SHARD = ITEMS.register("rough_spinel_shard", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SPINEL_SHARD = ITEMS.register("spinel_shard", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_TIN_NUGGET = ITEMS.register("raw_tin_nugget", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_PALLADIUM_NUGGET = ITEMS.register("raw_palladium_nugget", () -> new Item(new Item.Properties()));

    // Create Compat
    public static final DeferredItem<Item> CRUSHED_RAW_PALLADIUM = ITEMS.register("crushed_raw_palladium", () -> new Item(new Item.Properties()));

    // Galosphere
    public static final DeferredItem<Item> PALLADIUM_UPGRADE_SMITHING_TEMPLATE = ITEMS.register("palladium_upgrade_smithing_template", PalladiumUpgradeItem::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
