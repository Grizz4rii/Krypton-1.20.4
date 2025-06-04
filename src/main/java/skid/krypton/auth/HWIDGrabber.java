// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.auth;

import java.security.MessageDigest;

public class HWIDGrabber {
    public static String getHWID() {
        try {
            final String s = System.getenv("COMPUTERNAME") + System.getProperty("user.name") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_LEVEL");
            final MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(s.getBytes());
            final StringBuffer sb = new StringBuffer();
            final byte[] digest = instance.digest();
            for (int i = 0; i < digest.length; ++i) {
                final String hexString = Integer.toHexString(0xFF & digest[i]);
                if (hexString.length() == 1) {
                    sb.append('0');
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (final Exception ex) {
            ex.printStackTrace();
            return zarqskkoly(yxgidwlgofpyqxl(), 1141746861);
        }
    }

    private static byte[] yxgidwlgofpyqxl() {
        return new byte[]{0, 0, 0, 5, 0, 0, 0, 0};
    }
}
