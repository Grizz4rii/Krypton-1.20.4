package skid.krypton.auth;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import skid.krypton.utils.EncryptedString;

import java.util.concurrent.CompletableFuture;

public class Auth {
    private static final EncryptedString b;
    private static final int c = 5050;
    private static final Gson d;
    public static EncryptedString a;
    private final boolean e;
    private final JsonObject f;
    private final String g;

    public Auth(final EncryptedString a) {
        this.e = true;
        this.f = null;
        this.g = null;
        Auth.a = a;
    }

    public CompletableFuture<Boolean> a() {
        final CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        completableFuture.complete(true);
        return completableFuture;
    }

    public CompletableFuture<Boolean> a(final JsonObject jsonObject) {
        final CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        completableFuture.complete(true);
        return completableFuture;
    }

    public boolean a(final String s) {
        return this.e && (s.equals("Krypton") || (s.equals("Krypton+") && this.g.equals("Krypton+")));
    }

    public boolean b() {
        return true;
    }

    public JsonObject c() {
        return this.f;
    }

    public String d() {
        return this.g;
    }

    static {
        b = EncryptedString.of("127.0.0.1");
        d = new Gson();
    }
}
