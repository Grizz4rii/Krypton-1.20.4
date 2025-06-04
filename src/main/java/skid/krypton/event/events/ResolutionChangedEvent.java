package skid.krypton.event.events;

import net.minecraft.client.util.Window;
import skid.krypton.event.CancellableEvent;

public class ResolutionChangedEvent extends CancellableEvent {
    public Window a;

    public ResolutionChangedEvent(final Window a) {
        this.a = a;
    }
}
