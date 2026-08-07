package net.skellatex.integrated_mining;

import com.teamabnormals.blueprint.core.annotations.ConfigKey;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import org.apache.commons.lang3.tuple.Pair;

public class IMConfig {

    public static class Common {
        @ConfigKey("default_silver")
        public final BooleanValue defaultSilver;

        @ConfigKey("more_silver")
        public final BooleanValue moreSilver;
        @ConfigKey("turquoise_oregen")
        public final BooleanValue turquoiseOreGen;

        @ConfigKey("preserved_template_recipe")
        public final BooleanValue preservedTemplateRecipe;
        @ConfigKey("cheaper_lead_bolts")
        public final BooleanValue cheaperLeadBolts;
        @ConfigKey("old_rope_ladder")
        public final BooleanValue oldRopeLadder;

        @ConfigKey("old_sterling")
        public final BooleanValue oldSterling;

        public Common(ModConfigSpec.Builder builder) {
            builder.push("content_overlap");
            this.defaultSilver = builder.comment("Determines the default silver (true for Caverns & Chasms, false for Oreganized)").define("Caverns & Chasms Silver", true);
            builder.pop();

            builder.push("generation_tweaks");
            this.moreSilver = builder.comment("Whether to increase silver ore generation if Oreganized silver is the default").define("More Silver", true);
            this.turquoiseOreGen = builder.comment("Generates Turquoise ores from Caverns & Chasms if enabled (very rare)").define("Turquoise Ore Generation", false);
            builder.pop();

            builder.push("recipe_tweaks");
            this.preservedTemplateRecipe = builder.comment("If the Preserved Upgrade from Galosphere should have a duplication recipe").define("Preserved Template Recipe", true);
            this.cheaperLeadBolts = builder.comment("If Lead Bolts from Oreganized should cost less to craft").define("Cheaper Lead Bolts", true);
            this.oldRopeLadder = builder.comment("If Rope Ladders from Spelunkery should use the old recipe").define("Old Rope Ladder Recipe", false);
            builder.pop();

            builder.push("gameplay_tweaks");
            this.oldSterling = builder.comment("Brings back the Palladium Smithing Template and old recipes for Sterling armor if enabled").define("Old Sterling Crafing", false);
            builder.pop();
        }
    }

    public static final ModConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        Pair<Common, ModConfigSpec> commonSpecPair = new ModConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
    }
}
