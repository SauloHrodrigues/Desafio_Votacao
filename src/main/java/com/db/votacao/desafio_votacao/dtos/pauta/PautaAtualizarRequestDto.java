package com.db.votacao.desafio_votacao.dtos.pauta;

import com.db.votacao.desafio_votacao.models.Voto;
import java.time.LocalDateTime;
import java.util.List;

public record PautaAtualizarRequestDto(
        String autor,
        String titulo,
        String descricao

) {
}
