package com.example.sistemadereclutamiento.entrevista.controller;
  
import com.example.sistemadereclutamiento.entrevista.dto.request.EntrevistaRequestDTO;
import com.example.sistemadereclutamiento.entrevista.dto.response.EntrevistaResponseDTO;
import com.example.sistemadereclutamiento.entrevista.service.EntrevistaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
  
import java.util.List;
 
@RestController
@RequestMapping("/api/entrevistas")
public class EntrevistaController {
 
    @Autowired
    private EntrevistaService entrevistaService; 
  
    @PostMapping
    @PreAuthorize("hasRole('EMPRESA') or hasRole('ADMIN')")
    public ResponseEntity<EntrevistaResponseDTO> crear(@Valid @RequestBody EntrevistaRequestDTO dto) {
        return new ResponseEntity<>(entrevistaService.crear(dto), HttpStatus.CREATED);
    }
 
    @GetMapping
    @PreAuthorize("hasRole('EMPRESA') or hasRole('ADMIN')")
    public ResponseEntity<List<EntrevistaResponseDTO>> listar() {
        return ResponseEntity.ok(entrevistaService.listar());
    }
 
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EntrevistaResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(entrevistaService.obtener(id));
    }
}