// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.event.events;

import net.minecraft.network.packet.Packet;
import skid.krypton.event.CancellableEvent;

public class PacketReceiveEvent extends CancellableEvent {
    public Packet a;

    public PacketReceiveEvent(final Packet a) {
        this.a = a;
    }
}
