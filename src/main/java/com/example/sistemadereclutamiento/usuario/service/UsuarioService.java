package com.example.sistemadereclutamiento.usuario.service;

import com.example.sistemadereclutamiento.rol.entity.Rol;
import com.example.sistemadereclutamiento.rol.repository.RolRepository;
import com.example.sistemadereclutamiento.shared.exeption.BusinessException;
import com.example.sistemadereclutamiento.shared.exeption.ResourceNotFoundException;
import com.example.sistemadereclutamiento.usuario.dto.request.UsuarioRequestDTO;
import com.example.sistemadereclutamiento.usuario.entity.Usuario;
import com.example.sistemadereclutamiento.usuario.mapper.UsuarioMapper;
import com.example.sistemadereclutamiento.usuario.repository.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepositorio usuarioRepositorio;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    private Usuario crearUsuarioBase(UsuarioRequestDTO usuarioRequestDTO, String nombreRol) {
        Rol rol = rolRepository.findByNombre(nombreRol).orElseThrow(()-> new ResourceNotFoundException("rol no encontrado"));
        Usuario usuario = usuarioRepositorio.existsByEmail(usuarioRequestDTO.getEmail()).orElseThrow(()-> new BusinessException("ya existe un usuario con ese email"));
        usuarioMapper.toEntity(usuarioRequestDTO);
        usuario.setPassword(passwordEncoder.encode(usuarioRequestDTO.getPassword()));
        usuario.getRoles().add(rol);
        return usuarioRepositorio.save(usuario);

    }


}
