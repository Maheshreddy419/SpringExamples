package com.codingshuttle.springbootwebtutorial.exceptions;

import com.codingshuttle.springbootwebtutorial.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleReSourceNotFoundException(ResourceNotFoundException e){
       ApiError apiError = ApiError.builder()
               .status(HttpStatus.NOT_FOUND)
               .message(e.getMessage())
               .build();
       return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception e){
        ApiError apiError = ApiError
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
   }

   @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
       List<String> errors = e.getBindingResult()
                .getAllErrors()
                .stream().map(error ->error.getDefaultMessage())
                .toList();
       ApiError apiError = ApiError
               .builder()
               .status(HttpStatus.BAD_REQUEST)
               .message("Input Validation Failed")
               .subErrors(errors)
               .build();
       return buildErrorResponseEntity(apiError);
   }

    private static ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getStatus());
    }

}
