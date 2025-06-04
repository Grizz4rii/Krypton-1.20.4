// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import skid.krypton.enums.Enum7;
import skid.krypton.events.*;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.*;
import skid.krypton.utils.EncryptedString;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class AuctionSniper extends Module {
    private final ItemSetting c;
    private final StringSetting d;
    private final EnumSetting<Enum7> e;
    private final StringSetting f;
    private final NumberSetting g;
    private final NumberSetting h;
    private final NumberSetting i;
    private final BooleanSetting j;
    private int k;
    private boolean l;
    private final HttpClient m;
    private final Gson n;
    private long o;
    private final Map<String, Double> p;
    private boolean q;
    private boolean r;
    private int s;
    private String t;

    public AuctionSniper() {
        super(EncryptedString.a("Auction Sniper"), EncryptedString.a("Snipes items on auction house for cheap"), -1, Category.c);
        this.c = new ItemSetting(EncryptedString.a("Sniping Item"), Items.AIR);
        this.d = new StringSetting(EncryptedString.a("Price"), "1k");
        this.e = new EnumSetting(EncryptedString.a("Mode"), Enum7.b, Enum7.class).a(EncryptedString.a("Manual is faster but api doesnt require auction gui opened all the time"));
        this.f = new StringSetting(EncryptedString.a("Api Key"), "").a(EncryptedString.a("You can get it by typing /api in chat"));
        this.g = new NumberSetting(EncryptedString.a("Refresh Delay"), 0.0, 100.0, 2.0, 1.0);
        this.h = new NumberSetting(EncryptedString.a("Buy Delay"), 0.0, 100.0, 2.0, 1.0);
        this.i = new NumberSetting(EncryptedString.a("API Refresh Rate"), 10.0, 5000.0, 250.0, 10.0).a(EncryptedString.a("How often to query the API (in milliseconds)"));
        this.j = new BooleanSetting(EncryptedString.a("Show API Notifications"), true).a(EncryptedString.a("Show chat notifications for API actions"));
        this.o = 0L;
        this.p = new HashMap<String, Double>();
        this.q = false;
        this.r = false;
        this.s = -1;
        this.t = "";
        this.m = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5L)).build();
        this.n = new Gson();
        this.a(this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        final double a = this.a(this.d.getValue());
        if (a == -1.0) {
            if (this.b.player != null) {
                this.b.player.method_7353(Text.of("Invalid Price"), true);
            }
            this.toggle();
            return;
        }
        if (this.c.a() != Items.AIR) {
            this.p.put(this.c.a().toString(), a);
        }
        this.o = 0L;
        this.q = false;
        this.r = false;
        this.t = "";
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.r = false;
    }

    @EventListener
    public void a(final TickEvent tickEvent) {
        if (this.b.player == null) {
            return;
        }
        if (this.k > 0) {
            --this.k;
            return;
        }
        if (this.e.b(Enum7.a)) {
            this.j();
            return;
        }
        if (this.e.b(Enum7.b)) {
            final ScreenHandler field_7512 = this.b.player.field_7512;
            if (!(this.b.player.field_7512 instanceof GenericContainerScreenHandler)) {
                final String[] split = this.c.a().getTranslationKey().split("\\.");
                this.b.getNetworkHandler().sendChatCommand("ah " + Arrays.stream(split[split.length - 1].replace("_", " ").split(" ")).map(s -> s.substring(0, 1).toUpperCase() + s.substring(1)).collect((Collector<? super Object, ?, String>) Collectors.joining(" ")));
                this.k = 20;
                return;
            }
            if (((GenericContainerScreenHandler) field_7512).getRows() == 6) {
                this.a((GenericContainerScreenHandler) field_7512);
            } else if (((GenericContainerScreenHandler) field_7512).getRows() == 3) {
                this.b((GenericContainerScreenHandler) field_7512);
            }
        }
    }

    private void j() {
        if (this.r) {
            final ScreenHandler field_7512 = this.b.player.field_7512;
            if (this.b.player.field_7512 instanceof GenericContainerScreenHandler) {
                this.s = -1;
                if (((GenericContainerScreenHandler) field_7512).getRows() == 6) {
                    this.a((GenericContainerScreenHandler) field_7512);
                } else if (((GenericContainerScreenHandler) field_7512).getRows() == 3) {
                    this.b((GenericContainerScreenHandler) field_7512);
                }
            } else if (this.s == -1) {
                this.b.getNetworkHandler().sendChatCommand("ah " + this.t);
                this.s = 0;
            } else if (this.s > 40) {
                this.r = false;
                this.t = "";
            } else {
                ++this.s;
            }
            return;
        }
        if (this.b.player.field_7512 instanceof GenericContainerScreenHandler && this.b.currentScreen.getTitle().getString().contains("Page")) {
            this.b.player.method_7346();
            this.k = 20;
            return;
        }
        if (this.q) {
            return;
        }
        final long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.o > this.i.f()) {
            this.o = currentTimeMillis;
            if (this.f.getValue().isEmpty()) {
                if (this.j.c()) {
                    this.b.player.method_7353(Text.of("�cAPI key is not set. Set it using /api in-game."), false);
                }
                return;
            }
            this.q = true;
            this.k().thenAccept(this::a);
        }
    }

    private CompletableFuture k() {
        return CompletableFuture.supplyAsync(() -> {
            this.m.send(HttpRequest.newBuilder().uri(URI.create("https://api.donutsmp.net/v1/auction/list/" + true)).header("Authorization", "Bearer " + this.f.getValue()).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString("{\"sort\": \"recently_listed\"}")).build(), HttpResponse.BodyHandlers.ofString());
            final HttpResponse httpResponse;
            if (httpResponse.statusCode() != 200) {
                if (this.j.c() && this.b.player != null) {
                    this.b.player.method_7353(Text.of("�cAPI Error: " + httpResponse.statusCode()), false);
                }
                final ArrayList list = new ArrayList();
                this.q = false;
                return list;
            } else {
                ((JsonObject) this.n.fromJson((String) httpResponse.body(), (Class) JsonObject.class)).getAsJsonArray("result");
                final ArrayList list2 = new ArrayList();
                final JsonArray jsonArray;
                jsonArray.iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    list2.add(iterator.next().getAsJsonObject());
                }
                this.q = false;
                return list2;
            }
        });
    }

    private void a(final List list) {
        final Iterator iterator = list.iterator();
        while (true) {
            if (!iterator.hasNext()) {
                return;
            }
            final Object next = iterator.next();
            try {
                final String asString = ((JsonObject) next).getAsJsonObject("item").get("id").getAsString();
                final long asLong = ((JsonObject) next).get("price").getAsLong();
                final String asString2 = ((JsonObject) next).getAsJsonObject("seller").get("name").getAsString();
                double doubleValue = 0.0;
                Block_6:
                {
                    for (final Map.Entry<String, Double> next2 : this.p.entrySet()) {
                        final Object key = ((Map.Entry<Object, V>) next2).getKey();
                        doubleValue = next2.getValue();
                        if (asString.contains((CharSequence) key) && asLong <= doubleValue) {
                            break Block_6;
                        }
                    }
                    continue;
                }
                if (this.j.c() && this.b.player != null) {
                    this.b.player.method_7353(Text.of("�aFound " + asString + " for " + this.a((double) asLong) + " �r(threshold: " + this.a(doubleValue) + ") �afrom seller: " + asString2), false);
                }
                this.r = true;
                this.t = asString2;
            } catch (final Exception ex) {
                if (!this.j.c() || this.b.player == null) {
                    continue;
                }
                this.b.player.method_7353(Text.of("�cError processing auction: " + ex.getMessage()), false);
            }
        }
    }

    private void a(final GenericContainerScreenHandler genericContainerScreenHandler) {
        final ItemStack stack = genericContainerScreenHandler.method_7611(47).getStack();
        if (stack.isOf(Items.AIR)) {
            this.k = 2;
            return;
        }
        for (final Object next : stack.getTooltip(Item$TooltipContext.DEFAULT, (PlayerEntity) this.b.player, (TooltipType) TooltipType.BASIC)) {
            final String string = next.toString();
            if (string.contains("Recently Listed") && (((Text) next).getStyle().toString().contains("white") || string.contains("white"))) {
                this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, 47, 1, SlotActionType.QUICK_MOVE, (PlayerEntity) this.b.player);
                this.k = 5;
                return;
            }
        }
        int i = 0;
        while (i < 44) {
            final ItemStack stack2 = genericContainerScreenHandler.method_7611(i).getStack();
            if (stack2.isOf(this.c.a()) && this.a(stack2)) {
                if (this.l) {
                    this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, i, 1, SlotActionType.QUICK_MOVE, (PlayerEntity) this.b.player);
                    this.l = false;
                    return;
                }
                this.l = true;
                this.k = this.h.f();
                return;
            } else {
                ++i;
            }
        }
        if (this.r) {
            this.r = false;
            this.t = "";
            this.b.player.method_7346();
        } else {
            this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, 49, 1, SlotActionType.QUICK_MOVE, (PlayerEntity) this.b.player);
            this.k = this.g.f();
        }
    }

    private void b(final GenericContainerScreenHandler genericContainerScreenHandler) {
        if (this.a(genericContainerScreenHandler.method_7611(13).getStack())) {
            this.b.interactionManager.clickSlot(this.b.player.field_7512.syncId, 15, 1, SlotActionType.QUICK_MOVE, (PlayerEntity) this.b.player);
            this.k = 20;
        }
        if (this.r) {
            this.r = false;
            this.t = "";
        }
    }

    private double b(final List list) {
        if (list == null || list.isEmpty()) {
            return -1.0;
        }
        final Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            final String string = ((Text) iterator.next()).getString();
            if (!string.matches("(?i).*price\\s*:\\s*\\$.*")) {
                continue;
            }
            final Matcher matcher = Pattern.compile("([\\d]+(?:\\.[\\d]+)?)\\s*([KMB])?", 2).matcher(string.replaceAll("[,$]", ""));
            if (matcher.find()) {
                final String group = matcher.group(1);
                String upperCase;
                if (matcher.group(2) != null) {
                    upperCase = matcher.group(2).toUpperCase();
                } else {
                    upperCase = "";
                }
                return this.a(group + upperCase);
            }
        }
        return -1.0;
    }

    private boolean a(final ItemStack itemStack) {
        final List tooltip = itemStack.getTooltip(Item$TooltipContext.DEFAULT, (PlayerEntity) this.b.player, (TooltipType) TooltipType.BASIC);
        final double n = this.b(tooltip) / itemStack.getCount();
        final double a = this.a(this.d.getValue());
        if (a == -1.0) {
            if (this.b.player != null) {
                this.b.player.method_7353(Text.of("Invalid Price"), true);
            }
            this.toggle();
            return false;
        }
        if (n == -1.0) {
            if (this.b.player != null) {
                this.b.player.method_7353(Text.of("Invalid Auction Item Price"), true);
                for (int i = 0; i < tooltip.size() - 1; ++i) {
                    System.out.println(i + ". " + ((Text) tooltip.get(i)).getString());
                }
            }
            this.toggle();
            return false;
        }
        return n <= a;
    }

    private double a(final String s) {
        if (s != null && !s.isEmpty()) {
            String s2 = s.trim().toUpperCase();
            double n = 1.0;
            if (s2.endsWith("B")) {
                n = 1.0E9;
                s2 = s2.substring(0, s2.length() - 1);
            } else if (s2.endsWith("M")) {
                n = 1000000.0;
                s2 = s2.substring(0, s2.length() - 1);
            } else if (s2.endsWith("K")) {
                n = 1000.0;
                s2 = s2.substring(0, s2.length() - 1);
            }
            try {
                return Double.parseDouble(s2) * n;
            } catch (final NumberFormatException ex) {
            }
        }
        return -1.0;
    }

    private String a(final double d) {
        if (d >= 1.0E9) {
            return String.format("%.2fB", d / 1.0E9);
        }
        if (d >= 1000000.0) {
            return String.format("%.2fM", d / 1000000.0);
        }
        if (d >= 1000.0) {
            return String.format("%.2fK", d / 1000.0);
        }
        return String.format("%.2f", d);
    }
}
