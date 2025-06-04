// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.event.events;

import skid.krypton.event.CancellableEvent;

public class MouseScrolledEvent extends CancellableEvent {
    public double a;

    public MouseScrolledEvent(final double a) {
        this.a = a;
    }
}
