// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.math.MathHelper;
import skid.krypton.enums.ModuleListSorting;
import skid.krypton.event.events.Render2DEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.BooleanSetting;
import skid.krypton.setting.settings.EnumSetting;
import skid.krypton.setting.settings.NumberSetting;
import skid.krypton.utils.ColorUtil;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.RenderUtils;
import skid.krypton.utils.font.TextRenderer;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.EventListener;
import java.util.function.Function;

public final class HUD extends Module {
    private static final CharSequence c;
    private static final SimpleDateFormat d;
    private final BooleanSetting e;
    private final BooleanSetting f;
    private final BooleanSetting g;
    private final BooleanSetting h;
    private final BooleanSetting i;
    private final NumberSetting j;
    private final NumberSetting k;
    private final EnumSetting<ModuleListSorting> l;
    private final BooleanSetting m;
    private final NumberSetting n;
    private final Color o;
    private final Color p;

    public HUD() {
        super(EncryptedString.a("HUD"), EncryptedString.a("Customizable Heads-Up Display for client information"), -1, Category.d);
        this.e = new BooleanSetting(EncryptedString.a("Watermark"), true).a(EncryptedString.a("Shows client name on screen"));
        this.f = new BooleanSetting(EncryptedString.a("Info"), true).a(EncryptedString.a("Shows system information"));
        this.g = new BooleanSetting("Modules", true).a(EncryptedString.a("Renders module array list"));
        this.h = new BooleanSetting("Time", true).a(EncryptedString.a("Shows current time"));
        this.i = new BooleanSetting("Coordinates", true).a(EncryptedString.a("Shows player coordinates"));
        this.j = new NumberSetting("Opacity", 0.0, 1.0, 0.800000011920929, 0.05000000074505806).a(EncryptedString.a("Controls the opacity of HUD elements"));
        this.k = new NumberSetting("Corner Radius", 0.0, 10.0, 5.0, 0.5).a(EncryptedString.a("Controls the roundness of corners"));
        this.l = new EnumSetting("Sort Mode", ModuleListSorting.a, ModuleListSorting.class).a(EncryptedString.a("How to sort the module list"));
        this.m = new BooleanSetting("Rainbow", false).a(EncryptedString.a("Enables rainbow coloring effect"));
        this.n = new NumberSetting("Rainbow Speed", 0.10000000149011612, 10.0, 2.0, 0.10000000149011612).a(EncryptedString.a("Controls the speed of the rainbow effect"));
        this.o = new Color(65, 185, 255, 255);
        this.p = new Color(255, 110, 230, 255);
        this.a(this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n);
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
    public void a(final Render2DEvent render2DEvent) {
        if (this.b.currentScreen != Krypton.INSTANCE.GUI) {
            final DrawContext a = render2DEvent.a;
            final int width = this.b.getWindow().getWidth();
            final int height = this.b.getWindow().getHeight();
            RenderUtils.c();
            if (this.e.c()) {
                this.a(a, width);
            }
            if (this.f.c() && this.b.player != null) {
                this.a(a);
            }
            if (this.h.c()) {
                this.b(a, width);
            }
            if (this.i.c() && this.b.player != null) {
                this.c(a, height);
            }
            if (this.g.c()) {
                this.a(a, width, height);
            }
            RenderUtils.d();
        }
    }

    private void a(final DrawContext drawContext, final int n) {
        RenderUtils.a(drawContext.getMatrices(), new Color(35, 35, 35, (int) (this.j.g() * 255.0f)), 5.0, 5.0, 5.0f + TextRenderer.a(HUD.c) + 7.0f, 25.0, this.k.a(), 15.0);
        Color color;
        if (this.m.c()) {
            color = ColorUtil.a(this.n.f(), 1);
        } else {
            color = this.o;
        }
        TextRenderer.a(HUD.c, drawContext, 8, 8, color.getRGB());
    }

    private void a(final DrawContext drawContext) {
        final String k = this.k();
        final String s = "FPS: " + this.b.getCurrentFps() + " | ";
        String address;
        if (this.b.getCurrentServerEntry() == null) {
            address = "Single Player";
        } else {
            address = this.b.getCurrentServerEntry().address;
        }
        RenderUtils.a(drawContext.getMatrices(), new Color(35, 35, 35, (int) (this.j.g() * 255.0f)), 5.0, 30.0, 5.0f + (TextRenderer.a(s) + TextRenderer.a(k) + TextRenderer.a(address)) + 9.0f, 50.0, this.k.a(), 15.0);
        TextRenderer.a(s, drawContext, 10, 33, this.o.getRGB());
        final int n = 10 + TextRenderer.a(s);
        TextRenderer.a(k, drawContext, n, 33, this.p.getRGB());
        TextRenderer.a(address, drawContext, n + TextRenderer.a(k), 33, new Color(175, 175, 175, 255).getRGB());
    }

    private void b(final DrawContext drawContext, final int n) {
        final String format = HUD.d.format(new Date());
        final int a = TextRenderer.a(format);
        final int n2 = n / 2;
        RenderUtils.a(drawContext.getMatrices(), new Color(35, 35, 35, (int) (this.j.g() * 255.0f)), n2 - a / 2.0f - 3.0f, 5.0, n2 + a / 2.0f + 5.0f, 25.0, this.k.a(), 15.0);
        int n3;
        if (this.m.c()) {
            n3 = ColorUtil.a(this.n.f(), 1).getRGB();
        } else {
            n3 = this.o.getRGB();
        }
        TextRenderer.a(format, drawContext, (int) (n2 - a / 2.0f), 8, n3);
    }

    private void c(final DrawContext drawContext, final int n) {
        final String format = String.format("X: %.1f", this.b.player.method_23317());
        final String format2 = String.format("Y: %.1f", this.b.player.method_23318());
        final String format3 = String.format("Z: %.1f", this.b.player.method_23321());
        String s = "";
        if (this.b.world != null) {
            final boolean contains = this.b.world.method_27983().getValue().getPath().contains("nether");
            final boolean contains2 = this.b.world.method_27983().getValue().getPath().contains("overworld");
            if (contains) {
                s = String.format(" [%.1f, %.1f]", this.b.player.method_23317() * 8.0, this.b.player.method_23321() * 8.0);
            } else if (contains2) {
                s = String.format(" [%.1f, %.1f]", this.b.player.method_23317() / 8.0, this.b.player.method_23321() / 8.0);
            }
        }
        final String s2 = format + " | " + format2 + " | " + format3 + s;
        RenderUtils.a(drawContext.getMatrices(), new Color(35, 35, 35, (int) (this.j.g() * 255.0f)), 5.0, n - 25, 5.0f + TextRenderer.a(s2) + 5.0f, n - 5, this.k.a(), 15.0);
        TextRenderer.a(s2, drawContext, 10, n - 22, this.o.getRGB());
    }

    private void a(final DrawContext drawContext, final int n, final int n2) {
        final List j = this.j();
        int n3 = 5;
        for (final Object next : j) {
            final String string = ((Module) next).getName();
            final int a = TextRenderer.a(string);
            final int n4 = n - 5;
            RenderUtils.a(drawContext.getMatrices(), new Color(35, 35, 35, (int) (this.j.g() * 255.0f)), n - (float) a - 13.0f, n3, (float) n4, n3 + 20, this.k.a(), 15.0);
            Color color;
            if (this.m.c()) {
                color = ColorUtil.a(this.n.f() + j.indexOf(next), 1);
            } else {
                color = this.a(this.o, this.p, j.indexOf(next) / (float) j.size());
            }
            drawContext.fill((int) (float) n4 - 2, n3, (int) (float) n4, n3 + 20, color.getRGB());
            TextRenderer.a(string, drawContext, (int) (n - (float) a - 10.0f), n3 + 3, color.getRGB());
            n3 += 25;
        }
    }

    private List j() {
        final List b = Krypton.INSTANCE.b().b();
        final int n = this.l.a().ordinal() ^ 0x4E35B0BF;
        int n2;
        if (n != 0) {
            n2 = ((n * 31 >>> 4) % n ^ n >>> 16);
        } else {
            n2 = 0;
        }
        List list = null;
        switch (n2) {
            default: {
                throw new MatchException(null, null);
            }
            case 126371909: {
                list = b.stream().sorted(Comparator.comparing(module -> module.getName().toString())).toList();
                break;
            }
            case 126371911: {
                list = b.stream().sorted((module2, module3) -> Integer.compare(TextRenderer.a(module3.getName()), TextRenderer.a(module2.getName()))).toList();
                break;
            }
            case 126371931: {
                list = b.stream().sorted(Comparator.comparing((Function<? super Object, ? extends Comparable>) Module::getCategory).thenComparing(module4 -> module4.getName().toString())).toList();
                break;
            }
        }
        return list;
    }

    private String k() {
        String s;
        if (this.b != null && this.b.player != null && this.b.getNetworkHandler() != null) {
            final PlayerListEntry playerListEntry = this.b.getNetworkHandler().getPlayerListEntry(this.b.player.method_5667());
            if (playerListEntry != null) {
                s = "Ping: " + playerListEntry.getLatency() + "ms | ";
            } else {
                s = "Ping: " + "N/A | ";
            }
        } else {
            s = "Ping: " + "N/A | ";
        }
        return s;
    }

    private Color a(final Color color, final Color color2, final float n) {
        return new Color(MathHelper.clamp((int) (color.getRed() + (color2.getRed() - color.getRed()) * n), 0, 255), MathHelper.clamp((int) (color.getGreen() + (color2.getGreen() - color.getGreen()) * n), 0, 255), MathHelper.clamp((int) (color.getBlue() + (color2.getBlue() - color.getBlue()) * n), 0, 255), 255);
    }

    static {
        c = EncryptedString.a("Krypton+");
        d = new SimpleDateFormat("HH:mm:ss");
    }
}
