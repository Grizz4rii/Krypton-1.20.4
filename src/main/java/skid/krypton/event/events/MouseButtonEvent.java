package skid.krypton.event.events;

import skid.krypton.event.CancellableEvent;

public class MouseButtonEvent extends CancellableEvent {
    public int button;
    public int actions;
    public long window;

    public MouseButtonEvent(final int a, final long c, final int b) {
        this.button = a;
        this.window = c;
        this.actions = b;
    }
}
