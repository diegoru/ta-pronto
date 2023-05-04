package br.com.ruescas.libraryapi.config;

import br.com.ruescas.libraryapi.api.exception.ApiErrors;
import br.com.ruescas.libraryapi.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrors> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrors(bindingResult));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrors> handleBusinessException(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrors(ex));
    }
}
