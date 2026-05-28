package com.example.sistemadereclutamiento.postulacion.controller;

import com.example.sistemadereclutamiento.postulacion.dto.request.PostulacionRequestDTO;
import com.example.sistemadereclutamiento.postulacion.dto.request.PostulacionUpdateDTO;
import com.example.sistemadereclutamiento.postulacion.dto.response.PostulacionResponseDTO;
import com.example.sistemadereclutamiento.postulacion.entity.EstadoPostulacion;
import com.example.sistemadereclutamiento.postulacion.service.PostulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// IMPORT DE SEGURIDAD
// import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/postulaciones")
public class PostulacionController {

    @Autowired
    private PostulacionService postulacionService;

    // @PreAuthorize("hasAuthority('POSTULAR')") // COMENTADO TEMPORALMENTE
    @PostMapping
    public ResponseEntity<PostulacionResponseDTO> crearPostulacion(@RequestBody PostulacionRequestDTO requestDTO) {
        PostulacionResponseDTO response = postulacionService.crearPostulacion(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // @PreAuthorize("hasAuthority('OFERTA_UPDATE') or hasAuthority('POSTULAR')") // COMENTADO TEMPORALMENTE
    @PatchMapping("/{id}")
    public ResponseEntity<PostulacionResponseDTO> actualizarPostulacion(
            @PathVariable Long id, 
            @RequestBody PostulacionUpdateDTO requestDTO) {
        
        PostulacionResponseDTO response = postulacionService.actualizarPostulacion(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    // Obtener todas las postulaciones de un Postulante específico
    // @PreAuthorize("hasAuthority('POSTULAR')") // COMENTADO TEMPORALMENTE
    @GetMapping("/postulante/{postulanteId}")
    public ResponseEntity<List<PostulacionResponseDTO>> obtenerPorPostulante(@PathVariable Long postulanteId) {
        List<PostulacionResponseDTO> response = postulacionService.obtenerPorPostulante(postulanteId);
        return ResponseEntity.ok(response);
    }

    // Obtener todas las postulaciones recibidas en una Oferta Laboral específica
    // @PreAuthorize("hasAuthority('OFERTA_VIEW') or hasAuthority('OFERTA_UPDATE')") // COMENTADO TEMPORALMENTE
    @GetMapping("/oferta/{ofertaId}")
    public ResponseEntity<List<PostulacionResponseDTO>> obtenerPorOferta(@PathVariable Long ofertaId) {
        List<PostulacionResponseDTO> response = postulacionService.obtenerPorOferta(ofertaId);
        return ResponseEntity.ok(response);
    }

    // Obtener una postulación detallada
    @GetMapping("/{id}")
    public ResponseEntity<PostulacionResponseDTO> obtenerDetalle(@PathVariable Long id) {
        PostulacionResponseDTO response = postulacionService.obtenerDetalle(id);
        return ResponseEntity.ok(response);
    }

    // Obtener todas las postulaciones en un estado específico para una Empresa
    // @PreAuthorize("hasAuthority('OFERTA_VIEW') or hasAuthority('OFERTA_UPDATE')") // COMENTADO TEMPORALMENTE
    @GetMapping("/empresa/{empresaId}/estado")
    public ResponseEntity<List<PostulacionResponseDTO>> obtenerPorEstadoYEmpresa(
            @RequestParam EstadoPostulacion estado, 
            @PathVariable Long empresaId) {
        
        List<PostulacionResponseDTO> response = postulacionService.obtenerPorEstadoYEmpresa(estado, empresaId);
        return ResponseEntity.ok(response);
    }
}