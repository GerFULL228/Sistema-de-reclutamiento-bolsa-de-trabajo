package com.example.sistemadereclutamiento.service;

import com.example.sistemadereclutamiento.dto.EmpresaRequestDTO;
import com.example.sistemadereclutamiento.dto.EmpresaResponseDTO;
import com.example.sistemadereclutamiento.exception.ResourceNotFoundException;
import com.example.sistemadereclutamiento.model.Empresa;
import com.example.sistemadereclutamiento.model.Usuario;
import com.example.sistemadereclutamiento.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public EmpresaResponseDTO guardarEmpresa(EmpresaRequestDTO requestDTO) {
        Empresa empresa = mapearAEntidad(requestDTO);
        Empresa nuevaEmpresa = empresaRepository.save(empresa);
        return mapearADto(nuevaEmpresa);
    }

    public List<EmpresaResponseDTO> obtenerTodas() {
        return empresaRepository.findAll()
                .stream()
                .map(this::mapearADto)
                .collect(Collectors.toList());
    }

    public EmpresaResponseDTO obtenerPorId(Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la empresa con el ID: " + id));
        return mapearADto(empresa);
    }

    public EmpresaResponseDTO actualizarEmpresa(Long id, EmpresaRequestDTO requestDTO) {
        Empresa empresaExistente = empresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la empresa con el ID: " + id));

        empresaExistente.setNombreEmpresa(requestDTO.getNombreEmpresa());
        empresaExistente.setRazonSocial(requestDTO.getRazonSocial());
        empresaExistente.setRuc(requestDTO.getRuc());
        empresaExistente.setDescripcion(requestDTO.getDescripcion());
        empresaExistente.setDireccion(requestDTO.getDireccion());
        empresaExistente.setPaginaWeb(requestDTO.getPaginaWeb());

        Empresa empresaActualizada = empresaRepository.save(empresaExistente);
        return mapearADto(empresaActualizada);
    }

    public void eliminarEmpresa(Long id) {
        Empresa empresaExistente = empresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la empresa con el ID: " + id));
        empresaRepository.delete(empresaExistente);
    }

    
    private Empresa mapearAEntidad(EmpresaRequestDTO dto) {
        Empresa empresa = new Empresa();
        empresa.setNombreEmpresa(dto.getNombreEmpresa());
        empresa.setRazonSocial(dto.getRazonSocial());
        empresa.setRuc(dto.getRuc());
        empresa.setDescripcion(dto.getDescripcion());
        empresa.setDireccion(dto.getDireccion());
        empresa.setPaginaWeb(dto.getPaginaWeb());
        empresa.setEstadoValidacion("PENDIENTE"); 

        if (dto.getUsuario() != null) {
            Usuario usuario = new Usuario();
            usuario.setNombre(dto.getUsuario().getNombre());
            usuario.setApellido(dto.getUsuario().getApellido());
            usuario.setEmail(dto.getUsuario().getEmail());
            usuario.setPassword(dto.getUsuario().getPassword());
            usuario.setRol(dto.getUsuario().getRol());
            usuario.setEstado(true); // Activo por defecto
            empresa.setUsuario(usuario);
        }
        return empresa;
    }

    private EmpresaResponseDTO mapearADto(Empresa empresa) {
        EmpresaResponseDTO dto = new EmpresaResponseDTO();
        dto.setId(empresa.getId());
        dto.setNombreEmpresa(empresa.getNombreEmpresa());
        dto.setRazonSocial(empresa.getRazonSocial());
        dto.setRuc(empresa.getRuc());
        dto.setEstadoValidacion(empresa.getEstadoValidacion());
        
        if (empresa.getUsuario() != null) {
            dto.setUsuarioEmail(empresa.getUsuario().getEmail());
        }
        return dto;
    }
}