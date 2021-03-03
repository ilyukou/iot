package by.grsu.iot.service.util;

public class StringUtil {

    public static boolean hasSpace(String string) {
        return string.contains(" ");
    }

    public static boolean isValid(boolean spaceAllowed, String string) {
        if (string == null) {
            return false;
        }

        // Space are allowed
        if (spaceAllowed) {
            return true;
        }

        // Space aren't allowed
        return !StringUtil.hasSpace(string);
    }
}
