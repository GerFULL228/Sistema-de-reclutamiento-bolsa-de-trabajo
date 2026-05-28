package com.example.sistemadereclutamiento.usuario.repository;

import com.example.sistemadereclutamiento.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio  extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);



    @Query("""
     select u FROM Usuario u 
         join fetch u.roles r
             join fetch r.permisos
                 where u.email= :email
    """)
    Optional<Usuario> existsByEmailAndPermiso(String email);
}
