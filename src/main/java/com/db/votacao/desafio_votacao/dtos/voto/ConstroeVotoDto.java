package com.db.votacao.desafio_votacao.dtos.voto;

import com.db.votacao.desafio_votacao.enuns.VotoEnum;
import com.db.votacao.desafio_votacao.models.Associado;
import com.db.votacao.desafio_votacao.models.Pauta;

public record ConstroeVotoDto(
        Associado associado,
        Pauta pauta,
        VotoEnum voto
) {
}
