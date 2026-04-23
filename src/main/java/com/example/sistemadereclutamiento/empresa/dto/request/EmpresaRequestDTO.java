package com.example.sistemadereclutamiento.empresa.dto.request;

import com.example.sistemadereclutamiento.usuario.dto.request.UsuarioRequestDTO;
import lombok.Data;

@Data
public class EmpresaRequestDTO {
    private String nombreEmpresa;
    private String ruc;
    private String descripcion;
    private String direccion;
    private String paginaWeb;
    private UsuarioRequestDTO usuario;
}