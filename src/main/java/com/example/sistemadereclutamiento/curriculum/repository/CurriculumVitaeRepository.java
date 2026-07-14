package com.example.sistemadereclutamiento.curriculum.repository;

import com.example.sistemadereclutamiento.curriculum.entity.CurriculumVitae;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurriculumVitaeRepository extends JpaRepository<CurriculumVitae, Long> {

    Optional<CurriculumVitae> findByUsuario_Id(Long usuarioId);
}