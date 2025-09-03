package com.db.votacao.desafio_votacao.dtos.voto;

import io.swagger.v3.oas.annotations.media.Schema;

public record VotoResponseDto(
        @Schema(description = "ID do voto", example = "1")
        Long id,
        String pauta,

        String nomeDoAssociado,

        com.db.votacao.desafio_votacao.enuns.VotoEnum votoVoto
) {}