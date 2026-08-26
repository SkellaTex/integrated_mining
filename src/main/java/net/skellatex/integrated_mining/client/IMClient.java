package net.skellatex.integrated_mining.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.skellatex.integrated_mining.IntegratedMining;
import net.skellatex.integrated_mining.registry.IMBlocks;

@EventBusSubscriber(modid = IntegratedMining.MOD_ID, value = {Dist.CLIENT})
public class IMClient {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        IEventBus eventBus = NeoForge.EVENT_BUS;

        ItemBlockRenderTypes.setRenderLayer((Block) IMBlocks.CUPRIC_CHANDELIER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer((Block)IMBlocks.CUPRIC_LIGHT_STAND.get(), RenderType.cutout());
    }

}

