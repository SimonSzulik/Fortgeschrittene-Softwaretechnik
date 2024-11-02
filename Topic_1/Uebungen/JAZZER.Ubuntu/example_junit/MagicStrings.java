
import java.nio.charset.StandardCharsets;

public class MagicStrings {

    public static void compare(int pos, int len, int offset, String key, String msg) {
        if (len > 10)
            if (key.length() > 5)
                if (key.charAt(4) == '7')
                    if (onlyValidCharacters(key))
                        if (onlyValidCharacters(msg))
                            if (key.substring(pos, pos + len).equals(msg.substring(pos + offset, pos + offset + len))) {
                                System.out.println("pos=" + pos + ", len=" + len + ", offset=" + offset + ", key=" + key + ", msg=" + msg);
                                throw new IllegalStateException("Not reached");
                            }
    }


    static boolean onlyValidCharacters(String s) {
        byte[] bs = s.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < bs.length; i++) {
            byte a = bs[i];
            if ((a < '0') || (a > 'z')) return false;
        }
        return true;
    }
}