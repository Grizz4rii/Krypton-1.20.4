package skid.krypton.module.modules;

import net.minecraft.block.entity.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.WorldChunk;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.Render3DEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.BooleanSetting;
import skid.krypton.setting.settings.NumberSetting;
import skid.krypton.utils.BlockUtil;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.RenderUtils;

import java.awt.*;
import java.util.Iterator;

public final class StorageESP extends Module {
    private final NumberSetting c;
    private final BooleanSetting d;
    private final BooleanSetting e;
    private final BooleanSetting f;
    private final BooleanSetting g;
    private final BooleanSetting h;
    private final BooleanSetting i;
    private final BooleanSetting j;
    private final BooleanSetting k;
    private final BooleanSetting l;

    public StorageESP() {
        super(EncryptedString.a("Storage ESP"), EncryptedString.a("Renders storage blocks through walls"), -1, Category.d);
        this.c = new NumberSetting(EncryptedString.a("Alpha"), 1.0, 255.0, 125.0, 1.0);
        this.d = new BooleanSetting(EncryptedString.a("Tracers"), false).a(EncryptedString.a("Draws a line from your player to the storage block"));
        this.e = new BooleanSetting(EncryptedString.a("Chests"), true);
        this.f = new BooleanSetting(EncryptedString.a("Ender Chests"), true);
        this.g = new BooleanSetting(EncryptedString.a("Spawners"), true);
        this.h = new BooleanSetting(EncryptedString.a("Shulker Boxes"), true);
        this.i = new BooleanSetting(EncryptedString.a("Furnaces"), true);
        this.j = new BooleanSetting(EncryptedString.a("Barrels"), true);
        this.k = new BooleanSetting(EncryptedString.a("Enchanting Tables"), true);
        this.l = new BooleanSetting(EncryptedString.a("Pistons"), true);
        this.a(this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventListener
    public void a(final Render3DEvent render3DEvent) {
        this.b(render3DEvent);
    }

    private Color a(final BlockEntity blockEntity, final int a) {
        if (blockEntity instanceof TrappedChestBlockEntity && this.e.c()) {
            return new Color(200, 91, 0, a);
        }
        if (blockEntity instanceof ChestBlockEntity && this.e.c()) {
            return new Color(156, 91, 0, a);
        }
        if (blockEntity instanceof EnderChestBlockEntity && this.f.c()) {
            return new Color(117, 0, 255, a);
        }
        if (blockEntity instanceof MobSpawnerBlockEntity && this.g.c()) {
            return new Color(138, 126, 166, a);
        }
        if (blockEntity instanceof ShulkerBoxBlockEntity && this.h.c()) {
            return new Color(134, 0, 158, a);
        }
        if (blockEntity instanceof FurnaceBlockEntity && this.i.c()) {
            return new Color(125, 125, 125, a);
        }
        if (blockEntity instanceof BarrelBlockEntity && this.j.c()) {
            return new Color(255, 140, 140, a);
        }
        if (blockEntity instanceof EnchantingTableBlockEntity && this.k.c()) {
            return new Color(80, 80, 255, a);
        }
        if (blockEntity instanceof PistonBlockEntity && this.l.c()) {
            return new Color(35, 226, 0, a);
        }
        return new Color(255, 255, 255, 0);
    }

    private void b(final Render3DEvent render3DEvent) {
        final Camera camera = this.b.gameRenderer.getCamera();
        if (camera != null) {
            final MatrixStack a = render3DEvent.a;
            render3DEvent.a.push();
            final Vec3d pos = camera.getPos();
            a.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
            a.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0f));
            a.translate(-pos.x, -pos.y, -pos.z);
        }
        final Iterator iterator = BlockUtil.a().iterator();
        while (iterator.hasNext()) {
            for (final Object next : ((WorldChunk) iterator.next()).getBlockEntityPositions()) {
                final BlockEntity getBlockEntity = this.b.world.getBlockEntity((BlockPos) next);
                RenderUtils.a(render3DEvent.a, ((BlockPos) next).getX() + 0.1f, ((BlockPos) next).getY() + 0.05f, ((BlockPos) next).getZ() + 0.1f, ((BlockPos) next).getX() + 0.9f, ((BlockPos) next).getY() + 0.85f, ((BlockPos) next).getZ() + 0.9f, this.a(getBlockEntity, this.c.f()));
                if (this.d.c()) {
                    RenderUtils.a(render3DEvent.a, this.a(getBlockEntity, 255), this.b.crosshairTarget.getPos(), new Vec3d(((BlockPos) next).getX() + 0.5, ((BlockPos) next).getY() + 0.5, ((BlockPos) next).getZ() + 0.5));
                }
            }
        }
        render3DEvent.a.pop();
    }

    private static byte[] cqqihypzusnwqub() {
        return new byte[]{92, 17, 20, 87, 27, 114, 65, 105, 53, 108, 3, 41, 70, 16, 118, 35, 90, 10, 112, 62, 112, 75, 21, 31, 115, 25, 96, 75, 96, 73, 83, 74, 21, 112, 92, 8, 113, 91, 116, 39, 58, 80, 39, 91, 78, 73, 126, 14, 85, 21, 44, 27, 38};
    }
}
