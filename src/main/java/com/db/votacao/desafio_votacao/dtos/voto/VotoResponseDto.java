package com.db.votacao.desafio_votacao.dtos.voto;

public record VotoResponseDto(
        Long id,
        String pauta,

        String nomeDoAssociado,

        com.db.votacao.desafio_votacao.enuns.VotoEnum votoVoto
) {
}
