package com.example.sistemadereclutamiento.service;

import com.example.sistemadereclutamiento.dto.OfertaRequestDTO;
import com.example.sistemadereclutamiento.dto.OfertaResponseDTO;
import com.example.sistemadereclutamiento.exception.ResourceNotFoundException;
import com.example.sistemadereclutamiento.model.Empresa;
import com.example.sistemadereclutamiento.model.Oferta;
import com.example.sistemadereclutamiento.repository.EmpresaRepository;
import com.example.sistemadereclutamiento.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public OfertaResponseDTO guardarOferta(OfertaRequestDTO requestDTO) {

        Empresa empresa = empresaRepository.findById(requestDTO.getEmpresaId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No se encontró la empresa con ID: " + requestDTO.getEmpresaId()));

        Oferta oferta = new Oferta();
        oferta.setTitulo(requestDTO.getTitulo());
        oferta.setDescripcion(requestDTO.getDescripcion());
        oferta.setUbicacion(requestDTO.getUbicacion());
        oferta.setSalario(requestDTO.getSalario());
        oferta.setEstado(requestDTO.getEstado() != null ? requestDTO.getEstado() : "ACTIVA");
        oferta.setEmpresa(empresa);

        Oferta nuevaOferta = ofertaRepository.save(oferta);

        return mapearADTO(nuevaOferta);
    }

    public List<OfertaResponseDTO> obtenerTodas() {
        return ofertaRepository.findAll()
                .stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    public OfertaResponseDTO obtenerPorId(Long id) {

        Oferta oferta = ofertaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No se encontró la oferta con ID: " + id));

        return mapearADTO(oferta);
    }

    public OfertaResponseDTO actualizarOferta(Long id, OfertaRequestDTO requestDTO) {

        Oferta oferta = ofertaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No se encontró la oferta con ID: " + id));

        Empresa empresa = empresaRepository.findById(requestDTO.getEmpresaId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No se encontró la empresa con ID: " + requestDTO.getEmpresaId()));

        oferta.setTitulo(requestDTO.getTitulo());
        oferta.setDescripcion(requestDTO.getDescripcion());
        oferta.setUbicacion(requestDTO.getUbicacion());
        oferta.setSalario(requestDTO.getSalario());
        oferta.setEstado(requestDTO.getEstado() != null ? requestDTO.getEstado() : "ACTIVA");
        oferta.setEmpresa(empresa);

        Oferta actualizada = ofertaRepository.save(oferta);

        return mapearADTO(actualizada);
    }

    public void eliminarOferta(Long id) {

        Oferta oferta = ofertaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No se encontró la oferta con ID: " + id));

        ofertaRepository.delete(oferta);
    }

    private OfertaResponseDTO mapearADTO(Oferta oferta) {
        OfertaResponseDTO dto = new OfertaResponseDTO();
        dto.setId(oferta.getId());
        dto.setTitulo(oferta.getTitulo());
        dto.setDescripcion(oferta.getDescripcion());
        dto.setUbicacion(oferta.getUbicacion());
        dto.setSalario(oferta.getSalario());
        dto.setEstado(oferta.getEstado());

        if (oferta.getEmpresa() != null) {
            dto.setNombreEmpresa(oferta.getEmpresa().getNombreEmpresa());
        }

        return dto;
    }
}
