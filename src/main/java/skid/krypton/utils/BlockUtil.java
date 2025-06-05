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
    public static List<?> getLoadedChunks() {
        final int viewDistance = Math.max(2, Krypton.mc.options.getClampedViewDistance()) + 3;
        final ArrayList loadedChunks = new ArrayList();
        final ChunkPos playerChunkPos = Krypton.mc.player.getChunkPos();
        final int maxX = playerChunkPos.x + viewDistance;
        int currentZ = playerChunkPos.z - viewDistance;
        final int maxZ = playerChunkPos.z + viewDistance;
        for (int currentX = playerChunkPos.x - viewDistance; currentX <= maxX; ++currentX) {
            while (currentZ <= maxZ) {
                if (Krypton.mc.world.isChunkLoaded(currentX, currentZ)) {
                    final WorldChunk chunk = Krypton.mc.world.getChunk(currentX, currentZ);
                    if (chunk != null) {
                        loadedChunks.add(chunk);
                    }
                }
                ++currentZ;
            }
        }
        return loadedChunks;
    }

    public static boolean isBlockAtPosition(final BlockPos blockPos, final Block block) {
        return Krypton.mc.world.getBlockState(blockPos).getBlock() == block;
    }

    public static boolean isRespawnAnchorCharged(final BlockPos blockPos) {
        return isBlockAtPosition(blockPos, Blocks.RESPAWN_ANCHOR) &&
                (int) Krypton.mc.world.getBlockState(blockPos).get((Property) RespawnAnchorBlock.CHARGES) != 0;
    }

    public static boolean isRespawnAnchorUncharged(final BlockPos blockPos) {
        return isBlockAtPosition(blockPos, Blocks.RESPAWN_ANCHOR) &&
                (int) Krypton.mc.world.getBlockState(blockPos).get((Property) RespawnAnchorBlock.CHARGES) == 0;
    }

    public static void interactWithBlock(final BlockHitResult blockHitResult, final boolean shouldSwingHand) {
        final ActionResult result = Krypton.mc.interactionManager.interactBlock(Krypton.mc.player, Hand.MAIN_HAND, blockHitResult);
        if (result.isAccepted() && result.shouldSwingHand() && shouldSwingHand) {
            Krypton.mc.player.swingHand(Hand.MAIN_HAND);
        }
    }
}