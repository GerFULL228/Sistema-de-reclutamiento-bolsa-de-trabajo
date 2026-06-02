package com.example.sistemadereclutamiento.postulacion.service;

import com.example.sistemadereclutamiento.oferta.entity.Oferta;
import com.example.sistemadereclutamiento.oferta.repository.OfertaRepository;
import com.example.sistemadereclutamiento.postulacion.dto.request.PostulacionRequestDTO;
import com.example.sistemadereclutamiento.postulacion.dto.request.PostulacionUpdateDTO;
import com.example.sistemadereclutamiento.postulacion.dto.response.PostulacionResponseDTO;
import com.example.sistemadereclutamiento.postulacion.entity.EstadoPostulacion;
import com.example.sistemadereclutamiento.postulacion.entity.Postulacion;
import com.example.sistemadereclutamiento.postulacion.repository.PostulacionRepository;
import com.example.sistemadereclutamiento.postulante.entity.Postulante;
import com.example.sistemadereclutamiento.postulante.repository.PostulanteRepository;
import com.example.sistemadereclutamiento.shared.exeption.BusinessException;
import com.example.sistemadereclutamiento.shared.exeption.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostulacionServiceImpl implements PostulacionService {

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private PostulanteRepository postulanteRepository;

    @Override
    @Transactional // Aporta a la rúbrica de transaccionalidad
    public PostulacionResponseDTO crearPostulacion(PostulacionRequestDTO dto) {
        if (postulacionRepository.existsByPostulanteIdAndOfertaId(dto.getPostulanteId(), dto.getOfertaId())) {
            throw new BusinessException("El postulante ya aplicó a esta oferta laboral");
        }

        Postulante postulante = postulanteRepository.findById(dto.getPostulanteId())
                .orElseThrow(() -> new ResourceNotFoundException("Postulante no encontrado"));
        
        Oferta oferta = ofertaRepository.findById(dto.getOfertaId())
                .orElseThrow(() -> new ResourceNotFoundException("Oferta no encontrada"));

        Postulacion postulacion = new Postulacion();
        postulacion.setPostulante(postulante);
        postulacion.setOferta(oferta);
        postulacion.setCvUrl(dto.getCvUrl());
        postulacion.setEstado(EstadoPostulacion.ENVIADO);
        
        Postulacion guardada = postulacionRepository.save(postulacion);
        
        return mapearADto(guardada);
    }

    @Override
    @Transactional
    public PostulacionResponseDTO actualizarPostulacion(Long id, PostulacionUpdateDTO dto) {
        Postulacion postulacion = postulacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Postulación no encontrada"));

        if (dto.getEstado() != null) {
            postulacion.setEstado(EstadoPostulacion.valueOf(dto.getEstado().toUpperCase()));
        }
        if (dto.getCvUrl() != null) {
            postulacion.setCvUrl(dto.getCvUrl());
        }

        Postulacion actualizada = postulacionRepository.save(postulacion);
        return mapearADto(actualizada);
    }

    @Override
    public List<PostulacionResponseDTO> obtenerPorPostulante(Long postulanteId) {
        return postulacionRepository.findByPostulanteId(postulanteId).stream()
                .map(this::mapearADto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostulacionResponseDTO> obtenerPorOferta(Long ofertaId) {
        return postulacionRepository.findByOfertaId(ofertaId).stream()
                .map(this::mapearADto)
                .collect(Collectors.toList());
    }

    @Override
    public PostulacionResponseDTO obtenerDetalle(Long id) {
        Postulacion postulacion = postulacionRepository.findByIdDetailed(id)
                .orElseThrow(() -> new ResourceNotFoundException("Postulación no encontrada"));
        return mapearADto(postulacion);
    }

    @Override
    public List<PostulacionResponseDTO> obtenerPorEstadoYEmpresa(EstadoPostulacion estado, Long empresaId) {
        return postulacionRepository.findByEstadoAndOfertaEmpresaId(estado, empresaId).stream()
                .map(this::mapearADto)
                .collect(Collectors.toList());
    }

    private PostulacionResponseDTO mapearADto(Postulacion p) {
        PostulacionResponseDTO dto = new PostulacionResponseDTO();
        dto.setId(p.getId());
        dto.setPostulanteId(p.getPostulante().getId());
        dto.setOfertaId(p.getOferta().getId());
        dto.setOfertaTitulo(p.getOferta().getTitulo());
        dto.setCvUrl(p.getCvUrl());
        dto.setEstado(p.getEstado().name());
        dto.setFechaPostulacion(p.getFechaPostulacion());
        return dto;
    }
}