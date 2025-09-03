package com.db.votacao.desafio_votacao.dtos.voto;

import com.db.votacao.desafio_votacao.enuns.VotoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record VotoRequestDto(

        @NotNull(message = "O ID da pauta é campo de preenchimento obrigatório.")
        @Schema(description = "ID da pauta do voto", example = "1")
        Long idPauta,

        @NotNull(message = "O ID do associado é campo de preenchimento obrigatório.")
        @Schema(description = "ID do associado do voto", example = "1")
        Long idAssociado,

        @NotNull(message = "O voto é campo de preenchimento obrigatório.")
        @Schema(description = "Voto", implementation = VotoEnum.class)
        VotoEnum voto
) {}
