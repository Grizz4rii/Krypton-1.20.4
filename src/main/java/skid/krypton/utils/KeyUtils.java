package skid.krypton.utils;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.glfw.GLFW;
import skid.krypton.Krypton;

public final class KeyUtils {
    public static CharSequence a(final int n) {
        final int n2 = n ^ 0x57A3792D;
        int n3;
        if (n2 != 0) {
            n3 = ((n2 * 31 >>> 4) % n2 ^ n2 >>> 16);
        } else {
            n3 = 0;
        }
        switch (n3) {
            case 164425491: {
                return EncryptedString.a("F19");
            }
            case 164425507: {
                return EncryptedString.a("F12");
            }
            case 164425520: {
                return EncryptedString.a("F6");
            }
            case 164425508: {
                return EncryptedString.a("F8");
            }
            case 164425493: {
                return EncryptedString.a("F16");
            }
            case 164425547: {
                return EncryptedString.a("Print Screen");
            }
            case 164425574: {
                return EncryptedString.a("Arrow Up");
            }
            case 164425419: {
                return EncryptedString.a("Left Control");
            }
            case 164425495: {
                return EncryptedString.a("F21");
            }
            case 164424924: {
                return EncryptedString.a("World 1");
            }
            case 164425534: {
                return EncryptedString.a("F1");
            }
            case 164425017: {
                return EncryptedString.a("Semicolon");
            }
            case 164425487: {
                return EncryptedString.a("F25");
            }
            case 164425510: {
                return EncryptedString.a("F13");
            }
            case 164425421: {
                return EncryptedString.a("Left Alt");
            }
            case 164425500: {
                return EncryptedString.a("Home");
            }
            case 164425465: {
                return EncryptedString.a("Menu");
            }
            case 164425541: {
                return EncryptedString.a("Caps Lock");
            }
            case 164425411: {
                return EncryptedString.a("Right Control");
            }
            case 164425576: {
                return EncryptedString.a("Page Up");
            }
            case 164425597: {
                return EncryptedString.a("Pause");
            }
            default: {
                final String glfwGetKeyName = GLFW.glfwGetKeyName(n, 0);
                if (glfwGetKeyName == null) {
                    return EncryptedString.a("None");
                }
                return StringUtils.capitalize(glfwGetKeyName);
            }
            case 164424992: {
                return EncryptedString.a("Apostrophe");
            }
            case 164425002: {
                return EncryptedString.a("Space");
            }
            case 164425415: {
                return EncryptedString.a("Right Super");
            }
            case 164425522: {
                return EncryptedString.a("F3");
            }
            case 164425582: {
                return EncryptedString.a("Delete");
            }
            case 164425004: {
                return EncryptedString.a("Equals");
            }
            case 164425489: {
                return EncryptedString.a("F22");
            }
            case 164425506: {
                return EncryptedString.a("F11");
            }
            case 164425590: {
                return EncryptedString.a("Enter");
            }
            case 164425503: {
                return EncryptedString.a("F17");
            }
            case 164425572: {
                return EncryptedString.a("Arrow Down");
            }
            case 164425413: {
                return EncryptedString.a("Right Alt");
            }
            case 164425526: {
                return EncryptedString.a("F5");
            }
            case 164425417: {
                return EncryptedString.a("Left Shift");
            }
            case 164425409: {
                return EncryptedString.a("Right Shift");
            }
            case 104066410: {
                return EncryptedString.a("Unknown");
            }
            case 164425060: {
                return EncryptedString.a("LMB");
            }
            case 164425516: {
                return EncryptedString.a("F4");
            }
            case 164425514: {
                return EncryptedString.a("F7");
            }
            case 164425588: {
                return EncryptedString.a("Esc");
            }
            case 164425594: {
                return EncryptedString.a("Backspace");
            }
            case 164425545: {
                return EncryptedString.a("Num Lock");
            }
            case 164424937: {
                return EncryptedString.a("Backslash");
            }
            case 164425578: {
                return EncryptedString.a("Page Down");
            }
            case 164425518: {
                return EncryptedString.a("F9");
            }
            case 164425064: {
                return EncryptedString.a("MMB");
            }
            case 164425477: {
                return EncryptedString.a("F24");
            }
            case 164425483: {
                return EncryptedString.a("F23");
            }
            case 164425170: {
                return EncryptedString.a("Comma");
            }
            case 164425580: {
                return EncryptedString.a("Insert");
            }
            case 164425528: {
                return EncryptedString.a("F2");
            }
            case 164425497: {
                return EncryptedString.a("F18");
            }
            case 164425584: {
                return EncryptedString.a("Arrow Right");
            }
            case 164425512: {
                return EncryptedString.a("F10");
            }
            case 164425504: {
                return EncryptedString.a("F14");
            }
            case 164425501: {
                return EncryptedString.a("End");
            }
            case 164425374: {
                return EncryptedString.a("Numpad Enter");
            }
            case 164424938: {
                return EncryptedString.a("Right Bracket");
            }
            case 164425543: {
                return EncryptedString.a("Scroll Lock");
            }
            case 164425586: {
                return EncryptedString.a("Arrow Left");
            }
            case 164424870: {
                return EncryptedString.a("Grave Accent");
            }
            case 164424951: {
                return EncryptedString.a("Left Bracket");
            }
            case 164425062: {
                return EncryptedString.a("RMB");
            }
            case 164425499: {
                return EncryptedString.a("F15");
            }
            case 164425592: {
                return EncryptedString.a("Tab");
            }
            case 164425485: {
                return EncryptedString.a("F20");
            }
            case 164425423: {
                return EncryptedString.a("Left Super");
            }
            case 164424742: {
                return EncryptedString.a("World 2");
            }
        }
    }

    public static boolean b(final int n) {
        if (n <= 8) {
            return GLFW.glfwGetMouseButton(Krypton.mc.getWindow().getHandle(), n) == 1;
        }
        return GLFW.glfwGetKey(Krypton.mc.getWindow().getHandle(), n) == 1;
    }
}
