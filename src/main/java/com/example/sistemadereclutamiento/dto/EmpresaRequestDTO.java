package com.example.sistemadereclutamiento.dto;

import lombok.Data;

@Data
public class EmpresaRequestDTO {
    private String nombreEmpresa;
    private String razonSocial;
    private String ruc;
    private String descripcion;
    private String direccion;
    private String paginaWeb;
    private UsuarioRequestDTO usuario; 
}