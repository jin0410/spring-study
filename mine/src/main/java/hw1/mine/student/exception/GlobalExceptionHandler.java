package hw1.mine.student.exception;

import hw1.mine.restapi.Message;
import hw1.mine.restapi.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<RestResponse> handleCustomExceptions(CustomException ex) {
        RestResponse<Object> restResponse = new RestResponse<>();
        restResponse = RestResponse.builder()
                .code(ex.getErrorCode().status().value())
                .httpStatus(ex.getErrorCode().status())
                .message(ex.getErrorCode().label())
                .build();
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        RestResponse<Object> restResponse = new RestResponse<>();
        restResponse = RestResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(Message.WRONG_ARGUMENTS.label())
                .build();
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
