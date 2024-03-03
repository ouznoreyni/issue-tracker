package sn.noreyni.issuetrackerbackend.exception.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Builder
@Setter
@Getter
public class ApiErrorException extends RuntimeException{
    private HttpStatus responseCode  ;
    private String responseMessage  ;
    private Object data;
}