package com.example.sistemadereclutamiento.repository;

import com.example.sistemadereclutamiento.model.CurriculumVitae;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurriculumVitaeRepository extends JpaRepository<CurriculumVitae, Long> {

}