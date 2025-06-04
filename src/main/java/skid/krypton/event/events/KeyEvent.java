package skid.krypton.event.events;

import skid.krypton.event.CancellableEvent;

public class KeyEvent extends CancellableEvent {
    public int key;
    public int mode;
    public long c;

    public KeyEvent(final int key, final long c, final int mode) {
        this.key = key;
        this.c = c;
        this.mode = mode;
    }
}
