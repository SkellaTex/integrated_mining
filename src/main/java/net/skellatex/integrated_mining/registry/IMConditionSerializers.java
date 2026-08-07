package net.skellatex.integrated_mining.registry;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.blueprint.core.api.conditions.ConfigValueCondition;
import com.teamabnormals.blueprint.core.api.conditions.ConfigValueCondition.Serializer;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.skellatex.integrated_mining.IMConfig;
import net.skellatex.integrated_mining.IntegratedMining;

public class IMConditionSerializers {
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.CONDITION_SERIALIZERS, IntegratedMining.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends ICondition>, Serializer> CONFIG = CONDITION_SERIALIZERS.register("config", () -> new ConfigValueCondition.Serializer(DataUtil.getConfigValues(IMConfig.COMMON)));

}
