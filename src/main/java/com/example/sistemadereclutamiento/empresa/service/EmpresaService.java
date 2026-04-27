package com.example.sistemadereclutamiento.empresa.service;

import com.example.sistemadereclutamiento.empresa.dto.request.EmpresaRequestDTO;
import com.example.sistemadereclutamiento.empresa.dto.response.EmpresaResponseDTO;
import com.example.sistemadereclutamiento.empresa.entity.EstadoValidacion;
import com.example.sistemadereclutamiento.empresa.mapper.EmpresaMapper;
import com.example.sistemadereclutamiento.rol.entity.Rol;
import com.example.sistemadereclutamiento.rol.repository.RolRepository;
import com.example.sistemadereclutamiento.shared.exeption.BusinessException;
import com.example.sistemadereclutamiento.shared.exeption.ResourceNotFoundException;
import com.example.sistemadereclutamiento.empresa.entity.Empresa;
import com.example.sistemadereclutamiento.usuario.entity.Usuario;
import com.example.sistemadereclutamiento.empresa.repository.EmpresaRepository;
import com.example.sistemadereclutamiento.usuario.mapper.UsuarioMapper;
import com.example.sistemadereclutamiento.usuario.repository.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaMapper empresaMapper;
    private final UsuarioMapper usuarioMapper;
    private final EmpresaRepository empresaRepository;
    private final RolRepository rolRepository;
    private final UsuarioRepositorio usuarioRepositorio;



    public EmpresaResponseDTO guardarEmpresa(EmpresaRequestDTO requestDTO) {
        Empresa empresa = empresaMapper.toEntity(requestDTO);
        if (usuarioRepositorio.existsByEmail(requestDTO.getUsuario().getEmail())) {
            throw new BusinessException("el email ya existe " + empresa.getUsuario().getEmail());
        };
        Usuario usuario = usuarioMapper.toEntity(requestDTO.getUsuario());
        Rol rol = rolRepository.findByNombre("EMPRESA").orElseThrow(() -> new ResourceNotFoundException("ROL NO ENCONTRADO"));
        usuario.setRoles(Set.of(rol));
        empresa.setUsuario(usuario);
        empresa.setEstadoValidacion(EstadoValidacion.PENDIENTE);

        return empresaMapper.toDTO(empresaRepository.save(empresa));
    }

    public List<EmpresaResponseDTO> obtenerTodas() {
        return empresaRepository.listarEmpresas();
    }

    public EmpresaResponseDTO obtenerPorId(Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la empresa con el ID: " + id));
        return empresaMapper.toDTO(empresa);
    }

    public EmpresaResponseDTO actualizarEmpresa(Long id, EmpresaRequestDTO requestDTO) {
        Empresa empresaExistente = empresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la empresa con el ID: " + id));

        empresaMapper.updateEntityFromDto(requestDTO, empresaExistente);

        return empresaMapper.toDTO(empresaRepository.save(empresaExistente));
    }

    public void eliminarEmpresa(Long id) {
        Empresa empresaExistente = empresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la empresa con el ID: " + id));
        empresaRepository.delete(empresaExistente);
    }

    

}