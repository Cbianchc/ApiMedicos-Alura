package com.example.demo.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e){
        //var errores :List<DatosErrorValidacion> = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        var errores = e.getFieldErrors();
        return ResponseEntity.badRequest().body(errores.stream().map(DatosErrorValidacion::new).toList());
    }

    private record DatosErrorValidacion(String campo, String error) {

        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity errorHandlerValidacionesDeNegocio(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
