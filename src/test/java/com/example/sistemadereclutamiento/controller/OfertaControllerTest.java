package com.example.sistemadereclutamiento.controller;

import com.example.sistemadereclutamiento.oferta.controller.OfertaController;

import com.example.sistemadereclutamiento.oferta.service.OfertaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.data.domain.PageImpl;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@WebMvcTest(OfertaController.class)
class OfertaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfertaService ofertaService;

    @Test
    void testListarOfertas() throws Exception {

        when(ofertaService.obtenerTodas(org.mockito.Mockito.any()))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        mockMvc.perform(get("/api/ofertas"))
                .andExpect(status().isOk());
    }
}