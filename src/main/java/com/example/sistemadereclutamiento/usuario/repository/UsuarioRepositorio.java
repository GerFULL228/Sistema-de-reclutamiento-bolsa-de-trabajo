package com.example.sistemadereclutamiento.usuario.repository;

import com.example.sistemadereclutamiento.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio  extends JpaRepository<Usuario, Long> {
}
