package skid.krypton.utils;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;
import skid.krypton.Krypton;

import java.util.ArrayList;
import java.util.List;

public final class BlockUtil {
    public static List a() {
        final int n = Math.max(2, Krypton.e.options.getClampedViewDistance()) + 3;
        final ArrayList list = new ArrayList();
        final ChunkPos getChunkPos = Krypton.e.player.getChunkPos();
        final int n2 = getChunkPos.x + n;
        int i = getChunkPos.z - n;
        final int n3 = getChunkPos.z + n;
        for (int j = getChunkPos.x - n; j <= n2; ++j) {
            while (i <= n3) {
                if (Krypton.e.world.isChunkLoaded(j, i)) {
                    final WorldChunk getBlockX = Krypton.e.world.getChunk(j, i);
                    if (getBlockX != null) {
                        list.add(getBlockX);
                    }
                }
                ++i;
            }
        }
        return list;
    }

    public static boolean a(final BlockPos blockPos, final Block block) {
        return Krypton.e.world.getBlockState(blockPos).getBlock() == block;
    }

    public static boolean a(final BlockPos blockPos) {
        return a(blockPos, Blocks.RESPAWN_ANCHOR) && (int) Krypton.e.world.getBlockState(blockPos).get((Property) RespawnAnchorBlock.CHARGES) != 0;
    }

    public static boolean b(final BlockPos blockPos) {
        return a(blockPos, Blocks.RESPAWN_ANCHOR) && (int) Krypton.e.world.getBlockState(blockPos).get((Property) RespawnAnchorBlock.CHARGES) == 0;
    }

    public static void a(final BlockHitResult blockHitResult, final boolean b) {
        final ActionResult interactBlock = Krypton.e.interactionManager.interactBlock(Krypton.e.player, Hand.MAIN_HAND, blockHitResult);
        if (interactBlock.isAccepted() && interactBlock.shouldSwingHand() && b) {
            Krypton.e.player.swingHand(Hand.MAIN_HAND);
        }
    }
}
