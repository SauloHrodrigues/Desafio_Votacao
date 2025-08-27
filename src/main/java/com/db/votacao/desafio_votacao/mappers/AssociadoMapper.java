package com.db.votacao.desafio_votacao.mappers;

import com.db.votacao.desafio_votacao.dtos.associado.AssociadoAtualizaDto;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoRequestDto;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoResponseDto;
import com.db.votacao.desafio_votacao.models.Associado;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AssociadoMapper {

    AssociadoMapper INSTANCE = Mappers.getMapper(AssociadoMapper.class);

    @Mapping(target = "nome", expression = "java(dto.nome() != null ? dto.nome().toLowerCase() : null)")
    Associado toEntity(AssociadoRequestDto dto);

    AssociadoResponseDto toResponse(Associado associado);

    List<AssociadoResponseDto> toResponse(List<Associado> associados);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Associado updateAssociado(@MappingTarget Associado associado, AssociadoAtualizaDto associadoAtualizaDto);
}
