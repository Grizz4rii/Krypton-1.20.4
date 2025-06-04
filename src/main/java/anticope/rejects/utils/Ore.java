// 
// Decompiled by Procyon v0.6.0
// 

package anticope.rejects.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.HeightContext;
import net.minecraft.world.gen.WorldPreset;
import net.minecraft.world.gen.WorldPresets;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.PlacedFeatureIndexer;
import net.minecraft.world.gen.heightprovider.HeightProvider;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import skid.krypton.mixin.CountPlacementModifierAccessor;
import skid.krypton.mixin.HeightRangePlacementModifierAccessor;
import skid.krypton.mixin.RarityFilterPlacementModifierAccessor;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class Ore {
    public int a;
    public int b;
    public IntProvider c;
    public HeightProvider d;
    public HeightContext e;
    public float f;
    public float g;
    public int h;
    public Color i;
    public boolean j;

    public static Map a() {
        final RegistryWrapper$WrapperLookup wrapperLookup = BuiltinRegistries.createWrapperLookup();
        final RegistryWrapper$Impl wrapperOrThrow = wrapperLookup.getWrapperOrThrow(RegistryKeys.PLACED_FEATURE);
        final List list = ((WorldPreset) wrapperLookup.getWrapperOrThrow(RegistryKeys.WORLD_PRESET).method_46747(WorldPresets.DEFAULT).comp_349()).createDimensionsRegistryHolder().dimensions().get(DimensionOptions.NETHER).chunkGenerator().getBiomeSource().getBiomes().stream().toList();
        final List collectIndexedFeatures = PlacedFeatureIndexer.collectIndexedFeatures((List) list, registryEntry -> ((Biome) registryEntry.value()).getGenerationSettings().getFeatures(), true);
        final HashMap hashMap = new HashMap();
        a(hashMap, collectIndexedFeatures, wrapperOrThrow, OrePlacedFeatures.ORE_DEBRIS_SMALL, 7, new Color(209, 27, 245));
        a(hashMap, collectIndexedFeatures, wrapperOrThrow, OrePlacedFeatures.ORE_ANCIENT_DEBRIS_LARGE, 7, new Color(209, 27, 245));
        final HashMap hashMap2 = new HashMap();
        list.forEach(registryEntry2 -> {
            map.put(registryEntry2.getKey().get(), new ArrayList());
            ((Biome) registryEntry2.value()).getGenerationSettings().getFeatures().stream().flatMap(RegistryEntryList::stream).map(RegistryEntry::value);
            Objects.requireNonNull(obj);
            final Stream stream;
            stream.filter(obj::containsKey).forEach(placedFeature -> map2.get(registryEntry3.getKey().get()).add(map3.get(placedFeature)));
            return;
        });
        return hashMap2;
    }

    private static void a(final Map map, final List list, final RegistryWrapper$Impl registryWrapper$Impl, final RegistryKey registryKey, final int n, final Color color) {
        final Object comp_349 = registryWrapper$Impl.method_46747(registryKey).comp_349();
        map.put(comp_349, new Ore((PlacedFeature) comp_349, n, list.get(n).indexMapping().applyAsInt(comp_349), color));
    }

    private Ore(final PlacedFeature obj, final int a, final int b, final Color i) {
        this.c = ConstantIntProvider.create(1);
        this.f = 1.0f;
        this.a = a;
        this.b = b;
        this.i = i;
        this.e = new HeightContext(null, HeightLimitView.create(MinecraftClient.getInstance().world.method_31607(), MinecraftClient.getInstance().world.method_8597().logicalHeight()));
        for (final Object next : obj.placementModifiers()) {
            if (next instanceof CountPlacementModifier) {
                this.c = ((CountPlacementModifierAccessor) next).getCount();
            } else if (next instanceof HeightRangePlacementModifier) {
                this.d = ((HeightRangePlacementModifierAccessor) next).getHeight();
            } else {
                if (!(next instanceof RarityFilterPlacementModifier)) {
                    continue;
                }
                this.f = (float) ((RarityFilterPlacementModifierAccessor) next).getChance();
            }
        }
        final FeatureConfig config = ((ConfiguredFeature) obj.feature().value()).config();
        if (config instanceof OreFeatureConfig) {
            this.g = ((OreFeatureConfig) config).discardOnAirChance;
            this.h = ((OreFeatureConfig) config).size;
            if (((ConfiguredFeature) obj.feature().value()).feature() instanceof ScatteredOreFeature) {
                this.j = true;
            }
            return;
        }
        throw new IllegalStateException("config for " + obj + "is not OreFeatureConfig.class");
    }

    private static byte[] dwyrwvxcbpxjhuh() {
        return new byte[]{40, 4, 103, 33, 11, 101, 15, 97, 99, 53, 50, 44, 91, 16, 69, 71, 114, 86, 103, 108, 27, 65};
    }
}
