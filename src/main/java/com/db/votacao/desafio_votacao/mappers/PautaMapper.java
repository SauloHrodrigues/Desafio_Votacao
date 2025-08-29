package com.db.votacao.desafio_votacao.mappers;

import com.db.votacao.desafio_votacao.dtos.pauta.PautaAtualizarRequestDto;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaRequesteDto;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaResponseDto;
import com.db.votacao.desafio_votacao.models.Associado;
import com.db.votacao.desafio_votacao.models.Pauta;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PautaMapper {

    PautaMapper INSTANCE = Mappers.getMapper(PautaMapper.class);
    @Mapping(target = "titulo", expression = "java(dto.titulo() != null ? dto.titulo().toLowerCase() : null)")
    @Mapping(target = "autor", expression = "java(dto.autor() != null ? dto.autor().toLowerCase() : null)")
    @Mapping(target = "descricao", expression = "java(dto.descricao() != null ? dto.descricao() : null)")
    @Mapping(target = "votos", ignore = true)
    @Mapping(target = "dataDeCadastro", ignore = true)
    Pauta toEntity(PautaRequesteDto dto);

    PautaResponseDto toResponse(Pauta pauta);

    List<PautaResponseDto> toResponse(List<Pauta> pautas);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataDeCadastro", ignore = true)
//    @Mapping(target = "votos", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Pauta update(@MappingTarget Pauta pauta, PautaAtualizarRequestDto atualizacoes);
}
