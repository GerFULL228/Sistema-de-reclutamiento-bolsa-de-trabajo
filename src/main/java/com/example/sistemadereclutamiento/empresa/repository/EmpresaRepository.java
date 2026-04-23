package com.example.sistemadereclutamiento.empresa.repository;

import com.example.sistemadereclutamiento.empresa.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}