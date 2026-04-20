package com.example.sistemadereclutamiento.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlingResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return construirRespuesta(ex.getMessage(), request.getDescription(false), HttpStatus.NOT_FOUND);
    }

   
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handlingDataIntegrityException(DataIntegrityViolationException ex, WebRequest request) {
        String mensaje = "Error de conflicto: Es posible que el correo electrónico o el RUC ya estén registrados en el sistema.";
        return construirRespuesta(mensaje, request.getDescription(false), HttpStatus.CONFLICT);
    }

   
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handlingNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        String mensaje = "Error en la petición: El formato de los datos enviados es incorrecto. Verifique la estructura de su JSON.";
        return construirRespuesta(mensaje, request.getDescription(false), HttpStatus.BAD_REQUEST);
    }

    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handlingGlobalException(Exception ex, WebRequest request) {
        return construirRespuesta("Ocurrió un error inesperado en el servidor.", request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR);
    }

   
    private ResponseEntity<Map<String, Object>> construirRespuesta(String mensaje, String detalles, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", mensaje);
        body.put("details", detalles);
        return new ResponseEntity<>(body, status);
    }
}