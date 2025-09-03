package com.db.votacao.desafio_votacao.dtos.pauta;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Objeto para atualização de uma pauta. Informar apenas os campos a serem alterados.")
public record PautaAtualizarRequestDto(

        @Schema(description = "Nome do autor da pauta", example = "Carlos")
        @Size(min = 2, message = "O nome deve ter pelo menos 2 caracteres")
        String autor,

        @Schema(description = "Titulo da pauta", example = "Aborto")
        @Size(min = 2, message = "O nome deve ter pelo menos 2 caracteres")
        String titulo,

        @Schema(description = "Descrição da pauta", example = "Essa pauta tem o objetivo de...")
        @Size(min = 2, message = "O nome deve ter pelo menos 2 caracteres")
        String descricao

) {}
