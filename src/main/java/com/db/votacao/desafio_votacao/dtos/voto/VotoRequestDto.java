package com.db.votacao.desafio_votacao.dtos.voto;

import com.db.votacao.desafio_votacao.enuns.VotoEnum;

public record VotoRequestDto(

        Long idPauta,
        Long idAssociado,
        VotoEnum voto
) {
}
