package com.example.sistemadereclutamiento.oferta.service;

import com.example.sistemadereclutamiento.oferta.entity.OfertaEstado;
import com.example.sistemadereclutamiento.oferta.mapper.OfertaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.sistemadereclutamiento.oferta.dto.request.OfertaRequestDTO;
import com.example.sistemadereclutamiento.oferta.dto.response.OfertaResponseDTO;
import com.example.sistemadereclutamiento.shared.exeption.ResourceNotFoundException;
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

        Oferta oferta = ofertaMapper.toEntity(requestDTO);

        oferta.setEstado(OfertaEstado.DISPONIBLE);

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



        ofertaMapper.updateEntityFromDto(requestDTO, oferta);



        return ofertaMapper.toDTO(ofertaRepository.save(oferta));
    }

    public void eliminarOferta(Long id) {

        Oferta oferta = ofertaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No se encontró la oferta con ID: " + id));

        ofertaRepository.delete(oferta);
    }


}
