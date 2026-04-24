package com.example.sistemadereclutamiento.empresa.repository;

import com.example.sistemadereclutamiento.empresa.dto.response.EmpresaResponseDTO;
import com.example.sistemadereclutamiento.empresa.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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

}