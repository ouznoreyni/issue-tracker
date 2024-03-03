package sn.noreyni.issuetrackerbackend.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import sn.noreyni.issuetrackerbackend.exception.exceptions.BadRequestFilterException;
import sn.noreyni.issuetrackerbackend.shared.dto.ApiResponseModel;

public class BadRequestFilterHandler {
    @ExceptionHandler(BadRequestFilterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponseModel<Object> handle(BadRequestFilterException e) {
        return
                ApiResponseModel
                        .builder()
                        .success(false)
                        .resultCode(HttpStatus.BAD_REQUEST.value())
                        .resultMessage(e.getMessage())
                        .build();
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponseModel<Object> handle(IllegalArgumentException e) {
        return
                ApiResponseModel
                        .builder()
                        .success(false)
                        .resultCode(HttpStatus.BAD_REQUEST.value())
                        .resultMessage(e.getMessage())
                        .build();
    }
}
