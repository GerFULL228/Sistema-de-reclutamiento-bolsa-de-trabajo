package com.example.sistemadereclutamiento.security.service;

import com.example.sistemadereclutamiento.usuario.entity.Usuario;
import com.example.sistemadereclutamiento.usuario.repository.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor

public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;
    @Override
    public UserDetails loadUserByUsername(String enail) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepositorio.existsByEmailAndPermiso(enail).orElseThrow(() -> new UsernameNotFoundException("usuario no encontrado" + enail) );
        if (!usuario.isActivo()){
            throw new DisabledException("usuario no disponible");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        usuario.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getNombre()));
            role.getPermisos().forEach(permiso -> {
                authorities.add( new SimpleGrantedAuthority(permiso.getPermiso()));
            });
        });


        return new CustomUserDetail(usuario, authorities);
    }
}
