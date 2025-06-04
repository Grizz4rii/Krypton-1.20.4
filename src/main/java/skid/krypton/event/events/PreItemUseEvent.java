package skid.krypton.event.events;

import skid.krypton.event.CancellableEvent;

public class PreItemUseEvent extends CancellableEvent {
    public int a;

    public PreItemUseEvent(final int a) {
        this.a = a;
    }
}
