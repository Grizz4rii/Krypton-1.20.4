// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import skid.krypton.event.events.ChunkDataEvent;
import skid.krypton.event.events.EntitySpawnEvent;
import skid.krypton.manager.EventManager;

@Mixin({ClientPlayNetworkHandler.class})
public abstract class ClientPlayNetworkHandlerMixin {
    @Inject(method = {"onChunkData"}, at = {@At("TAIL")})
    private void onChunkData(final ChunkDataS2CPacket chunkDataS2CPacket, final CallbackInfo callbackInfo) {
        EventManager.b(new ChunkDataEvent(chunkDataS2CPacket));
    }

    @Inject(method = {"onEntitySpawn"}, at = {@At("HEAD")}, cancellable = true)
    private void onEntitySpawn(final EntitySpawnS2CPacket entitySpawnS2CPacket, final CallbackInfo callbackInfo) {
        final EntitySpawnEvent entitySpawnEvent = new EntitySpawnEvent(entitySpawnS2CPacket);
        EventManager.b(entitySpawnEvent);
        if (entitySpawnEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}
