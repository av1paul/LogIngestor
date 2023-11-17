package com.avi.controller;

import com.avi.exception.ApiException;
import com.avi.exception.ErrorCode;
import com.avi.exception.ErrorData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class RestControllerAdvice {

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ErrorData handleUnknownException(HttpServletRequest req, Throwable t) {
        t.printStackTrace();
        ErrorData data = ErrorData.builder()
                .code(ErrorCode.INTERNAL_SERVER_ERROR)
                .message("Internal Error")
                .build();
        return data;
    }


    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ErrorData handleApiException(HttpServletRequest req, ApiException e, HttpServletResponse response) {
        e.printStackTrace();
        setResponseStatus(response, e.getErrorCode());
        ErrorData data = ErrorData.builder()
                .code(e.getErrorCode())
                .message(e.getMessage())
                .build();
        return data;
    }


    private void setResponseStatus(HttpServletResponse response, ErrorCode errorCode) {
        switch (errorCode) {
            case BAD_REQUEST:
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                break;
            case INTERNAL_SERVER_ERROR:
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
