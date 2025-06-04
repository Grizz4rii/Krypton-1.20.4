package skid.krypton.module.modules;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.math.MathHelper;
import skid.krypton.Krypton;
import skid.krypton.enums.ModuleListSorting;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.Render2DEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.Setting;
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
import java.util.List;

public final class HUD
        extends Module {
    private static final CharSequence c = EncryptedString.a("Krypton+");
    private static final SimpleDateFormat d = new SimpleDateFormat("HH:mm:ss");
    private final BooleanSetting e = new BooleanSetting(EncryptedString.a("Watermark"), true).a(EncryptedString.a("Shows client name on screen"));
    private final BooleanSetting f = new BooleanSetting(EncryptedString.a("Info"), true).a(EncryptedString.a("Shows system information"));
    private final BooleanSetting g = new BooleanSetting("Modules", true).a(EncryptedString.a("Renders module array list"));
    private final BooleanSetting h = new BooleanSetting("Time", true).a(EncryptedString.a("Shows current time"));
    private final BooleanSetting i = new BooleanSetting("Coordinates", true).a(EncryptedString.a("Shows player coordinates"));
    private final NumberSetting j = new NumberSetting("Opacity", 0.0, 1.0, 0.8f, 0.05f).a(EncryptedString.a("Controls the opacity of HUD elements"));
    private final NumberSetting k = new NumberSetting("Corner Radius", 0.0, 10.0, 5.0, 0.5).a(EncryptedString.a("Controls the roundness of corners"));
    private final EnumSetting<ModuleListSorting> l = new EnumSetting("Sort Mode", ModuleListSorting.a, ModuleListSorting.class).a(EncryptedString.a("How to sort the module list"));
    private final BooleanSetting m = new BooleanSetting("Rainbow", false).a(EncryptedString.a("Enables rainbow coloring effect"));
    private final NumberSetting n = new NumberSetting("Rainbow Speed", 0.1f, 10.0, 2.0, 0.1f).a(EncryptedString.a("Controls the speed of the rainbow effect"));
    private final Color o = new Color(65, 185, 255, 255);
    private final Color p = new Color(255, 110, 230, 255);

    public HUD() {
        super(EncryptedString.a("HUD"), EncryptedString.a("Customizable Heads-Up Display for client information"), -1, Category.d);
        Setting[] settingArray = new Setting[]{this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n};
        this.a(settingArray);
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
    public void a(Render2DEvent render2DEvent) {
        if (this.b.currentScreen != Krypton.INSTANCE.GUI) {
            DrawContext drawContext = render2DEvent.a;
            int n = this.b.getWindow().getWidth();
            int n2 = this.b.getWindow().getHeight();
            RenderUtils.c();
            if (this.e.c()) {
                this.a(drawContext, n);
            }
            if (this.f.c() && this.b.player != null) {
                this.a(drawContext);
            }
            if (this.h.c()) {
                this.b(drawContext, n);
            }
            if (this.i.c() && this.b.player != null) {
                this.c(drawContext, n2);
            }
            if (this.g.c()) {
                this.a(drawContext, n, n2);
            }
            RenderUtils.d();
        }
    }

    private void a(DrawContext drawContext, int n) {
        int n2 = TextRenderer.a(c);
        RenderUtils.a(drawContext.getMatrices(), new Color(35, 35, 35, (int)(this.j.g() * 255.0f)), 5.0, 5.0, 5.0f + (float)n2 + 7.0f, 25.0, this.k.a(), 15.0);
        Color color = this.m.c() ? ColorUtil.a(this.n.f(), 1) : this.o;
        CharSequence charSequence = c;
        TextRenderer.a(charSequence, drawContext, 8, 8, color.getRGB());
    }

    private void a(DrawContext drawContext) {
        String string = this.k();
        String string2 = "FPS: " + this.b.getCurrentFps() + " | ";
        String string3 = this.b.getCurrentServerEntry() == null ? "Single Player" : this.b.getCurrentServerEntry().address;
        int n = TextRenderer.a(string2);
        int n2 = TextRenderer.a(string);
        RenderUtils.a(drawContext.getMatrices(), new Color(35, 35, 35, (int)(this.j.g() * 255.0f)), 5.0, 30.0, 5.0f + (float)(n + n2 + TextRenderer.a(string3)) + 9.0f, 50.0, this.k.a(), 15.0);
        TextRenderer.a(string2, drawContext, 10, 33, this.o.getRGB());
        int n3 = 10 + TextRenderer.a(string2);
        TextRenderer.a(string, drawContext, n3, 33, this.p.getRGB());
        TextRenderer.a(string3, drawContext, n3 + TextRenderer.a(string), 33, new Color(175, 175, 175, 255).getRGB());
    }

    private void b(DrawContext drawContext, int n) {
        SimpleDateFormat simpleDateFormat = d;
        String string = simpleDateFormat.format(new Date());
        int n2 = TextRenderer.a(string);
        int n3 = n / 2;
        RenderUtils.a(drawContext.getMatrices(), new Color(35, 35, 35, (int)(this.j.g() * 255.0f)), (float)n3 - (float)n2 / 2.0f - 3.0f, 5.0, (float)n3 + (float)n2 / 2.0f + 5.0f, 25.0, this.k.a(), 15.0);
        int n4 = this.m.c() ? ColorUtil.a(this.n.f(), 1).getRGB() : this.o.getRGB();
        TextRenderer.a(string, drawContext, (int)((float)n3 - (float)n2 / 2.0f), 8, n4);
    }

    private void c(DrawContext drawContext, int n) {
        Object[] objectArray = new Object[]{this.b.player.getX()};
        String string = String.format("X: %.1f", objectArray);
        Object[] objectArray2 = new Object[]{this.b.player.getY()};
        String string2 = String.format("Y: %.1f", objectArray2);
        Object[] objectArray3 = new Object[]{this.b.player.getZ()};
        String string3 = String.format("Z: %.1f", objectArray3);
        String string4 = "";
        if (this.b.world != null) {
            boolean bl = this.b.world.getRegistryKey().getValue().getPath().contains("nether");
            boolean bl2 = this.b.world.getRegistryKey().getValue().getPath().contains("overworld");
            if (bl) {
                Object[] objectArray4 = new Object[]{this.b.player.getX() * 8.0, this.b.player.getZ() * 8.0};
                string4 = String.format(" [%.1f, %.1f]", objectArray4);
            } else if (bl2) {
                Object[] objectArray5 = new Object[]{this.b.player.getX() / 8.0, this.b.player.getZ() / 8.0};
                string4 = String.format(" [%.1f, %.1f]", objectArray5);
            }
        }
        String string5 = string + " | " + string2 + " | " + string3 + string4;
        int n2 = TextRenderer.a(string5);
        RenderUtils.a(drawContext.getMatrices(), new Color(35, 35, 35, (int)(this.j.g() * 255.0f)), 5.0, n - 25, 5.0f + (float)n2 + 5.0f, n - 5, this.k.a(), 15.0);
        TextRenderer.a(string5, drawContext, 10, n - 22, this.o.getRGB());
    }

    private void a(DrawContext drawContext, int n, int n2) {
        List list = this.j();
        int n3 = 5;
        for (Object e : list) {
            String string = ((Module)e).getName().toString();
            int n4 = TextRenderer.a(string);
            int n5 = n - 5;
            RenderUtils.a(drawContext.getMatrices(), new Color(35, 35, 35, (int)(this.j.g() * 255.0f)), (float)n - (float)n4 - 13.0f, n3, n5, n3 + 20, this.k.a(), 15.0);
            Color color = this.m.c() ? ColorUtil.a(this.n.f() + list.indexOf(e), 1) : this.a(this.o, this.p, (float)list.indexOf(e) / (float)list.size());
            drawContext.fill((int)((float)n5) - 2, n3, (int)((float)n5), n3 + 20, color.getRGB());
            TextRenderer.a(string, drawContext, (int)((float)n - (float)n4 - 10.0f), n3 + 3, color.getRGB());
            n3 += 25;
        }
    }

    private List<Module> j() {
        List<Module> list = Krypton.INSTANCE.b().b();
        int n = this.l.a().ordinal() ^ 0x4E35B0BF;
        int n2 = n != 0 ? (n * 31 >>> 4) % n ^ n >>> 16 : 0;
        return switch (n2) {
            default -> throw new MatchException(null, null);
            case 126371909 -> list.stream().sorted(Comparator.comparing(module -> module.getName().toString())).toList();
            case 0x7884847 -> list.stream().sorted((module, module2) -> Integer.compare(TextRenderer.a(module2.getName()), TextRenderer.a(module.getName()))).toList();
            case 126371931 -> list.stream().sorted(Comparator.comparing(Module::getCategory).thenComparing(module -> module.getName().toString())).toList();
        };
    }

    private String k() {
        PlayerListEntry playerListEntry;
        String string = this.b != null && this.b.player != null && this.b.getNetworkHandler() != null ? ((playerListEntry = this.b.getNetworkHandler().getPlayerListEntry(this.b.player.getUuid())) != null ? "Ping: " + playerListEntry.getLatency() + "ms | " : "Ping: " + "N/A | ") : "Ping: " + "N/A | ";
        return string;
    }

    private Color a(Color color, Color color2, float f) {
        int n = color.getRed();
        int n2 = color2.getRed();
        int n3 = color.getGreen();
        int n4 = color2.getGreen();
        int n5 = color.getBlue();
        int n6 = color2.getBlue();
        return new Color(MathHelper.clamp((int)((float)n + (float)(n2 - color.getRed()) * f), 0, 255), MathHelper.clamp((int)((float)n3 + (float)(n4 - color.getGreen()) * f), 0, 255), MathHelper.clamp((int)((float)n5 + (float)(n6 - color.getBlue()) * f), 0, 255), 255);
    }
}
