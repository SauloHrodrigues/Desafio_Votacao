package com.db.votacao.desafio_votacao.dtos.pauta;

import com.db.votacao.desafio_votacao.models.Voto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
@Schema(description = "Objeto de saída representando uma pauta cadastrada.")
public record PautaResponseDto(
        @Schema(description = "ID único da pauta", example = "42")
        Long id,
        @Schema(description = "Nome do autor da pauta", example = "Carlos")
        String autor,
        @Schema(description = "Titulo da pauta", example = "Aborto")
        String titulo,
        @Schema(description = "Descrição da pauta", example = "Essa pauta tem o objetivo de...")
        String descricao,
        @Schema(description = "Data de cadastro da pauta", example = "2025-08-29T14:26:07.7841313")
        LocalDateTime dataDeCadastro,
        @Schema(description = "Lista de votos dessa pauta.")
        List<Voto> votos

) {
}
