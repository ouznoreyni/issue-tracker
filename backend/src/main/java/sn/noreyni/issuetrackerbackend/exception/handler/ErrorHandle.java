package sn.noreyni.issuetrackerbackend.exception.handler;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import sn.noreyni.issuetrackerbackend.shared.dto.ApiResponseModel;

@ControllerAdvice
@Order(2)
public class ErrorHandle {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ApiResponseModel<Object> handleException(Exception ex) {
        return  ApiResponseModel
                .builder()
                .success(false)
                .resultCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .resultMessage(ex.getMessage())
                .build();

    }
}
