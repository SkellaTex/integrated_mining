package net.skellatex.integrated_mining.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.skellatex.integrated_mining.IntegratedMining;

import java.util.function.Supplier;

public class IMSoundTypes {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, IntegratedMining.MOD_ID);

    public static final Supplier<SoundEvent> TIN_ORE_BREAK = registerSoundEvent("block.tin_ore.break");
    public static final Supplier<SoundEvent> TIN_ORE_STEP = registerSoundEvent("block.tin_ore.step");
    public static final Supplier<SoundEvent> TIN_ORE_PLACE = registerSoundEvent("block.tin_ore.place");
    public static final Supplier<SoundEvent> TIN_ORE_HIT = registerSoundEvent("block.tin_ore.hit");
    public static final Supplier<SoundEvent> TIN_ORE_FALL = registerSoundEvent("block.tin_ore.fall");

    public static final DeferredSoundType TIN_ORE_SOUNDS = new DeferredSoundType(1f, 1f,
            IMSoundTypes.TIN_ORE_BREAK, IMSoundTypes.TIN_ORE_STEP, IMSoundTypes.TIN_ORE_PLACE,
            IMSoundTypes.TIN_ORE_HIT, IMSoundTypes.TIN_ORE_FALL);


    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(IntegratedMining.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
