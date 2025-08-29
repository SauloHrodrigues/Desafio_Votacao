package com.db.votacao.desafio_votacao.mappers;

import com.db.votacao.desafio_votacao.dtos.associado.AssociadoResponseDto;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaAtualizarRequestDto;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaRequesteDto;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaResponseDto;
import com.db.votacao.desafio_votacao.dtos.voto.ConstroeVotoDto;
import com.db.votacao.desafio_votacao.dtos.voto.VotoRequestDto;
import com.db.votacao.desafio_votacao.dtos.voto.VotoResponseDto;
import com.db.votacao.desafio_votacao.models.Associado;
import com.db.votacao.desafio_votacao.models.Pauta;
import com.db.votacao.desafio_votacao.models.Voto;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VotoMapper {
    VotoMapper INSTANCE = Mappers.getMapper(VotoMapper.class);

    Voto toEntity(ConstroeVotoDto dto);

    default VotoResponseDto toResponse(Voto voto) {
        if (voto == null) {
            return null;
        }

        return new VotoResponseDto(
                voto.getId(),
                voto.getPauta().getTitulo(),
                voto.getAssociado().getNome(),
                voto.getVoto()
        );
    }
}