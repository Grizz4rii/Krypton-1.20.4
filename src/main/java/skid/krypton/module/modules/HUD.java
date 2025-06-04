package skid.krypton.module.modules;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.math.MathHelper;
import skid.krypton.Krypton;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.Render2DEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.Setting;
import skid.krypton.module.setting.BooleanSetting;
import skid.krypton.module.setting.ModeSetting;
import skid.krypton.module.setting.NumberSetting;
import skid.krypton.utils.ColorUtil;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.RenderUtils;
import skid.krypton.utils.TextRenderer;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public final class HUD
        extends Module {
    private static final CharSequence c = EncryptedString.of("Krypton+ Open Sourced by Prestige Edition");
    private static final SimpleDateFormat d = new SimpleDateFormat("HH:mm:ss");
    private final BooleanSetting e = new BooleanSetting(EncryptedString.of("Watermark"), true).setDescription(EncryptedString.of("Shows client name on screen"));
    private final BooleanSetting f = new BooleanSetting(EncryptedString.of("Info"), true).setDescription(EncryptedString.of("Shows system information"));
    private final BooleanSetting g = new BooleanSetting("Modules", true).setDescription(EncryptedString.of("Renders module array list"));
    private final BooleanSetting h = new BooleanSetting("Time", true).setDescription(EncryptedString.of("Shows current time"));
    private final BooleanSetting i = new BooleanSetting("Coordinates", true).setDescription(EncryptedString.of("Shows player coordinates"));
    private final NumberSetting j = new NumberSetting("Opacity", 0.0, 1.0, 0.8f, 0.05f).getValue(EncryptedString.of("Controls the opacity of HUD elements"));
    private final NumberSetting k = new NumberSetting("Corner Radius", 0.0, 10.0, 5.0, 0.5).getValue(EncryptedString.of("Controls the roundness of corners"));
    private final ModeSetting<ModuleListSorting> l = new ModeSetting("Sort Mode", ModuleListSorting.a, ModuleListSorting.class).setDescription(EncryptedString.of("How to sort the module list"));
    private final BooleanSetting m = new BooleanSetting("Rainbow", false).setDescription(EncryptedString.of("Enables rainbow coloring effect"));
    private final NumberSetting n = new NumberSetting("Rainbow Speed", 0.1f, 10.0, 2.0, 0.1f).getValue(EncryptedString.of("Controls the speed of the rainbow effect"));
    private final Color o = new Color(65, 185, 255, 255);
    private final Color p = new Color(255, 110, 230, 255);

    public HUD() {
        super(EncryptedString.of("HUD"), EncryptedString.of("Customizable Heads-Up Display for client information"), -1, Category.RENDER);
        Setting[] settingArray = new Setting[]{this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n};
        this.addSettings(settingArray);
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
        if (this.mc.currentScreen != Krypton.INSTANCE.GUI) {
            DrawContext drawContext = render2DEvent.a;
            int n = this.mc.getWindow().getWidth();
            int n2 = this.mc.getWindow().getHeight();
            RenderUtils.c();
            if (this.e.getValue()) {
                this.a(drawContext, n);
            }
            if (this.f.getValue() && this.mc.player != null) {
                this.a(drawContext);
            }
            if (this.h.getValue()) {
                this.b(drawContext, n);
            }
            if (this.i.getValue() && this.mc.player != null) {
                this.c(drawContext, n2);
            }
            if (this.g.getValue()) {
                this.a(drawContext, n, n2);
            }
            RenderUtils.d();
        }
    }

    private void a(DrawContext drawContext, int n) {
        int n2 = TextRenderer.a(c);
        RenderUtils.a(drawContext.getMatrices(), new Color(35, 35, 35, (int)(this.j.getFloatValue() * 255.0f)), 5.0, 5.0, 5.0f + (float)n2 + 7.0f, 25.0, this.k.getValue(), 15.0);
        Color color = this.m.getValue() ? ColorUtil.a(this.n.getIntValue(), 1) : this.o;
        CharSequence charSequence = c;
        TextRenderer.a(charSequence, drawContext, 8, 8, color.getRGB());
    }

    private void a(DrawContext drawContext) {
        String string = this.k();
        String string2 = "FPS: " + this.mc.getCurrentFps() + " | ";
        String string3 = this.mc.getCurrentServerEntry() == null ? "Single Player" : this.mc.getCurrentServerEntry().address;
        int n = TextRenderer.a(string2);
        int n2 = TextRenderer.a(string);
        RenderUtils.a(drawContext.getMatrices(), new Color(35, 35, 35, (int)(this.j.getFloatValue() * 255.0f)), 5.0, 30.0, 5.0f + (float)(n + n2 + TextRenderer.a(string3)) + 9.0f, 50.0, this.k.getValue(), 15.0);
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
        RenderUtils.a(drawContext.getMatrices(), new Color(35, 35, 35, (int)(this.j.getFloatValue() * 255.0f)), (float)n3 - (float)n2 / 2.0f - 3.0f, 5.0, (float)n3 + (float)n2 / 2.0f + 5.0f, 25.0, this.k.getValue(), 15.0);
        int n4 = this.m.getValue() ? ColorUtil.a(this.n.getIntValue(), 1).getRGB() : this.o.getRGB();
        TextRenderer.a(string, drawContext, (int)((float)n3 - (float)n2 / 2.0f), 8, n4);
    }

    private void c(DrawContext drawContext, int n) {
        Object[] objectArray = new Object[]{this.mc.player.getX()};
        String string = String.format("X: %.1f", objectArray);
        Object[] objectArray2 = new Object[]{this.mc.player.getY()};
        String string2 = String.format("Y: %.1f", objectArray2);
        Object[] objectArray3 = new Object[]{this.mc.player.getZ()};
        String string3 = String.format("Z: %.1f", objectArray3);
        String string4 = "";
        if (this.mc.world != null) {
            boolean bl = this.mc.world.getRegistryKey().getValue().getPath().contains("nether");
            boolean bl2 = this.mc.world.getRegistryKey().getValue().getPath().contains("overworld");
            if (bl) {
                Object[] objectArray4 = new Object[]{this.mc.player.getX() * 8.0, this.mc.player.getZ() * 8.0};
                string4 = String.format(" [%.1f, %.1f]", objectArray4);
            } else if (bl2) {
                Object[] objectArray5 = new Object[]{this.mc.player.getX() / 8.0, this.mc.player.getZ() / 8.0};
                string4 = String.format(" [%.1f, %.1f]", objectArray5);
            }
        }
        String string5 = string + " | " + string2 + " | " + string3 + string4;
        int n2 = TextRenderer.a(string5);
        RenderUtils.a(drawContext.getMatrices(), new Color(35, 35, 35, (int)(this.j.getFloatValue() * 255.0f)), 5.0, n - 25, 5.0f + (float)n2 + 5.0f, n - 5, this.k.getValue(), 15.0);
        TextRenderer.a(string5, drawContext, 10, n - 22, this.o.getRGB());
    }

    private void a(DrawContext drawContext, int n, int n2) {
        List list = this.j();
        int n3 = 5;
        for (Object e : list) {
            String string = ((Module)e).getName().toString();
            int n4 = TextRenderer.a(string);
            int n5 = n - 5;
            RenderUtils.a(drawContext.getMatrices(), new Color(35, 35, 35, (int)(this.j.getFloatValue() * 255.0f)), (float)n - (float)n4 - 13.0f, n3, n5, n3 + 20, this.k.getValue(), 15.0);
            Color color = this.m.getValue() ? ColorUtil.a(this.n.getIntValue() + list.indexOf(e), 1) : this.a(this.o, this.p, (float)list.indexOf(e) / (float)list.size());
            drawContext.fill((int)((float)n5) - 2, n3, (int)((float)n5), n3 + 20, color.getRGB());
            TextRenderer.a(string, drawContext, (int)((float)n - (float)n4 - 10.0f), n3 + 3, color.getRGB());
            n3 += 25;
        }
    }

    private List<Module> j() {
        List<Module> list = Krypton.INSTANCE.getModuleManager().b();
        int n = this.l.getValue().ordinal() ^ 0x4E35B0BF;
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
        String string = this.mc != null && this.mc.player != null && this.mc.getNetworkHandler() != null ? ((playerListEntry = this.mc.getNetworkHandler().getPlayerListEntry(this.mc.player.getUuid())) != null ? "Ping: " + playerListEntry.getLatency() + "ms | " : "Ping: " + "N/A | ") : "Ping: " + "N/A | ";
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

    enum ModuleListSorting {
        a("LENGTH", 0, "Length"),
        b("ALPHABETICAL", 1, "Alphabetical"),
        c("CATEGORY", 2, "Category");

        private final String d;

        ModuleListSorting(final String name, final int ordinal, final String d) {
            this.d = d;
        }

        @Override
        public String toString() {
            return this.d;
        }
    }

}
