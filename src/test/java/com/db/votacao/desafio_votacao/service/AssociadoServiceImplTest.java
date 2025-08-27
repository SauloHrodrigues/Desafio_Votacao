package com.db.votacao.desafio_votacao.service;

import com.db.votacao.desafio_votacao.auxiliares.fixtures.AssociadoFixture;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoAtualizaDto;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoRequestDto;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoResponseDto;
import com.db.votacao.desafio_votacao.exceptions.associados.AssociadoJaExistenteException;
import com.db.votacao.desafio_votacao.exceptions.associados.AssociadoNaoExistenteException;
import com.db.votacao.desafio_votacao.models.Associado;
import com.db.votacao.desafio_votacao.repositorys.AssociadoRepository;
import com.db.votacao.desafio_votacao.services.implementacoes.AssociadoServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssociadoServiceImplTest {

    @InjectMocks
    private AssociadoServiceImpl service;

    @Mock
    AssociadoRepository repository;

    @Test
    @DisplayName("Deve realizar o cadastro de um novo associado com sucesso.")
    void deveCriarAssociadoComSucesso() {
        AssociadoRequestDto dto = AssociadoFixture.dtoRequestFixture("Jose","222.333.444-55");
        Associado associado = AssociadoFixture.associadoFixture(dto);

        when(repository.save(any(Associado.class))).thenReturn(associado);

        AssociadoResponseDto resposta = service.criarAssociado(dto);

        assertEquals(associado.getId(), resposta.id());
        assertEquals(dto.nome(), resposta.nome());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar um associado já existente.")
    void deveLancarExcessaoAoTentarCriarAssociadoJaExistente() {

        AssociadoRequestDto dto = AssociadoFixture.dtoRequestFixture("Maria", "666.777.888-55");

        when(repository.findByNome(anyString()))
                .thenThrow(new AssociadoJaExistenteException(dto.nome()));

        AssociadoJaExistenteException exception = assertThrows(AssociadoJaExistenteException.class, () -> {
            service.criarAssociado(dto);
        });

        assertEquals("O associado de nome: "+dto.nome().toUpperCase()+", já é cadastrado no banco.",
                exception.getMessage());
    }



    @Test
    @DisplayName("Deve retornar todos os associados cadastrados no banco.")
    void retornaListaDeAssociadosCadastrados() {
        Associado associado01 = AssociadoFixture.associadoFixture("Juliano", "123.456.789-0");
        Associado associado02 = AssociadoFixture.associadoFixture("Eduardo", "234.567.980-14");
        Associado associado03 = AssociadoFixture.associadoFixture("Maria", "345.678.912-22");
        List<Associado> associados = List.of(associado01,associado02,associado03);
        Page<Associado> pageAssociados = new PageImpl<>(associados, PageRequest.of(0, 10), associados.size());
        Pageable pageable = PageRequest.of(0, 10);

        when(repository.findAll(any(Pageable.class))).thenReturn(pageAssociados);

        Page<AssociadoResponseDto>resposta = service.retornaListaDeAssociadosCadastrados(pageable);

        assertEquals(3, resposta.getContent().size());
        assertEquals(associado01.getNome(), resposta.getContent().get(0).nome());
        assertEquals(associado02.getNome(), resposta.getContent().get(1).nome());
        assertEquals(associado03.getNome(), resposta.getContent().get(2).nome());
    }

    @Test
    @DisplayName("Deve retornar um associado com determinado id, já cadastrado no banco.")
    void deveBuscaAssociadoPorId() {

        Associado associado = AssociadoFixture.associadoFixture("Paulo","222.333.444-88");
        Long id = associado.getId();

        when(repository.findById(id)).thenReturn(Optional.of(associado));

        AssociadoResponseDto resposta = service.buscaAssociadoPorId(id);

        assertEquals(associado.getId(),resposta.id());
        assertEquals(associado.getNome(),resposta.nome());
        assertEquals(associado.getDocumento(),resposta.documento());
    }

    @Test
    @DisplayName("Deve lançar excessão ao buscar um associado com determinado id, não cadastrado no banco.")
    void deveLancarExcessaoAoBuscaAssociadoPorIdEnaoEncontrar(){
        Long idNãoCadastrado = 3L;
        when(repository.findById(idNãoCadastrado)).thenReturn(Optional.empty());

        AssociadoNaoExistenteException resposta = assertThrows(AssociadoNaoExistenteException.class,
                ()->{service.buscaAssociadoPorId(idNãoCadastrado);
                });

        assertTrue(resposta.getMessage().contains("O associado de ID: "+idNãoCadastrado+", não foi encontrado no banco") );
        verify(repository).findById(idNãoCadastrado);
    }

    @Test
    @DisplayName("Deve atualizar um associado existente e retornar o DTO atualizado")
    void deveAtualizarDadosDeUmAssociadoJaExistente() {
        Associado associado = AssociadoFixture.associadoFixture("jose","123.456.789.10");
        AssociadoAtualizaDto atualizaDto = AssociadoFixture.dtoAtualizarFixture(null,"555,666,777-88");

        when(repository.findById(associado.getId())).thenReturn(Optional.of(associado));

        AssociadoResponseDto resposta = service.atualizaDadosAssociado(associado.getId(),atualizaDto);
        System.out.println(resposta);
        assertEquals(associado.getId(),resposta.id());
        assertEquals(associado.getNome(),resposta.nome());
        assertEquals(atualizaDto.documento(),resposta.documento());
    }

    @Test
    @DisplayName("Deve apagar um associado existente no banco de dados")
    void deveApagaAssociadoDoBanco() {
        Associado associado = AssociadoFixture.associadoFixture("jose","222.333.444-55");
        Long id = associado.getId();

        when(repository.findById(id)).thenReturn(Optional.of(associado));

        service.apagaAssociado(id);

        verify(repository).findById(id);
        verify(repository).delete(associado);
    }
}