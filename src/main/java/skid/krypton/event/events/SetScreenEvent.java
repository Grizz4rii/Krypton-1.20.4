package skid.krypton.event.events;

import net.minecraft.client.gui.screen.Screen;
import skid.krypton.event.CancellableEvent;

public class SetScreenEvent extends CancellableEvent {
    public Screen a;

    public SetScreenEvent(final Screen a) {
        this.a = a;
    }
}
