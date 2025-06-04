package skid.krypton.event.events;

import net.minecraft.client.gui.DrawContext;
import skid.krypton.event.CancellableEvent;

public class Render2DEvent extends CancellableEvent {
    public DrawContext a;
    public float b;

    public Render2DEvent(final DrawContext a, final float b) {
        this.a = a;
        this.b = b;
    }
}
