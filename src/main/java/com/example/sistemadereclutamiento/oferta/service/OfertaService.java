package com.example.sistemadereclutamiento.oferta.service;

import com.example.sistemadereclutamiento.oferta.mapper.OfertaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.sistemadereclutamiento.oferta.dto.request.OfertaRequestDTO;
import com.example.sistemadereclutamiento.oferta.dto.response.OfertaResponseDTO;
import com.example.sistemadereclutamiento.exception.ResourceNotFoundException;
import com.example.sistemadereclutamiento.empresa.entity.Empresa;
import com.example.sistemadereclutamiento.oferta.entity.Oferta;
import com.example.sistemadereclutamiento.empresa.repository.EmpresaRepository;
import com.example.sistemadereclutamiento.oferta.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private OfertaMapper ofertaMapper;

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

        return ofertaMapper.toDTO(nuevaOferta);
    }

    public Page<OfertaResponseDTO> obtenerTodas(Pageable pageable) {
    return ofertaRepository.findAll(pageable)
            .map(ofertaMapper::toDTO);
}

    public OfertaResponseDTO obtenerPorId(Long id) {

        Oferta oferta = ofertaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No se encontró la oferta con ID: " + id));

        return ofertaMapper.toDTO(oferta);
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
                return ofertaMapper.toDTO(oferta);
        }
}
