package com.example.sistemadereclutamiento.auth.dto;
 
public record RegisterRequest(
        String nombre,
        String apellido,
        String email,
        String password,
        String rol
) {}
  