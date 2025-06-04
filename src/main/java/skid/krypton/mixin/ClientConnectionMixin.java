package skid.krypton.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import skid.krypton.event.events.PacketEvent;
import skid.krypton.event.events.PacketReceiveEvent;
import skid.krypton.manager.EventManager;

@Mixin({ClientConnection.class})
public class ClientConnectionMixin {
    @Inject(method = {"handlePacket"}, at = {@At("HEAD")}, cancellable = true)
    private static void onPacketReceive(final Packet packet, final PacketListener packetListener, final CallbackInfo callbackInfo) {
        final PacketReceiveEvent packetReceiveEvent = new PacketReceiveEvent(packet);
        EventManager.b(packetReceiveEvent);
        if (packetReceiveEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"send(Lnet/minecraft/network/packet/Packet;)V"}, at = {@At("HEAD")}, cancellable = true)
    private void onPacketSend(final Packet packet, final CallbackInfo callbackInfo) {
        final PacketEvent packetEvent = new PacketEvent(packet);
        EventManager.b(packetEvent);
        if (packetEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}
