package net.skellatex.integrated_mining.registry;

import java.util.function.Supplier;

import com.simibubi.create.AllCreativeModeTabs;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.orcinus.galosphere.init.GItems;
import net.skellatex.integrated_mining.IMConfig;
import net.skellatex.integrated_mining.IntegratedMining;

import static net.skellatex.integrated_mining.compat.ModCompat.*;

@EventBusSubscriber(modid = IntegratedMining.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class IMTabs {

    private static final ResourceLocation GS_TAB = ResourceLocation.fromNamespaceAndPath(GALOSPHERE_ID, GALOSPHERE_ID);

    @SubscribeEvent
    public static void buildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tab = event.getTabKey();

        if (tab == CreativeModeTabs.NATURAL_BLOCKS) {
            if (ModList.get().isLoaded(CAVERNS_AND_CHASMS_ID) && ModList.get().isLoaded(SPELUNKERY_ID)) {
                putBefore(event, Blocks.RAW_GOLD_BLOCK, IMBlocks.ROUGH_SPINEL_BLOCK);
                putAfter(event, Blocks.DEEPSLATE_DIAMOND_ORE, IMBlocks.ANDESITE_SPINEL_ORE);
                putAfter(event, IMBlocks.ANDESITE_SPINEL_ORE.get(), IMBlocks.DIORITE_SPINEL_ORE);
                putAfter(event, IMBlocks.DIORITE_SPINEL_ORE.get(), IMBlocks.GRANITE_SPINEL_ORE);
                putAfter(event, IMBlocks.GRANITE_SPINEL_ORE.get(), IMBlocks.TUFF_SPINEL_ORE);
                putAfter(event, IMBlocks.TUFF_SPINEL_ORE.get(), IMBlocks.ANDESITE_TIN_ORE);
                putAfter(event, IMBlocks.ANDESITE_TIN_ORE.get(), IMBlocks.DIORITE_TIN_ORE);
                putAfter(event, IMBlocks.DIORITE_TIN_ORE.get(), IMBlocks.GRANITE_TIN_ORE);
                putAfter(event, IMBlocks.GRANITE_TIN_ORE.get(), IMBlocks.TUFF_TIN_ORE);
            }
            if (ModList.get().isLoaded(GALOSPHERE_ID) && ModList.get().isLoaded(SPELUNKERY_ID)) {
                putAfter(event, Blocks.DEEPSLATE_DIAMOND_ORE, IMBlocks.ANDESITE_PALLADIUM_ORE);
                putAfter(event, IMBlocks.ANDESITE_PALLADIUM_ORE.get(), IMBlocks.DIORITE_PALLADIUM_ORE);
                putAfter(event, IMBlocks.DIORITE_PALLADIUM_ORE.get(), IMBlocks.GRANITE_PALLADIUM_ORE);
                putAfter(event, IMBlocks.GRANITE_PALLADIUM_ORE.get(), IMBlocks.TUFF_PALLADIUM_ORE);
            }
        }

        if (tab == CreativeModeTabs.INGREDIENTS) {
            if (ModList.get().isLoaded(CAVERNS_AND_CHASMS_ID) && ModList.get().isLoaded(SPELUNKERY_ID)) {
                putBefore(event, Items.COAL, IMItems.ROUGH_SPINEL_SHARD);
                putAfter(event, IMItems.ROUGH_SPINEL_SHARD.get(), IMItems.RAW_TIN_NUGGET);
                putAfter(event, Items.GOLD_NUGGET, IMItems.SPINEL_SHARD);
                putAfter(event, Items.RAW_GOLD, IMItems.ROUGH_SPINEL);
            }
            if (ModList.get().isLoaded(GALOSPHERE_ID) && ModList.get().isLoaded(SPELUNKERY_ID)) {
                putBefore(event, Items.COAL, IMItems.RAW_PALLADIUM_NUGGET);
            }
        }
        if (ModList.get().isLoaded(CREATE_ID) && ModList.get().isLoaded(GALOSPHERE_ID)) {
            if (event.getTabKey().equals(AllCreativeModeTabs.BASE_CREATIVE_TAB.getKey())) {
                event.accept(IMItems.CRUSHED_RAW_PALLADIUM);
            }
        }
        if (ModList.get().isLoaded(GALOSPHERE_ID) && IMConfig.COMMON.oldSterling.get() && tab.location().equals(GS_TAB)) {
            putBefore(event, GItems.STERLING_HELMET.get(), IMItems.PALLADIUM_UPGRADE_SMITHING_TEMPLATE);
        }
    }


    @SafeVarargs
    private static void putAfter(BuildCreativeModeTabContentsEvent event, ItemLike after, Supplier<? extends ItemLike>... supplier) {
        for (int i = supplier.length - 1; i >= 0; i--) {
            ItemLike key = supplier[i].get();
            event.insertAfter(new ItemStack(after), new ItemStack(key), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    @SafeVarargs
    private static void putBefore(BuildCreativeModeTabContentsEvent event, ItemLike before, Supplier<? extends ItemLike>... supplier) {
        for (Supplier<? extends ItemLike> supplier1 : supplier) {
            ItemLike key = supplier1.get();
            event.insertBefore(new ItemStack(before), new ItemStack(key), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

}
