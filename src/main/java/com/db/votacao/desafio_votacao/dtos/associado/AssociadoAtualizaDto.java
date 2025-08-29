package com.db.votacao.desafio_votacao.dtos.associado;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "Objeto para atualização de um assistente. Informar apenas os campos a serem alterados.")
public record AssociadoAtualizaDto(

        @Schema(description = "Nome do associado", example = "Carlos")
        String nome,

        @Schema(description = "Documento do associado", example = "99977733366")
        @Size(max = 11, message = "O nome deve no máximo 11 caracteres")
        String documento
) {
}
