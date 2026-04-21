package com.example.sistemadereclutamiento.controller;

import com.example.sistemadereclutamiento.dto.OfertaRequestDTO;
import com.example.sistemadereclutamiento.dto.OfertaResponseDTO;
import com.example.sistemadereclutamiento.service.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaController {

    @Autowired
    private OfertaService ofertaService;

    @GetMapping
    public ResponseEntity<List<OfertaResponseDTO>> listarOfertas() {
        return ResponseEntity.ok(ofertaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfertaResponseDTO> obtenerOferta(@PathVariable Long id) {
        return ResponseEntity.ok(ofertaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<OfertaResponseDTO> crearOferta(
            @RequestBody OfertaRequestDTO requestDTO) {

        OfertaResponseDTO nuevaOferta =
                ofertaService.guardarOferta(requestDTO);

        return new ResponseEntity<>(nuevaOferta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfertaResponseDTO> actualizarOferta(
            @PathVariable Long id,
            @RequestBody OfertaRequestDTO requestDTO) {

        return ResponseEntity.ok(
                ofertaService.actualizarOferta(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOferta(@PathVariable Long id) {

        ofertaService.eliminarOferta(id);

        return ResponseEntity.noContent().build();
    }
}