package com.db.votacao.desafio_votacao.controllers;

import com.db.votacao.desafio_votacao.auxiliares.fixtures.AssociadoFixture;
import com.db.votacao.desafio_votacao.controllers.associado.AssociadoController;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoAtualizaDto;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoRequestDto;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoResponseDto;
import com.db.votacao.desafio_votacao.exceptions.associados.AssociadoJaExistenteException;
import com.db.votacao.desafio_votacao.exceptions.associados.AssociadoNaoExistenteException;
import com.db.votacao.desafio_votacao.models.Associado;
import com.db.votacao.desafio_votacao.services.AssociadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

@WebMvcTest(AssociadoController.class)
class AssociadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssociadoService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve realizar o cadastro de um novo associado.")
    void deveCadastrarNovoAssociado() throws Exception {
        AssociadoRequestDto dto = AssociadoFixture.dtoRequestFixture("maria", "111.222.333-44");
        Associado associado = AssociadoFixture.associadoFixture(dto);
        Long id = associado.getId();
        AssociadoResponseDto respostaEsperada = AssociadoFixture.responseDto(associado);

        when(service.criarAssociado(any(AssociadoRequestDto.class)))
                .thenReturn(respostaEsperada);

        var resposta = mockMvc.perform(post("/associado")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(dto))
        );

        resposta.andExpect(status().isCreated());
        resposta.andExpect(jsonPath("$.id").isNumber());

    }

    @Test
    @DisplayName("Deve retornar código 409, quando o asociado já estiver cadastrado")
    void deveRetornarCodigo409QuandoAssociadoJaEstiverCadastrado() throws Exception {
        AssociadoRequestDto dto = AssociadoFixture.dtoRequestFixture("maria", "111.222.333-44");
        Associado associado = AssociadoFixture.associadoFixture(dto);
        Long id = associado.getId();
        AssociadoResponseDto respostaEsperada = AssociadoFixture.responseDto(associado);

        when(service.criarAssociado(any(AssociadoRequestDto.class)))
                .thenThrow(new AssociadoJaExistenteException(
                        "O associado de nome: " + dto.nome().toUpperCase() + ", já é cadastrado no banco."
                ));

        var resposta = mockMvc.perform(post("/associado")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(dto))
        );

        resposta.andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Deve retornar a lista com todos os associados já cadastrados")
    void retornarTodosAssociadosCadastrados() throws Exception {

        Associado associado01 = AssociadoFixture.associadoFixture("Juliano", "123.456.789-0");
        Associado associado02 = AssociadoFixture.associadoFixture("Eduardo", "234.567.980-14");
        Associado associado03 = AssociadoFixture.associadoFixture("Maria", "345.678.912-22");
        List<AssociadoResponseDto> associados = new ArrayList<>();
        associados.add(AssociadoFixture.responseDto(associado01));
        associados.add(AssociadoFixture.responseDto(associado02));
        associados.add(AssociadoFixture.responseDto(associado03));
        Page<AssociadoResponseDto> pageAssociados = new PageImpl<>(associados, PageRequest.of(0, 10), associados.size());

        when(service.retornaListaDeAssociadosCadastrados(any(Pageable.class))).thenReturn(pageAssociados);

        mockMvc.perform(get("/associado")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(3))
                .andExpect(jsonPath("$.content[0].id").value(associado01.getId()))
                .andExpect(jsonPath("$.content[1].id").value(associado02.getId()))
                .andExpect(jsonPath("$.content[2].id").value(associado03.getId()));

    }

    @Test
    @DisplayName("Deve retornar código 200 um associado com determinado id, já cadastrado no banco.")
    void buscarUmAssociadoPorId() throws Exception {
        Associado associado01 = AssociadoFixture.associadoFixture("Juliano", "123.456.789-0");
        Long id = associado01.getId();
        AssociadoResponseDto response = AssociadoFixture.responseDto(associado01);

        when(service.buscaAssociadoPorId(id)).thenReturn(response);

        mockMvc.perform(get("/associado/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(response.id())
                );
    }
@Test
    @DisplayName("Deve retornar código 404 um associado não encontrado no banco.")
    void deveRetornarNotFoundAoBuscarUmAssociadoPorIdENãoEncontrar() throws Exception {
        Associado associado01 = AssociadoFixture.associadoFixture("Juliano", "123.456.789-0");
        Long id = associado01.getId();
        String mensagemEsperada = "O associado de ID: "+id+", não foi encontrado no banco.";

        when(service.buscaAssociadoPorId(id)).thenThrow(new AssociadoNaoExistenteException(id));

        mockMvc.perform(get("/associado/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mensagem").value(mensagemEsperada));
    }

    @Test
    @DisplayName("Deve retornar código 200 ao atualizar um associado do banco.")
    void atualizarUmAssociado() throws Exception {
        Associado associado = AssociadoFixture.associadoFixture("jose","123.456.789.10");
        AssociadoAtualizaDto atualizaDto = AssociadoFixture.dtoAtualizarFixture(null,"555,666,777-88");
        AssociadoResponseDto responseDto = AssociadoFixture.responseDto(associado,atualizaDto);

        when(service.atualizaDadosAssociado(associado.getId(),atualizaDto)).thenReturn(responseDto);

        mockMvc.perform(patch("/associado/{id}",associado.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(atualizaDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(responseDto.id()))
                .andExpect(jsonPath("$.nome").value(responseDto.nome()));

    }

    @Test
    void apagar() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/associado/{id}", id))
                .andExpect(status().isNoContent());

        verify(service).apagaAssociado(id);
    }
}