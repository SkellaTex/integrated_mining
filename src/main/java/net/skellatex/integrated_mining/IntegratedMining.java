package net.skellatex.integrated_mining;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.skellatex.integrated_mining.registry.IMBlocks;
import net.skellatex.integrated_mining.registry.IMConditionSerializers;
import net.skellatex.integrated_mining.registry.IMItems;
import net.skellatex.integrated_mining.registry.IMSoundTypes;


@Mod(IntegratedMining.MOD_ID)
public class IntegratedMining {
    public static final String MOD_ID = "integrated_mining";

    public IntegratedMining(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);

        IMItems.register(modEventBus);
        IMBlocks.register(modEventBus);
        IMSoundTypes.register(modEventBus);
        IMConditionSerializers.CONDITION_SERIALIZERS.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, IMConfig.COMMON_SPEC);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
        @SubscribeEvent
        public static void onAddPackFinders(AddPackFindersEvent event) {
            if (event.getPackType() == PackType.CLIENT_RESOURCES) {
                registerBuiltinResourcePack(event, "integrated_mining_resources", "Integrated Mining Resources", PackSource.BUILT_IN, false);
            }
        }

        private static void registerBuiltinResourcePack(AddPackFindersEvent event, String folder, String name, PackSource source, boolean alwaysActive) {
            event.addPackFinders(
                    ResourceLocation.fromNamespaceAndPath(IntegratedMining.MOD_ID,"resourcepacks/" + folder),
                    PackType.CLIENT_RESOURCES,
                    Component.literal(name),
                    source,
                    alwaysActive,
                    Pack.Position.TOP);
        }
    }
}
