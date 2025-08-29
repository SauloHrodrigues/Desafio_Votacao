package com.db.votacao.desafio_votacao.controllers.pauta;

import com.db.votacao.desafio_votacao.controllers.anotacoes_swagger.PautaControllerSwager;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaAtualizarRequestDto;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaRequesteDto;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaResponseDto;
import com.db.votacao.desafio_votacao.services.PautaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/pauta")
public class PautaController implements PautaControllerSwager {

    private final PautaService service;
    @PostMapping
    public ResponseEntity<PautaResponseDto> criarPauta(@Valid @RequestBody PautaRequesteDto dto) {
        PautaResponseDto respostaDto = service.criarPauta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respostaDto);
    }

    @GetMapping
    public ResponseEntity<Page<PautaResponseDto>> retornarTodosAsPautasCadastrados(@PageableDefault(size = 10, sort = {"titulo"}) Pageable pageable) {
        var resposta = service.listarPautas(pageable);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PautaResponseDto> buscarUmaPautaPorId(@PathVariable Long id) {
        var resposta = service.buscarPautaPorId(id);
        return ResponseEntity.ok(resposta);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PautaResponseDto> atualizarUmaPauta(@PathVariable Long id,@Valid @RequestBody PautaAtualizarRequestDto atualizacoesDto) {
        var associadoAtualizado = service.atualizarPautaPorId(id,atualizacoesDto);
        return ResponseEntity.ok(associadoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        service.apagarPautaporId(id);
        return ResponseEntity.noContent().build();
    }
}