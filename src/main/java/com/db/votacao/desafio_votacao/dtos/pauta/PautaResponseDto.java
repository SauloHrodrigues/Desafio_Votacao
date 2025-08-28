package com.db.votacao.desafio_votacao.dtos.pauta;

import com.db.votacao.desafio_votacao.models.Voto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;

public record PautaResponseDto(
        Long id,
        String autor,
        String titulo,
        String descricao,
        LocalDateTime dataDeCadastro,
        List<Voto> votos

) {
}
