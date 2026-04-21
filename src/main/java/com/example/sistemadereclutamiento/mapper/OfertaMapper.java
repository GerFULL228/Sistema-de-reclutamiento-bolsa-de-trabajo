package com.example.sistemadereclutamiento.mapper;

import com.example.sistemadereclutamiento.dto.OfertaResponseDTO;
import com.example.sistemadereclutamiento.model.Oferta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OfertaMapper {

    @Mapping(source = "empresa.nombreEmpresa", target = "nombreEmpresa")
    OfertaResponseDTO toDTO(Oferta oferta);
}
