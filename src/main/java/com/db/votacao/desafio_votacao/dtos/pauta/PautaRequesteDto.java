package com.db.votacao.desafio_votacao.dtos.pauta;

public record PautaRequesteDto(
        String autor,
        String titulo,
        String descricao
) {
}
