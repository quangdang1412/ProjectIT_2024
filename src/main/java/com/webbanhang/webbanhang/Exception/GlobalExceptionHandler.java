package com.webbanhang.webbanhang.Exception;
import org.springframework.data.mapping.PropertyReferenceException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class, IllegalArgumentException.class,PropertyReferenceException.class,})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerValidationException(Exception e, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getDescription(false).replace("uri=",""));
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        String message = e.getMessage();
        if(e instanceof MethodArgumentNotValidException) {
            int start = message.lastIndexOf("[");
            int end = message.lastIndexOf("]");
            message = message.substring(start+1,end-1);
            errorResponse.setError("Payload Invalid");
        }
        else if(e instanceof ConstraintViolationException || e instanceof IllegalArgumentException || e instanceof PropertyReferenceException ){
            message = message.substring(message.indexOf(" ")+1);
            errorResponse.setError("PathVariable Invalid");

        }
        errorResponse.setMessage(message);

        return errorResponse;
    }
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerInternalServerException(Exception e, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getDescription(false).replace("uri=",""));
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        if(e instanceof MethodArgumentTypeMismatchException)
            errorResponse.setMessage("Failed to convert value of type");

        return errorResponse;
    }
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<String> handleTokenException(TokenException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ex.getMessage()); // Trả về thông điệp lỗi với mã trạng thái 401
    }
    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<String> handleExpiredTokenException(ExpiredTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token đã hết hạn, vui lòng đăng nhập lại.");
    }
    
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

}
