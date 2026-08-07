package net.skellatex.integrated_mining.content.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;

import java.util.List;

public class PalladiumUpgradeItem extends Item {
    public PalladiumUpgradeItem() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        list.add(Component.translatable("item.integrated_mining.palladium_upgrade_smithing_template.description_0"));
        list.add(Component.translatable("item.integrated_mining.palladium_upgrade_smithing_template.description_1"));
        list.add(Component.translatable("item.integrated_mining.palladium_upgrade_smithing_template.description_2"));
        list.add(Component.translatable("item.integrated_mining.palladium_upgrade_smithing_template.description_3"));
        list.add(Component.translatable("item.integrated_mining.palladium_upgrade_smithing_template.description_4"));
        list.add(Component.translatable("item.integrated_mining.palladium_upgrade_smithing_template.description_5"));
    }
}
