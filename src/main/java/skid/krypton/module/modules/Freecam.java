package skid.krypton.module.modules;

import net.minecraft.client.option.Perspective;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3d;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.*;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.Setting;
import skid.krypton.module.setting.NumberSetting;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.KeyUtils;
import skid.krypton.utils.KryptonUtil;

public final class Freecam extends Module {
    private final NumberSetting i;
    public final Vector3d c;
    public final Vector3d d;
    private Perspective j;
    private double k;
    public float e;
    public float f;
    public float g;
    public float h;
    private boolean l;
    private boolean m;
    private boolean n;
    private boolean o;
    private boolean p;
    private boolean q;

    public Freecam() {
        super(EncryptedString.of("Freecam"), EncryptedString.of("Lets you move freely around the world without actually moving"), -1, Category.MISC);
        this.i = new NumberSetting(EncryptedString.of("Speed"), 1.0, 10.0, 1.0, 0.1);
        this.c = new Vector3d();
        this.d = new Vector3d();
        this.addSettings(new Setting[]{this.i});
    }

    @Override
    public void onEnable() {
        if (this.mc.player == null) {
            this.toggle();
            return;
        }
        this.mc.options.getFovEffectScale().setValue(0.0);
        this.mc.options.getBobView().setValue(false);
        this.e = this.mc.player.getYaw();
        this.f = this.mc.player.getPitch();
        this.j = this.mc.options.getPerspective();
        this.k = this.i.getValue();
        KryptonUtil.a(this.c, this.mc.gameRenderer.getCamera().getPos());
        KryptonUtil.a(this.d, this.mc.gameRenderer.getCamera().getPos());
        if (this.mc.options.getPerspective() == Perspective.THIRD_PERSON_FRONT) {
            this.e += 180.0f;
            this.f *= -1.0f;
        }
        this.g = this.e;
        this.h = this.f;
        this.l = this.mc.options.forwardKey.isPressed();
        this.m = this.mc.options.backKey.isPressed();
        this.n = this.mc.options.rightKey.isPressed();
        this.o = this.mc.options.leftKey.isPressed();
        this.p = this.mc.options.jumpKey.isPressed();
        this.q = this.mc.options.sneakKey.isPressed();
        this.j();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.j();
        this.d.set(this.c);
        this.g = this.e;
        this.h = this.f;
        super.onDisable();
    }

    private void j() {
        this.mc.options.forwardKey.setPressed(false);
        this.mc.options.backKey.setPressed(false);
        this.mc.options.rightKey.setPressed(false);
        this.mc.options.leftKey.setPressed(false);
        this.mc.options.jumpKey.setPressed(false);
        this.mc.options.sneakKey.setPressed(false);
    }

    @EventListener
    private void a(final SetScreenEvent setScreenEvent) {
        this.j();
        this.d.set(this.c);
        this.g = this.e;
        this.h = this.f;
    }

    @EventListener
    private void a(final TickEvent tickEvent) {
        if (this.mc.cameraEntity.isInsideWall()) {
            this.mc.getCameraEntity().noClip = true;
        }
        if (!this.j.isFirstPerson()) {
            this.mc.options.setPerspective(Perspective.FIRST_PERSON);
        }
        final Vec3d fromPolar = Vec3d.fromPolar(0.0f, this.e);
        final Vec3d fromPolar2 = Vec3d.fromPolar(0.0f, this.e + 90.0f);
        double n = 0.0;
        double n2 = 0.0;
        double n3 = 0.0;
        double n4 = 0.5;
        if (this.mc.options.sprintKey.isPressed()) {
            n4 = 1.0;
        }
        boolean b = false;
        if (this.l) {
            n = 0.0 + fromPolar.x * n4 * this.k;
            n3 = 0.0 + fromPolar.z * n4 * this.k;
            b = true;
        }
        if (this.m) {
            n -= fromPolar.x * n4 * this.k;
            n3 -= fromPolar.z * n4 * this.k;
            b = true;
        }
        boolean b2 = false;
        if (this.n) {
            n += fromPolar2.x * n4 * this.k;
            n3 += fromPolar2.z * n4 * this.k;
            b2 = true;
        }
        if (this.o) {
            n -= fromPolar2.x * n4 * this.k;
            n3 -= fromPolar2.z * n4 * this.k;
            b2 = true;
        }
        if (b && b2) {
            final double n5 = 1.0 / Math.sqrt(2.0);
            n *= n5;
            n3 *= n5;
        }
        if (this.p) {
            n2 = 0.0 + n4 * this.k;
        }
        if (this.q) {
            n2 -= n4 * this.k;
        }
        this.d.set(this.c);
        this.c.set(this.c.x + n, this.c.y + n2, this.c.z + n3);
    }

