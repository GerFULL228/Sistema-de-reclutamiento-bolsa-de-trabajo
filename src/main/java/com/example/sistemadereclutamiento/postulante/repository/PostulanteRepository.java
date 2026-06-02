package com.example.sistemadereclutamiento.postulante.repository;

import com.example.sistemadereclutamiento.postulante.entity.Postulante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostulanteRepository extends JpaRepository<Postulante, Long> {
    
    

}