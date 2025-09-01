package com.db.votacao.desafio_votacao.controllers;

import com.db.votacao.desafio_votacao.auxiliares.fixtures.PautaFixture;
import com.db.votacao.desafio_votacao.controllers.pauta.PautaController;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaAtualizarRequestDto;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaRequesteDto;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaResponseDto;
import com.db.votacao.desafio_votacao.exceptions.pautas.PautaInexistenteException;
import com.db.votacao.desafio_votacao.exceptions.pautas.PautaJaExistenteException;
import com.db.votacao.desafio_votacao.models.Pauta;
import com.db.votacao.desafio_votacao.services.PautaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PautaController.class)
class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PautaService service;

    @Autowired
    private ObjectMapper objectMapper;

    private PautaRequesteDto pautaRequest01;
    private PautaRequesteDto pautaRequest02;
    private PautaRequesteDto pautaRequest03;

    private Pauta pauta01;
    private Pauta pauta02;
    private Pauta pauta03;

    private PautaResponseDto pautaResponse01;
    private PautaResponseDto pautaResponse02;
    private PautaResponseDto pautaResponse03;


    @BeforeEach
    void setUp() {
        pautaRequest01 = PautaFixture.requesteDto("associado01", "maioridade penal", "voce é a favor da maioridade penal?");
        pautaRequest02 = PautaFixture.requesteDto("associado02", "aborto", "voce é a favor do aborto?");
        pautaRequest03 = PautaFixture.requesteDto("associado03", "Reducao da jornada", "voce é a favor da redução da jornada?");

        pauta01 = PautaFixture.pauta(pautaRequest01);
        pauta02 = PautaFixture.pauta(pautaRequest02);
        pauta03 = PautaFixture.pauta(pautaRequest03);

        pautaResponse01 = PautaFixture.responseDto(pauta01);
        pautaResponse02 = PautaFixture.responseDto(pauta02);
        pautaResponse03 = PautaFixture.responseDto(pauta03);
    }

    @Test
    @DisplayName("Deve criar uma nova pauta.")
    void deveRetornarStatusCode201AoCriarUmaPauta() throws Exception {


        when(service.criarPauta(any(PautaRequesteDto.class))).thenReturn(pautaResponse01);

        var resposta = mockMvc.perform(post("/pauta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(pautaRequest01)));

        resposta.andExpect(status().isCreated());
        resposta.andExpect(jsonPath("$.id").exists());
        resposta.andExpect(jsonPath("$.id", is(pauta01.getId().intValue())));

    }

    @Test
    @DisplayName("Deve lançar excessão ao tentar criar uma pauta ja existente.")
    void deveRetornarStatusCode409ConflictAoTentarCriarUmaPautaJaExistente() throws Exception {

        String mensagem = pauta02.getAutor().toUpperCase() + ", já tem uma pauta com titulo: " +
                pauta02.getTitulo().toUpperCase() + ". O Id é: " + pauta02.getId();

        when(service.criarPauta(any(PautaRequesteDto.class)))
                .thenThrow(new PautaJaExistenteException(pauta02.getAutor(), pauta02.getTitulo(), pauta02.getId()));

        var resposta = mockMvc.perform(post("/pauta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(pautaRequest02)));

        resposta.andExpect(status().isConflict());
        resposta.andExpect(jsonPath("$.mensagem").value(mensagem));
    }


    @Test
    @DisplayName("Deve retornar a lista com todas as pautas já cadastradas")
    void deveRetornarTodosAsPautasCadastradas() throws Exception {
        List<PautaResponseDto> pautas = new ArrayList<>();
        pautas.add(pautaResponse01);
        pautas.add(pautaResponse02);
        pautas.add(pautaResponse03);

        Pageable pageable = PageRequest.of(0, 10);
        Page<PautaResponseDto> pageResponse = new PageImpl<>(pautas, pageable, pautas.size());

        when(service.listarPautas(any(Pageable.class))).thenReturn(pageResponse);

        mockMvc.perform(get("/pauta")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(3))
                .andExpect(jsonPath("$.content[0].id").value(pauta01.getId()))
                .andExpect(jsonPath("$.content[1].id").value(pauta02.getId()))
                .andExpect(jsonPath("$.content[2].id").value(pauta03.getId()));
    }

    @Test
    @DisplayName("Deve retornar uma pauta com determinado id, já cadastrada no banco.")
    void deveRetornarDeterminadaPautaCadastradaBuscadaPorId() throws Exception {
        Long idBuscado = pauta03.getId();

        when(service.buscarPautaPorId(idBuscado)).thenReturn(pautaResponse03);

        mockMvc.perform(get("/pauta/{id}", idBuscado))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(pautaResponse03.id()))
                .andExpect(jsonPath("$.titulo").value(pautaResponse03.titulo()))
                .andExpect(jsonPath("$.autor").value(pautaResponse03.autor()))
                .andExpect(jsonPath("$.descricao").value(pautaResponse03.descricao()));

    }

    @Test
    @DisplayName("Deve lançar exceção para uma pauta buscada por id e não encontrada.")
    void deveLancarExcecaoAoBuscarPorIdInvalido() throws Exception {
        Long idBuscado = pauta01.getId();
        PautaRequesteDto request = pautaRequest01;
        String mensagemEsperada = "A pauta com Id: " + idBuscado + ", não existe no banco.";

        when(service.buscarPautaPorId(idBuscado)).thenThrow(new PautaInexistenteException(idBuscado));

        mockMvc.perform(get("/pauta/{id}", idBuscado))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mensagem").value(mensagemEsperada));
    }

    @Test
    @DisplayName("Deve atualizar uma pauta existente e retornar o DTO atualizado")
    void deveAtualizarUmaPautaExistenteNoBanco() throws Exception {
        Long idBuscado = pauta03.getId();
        Pauta pautaEntytie = pauta03;
        PautaAtualizarRequestDto atualizacoes = new PautaAtualizarRequestDto("Mariazinha", null, "descrição Alterda");
        PautaResponseDto responseDto = PautaFixture.responseDto(pautaEntytie, atualizacoes);

        when(service.atualizarPautaPorId(idBuscado, atualizacoes)).thenReturn(responseDto);

        mockMvc.perform(patch("/pauta/{id}", idBuscado)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(atualizacoes)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(responseDto.id()))
                .andExpect(jsonPath("$.autor").value(responseDto.autor()))
                .andExpect(jsonPath("$.titulo").value(pautaEntytie.getTitulo()))
                .andExpect(jsonPath("$.descricao").value(responseDto.descricao()));
    }

    @Test
    @DisplayName("Deve deletar uma pauta existente e retornar status 204")
    void apagar() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/pauta/{id}", id))
                .andExpect(status().isNoContent());

        verify(service).apagarPautaporId(id);
    }
}