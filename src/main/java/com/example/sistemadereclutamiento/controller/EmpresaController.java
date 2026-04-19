package com.example.sistemadereclutamiento.controller;

import com.example.sistemadereclutamiento.model.Empresa;
import com.example.sistemadereclutamiento.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    public List<Empresa> listarEmpresas() {
        return empresaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obtenerEmpresa(@PathVariable Long id) {
        Optional<Empresa> empresa = empresaService.obtenerPorId(id);
        return empresa.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Empresa> crearEmpresa(@RequestBody Empresa empresa) {
        Empresa nuevaEmpresa = empresaService.guardarEmpresa(empresa);
        return ResponseEntity.ok(nuevaEmpresa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> actualizarEmpresa(@PathVariable Long id, @RequestBody Empresa detallesEmpresa) {
        Optional<Empresa> empresaExistente = empresaService.obtenerPorId(id);
        
        if (empresaExistente.isPresent()) {
            Empresa empresaActualizada = empresaExistente.get();
            empresaActualizada.setRazonSocial(detallesEmpresa.getRazonSocial());
            empresaActualizada.setRuc(detallesEmpresa.getRuc());
            return ResponseEntity.ok(empresaService.guardarEmpresa(empresaActualizada));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable Long id) {
        Optional<Empresa> empresaExistente = empresaService.obtenerPorId(id);
        
        if (empresaExistente.isPresent()) {
            empresaService.eliminarEmpresa(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}