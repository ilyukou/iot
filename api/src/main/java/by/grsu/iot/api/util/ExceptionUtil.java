package by.grsu.iot.api.util;

import by.grsu.iot.api.exception.ApiException;
import by.grsu.iot.api.exception.BadRequestException;
import by.grsu.iot.api.exception.EntityNotFoundException;
import by.grsu.iot.api.exception.NotAccessForOperationException;
import org.springframework.validation.BindingResult;


public class ExceptionUtil {

    public static void throwException(BindingResult bindingResult){
        String field = ExceptionUtil.getField(bindingResult);
        String message = ExceptionUtil.getExceptionMessage(bindingResult);

        Object obj = getExceptionObject(bindingResult);

        if(obj instanceof ApiException){
            
            if(obj instanceof BadRequestException){
                throw new BadRequestException(field, message);
            }

            if(obj instanceof EntityNotFoundException){
                throw new EntityNotFoundException(field, message);
            }

            if(obj instanceof NotAccessForOperationException){
                throw new NotAccessForOperationException(field, message);
            }
        }

        throw new RuntimeException(message);
    }

    public static String getField(BindingResult bindingResult){
        Object obj = getExceptionObject(bindingResult);

        if(obj instanceof ApiException){
            ApiException exception = (ApiException) obj;
            return exception.getField();
        }

        return null;
    }

    public static String getExceptionMessage(BindingResult bindingResult){
        Object obj = getExceptionObject(bindingResult);

        if(obj instanceof Exception){
            Exception exception = (Exception) obj;
            return exception.getMessage();
        }

        return null;
    }

    private static Object getExceptionObject(BindingResult bindingResult){
        return bindingResult.getAllErrors().get(0).getArguments()[0];
    }
}