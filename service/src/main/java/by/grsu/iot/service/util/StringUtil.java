package by.grsu.iot.service.util;

public class StringUtil {

    public static boolean isStringHasSpace(String string){
        return string.contains(" ");
    }

    public static boolean isStringValidByParam(boolean space, String string){
        if(string == null){
            return false;
        }

        // Space are allowed
        if(space){
            return true;
        }

        // Space aren't allowed
        if(StringUtil.isStringHasSpace(string)){
            return false;
        }

        return true;
    }
}
