package skid.krypton.event.events;

import skid.krypton.event.CancellableEvent;

public class PostItemUseEvent extends CancellableEvent {
    public int a;

    public PostItemUseEvent(final int a) {
        this.a = a;
    }
}
