package com.example.sistemadereclutamiento.postulante.dto.request;

import com.example.sistemadereclutamiento.usuario.dto.request.UsuarioRequestDTO;

import java.time.LocalDate;

public record PostulanteRequest(
        UsuarioRequestDTO usuario,
        String direccion,
        String genero,
        LocalDate fechaNacimiento
) {}
