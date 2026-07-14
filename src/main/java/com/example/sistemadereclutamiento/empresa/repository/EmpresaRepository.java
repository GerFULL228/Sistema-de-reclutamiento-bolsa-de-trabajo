package com.example.sistemadereclutamiento.empresa.repository;

import com.example.sistemadereclutamiento.empresa.dto.response.EmpresaResponseDTO;
import com.example.sistemadereclutamiento.empresa.entity.Empresa;
import com.example.sistemadereclutamiento.empresa.entity.EstadoValidacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    @Query(
            """
        SELECT new com.example.sistemadereclutamiento.empresa.dto.response.EmpresaResponseDTO(
                e.id,
                   e.nombreEmpresa,
                   e.razonSocial,
                   e.ruc,
                   e.estadoValidacion,
                   u.email
                ) FROM Empresa e
                        JOIN e.usuario u
        """
    )
    List<EmpresaResponseDTO> listarEmpresas();

    boolean existsByIdAndUsuario_Email(Long id, String email);

    Optional<Empresa> findEmpresasByUsuario_Id(Long id);

    boolean existsByRuc(String ruc);

    long countByEstadoValidacion(EstadoValidacion estadoValidacion);

    // Panel admin (pestaña "Empresas"): permite filtrar por cuenta habilitada/deshabilitada
    // (usuario.activo) y por estado de validación (PENDIENTE/ACTIVO/RECHAZADO). Ambos filtros
    // son opcionales: si se envía null, esa condición se ignora.
    @Query("""
        select e from Empresa e
            join e.usuario u
                where (:activo is null or u.activo = :activo)
                and (:estado is null or e.estadoValidacion = :estado)
                order by u.fechaCreacion desc
    """)
    Page<Empresa> findAllForAdmin(
            @Param("activo") Boolean activo,
            @Param("estado") EstadoValidacion estado,
            Pageable pageable
    );

}