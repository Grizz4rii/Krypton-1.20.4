// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import anticope.rejects.utils.Ore;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import skid.krypton.event.events.ChunkDataEvent;
import skid.krypton.event.events.Render3DEvent;
import skid.krypton.event.events.SetBlockStateEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.NumberSetting;
import skid.krypton.utils.BlockUtil;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.RenderUtils;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class NetheriteFinder extends Module {
    private final NumberSetting c;
    private final NumberSetting d;
    private final Map<Long, Map<Ore, Set<Vec3d>>> e;
    private Map<RegistryKey<Biome>, List<Ore>> f;

    public NetheriteFinder() {
        super(EncryptedString.a("Netherite Finder"), EncryptedString.a("Finds netherites"), -1, Category.c);
        this.c = new NumberSetting(EncryptedString.a("Alpha"), 1.0, 255.0, 125.0, 1.0);
        this.d = new NumberSetting(EncryptedString.a("Range"), 1.0, 10.0, 5.0, 1.0);
        this.e = new ConcurrentHashMap<Long, Map<Ore, Set<Vec3d>>>();
        this.a(this.c, this.d);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.j();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventListener
    public void a(final Render3DEvent render3DEvent) {
        if (this.b.player == null || this.f == null) {
            return;
        }
        final Camera camera = this.b.gameRenderer.getCamera();
        if (camera != null) {
            final MatrixStack a = render3DEvent.a;
            render3DEvent.a.push();
            final Vec3d pos = camera.getPos();
            a.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
            a.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0f));
            a.translate(-pos.x, -pos.y, -pos.z);
        }
        final int x = this.b.player.method_31476().x;
        final int z = this.b.player.method_31476().z;
        final int f = this.d.f();
        if (0 <= f) {
            for (int i = x; i <= x; ++i) {
                this.a(i, z - f, render3DEvent);
            }
            throw new IllegalAccessException();
        }
        render3DEvent.a.pop();
    }

    private void a(final int n, final int n2, final Render3DEvent render3DEvent) {
        final long long1 = ChunkPos.toLong(n, n2);
        if (this.e.containsKey(long1)) {
            final Iterator iterator = this.e.get(long1).entrySet().iterator();
            while (iterator.hasNext()) {
                for (final Object next : ((Map.Entry<K, Set>) iterator.next()).getValue()) {
                    RenderUtils.a(render3DEvent.a, (float) ((Vec3d) next).x, (float) ((Vec3d) next).y, (float) ((Vec3d) next).z, (float) (((Vec3d) next).x + 1.0), (float) (((Vec3d) next).y + 1.0), (float) (((Vec3d) next).z + 1.0), this.b(this.c.f()));
                }
            }
        }
    }

    private void j() {
        this.e.clear();
        if (this.b.world != null) {
            this.f = Ore.register();
            this.k();
        }
    }

    private Color b(final int a) {
        return new Color(191, 64, 191, a);
    }

    @EventListener
    public void a(final ChunkDataEvent chunkDataEvent) {
        if (this.f == null && this.b.world != null) {
            this.f = Ore.register();
        }
        this.a((Chunk) this.b.world.method_8497(chunkDataEvent.a.getChunkX(), chunkDataEvent.a.getChunkZ()));
    }

    @EventListener
    public void a(final SetBlockStateEvent setBlockStateEvent) {
        if (!setBlockStateEvent.c.method_26204().equals(Blocks.AIR)) {
            return;
        }
        final long long1 = ChunkPos.toLong(setBlockStateEvent.a);
        if (this.e.containsKey(long1)) {
            final Vec3d of = Vec3d.of(setBlockStateEvent.a);
            final Iterator iterator = this.e.get(long1).values().iterator();
            while (iterator.hasNext()) {
                ((Set) iterator.next()).remove(of);
            }
        }
    }

    private void k() {
        if (this.b.player == null) {
            return;
        }
        final Iterator iterator = BlockUtil.a().iterator();
        while (iterator.hasNext()) {
            this.a((Chunk) iterator.next());
        }
    }

    private void a(final Chunk chunk) {
        if (this.f == null) {
            return;
        }
        final ChunkPos pos = chunk.getPos();
        final long long1 = pos.toLong();
        final ClientWorld world = this.b.world;
        if (this.e.containsKey(long1) || world == null) {
            return;
        }
        final HashSet set = new HashSet();
        ChunkPos.stream(pos, 1).forEach(chunkPos -> {
            clientWorld.method_8402(chunkPos.x, chunkPos.z, ChunkStatus.BIOMES, (boolean) (0 != 0));
            final Chunk chunk2;
            if (chunk2 == null) {
            } else {
                chunk2.getSectionArray();
                int j = 0;
                final ChunkSection[] array;
                while (j < array.length) {
                    array[j].getBiomeContainer().forEachValue(registryEntry -> set2.add(registryEntry.getKey().get()));
                    ++j;
                }
            }
        });
        final Object collect = set.stream().flatMap(registryKey -> this.a(registryKey).stream()).collect(Collectors.toSet());
        final int n = pos.x << 4;
        final int n2 = pos.z << 4;
        final ChunkRandom chunkRandom = new ChunkRandom(ChunkRandom$RandomProvider.XOROSHIRO.create(0L));
        final long setPopulationSeed = chunkRandom.setPopulationSeed(6608149111735331168L, n, n2);
        final HashMap hashMap = new HashMap();
        for (final Object next : (Set) collect) {
            final HashSet value = new HashSet();
            chunkRandom.setDecoratorSeed(setPopulationSeed, ((Ore) next).b, ((Ore) next).a);
            for (int value2 = ((Ore) next).c.get(chunkRandom), i = 0; i < value2; ++i) {
                if (((Ore) next).f == 1.0f || chunkRandom.method_43057() < 1.0f / ((Ore) next).f) {
                    final int n3 = chunkRandom.method_43048(16) + n;
                    final int n4 = chunkRandom.method_43048(16) + n2;
                    final int value3 = ((Ore) next).d.get(chunkRandom, ((Ore) next).e);
                    final BlockPos blockPos = new BlockPos(n3, value3, n4);
                    if (this.a((RegistryKey) chunk.method_16359(n3, value3, n4).getKey().get()).contains(next)) {
                        if (((Ore) next).j) {
                            value.addAll(this.a(world, chunkRandom, blockPos, ((Ore) next).h));
                        } else {
                            value.addAll(this.a(world, chunkRandom, blockPos, ((Ore) next).h, ((Ore) next).g));
                        }
                    }
                }
            }
            if (!value.isEmpty()) {
                hashMap.put(next, value);
            }
        }
        this.e.put(long1, hashMap);
    }

    private List a(final RegistryKey registryKey) {
        if (this.f == null) {
            this.f = Ore.register();
        }
        if (this.f.containsKey(registryKey)) {
            return this.f.get(registryKey);
        }
        return this.f.values().stream().findAny().get();
    }

    private ArrayList a(final ClientWorld clientWorld, final ChunkRandom chunkRandom, final BlockPos blockPos, final int n, final float n2) {
        final float n3 = chunkRandom.method_43057() * 3.1415927f;
        final float n4 = n / 8.0f;
        final int ceil = MathHelper.ceil((n / 16.0f * 2.0f + 1.0f) / 2.0f);
        final int method_10263 = blockPos.method_10263();
        final int method_10264 = blockPos.method_10263();
        final double sin = Math.sin(n3);
        final int method_10265 = blockPos.method_10260();
        final int method_10266 = blockPos.method_10260();
        final double cos = Math.cos(n3);
        final int method_10267 = blockPos.method_10264();
        final int method_10268 = blockPos.method_10264();
        final int n5 = blockPos.method_10263() - MathHelper.ceil(n4) - ceil;
        final int n6 = blockPos.method_10264() - 2 - ceil;
        final int n7 = blockPos.method_10260() - MathHelper.ceil(n4) - ceil;
        for (int n8 = 2 * (MathHelper.ceil(n4) + ceil), i = n5; i <= n5 + n8; ++i) {
            for (int j = n7; j <= n7 + n8; ++j) {
                if (n6 <= clientWorld.method_8624(Heightmap$Type.MOTION_BLOCKING, i, j)) {
                    return this.a(clientWorld, chunkRandom, n, method_10263 + Math.sin(n3) * n4, method_10264 - sin * n4, method_10265 + Math.cos(n3) * n4, method_10266 - cos * n4, method_10267 + chunkRandom.method_43048(3) - 2, method_10268 + chunkRandom.method_43048(3) - 2, n5, n6, n7, n8, 2 * (2 + ceil), n2);
                }
            }
        }
        return new ArrayList();
    }

    private ArrayList a(final ClientWorld clientWorld, final ChunkRandom chunkRandom, final int n, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int b, final int b2, final int b3, final int n8, final int n9, final float n10) {
        final BitSet set = new BitSet(n8 * n9 * n8);
        final BlockPos$Mutable blockPos$Mutable = new BlockPos$Mutable();
        final double[] array = new double[n * 4];
        final ArrayList list = new ArrayList();
        for (int i = 0; i < n; ++i) {
            final float n11 = i / (float) n;
            final double lerp = MathHelper.lerp(n11, n2, n3);
            final double lerp2 = MathHelper.lerp(n11, n6, n7);
            final double lerp3 = MathHelper.lerp(n11, n4, n5);
            array[i * 4] = lerp;
            array[i * 4 + 1] = lerp2;
            array[i * 4 + 2] = lerp3;
            array[i * 4 + 3] = ((MathHelper.sin(3.1415927f * n11) + 1.0f) * (chunkRandom.method_43058() * n / 16.0) + 1.0) / 2.0;
        }
        for (int j = 0; j < n - 1; ++j) {
            if (array[j * 4 + 3] > 0.0) {
                for (int k = j + 1; k < n; ++k) {
                    if (array[k * 4 + 3] > 0.0) {
                        final double n12 = array[j * 4] - array[k * 4];
                        final double n13 = array[j * 4 + 1] - array[k * 4 + 1];
                        final double n14 = array[j * 4 + 2] - array[k * 4 + 2];
                        final double n15 = array[j * 4 + 3] - array[k * 4 + 3];
                        if (n15 * n15 > n12 * n12 + n13 * n13 + n14 * n14) {
                            if (n15 > 0.0) {
                                array[k * 4 + 3] = -1.0;
                            } else {
                                array[j * 4 + 3] = -1.0;
                            }
                        }
                    }
                }
            }
        }
        for (int l = 0; l < n; ++l) {
            final double n16 = array[l * 4 + 3];
            if (n16 >= 0.0) {
                final double n17 = array[l * 4];
                final double n18 = array[l * 4 + 1];
                final double n19 = array[l * 4 + 2];
                int max = Math.max(MathHelper.floor(n17 - n16), b);
                int max2 = Math.max(MathHelper.floor(n18 - n16), b2);
                final int max3 = Math.max(MathHelper.floor(n19 - n16), b3);
                final int max4 = Math.max(MathHelper.floor(n17 + n16), max);
                final int max5 = Math.max(MathHelper.floor(n18 + n16), max2);
                final int max6 = Math.max(MathHelper.floor(n19 + n16), max3);
                while (max <= max4) {
                    final double n20 = (max + 0.5 - n17) / n16;
                    if (n20 * n20 < 1.0) {
                        while (max2 <= max5) {
                            final double n21 = (max2 + 0.5 - n18) / n16;
                            if (n20 * n20 + n21 * n21 < 1.0 && max3 <= max6) {
                                final double n22 = (max3 + 0.5 - n19) / n16;
                                if (n20 * n20 + n21 * n21 + n22 * n22 < 1.0) {
                                    final int n23 = max - b + (max2 - b2) * n8 + (max3 - b3) * n8 * n9;
                                    if (!set.get(n23)) {
                                        set.set(n23);
                                        blockPos$Mutable.set(max, max2, max3);
                                        if (max2 >= -64 && max2 < 320 && clientWorld.method_8320((BlockPos) blockPos$Mutable).method_26225() && this.a(clientWorld, (BlockPos) blockPos$Mutable, n10, chunkRandom)) {
                                            list.add(new Vec3d(max, max2, max3));
                                        }
                                    }
                                }
                                throw new IllegalAccessException();
                            }
                            ++max2;
                        }
                    }
                    ++max;
                }
            }
        }
        return list;
    }

    private boolean a(final ClientWorld clientWorld, final BlockPos blockPos, final float n, final ChunkRandom chunkRandom) {
        if (n == 0.0f || (n != 1.0f && chunkRandom.method_43057() >= n)) {
            return true;
        }
        final Direction[] values = Direction.values();
        for (int i = 0; i < values.length; ++i) {
            if (!clientWorld.method_8320(blockPos.add(values[i].getVector())).method_26225() && n != 1.0f) {
                return false;
            }
        }
        return true;
    }

    private ArrayList a(final ClientWorld clientWorld, final ChunkRandom chunkRandom, final BlockPos blockPos, final int n) {
        final ArrayList list = new ArrayList();
        for (int method_43048 = chunkRandom.method_43048(n + 1), i = 0; i < method_43048; ++i) {
            final int min = Math.min(i, 7);
            final int n2 = this.a(chunkRandom, min) + blockPos.method_10263();
            final int n3 = this.a(chunkRandom, min) + blockPos.method_10264();
            final int n4 = this.a(chunkRandom, min) + blockPos.method_10260();
            if (clientWorld.method_8320(new BlockPos(n2, n3, n4)).method_26225() && this.a(clientWorld, new BlockPos(n2, n3, n4), 1.0f, chunkRandom)) {
                list.add(new Vec3d(n2, n3, n4));
            }
        }
        return list;
    }

    private int a(final ChunkRandom chunkRandom, final int n) {
        return Math.round((chunkRandom.method_43057() - chunkRandom.method_43057()) * n);
    }

    private static byte[] mrzrjrnvarhcobb() {
        return new byte[]{31, 123, 56, 120, 22, 35, 28, 109, 125, 101, 98, 81, 76, 58, 124, 115, 48, 39, 87, 71, 110, 47, 67, 16, 38, 116, 24, 52, 100, 52, 68, 51, 115, 69, 113, 103, 24, 14, 53, 52, 76, 48, 98, 1, 59, 28, 78, 23, 109, 124, 34, 94, 47, 113, 27, 3, 42, 53, 64, 98, 76, 30, 88, 47, 33, 43, 29, 111, 45, 62, 51, 62, 6, 13, 35, 119, 104, 121, 39, 78, 89, 44};
    }
}
