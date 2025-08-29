package com.db.votacao.desafio_votacao.controllers.anotacoes_swagger;

import com.db.votacao.desafio_votacao.dtos.associado.AssociadoAtualizaDto;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoRequestDto;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoResponseDto;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaAtualizarRequestDto;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaRequesteDto;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Pauta", description = "Endpoints para gestão de pautas: cadastro, listagem, atualização e exclusão.")
public interface PautaControllerSwager {

    @Operation(summary = "Cadastra uma nova pauta")
    @ApiResponse(responseCode = "201", description = "pauta cadastrada com sucesso.",
            content = @Content(schema = @Schema(implementation = PautaResponseDto.class)))
    ResponseEntity<PautaResponseDto> criarPauta(@Valid @RequestBody PautaRequesteDto dto);

    @Operation(summary = "Lista todos as pautas cadastrados no banco")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.",
            content = @Content(schema = @Schema(implementation = PautaResponseDto.class)))
    ResponseEntity<Page<PautaResponseDto>> retornarTodosAsPautasCadastrados(
            @Parameter(description = "Parâmetros de paginação e ordenação")
            @PageableDefault(size = 10, sort = {"titulo"}) Pageable pageable);

    @Operation(summary = "Busca uma pauta, no banco, pelo ID")
    @ApiResponse(responseCode = "200", description = "Retorna os dados da pauta.",
            content = @Content(schema = @Schema(implementation = PautaResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Pauta não encontrado.")
    ResponseEntity<PautaResponseDto> buscarUmaPautaPorId(
            @Parameter(description = "ID da pauta", example = "42") @PathVariable Long id);

    @Operation(summary = "Atualiza um associado existente")
    @ApiResponse(responseCode = "200", description = "Retorna os dados atualizados da pauta.",
            content = @Content(schema = @Schema(implementation = PautaResponseDto.class)))
    ResponseEntity<PautaResponseDto> atualizarUmaPauta(
            @Parameter(description = "ID da pauta", example = "42")
            @PathVariable Long id,@Valid @RequestBody PautaAtualizarRequestDto atualizacoesDto);

    @Operation(summary = "Exclui uma pauta existente no banco")
    @ApiResponse(responseCode = "204", description = "Pauta excluída com sucesso.")
    @ApiResponse(responseCode = "404", description = "Pauta não encontrada.")
    ResponseEntity<Void> apagar(@Parameter(description = "ID da pauta", example = "42")
                                @PathVariable Long id);
}