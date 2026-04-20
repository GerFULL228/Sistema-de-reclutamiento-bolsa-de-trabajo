package com.example.sistemadereclutamiento.dto;

import lombok.Data;

@Data
public class OfertaRequestDTO {

    private String titulo;
    private String descripcion;
    private String ubicacion;
    private Double salario;
    private String estado;
    private Long empresaId;
}
