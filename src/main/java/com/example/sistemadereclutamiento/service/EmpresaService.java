package com.example.sistemadereclutamiento.service;

import com.example.sistemadereclutamiento.model.Empresa;
import com.example.sistemadereclutamiento.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public Empresa guardarEmpresa(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public List<Empresa> obtenerTodas() {
        return empresaRepository.findAll();
    }

    public Optional<Empresa> obtenerPorId(Long id) {
        return empresaRepository.findById(id);
    }

    public void eliminarEmpresa(Long id) {
        empresaRepository.deleteById(id);
    }
}