package com.db.votacao.desafio_votacao.controllers.associado;

import com.db.votacao.desafio_votacao.controllers.anotacoes_swagger.AssociadoControllerSwager;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoAtualizaDto;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoRequestDto;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoResponseDto;
import com.db.votacao.desafio_votacao.services.AssociadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/associado")
public class AssociadoController implements AssociadoControllerSwager {

    private final AssociadoService service;


    @PostMapping
    public ResponseEntity<AssociadoResponseDto> cadastrarNovoAssociado(@Valid @RequestBody AssociadoRequestDto dto) {
        AssociadoResponseDto respostaDto = service.criarAssociado(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respostaDto);
    }

    @GetMapping
    public ResponseEntity<Page<AssociadoResponseDto>> retornarTodosAssociadosCadastrados(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        var resposta = service.retornaListaDeAssociadosCadastrados(pageable);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssociadoResponseDto> buscarUmAssociadoPorId(@PathVariable Long id) {
        var resposta = service.buscaAssociadoPorId(id);
        return ResponseEntity.ok(resposta);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AssociadoResponseDto> atualizarUmAssociado(@PathVariable Long id,@Valid @RequestBody AssociadoAtualizaDto associadoAtualizaDto) {
        var associadoAtualizado = service.atualizaDadosAssociado(id,associadoAtualizaDto);
        return ResponseEntity.ok(associadoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        service.apagaAssociado(id);
        return ResponseEntity.noContent().build();
    }
}