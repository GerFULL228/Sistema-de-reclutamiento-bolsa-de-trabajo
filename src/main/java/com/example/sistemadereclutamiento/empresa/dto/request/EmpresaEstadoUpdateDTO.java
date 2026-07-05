package com.example.sistemadereclutamiento.empresa.dto.request;

import com.example.sistemadereclutamiento.empresa.entity.EstadoValidacion;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmpresaEstadoUpdateDTO {

    @NotNull(message = "El estado de validación es obligatorio")
    private EstadoValidacion estadoValidacion;
}