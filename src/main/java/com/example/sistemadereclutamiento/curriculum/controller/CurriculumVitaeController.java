package com.example.sistemadereclutamiento.curriculum.controller;
 
import com.example.sistemadereclutamiento.curriculum.dto.CurriculumVitaeRequestDTO;
import com.example.sistemadereclutamiento.curriculum.dto.CurriculumVitaeResponseDTO;
import com.example.sistemadereclutamiento.curriculum.service.CurriculumVitaeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping("/api/cv")
public class CurriculumVitaeController {
 
    @Autowired
    private CurriculumVitaeService curriculumVitaeService;
 
    @GetMapping
    @PreAuthorize("hasRole('EMPRESA') or hasRole('ADMIN')")
    public ResponseEntity<List<CurriculumVitaeResponseDTO>> listarCVs() {
        return ResponseEntity.ok(curriculumVitaeService.obtenerTodos());
    }
 
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CurriculumVitaeResponseDTO> obtenerCV(@PathVariable Long id) {
        return ResponseEntity.ok(curriculumVitaeService.obtenerPorId(id));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('POSTULANTE') or hasRole('ADMIN')")
    public ResponseEntity<CurriculumVitaeResponseDTO> crearCV(@RequestBody CurriculumVitaeRequestDTO dto) {
        CurriculumVitaeResponseDTO nuevoCV = curriculumVitaeService.guardarCV(dto);
        return new ResponseEntity<>(nuevoCV, HttpStatus.CREATED);
    }
 
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('POSTULANTE') or hasRole('ADMIN')")
    public ResponseEntity<CurriculumVitaeResponseDTO> actualizarCV(
            @PathVariable Long id,
            @RequestBody CurriculumVitaeRequestDTO dto) {
 
        return ResponseEntity.ok(curriculumVitaeService.actualizarCV(id, dto));
    }
 
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarCV(@PathVariable Long id) {
        curriculumVitaeService.eliminarCV(id);
        return ResponseEntity.noContent().build();
    }
}