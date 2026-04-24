package com.example.sistemadereclutamiento.oferta.dto.response;

import com.example.sistemadereclutamiento.oferta.entity.OfertaEstado;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OfertaResponseDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private String ubicacion;
    private Double salario;
    private String estado;
    private String nombreEmpresa;
}
