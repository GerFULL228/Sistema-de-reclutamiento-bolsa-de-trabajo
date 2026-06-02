package com.example.sistemadereclutamiento.usuario.controller;

import com.example.sistemadereclutamiento.postulante.dto.request.PostulanteRequest;
import com.example.sistemadereclutamiento.postulante.dto.response.TokenResponsePostulante;

import com.example.sistemadereclutamiento.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping("/postulante/register")
    public ResponseEntity<TokenResponsePostulante> registerUsuario(@RequestBody PostulanteRequest request){
        return ResponseEntity.ok(usuarioService.crearPostulante(request));
    }

}
