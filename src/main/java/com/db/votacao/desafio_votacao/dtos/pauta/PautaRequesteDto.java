package com.db.votacao.desafio_votacao.dtos.pauta;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Objeto para inclusão de uma nova pauta.")
public record PautaRequesteDto(
        @Schema(description = "Nome do autor da pauta", example = "Carlos")
        @NotBlank(message = "O nome do autor da pauta é campo de preenchimento obrigatório.")
        @Size(min = 2, message = "O nome deve ter pelo menos 2 caracteres")
        String autor,

        @Schema(description = "Titulo da pauta", example = "Aborto")
        @NotBlank(message = "O nome do autor da pauta é campo de preenchimento obrigatório.")
        @Size(min = 2, message = "O nome deve ter pelo menos 2 caracteres")
        String titulo,

        @Schema(description = "Descrição da pauta", example = "Essa pauta tem o objetivo de...")
        @NotBlank(message = "A derscrição da pauta é campo de preenchimento obrigatório.")
        @Size(min = 2, message = "O nome deve ter pelo menos 2 caracteres")
        String descricao
) {
}
