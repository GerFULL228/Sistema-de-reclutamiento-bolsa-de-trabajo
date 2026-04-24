package com.example.sistemadereclutamiento.oferta.repository;

import com.example.sistemadereclutamiento.oferta.dto.response.OfertaResponseDTO;
import com.example.sistemadereclutamiento.oferta.entity.Oferta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Long> {

    @Query("""
            SELECT new com.example.sistemadereclutamiento.oferta.dto.response.OfertaResponseDTO(
                          o.id,
                          o.titulo,
                          o.descripcion,
                          o.ubicacion,
                          o.salario,
                        CAST(o.estado AS string),
                          e.nombreEmpresa
                        ) FROM Oferta o
                                  LEFT  join o.empresa e
            """)
    Page<OfertaResponseDTO> listarOfertas(Pageable pageable);
}