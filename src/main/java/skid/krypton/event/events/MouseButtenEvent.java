package skid.krypton.event.events;

import skid.krypton.event.CancellableEvent;

public class MouseButtenEvent extends CancellableEvent {
    public int a;
    public int b;
    public long c;

    public MouseButtenEvent(final int a, final long c, final int b) {
        this.a = a;
        this.c = c;
        this.b = b;
    }
}
