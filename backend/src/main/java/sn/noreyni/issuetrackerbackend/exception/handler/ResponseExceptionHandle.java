package sn.noreyni.issuetrackerbackend.exception.handler;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sn.noreyni.issuetrackerbackend.exception.exceptions.ApiErrorException;
import sn.noreyni.issuetrackerbackend.shared.dto.ApiResponseModel;

import java.util.List;

@RestControllerAdvice
@Order(1)
public class ResponseExceptionHandle {

    @ExceptionHandler(ApiErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponseModel<Object> apiErrorException(ApiErrorException apiErrorException){
        return ApiResponseModel.builder()
                .data(apiErrorException.getData())
                .resultCode(apiErrorException.getResponseCode().value())
                .success(false)
                .resultMessage(apiErrorException.getResponseMessage())
                .build();

    }
}