    @EventListener
    public void a(final KeyEvent keyEvent) {
        if (KeyUtils.b(292)) {
            return;
        }
        boolean b = true;
        if (this.mc.options.forwardKey.matchesKey(keyEvent.key, 0)) {
            this.l = (keyEvent.mode != 0);
            this.mc.options.forwardKey.setPressed(false);
        } else if (this.mc.options.backKey.matchesKey(keyEvent.key, 0)) {
            this.m = (keyEvent.mode != 0);
            this.mc.options.backKey.setPressed(false);
        } else if (this.mc.options.rightKey.matchesKey(keyEvent.key, 0)) {
            this.n = (keyEvent.mode != 0);
            this.mc.options.rightKey.setPressed(false);
        } else if (this.mc.options.leftKey.matchesKey(keyEvent.key, 0)) {
            this.o = (keyEvent.mode != 0);
            this.mc.options.leftKey.setPressed(false);
        } else if (this.mc.options.jumpKey.matchesKey(keyEvent.key, 0)) {
            this.p = (keyEvent.mode != 0);
            this.mc.options.jumpKey.setPressed(false);
        } else if (this.mc.options.sneakKey.matchesKey(keyEvent.key, 0)) {
            this.q = (keyEvent.mode != 0);
            this.mc.options.sneakKey.setPressed(false);
        } else {
            b = false;
        }
        if (b) {
            keyEvent.cancel();
        }
    }

    @EventListener
    private void a(final MouseButtonEvent event) {
        boolean b = true;
        if (this.mc.options.forwardKey.matchesMouse(event.button)) {
            this.l = (event.actions != 0);
            this.mc.options.forwardKey.setPressed(false);
        } else if (this.mc.options.backKey.matchesMouse(event.button)) {
            this.m = (event.actions != 0);
            this.mc.options.backKey.setPressed(false);
        } else if (this.mc.options.rightKey.matchesMouse(event.button)) {
            this.n = (event.actions != 0);
            this.mc.options.rightKey.setPressed(false);
        } else if (this.mc.options.leftKey.matchesMouse(event.button)) {
            this.o = (event.actions != 0);
            this.mc.options.leftKey.setPressed(false);
        } else if (this.mc.options.jumpKey.matchesMouse(event.button)) {
            this.p = (event.actions != 0);
            this.mc.options.jumpKey.setPressed(false);
        } else if (this.mc.options.sneakKey.matchesMouse(event.button)) {
            this.q = (event.actions != 0);
            this.mc.options.sneakKey.setPressed(false);
        } else {
            b = false;
        }
        if (b) {
            event.cancel();
        }
    }

    @EventListener
    private void a(final MouseScrolledEvent mouseScrolledEvent) {
        if (this.mc.currentScreen == null) {
            this.k += mouseScrolledEvent.amount * 0.25 * this.k;
            if (this.k < 0.1) {
                this.k = 0.1;
            }
            mouseScrolledEvent.cancel();
        }
    }

    @EventListener
    private void a(final ChunkMarkClosedEvent chunkMarkClosedEvent) {
        chunkMarkClosedEvent.cancel();
    }

    public void a(final double n, final double n2) {
        this.g = this.e;
        this.h = this.f;
        this.e += (float) n;
        this.f += (float) n2;
        this.f = MathHelper.clamp(this.f, -90.0f, 90.0f);
    }

    public double a(final float n) {
        return MathHelper.lerp(n, this.d.x, this.c.x);
    }

    public double b(final float n) {
        return MathHelper.lerp(n, this.d.y, this.c.y);
    }

    public double c(final float n) {
        return MathHelper.lerp(n, this.d.z, this.c.z);
    }

    public double d(final float n) {
        return MathHelper.lerp(n, this.g, this.e);
    }

    public double e(final float n) {
        return MathHelper.lerp(n, this.h, this.f);
    }
}
