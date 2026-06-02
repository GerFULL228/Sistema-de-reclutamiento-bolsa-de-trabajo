package com.example.sistemadereclutamiento.postulacion.controller;
  
import com.example.sistemadereclutamiento.postulacion.dto.request.PostulacionRequestDTO;
import com.example.sistemadereclutamiento.postulacion.dto.request.PostulacionUpdateDTO;
import com.example.sistemadereclutamiento.postulacion.dto.response.PostulacionResponseDTO;
import com.example.sistemadereclutamiento.postulacion.entity.EstadoPostulacion;
import com.example.sistemadereclutamiento.postulacion.service.PostulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
  
import java.util.List;
 
@RestController
@RequestMapping("/api/postulaciones")
public class PostulacionController {
 
    @Autowired
    private PostulacionService postulacionService;
  
    // Crear postulación → solo POSTULANTE (tiene permiso POSTULAR en BD)
    @PostMapping
    @PreAuthorize("hasAuthority('POSTULAR')")
    public ResponseEntity<PostulacionResponseDTO> crearPostulacion(@RequestBody PostulacionRequestDTO requestDTO) {
        PostulacionResponseDTO response = postulacionService.crearPostulacion(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
 
    // Actualizar estado de postulación → EMPRESA actualiza (ej: ACEPTADO/RECHAZADO), POSTULANTE puede retirar
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('OFERTA_UPDATE') or hasAuthority('POSTULAR')")
    public ResponseEntity<PostulacionResponseDTO> actualizarPostulacion(
            @PathVariable Long id,
            @RequestBody PostulacionUpdateDTO requestDTO) {
 
        PostulacionResponseDTO response = postulacionService.actualizarPostulacion(id, requestDTO);
        return ResponseEntity.ok(response);
    }
 
    // Ver postulaciones de un postulante → el propio postulante o la empresa/admin
    @GetMapping("/postulante/{postulanteId}")
    @PreAuthorize("hasAuthority('POSTULAR') or hasAuthority('OFERTA_VIEW')")
    public ResponseEntity<List<PostulacionResponseDTO>> obtenerPorPostulante(@PathVariable Long postulanteId) {
        List<PostulacionResponseDTO> response = postulacionService.obtenerPorPostulante(postulanteId);
        return ResponseEntity.ok(response);
    }
 
    // Ver postulaciones recibidas en una oferta → EMPRESA y ADMIN
    @GetMapping("/oferta/{ofertaId}")
    @PreAuthorize("hasAuthority('OFERTA_VIEW')")
    public ResponseEntity<List<PostulacionResponseDTO>> obtenerPorOferta(@PathVariable Long ofertaId) {
        List<PostulacionResponseDTO> response = postulacionService.obtenerPorOferta(ofertaId);
        return ResponseEntity.ok(response);
    }
 
    // Ver detalle de una postulación → cualquier usuario autenticado con contexto
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('POSTULAR') or hasAuthority('OFERTA_VIEW')")
    public ResponseEntity<PostulacionResponseDTO> obtenerDetalle(@PathVariable Long id) {
        PostulacionResponseDTO response = postulacionService.obtenerDetalle(id);
        return ResponseEntity.ok(response);
    }
 
    // Ver postulaciones por estado para una empresa → EMPRESA y ADMIN
    @GetMapping("/empresa/{empresaId}/estado")
    @PreAuthorize("hasAuthority('OFERTA_VIEW') or hasAuthority('OFERTA_UPDATE')")
    public ResponseEntity<List<PostulacionResponseDTO>> obtenerPorEstadoYEmpresa(
            @RequestParam EstadoPostulacion estado,
            @PathVariable Long empresaId) {
 
        List<PostulacionResponseDTO> response = postulacionService.obtenerPorEstadoYEmpresa(estado, empresaId);
        return ResponseEntity.ok(response);
    }
}
 