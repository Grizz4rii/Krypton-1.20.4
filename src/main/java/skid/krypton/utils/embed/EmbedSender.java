package skid.krypton.utils.embed;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EmbedSender {
    private final String a;
    private String b;
    private String c;
    private String d;
    private boolean e;
    private final List<bn> f;

    public EmbedSender(final String a) {
        this.f = new ArrayList<bn>();
        this.a = a;
    }

    public void a(final String b) {
        this.b = b;
    }

    public void b(final String c) {
        this.c = c;
    }

    public void c(final String d) {
        this.d = d;
    }

    public void a(final boolean e) {
        this.e = e;
    }

    public void a(final bn bn) {
        this.f.add(bn);
    }

    public void a() throws Throwable {
        if (this.b == null && this.f.isEmpty()) {
            throw new IllegalArgumentException("Set content or add at least one EmbedObject");
        }
        final JSONSerializer JSONSerializer = new JSONSerializer(this);
        JSONSerializer.a("content", this.b);
        JSONSerializer.a("username", this.c);
        JSONSerializer.a("avatar_url", this.d);
        JSONSerializer.a("tts", this.e);
        if (!this.f.isEmpty()) {
            final ArrayList list = new ArrayList();
            for (final bn next : this.f) {
                final JSONSerializer JSONSerializer2 = new JSONSerializer(this);
                JSONSerializer2.a("title", next.a());
                JSONSerializer2.a("description", next.b());
                JSONSerializer2.a("url", next.c());
                if (next.d() != null) {
                    final Color d = next.d();
                    JSONSerializer2.a("color", ((d.getRed() << 8) + d.getGreen() << 8) + d.getBlue());
                }
                final bq e = next.e();
                final br g = next.g();
                final bs f = next.f();
                final bo h = next.h();
                final List i = next.i();
                if (e != null) {
                    final JSONSerializer JSONSerializer3 = new JSONSerializer(this);
                    JSONSerializer3.a("text", e.a());
                    JSONSerializer3.a("icon_url", e.b());
                    JSONSerializer2.a("footer", JSONSerializer3);
                }
                if (g != null) {
                    final JSONSerializer JSONSerializer4 = new JSONSerializer(this);
                    JSONSerializer4.a("url", g.a());
                    JSONSerializer2.a("image", JSONSerializer4);
                }
                if (f != null) {
                    final JSONSerializer JSONSerializer5 = new JSONSerializer(this);
                    JSONSerializer5.a("url", f.a());
                    JSONSerializer2.a("thumbnail", JSONSerializer5);
                    if (h != null) {
                        final JSONSerializer JSONSerializer6 = new JSONSerializer(this);
                        JSONSerializer6.a("name", h.a());
                        JSONSerializer6.a("url", h.b());
                        JSONSerializer6.a("icon_url", h.c());
                        JSONSerializer2.a("author", JSONSerializer6);
                        final ArrayList list2 = new ArrayList();
                        for (final Object next2 : i) {
                            final JSONSerializer JSONSerializer7 = new JSONSerializer(this);
                            JSONSerializer7.a("name", ((bp) next2).a());
                            JSONSerializer7.a("value", ((bp) next2).b());
                            JSONSerializer7.a("inline", ((bp) next2).c());
                            list2.add(JSONSerializer7);
                        }
                        JSONSerializer2.a("fields", list2.toArray());
                        list.add(JSONSerializer2);
                        continue;
                    }
                }
            }
            JSONSerializer.a("embeds", list.toArray());
        }
        final URLConnection openConnection = new URL(this.a).openConnection();
        openConnection.addRequestProperty("Content-Type", "application/json");
        openConnection.addRequestProperty("User-Agent", "YourLocalLinuxUser");
        openConnection.setDoOutput(true);
        ((HttpsURLConnection) openConnection).setRequestMethod("POST");
        final OutputStream outputStream = openConnection.getOutputStream();
        outputStream.write(JSONSerializer.toString().getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
        openConnection.getInputStream().close();
        ((HttpsURLConnection) openConnection).disconnect();
    }
}
