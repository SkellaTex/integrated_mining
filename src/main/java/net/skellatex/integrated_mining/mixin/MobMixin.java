package net.skellatex.integrated_mining.mixin;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.skellatex.integrated_mining.IMConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.skellatex.integrated_mining.compat.ModCompat.CAVERNS_AND_CHASMS_ID;

@Mixin(Mob.class)
public abstract class MobMixin {

    @Inject(
            method = "populateDefaultEquipmentSlots(Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/DifficultyInstance;)V",
            at = @At("TAIL")
    )
    private void stripSilverArmor(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
        Mob self = (Mob) (Object) this;
        if (ModList.get().isLoaded(CAVERNS_AND_CHASMS_ID) && !IMConfig.COMMON.silverEquipmentCavernsAndChasms.get()) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.isArmor()) {
                    ItemStack stack = self.getItemBySlot(slot);
                    if (!stack.isEmpty()) {
                        ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
                        if (id.getNamespace().equals("caverns_and_chasms")) {
                            self.setItemSlot(slot, ItemStack.EMPTY);
                        }
                    }
                }
            }
        }
    }
}