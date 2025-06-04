// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import net.minecraft.block.BambooBlock;
import net.minecraft.block.BambooShootBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.*;
import net.minecraft.registry.tag.BlockTags;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.AttackBlockEvent;
import skid.krypton.events.*;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.BooleanSetting;
import skid.krypton.setting.settings.NumberSetting;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.InventoryUtil;

import java.util.function.Predicate;

public final class AutoTool extends Module {
    private final BooleanSetting c;
    private final NumberSetting d;
    private boolean e;
    private boolean enabled;
    private int keybind;
    private int h;

    public AutoTool() {
        super(EncryptedString.a("Auto Tool"), EncryptedString.a("Module that automatically switches to best tool"), -1, Category.b);
        this.c = new BooleanSetting(EncryptedString.a("Anti Break"), true);
        this.d = new NumberSetting(EncryptedString.a("Anti Break Percentage"), 1.0, 100.0, 5.0, 1.0);
        this.a(this.c, this.d);
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
    public void a(final TickEvent tickEvent) {
        if (this.keybind <= 0 && this.enabled && this.h != -1) {
            InventoryUtil.a(this.h);
            this.enabled = false;
        } else {
            --this.keybind;
        }
        this.e = this.b.options.attackKey.isPressed();
    }

    @EventListener
    public void a(final AttackBlockEvent attackBlockEvent) {
        final BlockState method_8320 = this.b.world.method_8320(attackBlockEvent.a);
        final ItemStack method_8321 = this.b.player.method_6047();
        double n = -1.0;
        this.h = -1;
        for (int i = 0; i < 9; ++i) {
            final double a = a(this.b.player.method_31548().method_5438(i), method_8320, itemStack -> !this.b(itemStack));
            if (a >= 0.0) {
                if (a > n) {
                    this.h = i;
                    n = a;
                }
            }
        }
        if ((this.h != -1 && n > a(method_8321, method_8320, itemStack2 -> !this.b(itemStack2))) || this.b(method_8321) || !a(method_8321)) {
            InventoryUtil.a(this.h);
        }
        final ItemStack method_8322 = this.b.player.method_6047();
        if (this.b(method_8322) && a(method_8322)) {
            this.b.options.attackKey.setPressed(false);
            attackBlockEvent.cancel();
        }
    }

    public static double a(final ItemStack itemStack, final BlockState blockState, final Predicate predicate) {
        if (!predicate.test(itemStack) || !a(itemStack)) {
            return -1.0;
        }
        if (!itemStack.isSuitableFor(blockState) && (!(itemStack.getItem() instanceof SwordItem) || (!(blockState.method_26204() instanceof BambooBlock) && !(blockState.method_26204() instanceof BambooShootBlock))) && (!(itemStack.getItem() instanceof ShearsItem) || !(blockState.method_26204() instanceof LeavesBlock)) && !blockState.method_26164(BlockTags.WOOL)) {
            return -1.0;
        }
        return 0.0 + itemStack.getMiningSpeedMultiplier(blockState) * 1000.0f;
    }

    public static boolean a(final ItemStack itemStack) {
        return a(itemStack.getItem());
    }

    public static boolean a(final Item item) {
        return item instanceof ToolItem || item instanceof ShearsItem;
    }

    private boolean b(final ItemStack itemStack) {
        return this.c.c() && itemStack.getMaxDamage() - itemStack.getDamage() < itemStack.getMaxDamage() * this.d.f() / 100;
    }
}
