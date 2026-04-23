package com.example.sistemadereclutamiento.repository;

import com.example.sistemadereclutamiento.model.Empresa;
import com.example.sistemadereclutamiento.model.Oferta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OfertaRepositoryTest {

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Test
    void testGuardarOferta() {

        Empresa empresa = new Empresa();
        empresa.setNombreEmpresa("Empresa Test");
        empresa = empresaRepository.save(empresa);

        Oferta oferta = new Oferta();
        oferta.setTitulo("Programador");
        oferta.setDescripcion("Trabajo remoto");
        oferta.setUbicacion("Lima");
        oferta.setSalario(2500.0);
        oferta.setEstado("ACTIVA");
        oferta.setEmpresa(empresa);

        Oferta guardada = ofertaRepository.save(oferta);

        assertNotNull(guardada.getId());
        assertEquals("Programador", guardada.getTitulo());
    }
}