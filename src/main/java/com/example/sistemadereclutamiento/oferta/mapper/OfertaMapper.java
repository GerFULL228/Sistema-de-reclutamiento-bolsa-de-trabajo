package com.example.sistemadereclutamiento.oferta.mapper;

import com.example.sistemadereclutamiento.oferta.dto.response.OfertaResponseDTO;
import com.example.sistemadereclutamiento.oferta.entity.Oferta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OfertaMapper {

    @Mapping(source = "empresa.nombreEmpresa", target = "nombreEmpresa")
    OfertaResponseDTO toDTO(Oferta oferta);
}
