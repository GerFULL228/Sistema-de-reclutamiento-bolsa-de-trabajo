package com.example.sistemadereclutamiento.empresa.controller;

import com.example.sistemadereclutamiento.empresa.dto.request.EmpresaRequestDTO;
import com.example.sistemadereclutamiento.empresa.dto.response.EmpresaResponseDTO;
import com.example.sistemadereclutamiento.empresa.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @PreAuthorize("hasAuthority('EMPRESA_VIEW_ALL')")
    @GetMapping
    public ResponseEntity<List<EmpresaResponseDTO>> listarEmpresas() {
        return ResponseEntity.ok(empresaService.obtenerTodas());
    }

    @PreAuthorize("hasAuthority('EMPRESA_VIEW')")
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> obtenerEmpresa(@PathVariable Long id) {
        return ResponseEntity.ok(empresaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<EmpresaResponseDTO> crearEmpresa( @RequestBody @Valid EmpresaRequestDTO requestDTO) {
        EmpresaResponseDTO nuevaEmpresa = empresaService.guardarEmpresa(requestDTO);
        return new ResponseEntity<>(nuevaEmpresa, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('EMPRESA_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> actualizarEmpresa(  @PathVariable Long id, @RequestBody  @Valid EmpresaRequestDTO requestDTO) {
        return ResponseEntity.ok(empresaService.actualizarEmpresa(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable Long id) {
        empresaService.eliminarEmpresa(id);
        return ResponseEntity.noContent().build();
    }
}