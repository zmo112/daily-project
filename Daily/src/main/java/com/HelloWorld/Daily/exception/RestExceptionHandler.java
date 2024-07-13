package com.HelloWorld.Daily.exception;

import com.HelloWorld.Daily.common.ApiResponse;
import com.HelloWorld.Daily.exception.customException.NotExistDailyException;
import com.HelloWorld.Daily.exception.customException.NotExistMemberException;
import com.HelloWorld.Daily.exception.customException.WrittenDailyInADayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(NotExistMemberException.class)
    public ResponseEntity<ApiResponse<?>> handleNotExistdUserException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler(NotExistDailyException.class)
    public ResponseEntity<ApiResponse<?>> handleNotExistdDailyException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler(WrittenDailyInADayException.class)
    public ResponseEntity<ApiResponse<?>> handleWrittenDailyInADayException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(ApiResponse.createFail(exception.getBindingResult()));
    }

}
