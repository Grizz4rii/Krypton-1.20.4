package skid.krypton.module.modules.render;

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
import skid.krypton.module.setting.BooleanSetting;
import skid.krypton.module.setting.NumberSetting;
import skid.krypton.utils.BlockUtil;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.RenderUtils;

import java.awt.*;
import java.util.Iterator;

public final class StorageESP extends Module {
    private final NumberSetting alpha = new NumberSetting(EncryptedString.of("Alpha"), 1.0, 255.0, 125.0, 1.0);
    private final BooleanSetting tracers = new BooleanSetting(EncryptedString.of("Tracers"), false).setDescription(EncryptedString.of("Draws a line from your player to the storage block"));
    private final BooleanSetting chests = new BooleanSetting(EncryptedString.of("Chests"), true);
    private final BooleanSetting enderChests = new BooleanSetting(EncryptedString.of("Ender Chests"), true);
    private final BooleanSetting spawners = new BooleanSetting(EncryptedString.of("Spawners"), true);
    private final BooleanSetting shulkerBoxes = new BooleanSetting(EncryptedString.of("Shulker Boxes"), true);
    private final BooleanSetting furnaces = new BooleanSetting(EncryptedString.of("Furnaces"), true);
    private final BooleanSetting barrels = new BooleanSetting(EncryptedString.of("Barrels"), true);
    private final BooleanSetting enchant = new BooleanSetting(EncryptedString.of("Enchanting Tables"), true);
    private final BooleanSetting pistons = new BooleanSetting(EncryptedString.of("Pistons"), true);

    public StorageESP() {
        super(EncryptedString.of("Storage ESP"), EncryptedString.of("Renders storage blocks through walls"), -1, Category.RENDER);
        this.addSettings(this.alpha, this.tracers, this.chests, this.enderChests, this.spawners, this.shulkerBoxes, this.furnaces, this.barrels, this.enchant, this.pistons);
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
    public void onRender3D(final Render3DEvent render3DEvent) {
        this.renderStorageBlocks(render3DEvent);
    }

    private Color getBlockEntityColor(final BlockEntity blockEntity, final int a) {
        if (blockEntity instanceof TrappedChestBlockEntity && this.chests.getValue()) {
            return new Color(200, 91, 0, a);
        }
        if (blockEntity instanceof ChestBlockEntity && this.chests.getValue()) {
            return new Color(156, 91, 0, a);
        }
        if (blockEntity instanceof EnderChestBlockEntity && this.enderChests.getValue()) {
            return new Color(117, 0, 255, a);
        }
        if (blockEntity instanceof MobSpawnerBlockEntity && this.spawners.getValue()) {
            return new Color(138, 126, 166, a);
        }
        if (blockEntity instanceof ShulkerBoxBlockEntity && this.shulkerBoxes.getValue()) {
            return new Color(134, 0, 158, a);
        }
        if (blockEntity instanceof FurnaceBlockEntity && this.furnaces.getValue()) {
            return new Color(125, 125, 125, a);
        }
        if (blockEntity instanceof BarrelBlockEntity && this.barrels.getValue()) {
            return new Color(255, 140, 140, a);
        }
        if (blockEntity instanceof EnchantingTableBlockEntity && this.enchant.getValue()) {
            return new Color(80, 80, 255, a);
        }
        if (blockEntity instanceof PistonBlockEntity && this.pistons.getValue()) {
            return new Color(35, 226, 0, a);
        }
        return new Color(255, 255, 255, 0);
    }

    private void renderStorageBlocks(final Render3DEvent render3DEvent) {
        final Camera camera = this.mc.gameRenderer.getCamera();
        if (camera != null) {
            final MatrixStack a = render3DEvent.matrixStack;
            render3DEvent.matrixStack.push();
            final Vec3d pos = camera.getPos();
            a.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
            a.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0f));
            a.translate(-pos.x, -pos.y, -pos.z);
        }
        final Iterator iterator = BlockUtil.getLoadedChunks().iterator();
        while (iterator.hasNext()) {
            for (final Object next : ((WorldChunk) iterator.next()).getBlockEntityPositions()) {
                final BlockEntity getBlockEntity = this.mc.world.getBlockEntity((BlockPos) next);
                RenderUtils.renderFilledBox(render3DEvent.matrixStack, ((BlockPos) next).getX() + 0.1f, ((BlockPos) next).getY() + 0.05f, ((BlockPos) next).getZ() + 0.1f, ((BlockPos) next).getX() + 0.9f, ((BlockPos) next).getY() + 0.85f, ((BlockPos) next).getZ() + 0.9f, this.getBlockEntityColor(getBlockEntity, this.alpha.getIntValue()));
                if (this.tracers.getValue()) {
                    RenderUtils.renderLine(render3DEvent.matrixStack, this.getBlockEntityColor(getBlockEntity, 255), this.mc.crosshairTarget.getPos(), new Vec3d(((BlockPos) next).getX() + 0.5, ((BlockPos) next).getY() + 0.5, ((BlockPos) next).getZ() + 0.5));
                }
            }
        }
        render3DEvent.matrixStack.pop();
    }
}
