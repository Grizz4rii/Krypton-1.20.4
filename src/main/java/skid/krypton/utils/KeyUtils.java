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
                return EncryptedString.of("F19");
            }
            case 164425507: {
                return EncryptedString.of("F12");
            }
            case 164425520: {
                return EncryptedString.of("F6");
            }
            case 164425508: {
                return EncryptedString.of("F8");
            }
            case 164425493: {
                return EncryptedString.of("F16");
            }
            case 164425547: {
                return EncryptedString.of("Print Screen");
            }
            case 164425574: {
                return EncryptedString.of("Arrow Up");
            }
            case 164425419: {
                return EncryptedString.of("Left Control");
            }
            case 164425495: {
                return EncryptedString.of("F21");
            }
            case 164424924: {
                return EncryptedString.of("World 1");
            }
            case 164425534: {
                return EncryptedString.of("F1");
            }
            case 164425017: {
                return EncryptedString.of("Semicolon");
            }
            case 164425487: {
                return EncryptedString.of("F25");
            }
            case 164425510: {
                return EncryptedString.of("F13");
            }
            case 164425421: {
                return EncryptedString.of("Left Alt");
            }
            case 164425500: {
                return EncryptedString.of("Home");
            }
            case 164425465: {
                return EncryptedString.of("Menu");
            }
            case 164425541: {
                return EncryptedString.of("Caps Lock");
            }
            case 164425411: {
                return EncryptedString.of("Right Control");
            }
            case 164425576: {
                return EncryptedString.of("Page Up");
            }
            case 164425597: {
                return EncryptedString.of("Pause");
            }
            default: {
                final String glfwGetKeyName = GLFW.glfwGetKeyName(n, 0);
                if (glfwGetKeyName == null) {
                    return EncryptedString.of("None");
                }
                return StringUtils.capitalize(glfwGetKeyName);
            }
            case 164424992: {
                return EncryptedString.of("Apostrophe");
            }
            case 164425002: {
                return EncryptedString.of("Space");
            }
            case 164425415: {
                return EncryptedString.of("Right Super");
            }
            case 164425522: {
                return EncryptedString.of("F3");
            }
            case 164425582: {
                return EncryptedString.of("Delete");
            }
            case 164425004: {
                return EncryptedString.of("Equals");
            }
            case 164425489: {
                return EncryptedString.of("F22");
            }
            case 164425506: {
                return EncryptedString.of("F11");
            }
            case 164425590: {
                return EncryptedString.of("Enter");
            }
            case 164425503: {
                return EncryptedString.of("F17");
            }
            case 164425572: {
                return EncryptedString.of("Arrow Down");
            }
            case 164425413: {
                return EncryptedString.of("Right Alt");
            }
            case 164425526: {
                return EncryptedString.of("F5");
            }
            case 164425417: {
                return EncryptedString.of("Left Shift");
            }
            case 164425409: {
                return EncryptedString.of("Right Shift");
            }
            case 104066410: {
                return EncryptedString.of("Unknown");
            }
            case 164425060: {
                return EncryptedString.of("LMB");
            }
            case 164425516: {
                return EncryptedString.of("F4");
            }
            case 164425514: {
                return EncryptedString.of("F7");
            }
            case 164425588: {
                return EncryptedString.of("Esc");
            }
            case 164425594: {
                return EncryptedString.of("Backspace");
            }
            case 164425545: {
                return EncryptedString.of("Num Lock");
            }
            case 164424937: {
                return EncryptedString.of("Backslash");
            }
            case 164425578: {
                return EncryptedString.of("Page Down");
            }
            case 164425518: {
                return EncryptedString.of("F9");
            }
            case 164425064: {
                return EncryptedString.of("MMB");
            }
            case 164425477: {
                return EncryptedString.of("F24");
            }
            case 164425483: {
                return EncryptedString.of("F23");
            }
            case 164425170: {
                return EncryptedString.of("Comma");
            }
            case 164425580: {
                return EncryptedString.of("Insert");
            }
            case 164425528: {
                return EncryptedString.of("F2");
            }
            case 164425497: {
                return EncryptedString.of("F18");
            }
            case 164425584: {
                return EncryptedString.of("Arrow Right");
            }
            case 164425512: {
                return EncryptedString.of("F10");
            }
            case 164425504: {
                return EncryptedString.of("F14");
            }
            case 164425501: {
                return EncryptedString.of("End");
            }
            case 164425374: {
                return EncryptedString.of("Numpad Enter");
            }
            case 164424938: {
                return EncryptedString.of("Right Bracket");
            }
            case 164425543: {
                return EncryptedString.of("Scroll Lock");
            }
            case 164425586: {
                return EncryptedString.of("Arrow Left");
            }
            case 164424870: {
                return EncryptedString.of("Grave Accent");
            }
            case 164424951: {
                return EncryptedString.of("Left Bracket");
            }
            case 164425062: {
                return EncryptedString.of("RMB");
            }
            case 164425499: {
                return EncryptedString.of("F15");
            }
            case 164425592: {
                return EncryptedString.of("Tab");
            }
            case 164425485: {
                return EncryptedString.of("F20");
            }
            case 164425423: {
                return EncryptedString.of("Left Super");
            }
            case 164424742: {
                return EncryptedString.of("World 2");
            }
        }
    }

    public static boolean isKeyPressed(final int n) {
        if (n <= 8) {
            return GLFW.glfwGetMouseButton(Krypton.mc.getWindow().getHandle(), n) == 1;
        }
        return GLFW.glfwGetKey(Krypton.mc.getWindow().getHandle(), n) == 1;
    }
}
