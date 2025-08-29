package com.db.votacao.desafio_votacao.controllers.anotacoes_swagger;

import com.db.votacao.desafio_votacao.dtos.associado.AssociadoAtualizaDto;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoRequestDto;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoResponseDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Associados", description = "Endpoints para gestão de asociados: cadastro, listagem, atualização e exclusão.")
public interface AssociadoControllerSwager {

    @Operation(summary = "Cadastra um novo associado")
    @ApiResponse(responseCode = "201", description = "Associado cadastrado com sucesso.",
            content = @Content(schema = @Schema(implementation = AssociadoResponseDto.class)))
    public ResponseEntity<AssociadoResponseDto> cadastrarNovoAssociado(@Valid @RequestBody AssociadoRequestDto dto);

    @Operation(summary = "Lista todos os associados cadastrados no banco")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.",
            content = @Content(schema = @Schema(implementation = AssociadoResponseDto.class)))
    ResponseEntity<Page<AssociadoResponseDto>> retornarTodosAssociadosCadastrados(
            @Parameter(description = "Parâmetros de paginação e ordenação")
            @PageableDefault(size = 10, sort = {"nome"}) Pageable pageable);

    @Operation(summary = "Busca um associado no banco pelo ID")
    @ApiResponse(responseCode = "200", description = "Retorna os dados do associado.",
            content = @Content(schema = @Schema(implementation = AssociadoResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Associado não encontrado.")
    ResponseEntity<AssociadoResponseDto> buscarUmAssociadoPorId(
            @Parameter(description = "ID do associado", example = "42") @PathVariable Long id);

    @Operation(summary = "Atualiza um associado existente")
    @ApiResponse(responseCode = "200", description = "Retorna os dados atualizados do associado.",
            content = @Content(schema = @Schema(implementation = AssociadoResponseDto.class)))
    ResponseEntity<AssociadoResponseDto> atualizarUmAssociado(
            @Parameter(description = "ID do associado", example = "42")
            @PathVariable Long id,
            @Valid @RequestBody AssociadoAtualizaDto associadoAtualizaDto);

    @Operation(summary = "Exclui um associado existente no banco")
    @ApiResponse(responseCode = "204", description = "Assistente excluído com sucesso.")
    @ApiResponse(responseCode = "404", description = "Assistente não encontrado.")
    ResponseEntity<Void> apagar(@Parameter(description = "ID do assistente", example = "42")
                                @PathVariable Long id);
}
