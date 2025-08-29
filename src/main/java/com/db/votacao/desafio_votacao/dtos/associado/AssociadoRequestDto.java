package com.db.votacao.desafio_votacao.dtos.associado;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Objeto de entrada para cadastro de um novo associado.")
public record AssociadoRequestDto(

        @Schema(description = "Nome do associado", example = "Carlos")
        @NotBlank(message = "O nome do associado é campo de preenchimento obrigatório.")
        @Size(min = 2, message = "O nome deve ter pelo menos 2 caracteres")
        String nome,

        @Schema(description = "Documento do associado", example = "99977733366")
        @NotBlank(message = "O documento do associado é campo de preenchimento obrigatório.")
        @Size(max = 11, message = "O nome deve no máximo 11 caracteres")
        String documento
) {
}
