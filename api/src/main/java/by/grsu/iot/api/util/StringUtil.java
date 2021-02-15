package by.grsu.iot.api.util;

import by.grsu.iot.api.exception.BadRequestException;

public class StringUtil {

    public static boolean isStringHasSpace(String string){
        return string.contains(" ");
    }

    public static boolean isStringValidByParam(boolean space, String string){
        if(string == null){
            return false;
        }

        // пробелы допустимы
        if(space){
            return true;
        }

        // пробелы недопустимы
        if(StringUtil.isStringHasSpace(string)){
            return false;
        }

        return true;
    }
}
