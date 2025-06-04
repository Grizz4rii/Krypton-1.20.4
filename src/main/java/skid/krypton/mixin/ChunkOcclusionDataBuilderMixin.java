// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.mixin;

import net.minecraft.client.render.chunk.ChunkOcclusionDataBuilder;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import skid.krypton.event.events.ChunkMarkClosedEvent;
import skid.krypton.manager.EventManager;

@Mixin({ChunkOcclusionDataBuilder.class})
public abstract class ChunkOcclusionDataBuilderMixin {
    @Inject(method = {"markClosed"}, at = {@At("HEAD")}, cancellable = true)
    private void onMarkClosed(final BlockPos blockPos, final CallbackInfo callbackInfo) {
        final ChunkMarkClosedEvent chunkMarkClosedEvent = new ChunkMarkClosedEvent();
        EventManager.b(chunkMarkClosedEvent);
        if (chunkMarkClosedEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}
