// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.auth;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class JSONSerializer {
    private final HashMap<String, Object> a;

    JSONSerializer(final EmbedSender embedSender) {
        this.a = new HashMap<>();
    }

    void a(final String key, final Object value) {
        if (value != null) {
            this.a.put(key, value);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final Set<Map.Entry<String, Object>> entrySet = this.a.entrySet();
        sb.append("{");
        int n = 0;
        for (final Map.Entry<String, Object> next : entrySet) {
            final Object value = next.getValue();
            sb.append(this.a(next.getKey())).append(":");
            if (value instanceof String) {
                sb.append(this.a(String.valueOf(value)));
            } else if (value instanceof Integer) {
                sb.append(Integer.valueOf(String.valueOf(value)));
            } else if (value instanceof Boolean) {
                sb.append(value);
            } else if (value instanceof JSONSerializer) {
                sb.append(value);
            } else if (value.getClass().isArray()) {
                sb.append("[");
                for (int length = Array.getLength(value), i = 0; i < length; ++i) {
                    final StringBuilder append = sb.append(Array.get(value, i).toString());
                    String str;
                    if (i != length - 1) {
                        str = ",";
                    } else {
                        str = "";
                    }
                    append.append(str);
                }
                sb.append("]");
            }
            ++n;
            sb.append(n == entrySet.size() ? "}" : ",");
        }
        return sb.toString();
    }

    private String a(final String s) {
        return "\"" + s;
    }

    private static byte[] hoealqbrbxaxles() {
        return new byte[]{-109, -39, 124, 118};
    }

    private static byte[] sjmbzdtcmhvtxfs() {
        return new byte[]{-108, -42, 119, 33};
    }
}
