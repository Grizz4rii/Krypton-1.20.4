// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import skid.krypton.auth.bn;
import net.minecraft.block.entity.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import skid.krypton.auth.EmbedSender;
import skid.krypton.enums.Enum3;
import skid.krypton.events.*;
import skid.krypton.mixin.MobSpawnerLogicAccessor;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.BooleanSetting;
import skid.krypton.setting.settings.EnumSetting;
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
import java.util.Random;

public final class RtpBaseFinder extends Module {
    public final EnumSetting<Enum3> c;
    private final BooleanSetting d;
    private final NumberSetting e;
    private final BooleanSetting f;
    private final NumberSetting g;
    private final BooleanSetting h;
    private final NumberSetting i;
    private final BooleanSetting j;
    private final StringSetting k;
    private final BooleanSetting l;
    private final NumberSetting m;
    private final NumberSetting n;
    private Vec3d o;
    private Vec3d p;
    private double q;
    private double r;
    private boolean s;
    private boolean t;
    private boolean u;
    private boolean v;
    private int w;
    private int x;
    private int y;
    private int z;
    private int A;

    public RtpBaseFinder() {
        super(EncryptedString.a("Rtp Base Finder"), EncryptedString.a("Automatically searches for bases on DonutSMP"), -1, Category.c);
        this.c = new EnumSetting<Enum3>(EncryptedString.a("Mode"), Enum3.g, Enum3.class);
        this.d = new BooleanSetting(EncryptedString.a("Spawners"), true);
        this.e = new NumberSetting(EncryptedString.a("Minimum Storage"), 1.0, 500.0, 100.0, 1.0);
        this.f = new BooleanSetting(EncryptedString.a("Auto Totem Buy"), true);
        this.g = new NumberSetting(EncryptedString.a("Totem Slot"), 1.0, 9.0, 8.0, 1.0);
        this.h = new BooleanSetting(EncryptedString.a("Auto Mend"), true).a(EncryptedString.a("Automatically repairs pickaxe."));
        this.i = new NumberSetting(EncryptedString.a("XP Bottle Slot"), 1.0, 9.0, 9.0, 1.0);
        this.j = new BooleanSetting(EncryptedString.a("Discord Notification"), false);
        this.k = new StringSetting(EncryptedString.a("Webhook"), "");
        this.l = new BooleanSetting(EncryptedString.a("Totem Check"), true);
        this.m = new NumberSetting(EncryptedString.a("Totem Check Time"), 1.0, 120.0, 20.0, 1.0);
        this.n = new NumberSetting(EncryptedString.a("Dig To Y"), -59.0, 30.0, -20.0, 1.0);
        this.r = 0.0;
        this.s = false;
        this.t = false;
        this.u = false;
        this.v = false;
        this.w = 0;
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.A = 0;
        this.a(this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n);
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
        if (this.b.player == null) {
            return;
        }
        if (this.y > 0) {
            --this.y;
            return;
        }
        this.n();
        if (this.f.c()) {
            final int n = this.g.f() - 1;
            if (!this.b.player.method_31548().method_5438(n).isOf(Items.TOTEM_OF_UNDYING)) {
                if (this.z < 30 && !this.v) {
                    ++this.z;
                    return;
                }
                this.z = 0;
                this.v = true;
                if (this.b.player.method_31548().selectedSlot != n) {
                    InventoryUtil.a(n);
                }
                final ScreenHandler field_7512 = this.b.player.field_7512;
                if (!(this.b.player.field_7512 instanceof GenericContainerScreenHandler) || ((GenericContainerScreenHandler) field_7512).getRows() != 3) {
                    this.b.getNetworkHandler().sendChatCommand("shop");
                    this.y = 10;
                    return;
                }
                if (((GenericContainerScreenHandler) field_7512).method_7611(11).getStack().isOf(Items.END_STONE)) {
                    this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, 13, 0, SlotActionType.PICKUP, (PlayerEntity) this.b.player);
                    this.y = 10;
                    return;
                }
                if (((GenericContainerScreenHandler) field_7512).method_7611(16).getStack().isOf(Items.EXPERIENCE_BOTTLE)) {
                    this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, 13, 0, SlotActionType.PICKUP, (PlayerEntity) this.b.player);
                    this.y = 10;
                    return;
                }
                this.b.player.networkHandler.method_52787((Packet) new PlayerActionC2SPacket(PlayerActionC2SPacket$Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, Direction.DOWN));
                if (((GenericContainerScreenHandler) field_7512).method_7611(23).getStack().isOf(Items.LIME_STAINED_GLASS_PANE)) {
                    this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, 23, 0, SlotActionType.PICKUP, (PlayerEntity) this.b.player);
                    this.y = 10;
                    return;
                }
                this.b.getNetworkHandler().sendChatCommand("shop");
                this.y = 10;
                return;
            } else if (this.v) {
                if (this.b.currentScreen != null) {
                    this.b.player.method_7346();
                    this.y = 20;
                }
                this.v = false;
                this.z = 0;
            }
        }
        if (this.u) {
            final int n2 = this.i.f() - 1;
            final ItemStack method_5438 = this.b.player.method_31548().method_5438(n2);
            if (this.b.player.method_31548().selectedSlot != n2) {
                InventoryUtil.a(n2);
            }
            if (!method_5438.isOf(Items.EXPERIENCE_BOTTLE)) {
                final ScreenHandler field_7513 = this.b.player.field_7512;
                if (!(this.b.player.field_7512 instanceof GenericContainerScreenHandler) || ((GenericContainerScreenHandler) field_7513).getRows() != 3) {
                    this.b.getNetworkHandler().sendChatCommand("shop");
                    this.y = 10;
                    return;
                }
                if (((GenericContainerScreenHandler) field_7513).method_7611(11).getStack().isOf(Items.END_STONE)) {
                    this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, 13, 0, SlotActionType.PICKUP, (PlayerEntity) this.b.player);
                    this.y = 10;
                    return;
                }
                if (((GenericContainerScreenHandler) field_7513).method_7611(16).getStack().isOf(Items.EXPERIENCE_BOTTLE)) {
                    this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, 16, 0, SlotActionType.PICKUP, (PlayerEntity) this.b.player);
                    this.y = 10;
                    return;
                }
                if (((GenericContainerScreenHandler) field_7513).method_7611(17).getStack().isOf(Items.LIME_STAINED_GLASS_PANE)) {
                    this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, 17, 0, SlotActionType.PICKUP, (PlayerEntity) this.b.player);
                    this.y = 10;
                    return;
                }
                this.b.player.networkHandler.method_52787((Packet) new PlayerActionC2SPacket(PlayerActionC2SPacket$Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, Direction.DOWN));
                if (((GenericContainerScreenHandler) field_7513).method_7611(23).getStack().isOf(Items.LIME_STAINED_GLASS_PANE)) {
                    this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, 23, 0, SlotActionType.PICKUP, (PlayerEntity) this.b.player);
                    this.y = 10;
                    return;
                }
                this.b.getNetworkHandler().sendChatCommand("shop");
                this.y = 10;
            } else {
                if (this.b.currentScreen != null) {
                    this.b.player.method_7346();
                    this.y = 20;
                    return;
                }
                if (!EnchantmentUtil.a(this.b.player.method_6079(), Enchantments.MENDING)) {
                    this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, 36 + this.w, 40, SlotActionType.SWAP, (PlayerEntity) this.b.player);
                    this.y = 20;
                    return;
                }
                if (this.b.player.method_6079().getDamage() > 0) {
                    final ActionResult interactItem = this.b.interactionManager.interactItem(this.b.player, Hand.MAIN_HAND);
                    if (interactItem.isAccepted() && interactItem.shouldSwingHand()) {
                        this.b.player.method_6104(Hand.MAIN_HAND);
                    }
                    this.y = 1;
                    return;
                }
                this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, 36 + this.w, 40, SlotActionType.SWAP, (PlayerEntity) this.b.player);
                this.u = false;
            }
        } else {
            if (this.t) {
                this.m();
            }
            if (this.l.c()) {
                final boolean equals = this.b.player.method_6079().getItem().equals(Items.TOTEM_OF_UNDYING);
                final Module moduleByClass = Krypton.INSTANCE.MODULE_MANAGER.getModuleByClass(AutoTotem.class);
                if (equals) {
                    this.r = 0.0;
                } else if (moduleByClass.isEnabled() && ((AutoTotem) moduleByClass).a(Items.TOTEM_OF_UNDYING) != -1) {
                    this.r = 0.0;
                } else {
                    ++this.r;
                }
                if (this.r > this.m.a()) {
                    this.a("Your totem exploded", (int) this.b.player.method_23317(), (int) this.b.player.method_23318(), (int) this.b.player.method_23321());
                    return;
                }
            }
            if (this.x > 0) {
                --this.x;
                if (this.x < 1) {
                    if (this.p != null && this.p.distanceTo(this.b.player.method_19538()) < 100.0) {
                        this.k();
                        return;
                    }
                    this.b.player.method_36457(89.9f);
                    if (this.h.c()) {
                        final ItemStack method_5439 = this.b.player.method_6047();
                        if (EnchantmentUtil.a(method_5439, Enchantments.MENDING) && method_5439.getMaxDamage() - method_5439.getDamage() < 100) {
                            this.u = true;
                            this.w = this.b.player.method_31548().selectedSlot;
                        }
                    }
                    this.t = true;
                }
                return;
            }
            if (this.o != null && this.o.distanceTo(this.b.player.method_19538()) < 2.0) {
                ++this.q;
            } else {
                this.o = this.b.player.method_19538();
                this.q = 0.0;
            }
            if (this.q > 20.0 && this.s) {
                this.k();
                this.s = false;
                return;
            }
            if (this.q > 200.0) {
                this.k();
                this.q = 0.0;
                return;
            }
            if (this.b.player.method_23318() < this.n.f() && !this.s) {
                this.s = true;
                this.t = false;
            }
        }
    }

    private void k() {
        this.t = false;
        final ClientPlayNetworkHandler networkHandler = this.b.getNetworkHandler();
        Enum3 l;
        if (this.c.a() == Enum3.g) {
            l = this.l();
        } else {
            l = (Enum3) this.c.a();
        }
        networkHandler.sendChatCommand("rtp " + this.a(l));
        this.x = 150;
        this.q = 0.0;
        this.p = new Vec3d(this.b.player.method_19538().toVector3f());
    }

    private void a(final Text text) {
        final MutableText literal = Text.literal("[RTPBaseFinder] ");
        literal.append(text);
        this.toggle();
        this.b.player.networkHandler.method_52781(new DisconnectS2CPacket(literal));
    }

    private Enum3 l() {
        final Enum3[] array = {Enum3.a, Enum3.b, Enum3.c, Enum3.d, Enum3.e, Enum3.f};
        return array[new Random().nextInt(array.length)];
    }

    private String a(final Enum3 enum3) {
        final int n = enum3.ordinal() ^ 0x706A485C;
        int n2;
        if (n != 0) {
            n2 = ((n * 31 >>> 4) % n ^ n >>> 16);
        } else {
            n2 = 0;
        }
        String name = null;
        switch (n2) {
            case 164469854: {
                name = "eu west";
                break;
            }
            case 164469848: {
                name = "eu central";
                break;
            }
            default: {
                name = enum3.name();
                break;
            }
        }
        return name;
    }

    private void m() {
        final Module moduleByClass = Krypton.INSTANCE.MODULE_MANAGER.getModuleByClass(AutoEat.class);
        if (!moduleByClass.isEnabled()) {
            this.c(true);
            return;
        }
        if (((AutoEat) moduleByClass).j()) {
            return;
        }
        this.c(true);
    }

    private void c(final boolean b) {
        if (this.b.player.method_36455() != 89.9f) {
            this.b.player.method_36457(89.9f);
        }
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

    private void n() {
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
                if (method_8321 instanceof ChestBlockEntity || method_8321 instanceof EnderChestBlockEntity || method_8321 instanceof ShulkerBoxBlockEntity || method_8321 instanceof FurnaceBlockEntity || method_8321 instanceof BarrelBlockEntity || method_8321 instanceof EnchantingTableBlockEntity) {
                    ++n;
                }
            }
        }
        if (n2 > 0) {
            ++this.A;
        } else {
            this.A = 0;
        }
        if (this.A > 10) {
            this.a("YOU FOUND SPAWNER", blockPos.method_10263(), blockPos.method_10264(), blockPos.method_10260(), false);
            this.A = 0;
        }
        if (n > this.e.f()) {
            this.a("YOU FOUND BASE", (int) this.b.player.method_19538().x, (int) this.b.player.method_19538().y, (int) this.b.player.method_19538().z, true);
        }
    }

    private void a(final String s, final int n, final int n2, final int n3, final boolean b) {
        String s2;
        if (b) {
            s2 = "Base";
        } else {
            s2 = "Spawner";
        }
        if (this.j.c()) {
            final EmbedSender embedSender = new EmbedSender(this.k.a);
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

    private void a(final String s, final int n, final int n2, final int n3) {
        if (this.j.c()) {
            final EmbedSender embedSender = new EmbedSender(this.k.a);
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

    public boolean j() {
        return this.u;
    }
}
