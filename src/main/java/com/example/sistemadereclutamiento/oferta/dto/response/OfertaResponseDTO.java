package com.example.sistemadereclutamiento.oferta.dto.response;

import lombok.Data;

@Data
public class OfertaResponseDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private String ubicacion;
    private Double salario;
    private String estado;
    private String nombreEmpresa;
}
