package com.jawe.handler;


import com.jawe.response.Result;
import com.jawe.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.annotation.Retention;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //全局异常处理
    @ExceptionHandler(Exception.class)
    public Result error(Exception e) {
//        e.printStackTrace();
        log.error(e.getMessage());
        return Result.error();
    }

    //算术异常处理

    @ExceptionHandler(ArithmeticException.class)
    public Result arithmeticException(ArithmeticException e) {
//        e.printStackTrace();
        log.error(e.getMessage());
        return Result.error().code(ResultCode.ARITHMETIC_EXCEPTION.getCode()).message(ResultCode.ARITHMETIC_EXCEPTION.getMessage());
    }

    //用户信息异常处理
    @ExceptionHandler(BusinessException.class)
    public Result arithmeticException(BusinessException e) {
//        e.printStackTrace();
        log.error(e.getErrMsg());
        return Result.error().code(e.getCode())
                .message(e.getErrMsg());
    }


}
