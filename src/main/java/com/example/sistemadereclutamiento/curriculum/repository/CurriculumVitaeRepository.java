package com.example.sistemadereclutamiento.curriculum.repository;

import com.example.sistemadereclutamiento.curriculum.entity.CurriculumVitae;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurriculumVitaeRepository extends JpaRepository<CurriculumVitae, Long> {

}