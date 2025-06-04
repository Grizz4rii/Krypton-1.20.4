package skid.krypton.event.events;

import net.minecraft.network.packet.Packet;
import skid.krypton.event.CancellableEvent;

public class PacketEvent extends CancellableEvent {
    public Packet<?> packet;

    public PacketEvent(final Packet<?> a) {
        this.packet = a;
    }
}
