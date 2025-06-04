package skid.krypton.mixin;

import net.minecraft.world.gen.HeightContext;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({HeightContext.class})
public abstract class HeightContextMixin {
    @Redirect(method = {"<init>"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/chunk/ChunkGenerator;getMinimumY()I"))
    private int onMinY(final ChunkGenerator chunkGenerator) {
        int minimumY;
        if (chunkGenerator == null) {
            minimumY = -9999999;
        } else {
            minimumY = chunkGenerator.getMinimumY();
        }
        return minimumY;
    }

    @Redirect(method = {"<init>"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/chunk/ChunkGenerator;getWorldHeight()I"))
    private int onHeight(final ChunkGenerator chunkGenerator) {
        int worldHeight;
        if (chunkGenerator == null) {
            worldHeight = 100000000;
        } else {
            worldHeight = chunkGenerator.getWorldHeight();
        }
        return worldHeight;
    }
}
