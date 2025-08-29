package com.db.votacao.desafio_votacao.service;

import com.db.votacao.desafio_votacao.auxiliares.fixtures.AssociadoFixture;
import com.db.votacao.desafio_votacao.auxiliares.fixtures.PautaFixture;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoRequestDto;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoResponseDto;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaRequesteDto;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaResponseDto;
import com.db.votacao.desafio_votacao.exceptions.associados.AssociadoJaExistenteException;
import com.db.votacao.desafio_votacao.exceptions.pautas.PautaJaExistenteException;
import com.db.votacao.desafio_votacao.models.Associado;
import com.db.votacao.desafio_votacao.models.Pauta;
import com.db.votacao.desafio_votacao.repositorys.PautaRepository;
import com.db.votacao.desafio_votacao.services.implementacoes.PautaServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PautaServiceImplTest {

    @InjectMocks
    private PautaServiceImpl service;
    @Mock
    private PautaRepository repository;

    @Test
    @DisplayName("Deve criar uma pauta nova com sucesso.")
    void deveCriarPautaComSucesso() {
        PautaRequesteDto dto = PautaFixture.requesteDto("jose","Aborto", "Você é a favor?");
        Pauta pauta = PautaFixture.pauta(dto);

        when(repository.save(any(Pauta.class))).thenReturn(pauta);

        PautaResponseDto responseDto = service.criarPauta(dto);

        assertEquals(pauta.getId(),responseDto.id());
        assertEquals(dto.autor(),responseDto.autor());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar uma pauta já existente.")
    void deveLancarExcessaoAoTentarCadastrarUmaPautaJaExistente() {

        PautaRequesteDto dto = PautaFixture.requesteDto("jose","Aborto", "Você é a favor?");
        Pauta pauta = PautaFixture.pauta(dto);

        when(repository.findAllByAutor(anyString()))
                .thenReturn(List.of(pauta));

        PautaJaExistenteException exception = assertThrows(PautaJaExistenteException.class, () -> {
            service.criarPauta(dto);
        });

        assertEquals((dto.autor().toUpperCase()+", já tem uma pauta com titulo: "+
                        dto.titulo().toUpperCase()+". O Id é: "+pauta.getId()),
                exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar todas as pautas cadastradas no banco.")
    void deveListarPautasCadastradas() {
        Pauta pauta01 = PautaFixture.pauta("jose","Aborto", "Você é a favor?");
        Pauta pauta02 = PautaFixture.pauta("maria","Maioridade", "Você é a favor?");
        Pauta pauta03 = PautaFixture.pauta("Joao","Musica", "Você gosta?");

        List<Pauta> pautas = List.of(pauta01,pauta02,pauta03);
        Page<Pauta> pagePautas = new PageImpl<>(pautas, PageRequest.of(0, 10), pautas.size());
        Pageable pageable = PageRequest.of(0, 10);

        when(repository.findAll(any(Pageable.class))).thenReturn(pagePautas);

        Page<PautaResponseDto> resposta = service.listarPautas(pageable);

        assertEquals(3, resposta.getContent().size());
        assertEquals(pauta01.getTitulo(), resposta.getContent().get(0).titulo());
        assertEquals(pauta02.getTitulo(), resposta.getContent().get(1).titulo());
        assertEquals(pauta03.getTitulo(), resposta.getContent().get(2).titulo());

    }

    @Test
    @DisplayName("Deve retornar uma pauta com determinado id, já cadastrado no banco.")
    void deveRetornarUmaPautaBuscadaPorId() {

        Pauta pauta = PautaFixture.pauta("jose","Aborto", "Você é a favor?");
        Long id = pauta.getId();

        when(repository.findById(id)).thenReturn(Optional.of(pauta));

        PautaResponseDto resposta = service.buscarPautaPorId(id);

        assertEquals(pauta.getId(),resposta.id());
        assertEquals(pauta.getTitulo(),resposta.titulo());
        assertEquals(pauta.getAutor(),resposta.autor());
    }

    @Test
    void atualizarPautaPorId() {
    }

    @Test
    void apagarPautaporId() {
    }
}