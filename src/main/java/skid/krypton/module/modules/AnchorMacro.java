// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.hit.BlockHitResult;
import org.lwjgl.glfw.GLFW;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.TickEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.NumberSetting;
import skid.krypton.utils.BlockUtil;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.InventoryUtil;
import skid.krypton.utils.KeyUtils;

public final class AnchorMacro extends Module {
    private final NumberSetting c;
    private final NumberSetting d;
    private final NumberSetting e;
    private final NumberSetting f;
    private int keybind;
    private int h;
    private int i;

    public AnchorMacro() {
        super(EncryptedString.a("Anchor Macro"), EncryptedString.a("Automatically blows up respawn anchors for you"), -1, Category.a);
        this.c = new NumberSetting(EncryptedString.a("Switch Delay"), 0.0, 20.0, 0.0, 1.0);
        this.d = new NumberSetting(EncryptedString.a("Glowstone Delay"), 0.0, 20.0, 0.0, 1.0);
        this.e = new NumberSetting(EncryptedString.a("Explode Delay"), 0.0, 20.0, 0.0, 1.0);
        this.f = new NumberSetting(EncryptedString.a("Totem Slot"), 1.0, 9.0, 1.0, 1.0);
        this.keybind = 0;
        this.h = 0;
        this.i = 0;
        this.a(this.c, this.d, this.e, this.f);
    }

    @Override
    public void onEnable() {
        this.l();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventListener
    public void a(final TickEvent tickEvent) {
        if (this.b.currentScreen != null) {
            return;
        }
        if (this.j()) {
            return;
        }
        if (KeyUtils.b(1)) {
            this.k();
        }
    }

    private boolean j() {
        final boolean b = this.b.player.getMainHandStack().getItem().getComponents().contains(DataComponentTypes.FOOD) || this.b.player.getOffHandStack().getItem().getComponents().contains(DataComponentTypes.FOOD);
        final boolean b2 = this.b.player.getMainHandStack().getItem() instanceof ShieldItem || this.b.player.getOffHandStack().getItem() instanceof ShieldItem;
        final boolean b3 = GLFW.glfwGetMouseButton(this.b.getWindow().getHandle(), 1) == 1;
        return (b || b2) && b3;
    }

    private void k() {
        if (!(this.b.crosshairTarget instanceof BlockHitResult blockHitResult)) {
            return;
        }
        if (!BlockUtil.a(((BlockHitResult) this.b.crosshairTarget).getBlockPos(), Blocks.RESPAWN_ANCHOR)) {
            return;
        }
        this.b.options.useKey.setPressed(false);
        if (BlockUtil.b(blockHitResult.getBlockPos())) {
            this.a(blockHitResult);
        } else if (BlockUtil.a(blockHitResult.getBlockPos())) {
            this.b(blockHitResult);
        }
    }

    private void a(final BlockHitResult blockHitResult) {
        if (!this.b.player.getMainHandStack().isOf(Items.GLOWSTONE)) {
            if (this.keybind < this.c.f()) {
                ++this.keybind;
                return;
            }
            this.keybind = 0;
            InventoryUtil.a(Items.GLOWSTONE);
        }
        if (this.b.player.getMainHandStack().isOf(Items.GLOWSTONE)) {
            if (this.h < this.d.f()) {
                ++this.h;
                return;
            }
            this.h = 0;
            BlockUtil.a(blockHitResult, true);
        }
    }

    private void b(final BlockHitResult blockHitResult) {
        final int selectedSlot = this.f.f() - 1;
        if (this.b.player.getInventory().selectedSlot != selectedSlot) {
            if (this.keybind < this.c.f()) {
                ++this.keybind;
                return;
            }
            this.keybind = 0;
            this.b.player.getInventory().selectedSlot = selectedSlot;
        }
        if (this.b.player.getInventory().selectedSlot == selectedSlot) {
            if (this.i < this.e.f()) {
                ++this.i;
                return;
            }
            this.i = 0;
            BlockUtil.a(blockHitResult, true);
        }
    }

    private void l() {
        this.keybind = 0;
        this.h = 0;
        this.i = 0;
    }
}
