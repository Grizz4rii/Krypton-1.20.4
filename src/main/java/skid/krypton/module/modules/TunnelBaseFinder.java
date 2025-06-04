// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import a.b.c.bn;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.InfestedBlock;
import net.minecraft.block.entity.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import skid.krypton.auth.EmbedSender;
import skid.krypton.enums.Enum4;
import skid.krypton.events.*;
import skid.krypton.mixin.MobSpawnerLogicAccessor;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.BooleanSetting;
import skid.krypton.setting.settings.NumberSetting;
import skid.krypton.setting.settings.StringSetting;
import skid.krypton.utils.BlockUtil;
import skid.krypton.utils.EnchantmentUtil;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.InventoryUtil;

import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public final class TunnelBaseFinder extends Module {
    private final NumberSetting c;
    private final BooleanSetting d;
    private final BooleanSetting e;
    private final NumberSetting f;
    private final BooleanSetting g;
    private final NumberSetting h;
    private final BooleanSetting i;
    private final StringSetting j;
    private final BooleanSetting k;
    private final NumberSetting l;
    private static final double m = 0.1;
    private Enum4 n;
    private int o;
    private int p;
    private int q;
    private Vec3d r;
    private boolean s;
    private boolean t;
    private int u;
    private int v;
    private double w;

    public TunnelBaseFinder() {
        super(EncryptedString.a("Tunnel Base Finder"), EncryptedString.a("Finds bases digging tunnel"), -1, Category.c);
        this.c = new NumberSetting(EncryptedString.a("Minimum Storage"), 1.0, 500.0, 100.0, 1.0);
        this.d = new BooleanSetting(EncryptedString.a("Spawners"), true);
        this.e = new BooleanSetting(EncryptedString.a("Auto Totem Buy"), true);
        this.f = new NumberSetting(EncryptedString.a("Totem Slot"), 1.0, 9.0, 8.0, 1.0);
        this.g = new BooleanSetting(EncryptedString.a("Auto Mend"), true).a(EncryptedString.a("Automatically repairs pickaxe."));
        this.h = new NumberSetting(EncryptedString.a("XP Bottle Slot"), 1.0, 9.0, 9.0, 1.0);
        this.i = new BooleanSetting(EncryptedString.a("Discord Notification"), false);
        this.j = new StringSetting(EncryptedString.a("Webhook"), "");
        this.k = new BooleanSetting(EncryptedString.a("Totem Check"), true);
        this.l = new NumberSetting(EncryptedString.a("Totem Check Time"), 1.0, 120.0, 20.0, 1.0);
        this.s = false;
        this.t = false;
        this.u = 0;
        this.v = 0;
        this.w = 0.0;
        this.a(this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.n = this.k();
        this.o = 0;
        this.q = 0;
        this.p = 0;
        this.r = null;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.b.options.leftKey.setPressed(false);
        this.b.options.rightKey.setPressed(false);
        this.b.options.forwardKey.setPressed(false);
    }

    @EventListener
    public void a(final TickEvent tickEvent) {
        if (this.n == null) {
            return;
        }
        final Module moduleByClass = Krypton.INSTANCE.MODULE_MANAGER.getModuleByClass(AutoEat.class);
        if (moduleByClass.isEnabled() && ((AutoEat) moduleByClass).j()) {
            return;
        }
        final int n = (this.a(this.n) + 90 * this.q) % 360;
        if (this.b.player.method_36454() != n) {
            this.b.player.method_36456((float) n);
        }
        if (this.b.player.method_36455() != 2.0f) {
            this.b.player.method_36457(2.0f);
        }
        this.b(this.k());
        if (this.o > 0) {
            this.b.options.forwardKey.setPressed(false);
            --this.o;
            return;
        }
        this.l();
        if (this.e.c()) {
            final int n2 = this.f.f() - 1;
            if (!this.b.player.method_31548().method_5438(n2).isOf(Items.TOTEM_OF_UNDYING)) {
                if (this.v < 30 && !this.t) {
                    ++this.v;
                    return;
                }
                this.v = 0;
                this.t = true;
                if (this.b.player.method_31548().selectedSlot != n2) {
                    InventoryUtil.a(n2);
                }
                final ScreenHandler field_7512 = this.b.player.field_7512;
                if (!(this.b.player.field_7512 instanceof GenericContainerScreenHandler) || ((GenericContainerScreenHandler) field_7512).getRows() != 3) {
                    this.b.getNetworkHandler().sendChatCommand("shop");
                    this.o = 10;
                    return;
                }
                if (((GenericContainerScreenHandler) field_7512).method_7611(11).getStack().isOf(Items.END_STONE)) {
                    this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, 13, 0, SlotActionType.PICKUP, (PlayerEntity) this.b.player);
                    this.o = 10;
                    return;
                }
                if (((GenericContainerScreenHandler) field_7512).method_7611(16).getStack().isOf(Items.EXPERIENCE_BOTTLE)) {
                    this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, 13, 0, SlotActionType.PICKUP, (PlayerEntity) this.b.player);
                    this.o = 10;
                    return;
                }
                this.b.player.networkHandler.method_52787((Packet) new PlayerActionC2SPacket(PlayerActionC2SPacket$Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, Direction.DOWN));
                if (((GenericContainerScreenHandler) field_7512).method_7611(23).getStack().isOf(Items.LIME_STAINED_GLASS_PANE)) {
                    this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, 23, 0, SlotActionType.PICKUP, (PlayerEntity) this.b.player);
                    this.o = 10;
                    return;
                }
                this.b.getNetworkHandler().sendChatCommand("shop");
                this.o = 10;
                return;
            } else if (this.t) {
                if (this.b.currentScreen != null) {
                    this.b.player.method_7346();
                    this.o = 20;
                }
                this.t = false;
                this.v = 0;
            }
        }
        if (this.s) {
            final int n3 = this.h.f() - 1;
            final ItemStack method_5438 = this.b.player.method_31548().method_5438(n3);
            if (this.b.player.method_31548().selectedSlot != n3) {
                InventoryUtil.a(n3);
            }
            if (!method_5438.isOf(Items.EXPERIENCE_BOTTLE)) {
                final ScreenHandler field_7513 = this.b.player.field_7512;
                if (!(this.b.player.field_7512 instanceof GenericContainerScreenHandler) || ((GenericContainerScreenHandler) field_7513).getRows() != 3) {
                    this.b.getNetworkHandler().sendChatCommand("shop");
                    this.o = 10;
                    return;
                }
                if (((GenericContainerScreenHandler) field_7513).method_7611(11).getStack().isOf(Items.END_STONE)) {
                    this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, 13, 0, SlotActionType.PICKUP, (PlayerEntity) this.b.player);
                    this.o = 10;
                    return;
                }
                if (((GenericContainerScreenHandler) field_7513).method_7611(16).getStack().isOf(Items.EXPERIENCE_BOTTLE)) {
                    this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, 16, 0, SlotActionType.PICKUP, (PlayerEntity) this.b.player);
                    this.o = 10;
                    return;
                }
                if (((GenericContainerScreenHandler) field_7513).method_7611(17).getStack().isOf(Items.LIME_STAINED_GLASS_PANE)) {
                    this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, 17, 0, SlotActionType.PICKUP, (PlayerEntity) this.b.player);
                    this.o = 10;
                    return;
                }
                this.b.player.networkHandler.method_52787((Packet) new PlayerActionC2SPacket(PlayerActionC2SPacket$Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, Direction.DOWN));
                if (((GenericContainerScreenHandler) field_7513).method_7611(23).getStack().isOf(Items.LIME_STAINED_GLASS_PANE)) {
                    this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, 23, 0, SlotActionType.PICKUP, (PlayerEntity) this.b.player);
                    this.o = 10;
                    return;
                }
                this.b.getNetworkHandler().sendChatCommand("shop");
                this.o = 10;
            } else {
                if (this.b.currentScreen != null) {
                    this.b.player.method_7346();
                    this.o = 20;
                    return;
                }
                if (!EnchantmentUtil.a(this.b.player.method_6079(), Enchantments.MENDING)) {
                    this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, 36 + this.u, 40, SlotActionType.SWAP, (PlayerEntity) this.b.player);
                    this.o = 20;
                    return;
                }
                if (this.b.player.method_6079().getDamage() > 0) {
                    final ActionResult interactItem = this.b.interactionManager.interactItem(this.b.player, Hand.MAIN_HAND);
                    if (interactItem.isAccepted() && interactItem.shouldSwingHand()) {
                        this.b.player.method_6104(Hand.MAIN_HAND);
                    }
                    this.o = 1;
                    return;
                }
                this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, 36 + this.u, 40, SlotActionType.SWAP, (PlayerEntity) this.b.player);
                this.s = false;
            }
        } else {
            if (this.g.c()) {
                final ItemStack method_5439 = this.b.player.method_6047();
                if (EnchantmentUtil.a(method_5439, Enchantments.MENDING) && method_5439.getMaxDamage() - method_5439.getDamage() < 100) {
                    this.s = true;
                    this.u = this.b.player.method_31548().selectedSlot;
                }
            }
            if (this.k.c()) {
                final boolean equals = this.b.player.method_6079().getItem().equals(Items.TOTEM_OF_UNDYING);
                final Module moduleByClass2 = Krypton.INSTANCE.MODULE_MANAGER.getModuleByClass(AutoTotem.class);
                if (equals) {
                    this.w = 0.0;
                } else if (moduleByClass2.isEnabled() && ((AutoTotem) moduleByClass2).a(Items.TOTEM_OF_UNDYING) != -1) {
                    this.w = 0.0;
                } else {
                    ++this.w;
                }
                if (this.w > this.l.a()) {
                    this.a("Your totem exploded", (int) this.b.player.method_23317(), (int) this.b.player.method_23318(), (int) this.b.player.method_23321());
                    return;
                }
            }
            boolean a = false;
            final HitResult crosshairTarget = this.b.crosshairTarget;
            if (this.b.crosshairTarget instanceof BlockHitResult) {
                final BlockPos blockPos = ((BlockHitResult) crosshairTarget).getBlockPos();
                if (!BlockUtil.a(blockPos, Blocks.AIR)) {
                    a = this.a(blockPos, this.b.player.method_5735());
                }
            }
            if (a) {
                this.c(true);
            }
            final boolean a2 = this.a(this.b.player.method_5735(), 3);
            boolean b = false;
            final HitResult crosshairTarget2 = this.b.crosshairTarget;
            if (this.b.crosshairTarget instanceof BlockHitResult) {
                b = (this.b.player.method_5836(1.0f).distanceTo(Vec3d.ofCenter(((BlockHitResult) crosshairTarget2).getBlockPos())) > 3.0);
            }
            if (!a && (!b || !a2)) {
                ++this.q;
                this.r = this.b.player.method_19538();
                this.o = 5;
                return;
            }
            this.b.options.forwardKey.setPressed(a2 && b);
            if (this.q > 0 && this.r != null && this.b.player.method_19538().distanceTo(this.r) > 1.0) {
                this.r = this.b.player.method_19538();
                final Direction rotateYCounterclockwise = this.b.player.method_5735().rotateYCounterclockwise();
                BlockPos blockPos2 = this.b.player.method_24515().up().offset(rotateYCounterclockwise);
                for (int i = 0; i < 5; ++i) {
                    blockPos2 = blockPos2.offset(rotateYCounterclockwise);
                    if (!this.b.world.method_8320(blockPos2).method_26204().equals(Blocks.AIR)) {
                        if (this.a(blockPos2, rotateYCounterclockwise) && this.a(blockPos2.offset(rotateYCounterclockwise), rotateYCounterclockwise)) {
                            --this.q;
                            this.o = 5;
                        }
                        return;
                    }
                }
                throw new IllegalAccessException();
            }
        }
    }

    private int a(final Enum4 enum4) {
        if (enum4 == Enum4.a) {
            return 180;
        }
        if (enum4 == Enum4.b) {
            return 0;
        }
        if (enum4 == Enum4.c) {
            return 270;
        }
        if (enum4 == Enum4.d) {
            return 90;
        }
        return Math.round(this.b.player.method_36454());
    }

    private boolean a(final Direction direction, final int n) {
        final BlockPos down = this.b.player.method_24515().down();
        final BlockPos method_24515 = this.b.player.method_24515();
        for (int i = 0; i < n; ++i) {
            final BlockPos offset = down.offset(direction, i);
            final BlockPos offset2 = method_24515.offset(direction, i);
            if (this.b.world.method_8320(offset).method_26215() || !this.a(offset)) {
                return false;
            }
            if (!this.b.world.method_8320(offset2).method_26215()) {
                return false;
            }
        }
        return true;
    }

    private boolean a(final BlockPos blockPos, final Direction direction) {
        final BlockPos offset = blockPos.offset(direction);
        final Direction rotateYClockwise = direction.rotateYClockwise();
        final Direction up = Direction.UP;
        final BlockPos offset2 = blockPos.offset(Direction.UP, 2);
        final BlockPos offset3 = blockPos.offset(Direction.DOWN, -2);
        final BlockPos offset4 = offset2.offset(rotateYClockwise, -1);
        if (!this.a(offset4) || this.b.world.method_8320(offset4).method_26204() == Blocks.GRAVEL) {
            return false;
        }
        if (!this.a(offset3.offset(rotateYClockwise, -1))) {
            return false;
        }
        final BlockPos offset5 = blockPos.offset(rotateYClockwise, 2);
        final BlockPos offset6 = blockPos.offset(rotateYClockwise, -2);
        if (!this.a(offset5.offset(up, -1))) {
            return false;
        }
        if (!this.a(offset6.offset(up, -1))) {
            return false;
        }
        while (this.a(offset.offset(rotateYClockwise, -1).offset(up, -1))) {
        }
        return false;
    }

    private boolean a(final BlockPos blockPos) {
        return this.a(this.b.world.method_8320(blockPos).method_26204());
    }

    private boolean a(final Block block) {
        return block != Blocks.LAVA && block != Blocks.WATER;
    }

    private boolean b(final BlockPos blockPos) {
        return this.b.world.method_8320(blockPos).method_26204() instanceof InfestedBlock;
    }

    private void b(final Enum4 enum4) {
        final double method_23317 = this.b.player.method_23317();
        final double method_23318 = this.b.player.method_23321();
        final double floor = Math.floor(method_23318);
        final double n = Math.floor(method_23317) + 0.5 - method_23317;
        final double n2 = floor + 0.5 - method_23318;
        this.b.options.leftKey.setPressed(false);
        this.b.options.rightKey.setPressed(false);
        boolean b = false;
        boolean b2 = false;
        if (enum4 == Enum4.b) {
            if (n > 0.1) {
                b2 = true;
            } else if (n < -0.1) {
                b = true;
            }
        }
        if (enum4 == Enum4.a) {
            if (n > 0.1) {
                b = true;
            } else if (n < -0.1) {
                b2 = true;
            }
        }
        if (enum4 == Enum4.d) {
            if (n2 > 0.1) {
                b2 = true;
            } else if (n2 < -0.1) {
                b = true;
            }
        }
        if (enum4 == Enum4.c) {
            if (n2 > 0.1) {
                b = true;
            } else if (n2 < -0.1) {
                b2 = true;
            }
        }
        if (b) {
            this.b.options.rightKey.setPressed(true);
        }
        if (b2) {
            this.b.options.leftKey.setPressed(true);
        }
    }

    private void c(final boolean b) {
        if (!this.b.player.method_6115()) {
            if (b && this.b.crosshairTarget != null && this.b.crosshairTarget.getType() == HitResult$Type.BLOCK) {
                final BlockHitResult blockHitResult = (BlockHitResult) this.b.crosshairTarget;
                final BlockPos blockPos = ((BlockHitResult) this.b.crosshairTarget).getBlockPos();
                if (!this.b.world.method_8320(blockPos).method_26215()) {
                    final Direction side = blockHitResult.getSide();
                    if (this.b.interactionManager.updateBlockBreakingProgress(blockPos, side)) {
                        this.b.particleManager.addBlockBreakingParticles(blockPos, side);
                        this.b.player.method_6104(Hand.MAIN_HAND);
                    }
                }
            } else {
                this.b.interactionManager.cancelBlockBreaking();
            }
        }
    }

    private Enum4 k() {
        float n = this.b.player.method_36454() % 360.0f;
        if (n < 0.0f) {
            n += 360.0f;
        }
        if (n >= 45.0f && n < 135.0f) {
            return Enum4.d;
        }
        if (n >= 135.0f && n < 225.0f) {
            return Enum4.a;
        }
        if (n >= 225.0f && n < 315.0f) {
            return Enum4.c;
        }
        return Enum4.b;
    }

    private void l() {
        int n = 0;
        int n2 = 0;
        BlockPos blockPos = null;
        final Iterator iterator = BlockUtil.a().iterator();
        while (iterator.hasNext()) {
            for (final Object next : ((WorldChunk) iterator.next()).method_12021()) {
                final BlockEntity method_8321 = this.b.world.method_8321((BlockPos) next);
                if (this.d.c() && method_8321 instanceof MobSpawnerBlockEntity) {
                    final String string = ((MobSpawnerLogicAccessor) ((MobSpawnerBlockEntity) method_8321).getLogic()).getSpawnEntry((World) this.b.world, this.b.world.method_8409(), (BlockPos) next).getNbt().getString("id");
                    if (string != "minecraft:cave_spider" && string != "minecraft:spider") {
                        ++n2;
                        blockPos = (BlockPos) next;
                    }
                }
                if (!(method_8321 instanceof ChestBlockEntity) && !(method_8321 instanceof EnderChestBlockEntity)) {
                    if (method_8321 instanceof ShulkerBoxBlockEntity) {
                        throw new RuntimeException();
                    }
                    if (!(method_8321 instanceof FurnaceBlockEntity) && !(method_8321 instanceof BarrelBlockEntity) && !(method_8321 instanceof EnchantingTableBlockEntity)) {
                        continue;
                    }
                }
                ++n;
            }
        }
        if (n2 > 0) {
            ++this.p;
        } else {
            this.p = 0;
        }
        if (this.p > 10) {
            this.a("YOU FOUND SPAWNER", blockPos.method_10263(), blockPos.method_10264(), blockPos.method_10260(), false);
            this.p = 0;
        }
        if (n > this.c.f()) {
            this.a("YOU FOUND BASE", (int) this.b.player.method_19538().x, (int) this.b.player.method_19538().y, (int) this.b.player.method_19538().z, true);
        }
    }

    private void a(final String s, final int n, final int n2, final int n3) {
        if (this.i.c()) {
            final EmbedSender embedSender = new EmbedSender(this.j.a);
            final bn bn = new bn();
            bn.a("Totem Exploded");
            bn.d("https://render.crafty.gg/3d/bust/" + MinecraftClient.getInstance().getSession().getUuidOrNull() + "?format=webp");
            bn.b("Your Totem Exploded - " + MinecraftClient.getInstance().getSession().getUsername());
            bn.a(Color.RED);
            bn.a(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")), null);
            bn.a("Location", "x: " + n + " y: " + n2 + " z: " + n3, true);
            embedSender.a(bn);
            try {
                embedSender.a();
            } catch (final Exception ex) {
            }
        }
        this.a(Text.of(s));
    }

    private void a(final String s, final int n, final int n2, final int n3, final boolean b) {
        String s2;
        if (b) {
            s2 = "Base";
        } else {
            s2 = "Spawner";
        }
        if (this.i.c()) {
            final EmbedSender embedSender = new EmbedSender(this.j.a);
            final bn bn = new bn();
            bn.a(s2);
            bn.d("https://render.crafty.gg/3d/bust/" + MinecraftClient.getInstance().getSession().getUuidOrNull() + "?format=webp");
            bn.b(s2 + " Found - " + MinecraftClient.getInstance().getSession().getUsername());
            bn.a(Color.GRAY);
            bn.a(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")), null);
            bn.a(s2 + "Found at", "x: " + n + " y: " + n2 + " z: " + n3, true);
            embedSender.a(bn);
            try {
                embedSender.a();
            } catch (final Exception ex) {
            }
        }
        this.toggle();
        this.a(Text.of(s));
    }

    private void a(final Text text) {
        final MutableText literal = Text.literal("[TunnelBaseFinder] ");
        literal.append(text);
        this.toggle();
        this.b.player.networkHandler.method_52781(new DisconnectS2CPacket(literal));
    }

    public boolean j() {
        return this.s;
    }
}
