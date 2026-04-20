package com.example.sistemadereclutamiento.dto;

import lombok.Data;

@Data
public class EmpresaResponseDTO {
    private Long id;
    private String nombreEmpresa;
    private String razonSocial;
    private String ruc;
    private String estadoValidacion;
    private String usuarioEmail; 
}