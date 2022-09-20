package myjob.common.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobaExceptionHandler {
    @Autowired
    ErrorConfig errorConfig;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result globaException(MyJobException e) {
        Result result = new Result();
        result.setMessage(errorConfig.getErrorMessage(e.getCode()));
        result.setCode(e.getCode());
        result.setSuccess(0);
        return result;
    }
}
