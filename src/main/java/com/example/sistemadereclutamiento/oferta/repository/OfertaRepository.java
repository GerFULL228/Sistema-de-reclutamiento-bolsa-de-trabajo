package com.example.sistemadereclutamiento.oferta.repository;

import com.example.sistemadereclutamiento.oferta.entity.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Long> {
}