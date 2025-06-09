package skid.krypton.module.modules.combat;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.PreItemUseEvent;
import skid.krypton.event.events.TickEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.BindSetting;
import skid.krypton.module.setting.NumberSetting;
import skid.krypton.utils.BlockUtil;

public final class AutoCrystal extends Module {
    // Key bind untuk aktifasi (default: right mouse button)
    private final BindSetting activateKey = new BindSetting("Activate Key", GLFW.GLFW_MOUSE_BUTTON_RIGHT, false)
            .setDescription("Key that does the crystalling");

    private final NumberSetting placeDelay = new NumberSetting("Place Delay", 0.0, 20.0, 0.0, 1.0);
    private final NumberSetting breakDelay = new NumberSetting("Break Delay", 0.0, 20.0, 0.0, 1.0);

    private int placeDelayCounter;
    private int breakDelayCounter;
    public boolean isActive;

    public AutoCrystal() {
        super("Auto Crystal", "Automatically crystals fast for you", -1, Category.COMBAT);
        this.addSettings(this.activateKey, this.placeDelay, this.breakDelay);
    }

    @Override
    public void onEnable() {
        this.resetCounters();
        this.isActive = false;
        super.onEnable();
    }

    private void resetCounters() {
        this.placeDelayCounter = 0;
        this.breakDelayCounter = 0;
    }

    @EventListener
    public void onTick(final TickEvent tickEvent) {
        // Jangan jalankan kalau ada GUI terbuka
        if (this.mc.currentScreen != null) {
            return;
        }
        this.updateCounters();

        if (this.mc.player.isUsingItem()) {
            return;
        }
        if (!this.isKeyActive()) {
            return;
        }
        if (this.mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL) {
            return;
        }
        this.handleInteraction();
    }

    private void updateCounters() {
        if (this.placeDelayCounter > 0) {
            --this.placeDelayCounter;
        }
        if (this.breakDelayCounter > 0) {
            --this.breakDelayCounter;
        }
    }

    // Cek apakah tombol aktifasi ditekan
    private boolean isKeyActive() {
        int key = this.activateKey.getValue();
        if (key != -1 && !InputUtil.isKeyPressed(this.mc.getWindow().getHandle(), key)) {
            this.resetCounters();
            this.isActive = false;
            return false;
        }
        this.isActive = true;
        return true;
    }

    private void handleInteraction() {
        final HitResult crosshairTarget = this.mc.crosshairTarget;
        if (crosshairTarget instanceof BlockHitResult) {
            this.handleBlockInteraction((BlockHitResult) crosshairTarget);
        } else if (crosshairTarget instanceof EntityHitResult entityHitResult) {
            this.handleEntityInteraction(entityHitResult);
        }
    }

    private void handleBlockInteraction(final BlockHitResult blockHitResult) {
        if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }
        if (this.placeDelayCounter > 0) {
            return;
        }

        final BlockPos blockPos = blockHitResult.getBlockPos();

        // Cek apakah bloknya obsidian atau bedrock dan tempatnya valid untuk crystal
        if ((BlockUtil.isBlockAtPosition(blockPos, Blocks.OBSIDIAN) || BlockUtil.isBlockAtPosition(blockPos, Blocks.BEDROCK))
                && this.isValidCrystalPlacement(blockPos)) {

            BlockUtil.interactWithBlock(blockHitResult, true);
            this.placeDelayCounter = this.placeDelay.getIntValue();
        }
    }

    private void handleEntityInteraction(final EntityHitResult entityHitResult) {
        if (this.breakDelayCounter > 0) {
            return;
        }

        final Entity entity = entityHitResult.getEntity();

        // Hanya serang End Crystal saja
        if (!(entity instanceof EndCrystalEntity)) {
            return;
        }

        this.mc.interactionManager.attackEntity(this.mc.player, entity);
        this.mc.player.swingHand(Hand.MAIN_HAND);
        this.breakDelayCounter = this.breakDelay.getIntValue();
    }

    @EventListener
    public void onPreItemUse(final PreItemUseEvent preItemUseEvent) {
        if (this.mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL) {
            return;
        }
        if (!(this.mc.crosshairTarget instanceof BlockHitResult blockHitResult)) {
            return;
        }
        if (this.mc.crosshairTarget.getType() != HitResult.Type.BLOCK) {
            return;
        }

        final BlockPos blockPos = blockHitResult.getBlockPos();

        if (BlockUtil.isBlockAtPosition(blockPos, Blocks.OBSIDIAN) || BlockUtil.isBlockAtPosition(blockPos, Blocks.BEDROCK)) {
            preItemUseEvent.cancel();
        }
    }

    private boolean isValidCrystalPlacement(final BlockPos blockPos) {
        final BlockPos up = blockPos.up();

        // Blok atas harus kosong
        if (!this.mc.world.isAir(up)) {
            return false;
        }

        // Cek apakah ada entitas yang menghalangi di kotak atas
        final int x = up.getX();
        final int y = up.getY();
        final int z = up.getZ();

        return this.mc.world.getOtherEntities(null, new Box(x, y, z, x + 1.0, y + 2.0, z + 1.0)).isEmpty();
    }
            }
