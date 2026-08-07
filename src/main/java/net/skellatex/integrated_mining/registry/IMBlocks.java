package net.skellatex.integrated_mining.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.skellatex.integrated_mining.IntegratedMining;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class IMBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(IntegratedMining.MOD_ID);

    // Spelunkery - Caverns & Chasms compat
    public static final Supplier<Block> ROUGH_SPINEL_BLOCK = registerBlock("rough_spinel_block",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.CALCITE).requiresCorrectToolForDrops()));
    public static final Supplier<Block> ANDESITE_SPINEL_ORE = registerBlock("andesite_spinel_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 5), Block.Properties.ofFullCopy(Blocks.IRON_ORE).requiresCorrectToolForDrops()));
    public static final Supplier<Block> DIORITE_SPINEL_ORE = registerBlock("diorite_spinel_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 5), Block.Properties.ofFullCopy(Blocks.IRON_ORE).requiresCorrectToolForDrops()));
    public static final Supplier<Block> GRANITE_SPINEL_ORE = registerBlock("granite_spinel_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 5), Block.Properties.ofFullCopy(Blocks.IRON_ORE).requiresCorrectToolForDrops()));
    public static final Supplier<Block> TUFF_SPINEL_ORE = registerBlock("tuff_spinel_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 5), Block.Properties.ofFullCopy(Blocks.IRON_ORE).requiresCorrectToolForDrops().sound(SoundType.TUFF)));

    public static final Supplier<Block> ANDESITE_TIN_ORE = registerBlock("andesite_tin_ore",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.IRON_ORE).requiresCorrectToolForDrops().sound(IMSoundTypes.TIN_ORE_SOUNDS)));
    public static final Supplier<Block> DIORITE_TIN_ORE = registerBlock("diorite_tin_ore",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.IRON_ORE).requiresCorrectToolForDrops().sound(IMSoundTypes.TIN_ORE_SOUNDS)));
    public static final Supplier<Block> GRANITE_TIN_ORE = registerBlock("granite_tin_ore",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.IRON_ORE).requiresCorrectToolForDrops().sound(IMSoundTypes.TIN_ORE_SOUNDS)));
    public static final Supplier<Block> TUFF_TIN_ORE = registerBlock("tuff_tin_ore",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.IRON_ORE).requiresCorrectToolForDrops().sound(IMSoundTypes.TIN_ORE_SOUNDS)));

    // Spelunkery - Galosphere compat
    public static final Supplier<Block> ANDESITE_PALLADIUM_ORE = registerBlock("andesite_palladium_ore",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.IRON_ORE).requiresCorrectToolForDrops()));
    public static final Supplier<Block> DIORITE_PALLADIUM_ORE = registerBlock("diorite_palladium_ore",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.IRON_ORE).requiresCorrectToolForDrops()));
    public static final Supplier<Block> GRANITE_PALLADIUM_ORE = registerBlock("granite_palladium_ore",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.IRON_ORE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> TUFF_PALLADIUM_ORE = registerBlock("tuff_palladium_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE).requiresCorrectToolForDrops().sound(SoundType.TUFF)));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        IMItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
