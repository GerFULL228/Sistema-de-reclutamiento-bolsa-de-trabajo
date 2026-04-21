package com.example.sistemadereclutamiento.service;

import com.example.sistemadereclutamiento.dto.OfertaRequestDTO;
import com.example.sistemadereclutamiento.dto.OfertaResponseDTO;
import com.example.sistemadereclutamiento.mapper.OfertaMapper;
import com.example.sistemadereclutamiento.model.Empresa;
import com.example.sistemadereclutamiento.model.Oferta;
import com.example.sistemadereclutamiento.repository.EmpresaRepository;
import com.example.sistemadereclutamiento.repository.OfertaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class OfertaServiceTest {

    @MockBean
    private OfertaRepository ofertaRepository;

    @MockBean
    private EmpresaRepository empresaRepository;

    @MockBean
    private OfertaMapper ofertaMapper;

    @Autowired
    private OfertaService ofertaService;

    @Test
    void testGuardarOferta() {

        OfertaRequestDTO request = new OfertaRequestDTO();
        request.setTitulo("Backend");
        request.setDescripcion("Java");
        request.setUbicacion("Lima");
        request.setSalario(3000.0);
        request.setEmpresaId(1L);

        Empresa empresa = new Empresa();
        empresa.setId(1L);

        Oferta oferta = new Oferta();
        oferta.setTitulo("Backend");

        OfertaResponseDTO response = new OfertaResponseDTO();
        response.setTitulo("Backend");

        when(empresaRepository.findById(1L)).thenReturn(Optional.of(empresa));
        when(ofertaRepository.save(org.mockito.Mockito.any(Oferta.class))).thenReturn(oferta);
        when(ofertaMapper.toDTO(oferta)).thenReturn(response);

        OfertaResponseDTO resultado = ofertaService.guardarOferta(request);

        assertNotNull(resultado);
        assertEquals("Backend", resultado.getTitulo());
    }
}