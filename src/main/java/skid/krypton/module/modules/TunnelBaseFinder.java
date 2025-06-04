package skid.krypton.module.modules;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.InfestedBlock;
import net.minecraft.block.entity.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.WorldChunk;
import skid.krypton.Krypton;
import skid.krypton.utils.embed.EmbedSender;
import skid.krypton.utils.embed.bn;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.TickEvent;
import skid.krypton.mixin.MobSpawnerLogicAccessor;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.BooleanSetting;
import skid.krypton.module.setting.NumberSetting;
import skid.krypton.module.setting.StringSetting;
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
    private Direction n;
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
        super(EncryptedString.of("Tunnel Base Finder"), EncryptedString.of("Finds bases digging tunnel"), -1, Category.DONUT);
        this.c = new NumberSetting(EncryptedString.of("Minimum Storage"), 1.0, 500.0, 100.0, 1.0);
        this.d = new BooleanSetting(EncryptedString.of("Spawners"), true);
        this.e = new BooleanSetting(EncryptedString.of("Auto Totem Buy"), true);
        this.f = new NumberSetting(EncryptedString.of("Totem Slot"), 1.0, 9.0, 8.0, 1.0);
        this.g = new BooleanSetting(EncryptedString.of("Auto Mend"), true).setDescription(EncryptedString.of("Automatically repairs pickaxe."));
        this.h = new NumberSetting(EncryptedString.of("XP Bottle Slot"), 1.0, 9.0, 9.0, 1.0);
        this.i = new BooleanSetting(EncryptedString.of("Discord Notification"), false);
        this.j = new StringSetting(EncryptedString.of("Webhook"), "");
        this.k = new BooleanSetting(EncryptedString.of("Totem Check"), true);
        this.l = new NumberSetting(EncryptedString.of("Totem Check Time"), 1.0, 120.0, 20.0, 1.0);
        this.s = false;
        this.t = false;
        this.u = 0;
        this.v = 0;
        this.w = 0.0;
        this.addSettings(this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l);
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
        this.mc.options.leftKey.setPressed(false);
        this.mc.options.rightKey.setPressed(false);
        this.mc.options.forwardKey.setPressed(false);
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
        if (this.mc.player.getYaw() != n) {
            this.mc.player.setYaw((float) n);
        }
        if (this.mc.player.getPitch() != 2.0f) {
            this.mc.player.setPitch(2.0f);
        }
        this.b(this.k());
        if (this.o > 0) {
            this.mc.options.forwardKey.setPressed(false);
            --this.o;
            return;
        }
        this.l();
        if (this.e.getValue()) {
            final int n2 = this.f.getIntValue() - 1;
            if (!this.mc.player.getInventory().getStack(n2).isOf(Items.TOTEM_OF_UNDYING)) {
                if (this.v < 30 && !this.t) {
                    ++this.v;
                    return;
                }
                this.v = 0;
                this.t = true;
                if (this.mc.player.getInventory().selectedSlot != n2) {
                    InventoryUtil.a(n2);
                }
                final ScreenHandler currentScreenHandler = this.mc.player.currentScreenHandler;
                if (!(this.mc.player.currentScreenHandler instanceof GenericContainerScreenHandler) || ((GenericContainerScreenHandler) currentScreenHandler).getRows() != 3) {
                    this.mc.getNetworkHandler().sendChatCommand("shop");
                    this.o = 10;
                    return;
                }
                if (currentScreenHandler.getSlot(11).getStack().isOf(Items.END_STONE)) {
                    this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 13, 0, SlotActionType.PICKUP, this.mc.player);
                    this.o = 10;
                    return;
                }
                if (currentScreenHandler.getSlot(16).getStack().isOf(Items.EXPERIENCE_BOTTLE)) {
                    this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 13, 0, SlotActionType.PICKUP, this.mc.player);
                    this.o = 10;
                    return;
                }
                this.mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, net.minecraft.util.math.Direction.DOWN));
                if (currentScreenHandler.getSlot(23).getStack().isOf(Items.LIME_STAINED_GLASS_PANE)) {
                    this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 23, 0, SlotActionType.PICKUP, this.mc.player);
                    this.o = 10;
                    return;
                }
                this.mc.getNetworkHandler().sendChatCommand("shop");
                this.o = 10;
                return;
            } else if (this.t) {
                if (this.mc.currentScreen != null) {
                    this.mc.player.closeHandledScreen();
                    this.o = 20;
                }
                this.t = false;
                this.v = 0;
            }
        }
        if (this.s) {
            final int n3 = this.h.getIntValue() - 1;
            final ItemStack getStack = this.mc.player.getInventory().getStack(n3);
            if (this.mc.player.getInventory().selectedSlot != n3) {
                InventoryUtil.a(n3);
            }
            if (!getStack.isOf(Items.EXPERIENCE_BOTTLE)) {
                final ScreenHandler fishHook = this.mc.player.currentScreenHandler;
                if (!(this.mc.player.currentScreenHandler instanceof GenericContainerScreenHandler) || ((GenericContainerScreenHandler) fishHook).getRows() != 3) {
                    this.mc.getNetworkHandler().sendChatCommand("shop");
                    this.o = 10;
                    return;
                }
                if (fishHook.getSlot(11).getStack().isOf(Items.END_STONE)) {
                    this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 13, 0, SlotActionType.PICKUP, this.mc.player);
                    this.o = 10;
                    return;
                }
                if (fishHook.getSlot(16).getStack().isOf(Items.EXPERIENCE_BOTTLE)) {
                    this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 16, 0, SlotActionType.PICKUP, this.mc.player);
                    this.o = 10;
                    return;
                }
                if (fishHook.getSlot(17).getStack().isOf(Items.LIME_STAINED_GLASS_PANE)) {
                    this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 17, 0, SlotActionType.PICKUP, this.mc.player);
                    this.o = 10;
                    return;
                }
                this.mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, net.minecraft.util.math.Direction.DOWN));
                if (fishHook.getSlot(23).getStack().isOf(Items.LIME_STAINED_GLASS_PANE)) {
                    this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 23, 0, SlotActionType.PICKUP, this.mc.player);
                    this.o = 10;
                    return;
                }
                this.mc.getNetworkHandler().sendChatCommand("shop");
                this.o = 10;
            } else {
                if (this.mc.currentScreen != null) {
                    this.mc.player.closeHandledScreen();
                    this.o = 20;
                    return;
                }
                if (!EnchantmentUtil.a(this.mc.player.getOffHandStack(), Enchantments.MENDING)) {
                    this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 36 + this.u, 40, SlotActionType.SWAP, this.mc.player);
                    this.o = 20;
                    return;
                }
                if (this.mc.player.getOffHandStack().getDamage() > 0) {
                    final ActionResult interactItem = this.mc.interactionManager.interactItem(this.mc.player, Hand.MAIN_HAND);
                    if (interactItem.isAccepted() && interactItem.shouldSwingHand()) {
                        this.mc.player.swingHand(Hand.MAIN_HAND);
                    }
                    this.o = 1;
                    return;
                }
                this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 36 + this.u, 40, SlotActionType.SWAP, this.mc.player);
                this.s = false;
            }
        } else {
            if (this.g.getValue()) {
                final ItemStack size = this.mc.player.getMainHandStack();
                if (EnchantmentUtil.a(size, Enchantments.MENDING) && size.getMaxDamage() - size.getDamage() < 100) {
                    this.s = true;
                    this.u = this.mc.player.getInventory().selectedSlot;
                }
            }
            if (this.k.getValue()) {
                final boolean equals = this.mc.player.getOffHandStack().getItem().equals(Items.TOTEM_OF_UNDYING);
                final Module moduleByClass2 = Krypton.INSTANCE.MODULE_MANAGER.getModuleByClass(AutoTotem.class);
                if (equals) {
                    this.w = 0.0;
                } else if (moduleByClass2.isEnabled() && ((AutoTotem) moduleByClass2).a(Items.TOTEM_OF_UNDYING) != -1) {
                    this.w = 0.0;
                } else {
                    ++this.w;
                }
                if (this.w > this.l.getValue()) {
                    this.a("Your totem exploded", (int) this.mc.player.getX(), (int) this.mc.player.getY(), (int) this.mc.player.getZ());
                    return;
                }
            }
            boolean a = false;
            final HitResult crosshairTarget = this.mc.crosshairTarget;
            if (this.mc.crosshairTarget instanceof BlockHitResult) {
                final BlockPos blockPos = ((BlockHitResult) crosshairTarget).getBlockPos();
                if (!BlockUtil.a(blockPos, Blocks.AIR)) {
                    a = this.a(blockPos, this.mc.player.getHorizontalFacing());
                }
            }
            if (a) {
                this.c(true);
            }
            final boolean a2 = this.a(this.mc.player.getHorizontalFacing(), 3);
            boolean b = false;
            final HitResult crosshairTarget2 = this.mc.crosshairTarget;
            if (this.mc.crosshairTarget instanceof BlockHitResult) {
                b = (this.mc.player.getCameraPosVec(1.0f).distanceTo(Vec3d.ofCenter(((BlockHitResult) crosshairTarget2).getBlockPos())) > 3.0);
            }
            if (!a && (!b || !a2)) {
                ++this.q;
                this.r = this.mc.player.getPos();
                this.o = 5;
                return;
            }
            this.mc.options.forwardKey.setPressed(a2 && b);
            if (this.q > 0 && this.r != null && this.mc.player.getPos().distanceTo(this.r) > 1.0) {
                this.r = this.mc.player.getPos();
                final net.minecraft.util.math.Direction rotateYCounterclockwise = this.mc.player.getHorizontalFacing().rotateYCounterclockwise();
                BlockPos blockPos2 = this.mc.player.getBlockPos().up().offset(rotateYCounterclockwise);
                for (int i = 0; i < 5; ++i) {
                    blockPos2 = blockPos2.offset(rotateYCounterclockwise);
                    if (!this.mc.world.getBlockState(blockPos2).getBlock().equals(Blocks.AIR)) {
                        if (this.a(blockPos2, rotateYCounterclockwise) && this.a(blockPos2.offset(rotateYCounterclockwise), rotateYCounterclockwise)) {
                            --this.q;
                            this.o = 5;
                        }
                        return;
                    }
                }
            }
        }
    }

    private int a(final Direction enum4) {
        if (enum4 == Direction.NORTH) {
            return 180;
        }
        if (enum4 == Direction.SOUTH) {
            return 0;
        }
        if (enum4 == Direction.EAST) {
            return 270;
        }
        if (enum4 == Direction.WEST) {
            return 90;
        }
        return Math.round(this.mc.player.getYaw());
    }

    private boolean a(final net.minecraft.util.math.Direction direction, final int n) {
        final BlockPos down = this.mc.player.getBlockPos().down();
        final BlockPos getBlockPos = this.mc.player.getBlockPos();
        for (int i = 0; i < n; ++i) {
            final BlockPos offset = down.offset(direction, i);
            final BlockPos offset2 = getBlockPos.offset(direction, i);
            if (this.mc.world.getBlockState(offset).isAir() || !this.a(offset)) {
                return false;
            }
            if (!this.mc.world.getBlockState(offset2).isAir()) {
                return false;
            }
        }
        return true;
    }

    private boolean a(final BlockPos blockPos, final net.minecraft.util.math.Direction direction) {
        final BlockPos offset = blockPos.offset(direction);
        final net.minecraft.util.math.Direction rotateYClockwise = direction.rotateYClockwise();
        final net.minecraft.util.math.Direction up = net.minecraft.util.math.Direction.UP;
        final BlockPos offset2 = blockPos.offset(net.minecraft.util.math.Direction.UP, 2);
        final BlockPos offset3 = blockPos.offset(net.minecraft.util.math.Direction.DOWN, -2);
        final BlockPos offset4 = offset2.offset(rotateYClockwise, -1);
        if (!this.a(offset4) || this.mc.world.getBlockState(offset4).getBlock() == Blocks.GRAVEL) {
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
        return this.a(this.mc.world.getBlockState(blockPos).getBlock());
    }

    private boolean a(final Block block) {
        return block != Blocks.LAVA && block != Blocks.WATER;
    }

    private boolean b(final BlockPos blockPos) {
        return this.mc.world.getBlockState(blockPos).getBlock() instanceof InfestedBlock;
    }

    private void b(final Direction enum4) {
        final double getX = this.mc.player.getX();
        final double getY = this.mc.player.getZ();
        final double floor = Math.floor(getY);
        final double n = Math.floor(getX) + 0.5 - getX;
        final double n2 = floor + 0.5 - getY;
        this.mc.options.leftKey.setPressed(false);
        this.mc.options.rightKey.setPressed(false);
        boolean b = false;
        boolean b2 = false;
        if (enum4 == Direction.SOUTH) {
            if (n > 0.1) {
                b2 = true;
            } else if (n < -0.1) {
                b = true;
            }
        }
        if (enum4 == Direction.NORTH) {
            if (n > 0.1) {
                b = true;
            } else if (n < -0.1) {
                b2 = true;
            }
        }
        if (enum4 == Direction.WEST) {
            if (n2 > 0.1) {
                b2 = true;
            } else if (n2 < -0.1) {
                b = true;
            }
        }
        if (enum4 == Direction.EAST) {
            if (n2 > 0.1) {
                b = true;
            } else if (n2 < -0.1) {
                b2 = true;
            }
        }
        if (b) {
            this.mc.options.rightKey.setPressed(true);
        }
        if (b2) {
            this.mc.options.leftKey.setPressed(true);
        }
    }

    private void c(final boolean b) {
        if (!this.mc.player.isUsingItem()) {
            if (b && this.mc.crosshairTarget != null && this.mc.crosshairTarget.getType() == HitResult.Type.BLOCK) {
                final BlockHitResult blockHitResult = (BlockHitResult) this.mc.crosshairTarget;
                final BlockPos blockPos = ((BlockHitResult) this.mc.crosshairTarget).getBlockPos();
                if (!this.mc.world.getBlockState(blockPos).isAir()) {
                    final net.minecraft.util.math.Direction side = blockHitResult.getSide();
                    if (this.mc.interactionManager.updateBlockBreakingProgress(blockPos, side)) {
                        this.mc.particleManager.addBlockBreakingParticles(blockPos, side);
                        this.mc.player.swingHand(Hand.MAIN_HAND);
                    }
                }
            } else {
                this.mc.interactionManager.cancelBlockBreaking();
            }
        }
    }

    private Direction k() {
        float n = this.mc.player.getYaw() % 360.0f;
        if (n < 0.0f) {
            n += 360.0f;
        }
        if (n >= 45.0f && n < 135.0f) {
            return Direction.WEST;
        }
        if (n >= 135.0f && n < 225.0f) {
            return Direction.NORTH;
        }
        if (n >= 225.0f && n < 315.0f) {
            return Direction.EAST;
        }
        return Direction.SOUTH;
    }

    private void l() {
        int n = 0;
        int n2 = 0;
        BlockPos blockPos = null;
        final Iterator iterator = BlockUtil.a().iterator();
        while (iterator.hasNext()) {
            for (final Object next : ((WorldChunk) iterator.next()).getBlockEntityPositions()) {
                final BlockEntity getBlockEntity = this.mc.world.getBlockEntity((BlockPos) next);
                if (this.d.getValue() && getBlockEntity instanceof MobSpawnerBlockEntity) {
                    final String string = ((MobSpawnerLogicAccessor) ((MobSpawnerBlockEntity) getBlockEntity).getLogic()).getSpawnEntry(this.mc.world, this.mc.world.getRandom(), (BlockPos) next).getNbt().getString("id");
                    if (string != "minecraft:cave_spider" && string != "minecraft:spider") {
                        ++n2;
                        blockPos = (BlockPos) next;
                    }
                }
                if (!(getBlockEntity instanceof ChestBlockEntity) && !(getBlockEntity instanceof EnderChestBlockEntity)) {
                    if (getBlockEntity instanceof ShulkerBoxBlockEntity) {
                        throw new RuntimeException();
                    }
                    if (!(getBlockEntity instanceof FurnaceBlockEntity) && !(getBlockEntity instanceof BarrelBlockEntity) && !(getBlockEntity instanceof EnchantingTableBlockEntity)) {
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
            this.a("YOU FOUND SPAWNER", blockPos.getX(), blockPos.getY(), blockPos.getZ(), false);
            this.p = 0;
        }
        if (n > this.c.getIntValue()) {
            this.a("YOU FOUND BASE", (int) this.mc.player.getPos().x, (int) this.mc.player.getPos().y, (int) this.mc.player.getPos().z, true);
        }
    }

    private void a(final String s, final int n, final int n2, final int n3) {
        if (this.i.getValue()) {
            final EmbedSender embedSender = new EmbedSender(this.j.value);
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
            } catch (final Throwable ex) {
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
        if (this.i.getValue()) {
            final EmbedSender embedSender = new EmbedSender(this.j.value);
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
            } catch (final Throwable ex) {
            }
        }
        this.toggle();
        this.a(Text.of(s));
    }

    private void a(final Text text) {
        final MutableText literal = Text.literal("[TunnelBaseFinder] ");
        literal.append(text);
        this.toggle();
        this.mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket(literal));
    }

    public boolean j() {
        return this.s;
    }

    enum Direction {
        NORTH("north", 0),
        SOUTH("south", 1),
        EAST("east", 2),
        WEST("west", 3);

        Direction(final String name, final int ordinal) {
        }
    }

}
