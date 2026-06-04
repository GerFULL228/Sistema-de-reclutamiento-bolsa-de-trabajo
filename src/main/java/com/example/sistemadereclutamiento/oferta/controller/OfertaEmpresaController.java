package com.example.sistemadereclutamiento.oferta.controller;

import com.example.sistemadereclutamiento.oferta.dto.request.OfertaRequestDTO;
import com.example.sistemadereclutamiento.oferta.dto.request.OfertaUpdateDTO;
import com.example.sistemadereclutamiento.oferta.dto.response.OfertaResponseDTO;
import com.example.sistemadereclutamiento.oferta.service.OfertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empresa/ofertas")
@RequiredArgsConstructor
public class OfertaEmpresaController {

    private final OfertaService ofertaService;

    @PreAuthorize("hasAuthority('OFERTA_VIEW') and hasRole('EMPRESA')")
    @GetMapping
    public ResponseEntity<Page<OfertaResponseDTO>> listarOfertasEmpresa(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(
                ofertaService.obtenerOfertasEmpresa(pageable)
        );
    }

    @PreAuthorize("hasAuthority('OFERTA_CREATE')")
    @PostMapping
    public ResponseEntity<OfertaResponseDTO> crearOferta(
            @RequestBody OfertaRequestDTO requestDTO) {

        OfertaResponseDTO nuevaOferta =
                ofertaService.guardarOferta(requestDTO);

        return new ResponseEntity<>(nuevaOferta, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OfertaResponseDTO> actualizarOferta(
            @PathVariable Long id,
            @RequestBody OfertaUpdateDTO requestDTO) {

        return ResponseEntity.ok(
                ofertaService.actualizarOferta(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOferta(@PathVariable Long id) {

        ofertaService.eliminarOferta(id);

        return ResponseEntity.noContent().build();
    }
}
