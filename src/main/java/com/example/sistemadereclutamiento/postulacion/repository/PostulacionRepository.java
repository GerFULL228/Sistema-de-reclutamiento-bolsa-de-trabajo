package com.example.sistemadereclutamiento.postulacion.repository;

import com.example.sistemadereclutamiento.postulacion.entity.EstadoPostulacion;
import com.example.sistemadereclutamiento.postulacion.entity.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {

    List<Postulacion> findByPostulanteId(Long postulanteId);

    List<Postulacion> findByOfertaId(Long ofertaId);

    @Query("SELECT p FROM Postulacion p JOIN FETCH p.postulante pos JOIN FETCH pos.usuario JOIN FETCH p.oferta o JOIN FETCH o.empresa WHERE p.id = :id")
    Optional<Postulacion> findByIdDetailed(@Param("id") Long id);

    List<Postulacion> findByEstadoAndOfertaEmpresaId(EstadoPostulacion estado, Long empresaId);

    boolean existsByPostulanteIdAndOfertaId(Long postulanteId, Long ofertaId);

    Optional<Postulacion> findByPostulanteIdAndOfertaId(Long postulanteId, Long ofertaId);
}