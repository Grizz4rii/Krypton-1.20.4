package skid.krypton.module.modules;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.TickEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.Setting;
import skid.krypton.module.setting.*;
import skid.krypton.utils.EncryptedString;

import java.io.PrintStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class AuctionSniper
        extends Module {
    private final ItemSetting c = new ItemSetting(EncryptedString.of("Sniping Item"), Items.AIR);
    private final StringSetting d = new StringSetting(EncryptedString.of("Price"), "1k");
    private final ModeSetting<Mode> e = new ModeSetting(EncryptedString.of("Mode"), Mode.MANUAL, Mode.class).setDescription(EncryptedString.of("Manual is faster but api doesnt require auction gui opened all the time"));
    private final StringSetting f = new StringSetting(EncryptedString.of("Api Key"), "").setDescription(EncryptedString.of("You can get it by typing /api in chat"));
    private final NumberSetting g = new NumberSetting(EncryptedString.of("Refresh Delay"), 0.0, 100.0, 2.0, 1.0);
    private final NumberSetting h = new NumberSetting(EncryptedString.of("Buy Delay"), 0.0, 100.0, 2.0, 1.0);
    private final NumberSetting i = new NumberSetting(EncryptedString.of("API Refresh Rate"), 10.0, 5000.0, 250.0, 10.0).getValue(EncryptedString.of("How often to query the API (in milliseconds)"));
    private final BooleanSetting j = new BooleanSetting(EncryptedString.of("Show API Notifications"), true).setDescription(EncryptedString.of("Show chat notifications for API actions"));
    private int k;
    private boolean l;
    private final HttpClient m;
    private final Gson n;
    private long o = 0L;
    private final Map<String, Double> p = new HashMap<String, Double>();
    private boolean q = false;
    private boolean r = false;
    private int s = -1;
    private String t = "";

    public AuctionSniper() {
        super(EncryptedString.of("Auction Sniper"), EncryptedString.of("Snipes items on auction house for cheap"), -1, Category.DONUT);
        this.m = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5L)).build();
        this.n = new Gson();
        Setting[] settingArray = new Setting[]{this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j};
        this.addSettings(settingArray);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        double d = this.a(this.d.getValue());
        if (d == -1.0) {
            if (this.mc.player != null) {
                ClientPlayerEntity clientPlayerEntity = this.mc.player;
                clientPlayerEntity.sendMessage(Text.of("Invalid Price"), true);
            }
            this.toggle();
            return;
        }
        if (this.c.getItem() != Items.AIR) {
            Map<String, Double> map = this.p;
            map.put(this.c.getItem().toString(), d);
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
    public void a(TickEvent tickEvent) {
        block10: {
            block9: {
                if (this.mc.player == null) {
                    return;
                }
                if (this.k > 0) {
                    --this.k;
                    return;
                }
                if (this.e.isMode(Mode.API)) {
                    this.j();
                    return;
                }
                if (!this.e.isMode(Mode.MANUAL)) break block9;
                ScreenHandler screenHandler = this.mc.player.currentScreenHandler;
                if (!(this.mc.player.currentScreenHandler instanceof GenericContainerScreenHandler)) break block10;
                if (((GenericContainerScreenHandler)screenHandler).getRows() == 6) {
                    this.a((GenericContainerScreenHandler)screenHandler);
                } else if (((GenericContainerScreenHandler)screenHandler).getRows() == 3) {
                    this.b((GenericContainerScreenHandler)screenHandler);
                }
            }
            return;
        }
        String[] stringArray = this.c.getItem().getTranslationKey().split("\\.");
        String string2 = stringArray[stringArray.length - 1];
        String string3 = Arrays.stream(string2.replace("_", " ").split(" ")).map(string -> string.substring(0, 1).toUpperCase() + string.substring(1)).collect(Collectors.joining(" "));
        this.mc.getNetworkHandler().sendChatCommand("ah " + string3);
        this.k = 20;
    }

    private void j() {
        block10: {
            block12: {
                block8: {
                    block11: {
                        block9: {
                            if (!this.r) break block8;
                            ScreenHandler screenHandler = this.mc.player.currentScreenHandler;
                            if (!(this.mc.player.currentScreenHandler instanceof GenericContainerScreenHandler)) break block9;
                            this.s = -1;
                            if (((GenericContainerScreenHandler)screenHandler).getRows() == 6) {
                                this.a((GenericContainerScreenHandler)screenHandler);
                            } else if (((GenericContainerScreenHandler)screenHandler).getRows() == 3) {
                                this.b((GenericContainerScreenHandler)screenHandler);
                            }
                            break block10;
                        }
                        if (this.s != -1) break block11;
                        this.mc.getNetworkHandler().sendChatCommand("ah " + this.t);
                        this.s = 0;
                        break block10;
                    }
                    if (this.s <= 40) break block12;
                    this.r = false;
                    this.t = "";
                    break block10;
                }
                if (this.mc.player.currentScreenHandler instanceof GenericContainerScreenHandler && this.mc.currentScreen.getTitle().getString().contains("Page")) {
                    this.mc.player.closeHandledScreen();
                    this.k = 20;
                    return;
                }
                if (this.q) {
                    return;
                }
                long l = System.currentTimeMillis();
                long l2 = l - this.o;
                if (l2 > (long)this.i.getIntValue()) {
                    this.o = l;
                    if (this.f.getValue().isEmpty()) {
                        if (this.j.getValue()) {
                            ClientPlayerEntity clientPlayerEntity = this.mc.player;
                            clientPlayerEntity.sendMessage(Text.of("\u00a7cAPI key is not set. Set it using /api in-game."), false);
                        }
                        return;
                    }
                    this.q = true;
                    this.k().thenAccept(this::a);
                }
                return;
            }
            ++this.s;
        }
    }

    private CompletableFuture<List<?>> k() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String string = "https://api.donutsmp.net/v1/auction/list/" + 1;
                HttpResponse<String> httpResponse = this.m.send(HttpRequest.newBuilder().uri(URI.create(string)).header("Authorization", "Bearer " + this.f.getValue()).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString("{\"sort\": \"recently_listed\"}")).build(), HttpResponse.BodyHandlers.ofString());
                if (httpResponse.statusCode() != 200) {
                    if (this.j.getValue() && this.mc.player != null) {
                        ClientPlayerEntity clientPlayerEntity = this.mc.player;
                        clientPlayerEntity.sendMessage(Text.of("\u00a7cAPI Error: " + httpResponse.statusCode()), false);
                    }
                    ArrayList<?> arrayList = new ArrayList<>();
                    this.q = false;
                    return arrayList;
                }
                Gson gson = this.n;
                JsonArray jsonArray = gson.fromJson(httpResponse.body(), JsonObject.class).getAsJsonArray("result");
                ArrayList<JsonObject> arrayList = new ArrayList<>();
                for (JsonElement jsonElement : jsonArray) {
                    arrayList.add(jsonElement.getAsJsonObject());
                }
                this.q = false;
                return arrayList;
            } catch (Throwable _t) {
                _t.printStackTrace(System.err);
                return List.of();
            }
        });
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void a(List list) {
        block2: for (Object e : list) {
            try {
                double d;
                String string;
                String string2 = ((JsonObject)e).getAsJsonObject("item").get("id").getAsString();
                long l = ((JsonObject)e).get("price").getAsLong();
                String string3 = ((JsonObject)e).getAsJsonObject("seller").get("name").getAsString();
                Iterator<Map.Entry<String, Double>> iterator = this.p.entrySet().iterator();
                do {
                    if (!iterator.hasNext()) continue block2;
                    Map.Entry<String, Double> entry = iterator.next();
                    string = entry.getKey();
                    d = entry.getValue();
                } while (!string2.contains(string) || !((double)l <= d));
                if (this.j.getValue() && this.mc.player != null) {
                    ClientPlayerEntity clientPlayerEntity = this.mc.player;
                    clientPlayerEntity.sendMessage(Text.of("\u00a7aFound " + string2 + " for " + this.a(l) + " \u00a7r(threshold: " + this.a(d) + ") \u00a7afrom seller: " + string3), false);
                }
                this.r = true;
                this.t = string3;
                return;
            }
            catch (Exception exception) {
                if (!this.j.getValue() || this.mc.player == null) continue;
                ClientPlayerEntity clientPlayerEntity = this.mc.player;
                clientPlayerEntity.sendMessage(Text.of("\u00a7cError processing auction: " + exception.getMessage()), false);
            }
        }
    }

    private void a(GenericContainerScreenHandler genericContainerScreenHandler) {
        ItemStack itemStack = genericContainerScreenHandler.getSlot(47).getStack();
        if (itemStack.isOf(Items.AIR)) {
            this.k = 2;
            return;
        }
        for (Object e : itemStack.getTooltip(Item.TooltipContext.DEFAULT, this.mc.player, TooltipType.BASIC)) {
            String string = e.toString();
            if (!string.contains("Recently Listed") || !((Text)e).getStyle().toString().contains("white") && !string.contains("white")) continue;
            this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 47, 1, SlotActionType.QUICK_MOVE, this.mc.player);
            this.k = 5;
            return;
        }
        for (int i = 0; i < 44; ++i) {
            ItemStack itemStack2 = genericContainerScreenHandler.getSlot(i).getStack();
            if (!itemStack2.isOf(this.c.getItem()) || !this.a(itemStack2)) continue;
            if (this.l) {
                this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, i, 1, SlotActionType.QUICK_MOVE, this.mc.player);
                this.l = false;
                return;
            }
            this.l = true;
            this.k = this.h.getIntValue();
            return;
        }
        if (this.r) {
            this.r = false;
            this.t = "";
            this.mc.player.closeHandledScreen();
        } else {
            this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 49, 1, SlotActionType.QUICK_MOVE, this.mc.player);
            this.k = this.g.getIntValue();
        }
    }

    private void b(GenericContainerScreenHandler genericContainerScreenHandler) {
        if (this.a(genericContainerScreenHandler.getSlot(13).getStack())) {
            this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 15, 1, SlotActionType.QUICK_MOVE, this.mc.player);
            this.k = 20;
        }
        if (this.r) {
            this.r = false;
            this.t = "";
        }
    }

    private double b(List list) {
        String string;
        String string2;
        block2: {
            if (list == null || list.isEmpty()) {
                return -1.0;
            }
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                String string3 = ((Text)iterator.next()).getString();
                if (!string3.matches("(?i).*price\\s*:\\s*\\$.*")) continue;
                String string4 = string3.replaceAll("[,$]", "");
                Matcher matcher = Pattern.compile("([\\d]+(?:\\.[\\d]+)?)\\s*([KMB])?", 2).matcher(string4);
                if (!matcher.find()) continue;
                string2 = matcher.group(1);
                string = matcher.group(2) != null ? matcher.group(2).toUpperCase() : "";
                break block2;
            }
            return -1.0;
        }
        return this.a(string2 + string);
    }

    private boolean a(ItemStack itemStack) {
        List list = itemStack.getTooltip(Item.TooltipContext.DEFAULT, this.mc.player, TooltipType.BASIC);
        double d = this.b(list) / (double)itemStack.getCount();
        double d2 = this.a(this.d.getValue());
        if (d2 == -1.0) {
            if (this.mc.player != null) {
                ClientPlayerEntity clientPlayerEntity = this.mc.player;
                clientPlayerEntity.sendMessage(Text.of("Invalid Price"), true);
            }
            this.toggle();
            return false;
        }
        if (d == -1.0) {
            if (this.mc.player != null) {
                ClientPlayerEntity clientPlayerEntity = this.mc.player;
                clientPlayerEntity.sendMessage(Text.of("Invalid Auction Item Price"), true);
                for (int i = 0; i < list.size() - 1; ++i) {
                    PrintStream printStream = System.out;
                    printStream.println(i + ". " + ((Text)list.get(i)).getString());
                }
            }
            this.toggle();
            return false;
        }
        boolean bl = d <= d2;
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private double a(String string) {
        if (string == null) return -1.0;
        if (string.isEmpty()) {
            return -1.0;
        }
        String string2 = string.trim().toUpperCase();
        double d = 1.0;
        if (string2.endsWith("B")) {
            d = 1.0E9;
            string2 = string2.substring(0, string2.length() - 1);
        } else if (string2.endsWith("M")) {
            d = 1000000.0;
            string2 = string2.substring(0, string2.length() - 1);
        } else if (string2.endsWith("K")) {
            d = 1000.0;
            string2 = string2.substring(0, string2.length() - 1);
        }
        try {
            return Double.parseDouble(string2) * d;
        }
        catch (NumberFormatException numberFormatException) {
            return -1.0;
        }
    }

    private String a(double d) {
        if (d >= 1.0E9) {
            Object[] objectArray = new Object[]{d / 1.0E9};
            return String.format("%.2fB", objectArray);
        }
        if (d >= 1000000.0) {
            Object[] objectArray = new Object[]{d / 1000000.0};
            return String.format("%.2fM", objectArray);
        }
        if (d >= 1000.0) {
            Object[] objectArray = new Object[]{d / 1000.0};
            return String.format("%.2fK", objectArray);
        }
        Object[] objectArray = new Object[]{d};
        return String.format("%.2f", objectArray);
    }

    public enum Mode {
        API("API", 0),
        MANUAL("MANUAL", 1);

        Mode(final String name, final int ordinal) {
        }
    }

}
