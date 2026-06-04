package com.example.sistemadereclutamiento.oferta.service;

import com.example.sistemadereclutamiento.oferta.dto.request.OfertaUpdateDTO;
import com.example.sistemadereclutamiento.oferta.entity.OfertaEstado;
import com.example.sistemadereclutamiento.oferta.mapper.OfertaMapper;
import com.example.sistemadereclutamiento.shared.exeption.BusinessException;
import com.example.sistemadereclutamiento.usuario.entity.Usuario;
import com.example.sistemadereclutamiento.usuario.service.UsuarioSecurity;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class OfertaService {


    private final OfertaRepository ofertaRepository;


    private final EmpresaRepository empresaRepository;


    private final OfertaMapper ofertaMapper;

    private final UsuarioSecurity usuarioSecurity;

    public OfertaResponseDTO guardarOferta(OfertaRequestDTO requestDTO) {

        Usuario usuarioLogueado = usuarioSecurity.usuarioLogado();

        Empresa empresa = empresaRepository.findEmpresasByUsuario_Id(usuarioLogueado.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usted no tiene ninguna empresa registrada"));

        if (!usuarioSecurity.isEmpresa()) {
            throw new BusinessException("solo empresas pueden crear ofertas");
        }

        Oferta oferta = ofertaMapper.toEntity(requestDTO);
        oferta.setEmpresa(empresa);

        oferta.setEstado(OfertaEstado.ACTIVA);

        Oferta nuevaOferta = ofertaRepository.save(oferta);

        return ofertaMapper.toDTO(nuevaOferta);
    }

    public Page<OfertaResponseDTO> obtenerOfertasAdmin(Pageable pageable) {
        return ofertaRepository.listarOfertas(pageable);
    }

    public Page<OfertaResponseDTO> obtenerOfertasEmpresa(Pageable pageable) {

        Usuario usuarioLogueado = usuarioSecurity.usuarioLogado();
        return ofertaRepository.listarOfertasEmpresa(pageable,usuarioLogueado.getId());
    }

    public OfertaResponseDTO obtenerPorId(Long id) {

        Oferta oferta = ofertaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No se encontró la oferta con ID: " + id));

        return ofertaMapper.toDTO(oferta);
    }

    public OfertaResponseDTO actualizarOferta(Long id, OfertaUpdateDTO requestDTO) {

        Oferta oferta = ofertaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No se encontró la oferta con ID: " + id));


        if (oferta.getEstado() == OfertaEstado.CERRADA || oferta.getEstado() == OfertaEstado.ELIMINADA) {
            throw new BusinessException("esta oferta ya no se puede modificar");
        }

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
