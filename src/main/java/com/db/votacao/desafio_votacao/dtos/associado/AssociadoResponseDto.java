package com.db.votacao.desafio_votacao.dtos.associado;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto de saída representando um associado cadastrado.")
public record AssociadoResponseDto(

        @Schema(description = "ID único do assistente", example = "42")
        Long id,

        @Schema(description = "Nome do associado", example = "Carlos")
        String nome,

        @Schema(description = "Documento do associado", example = "99977733366")
        String documento
) {
}
