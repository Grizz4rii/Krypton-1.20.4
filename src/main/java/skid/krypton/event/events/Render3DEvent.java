package skid.krypton.event.events;

import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import skid.krypton.event.CancellableEvent;

public class Render3DEvent extends CancellableEvent {
    public MatrixStack a;
    public Matrix4f b;
    public float c;

    public Render3DEvent(final MatrixStack a, final Matrix4f b, final float c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
}
