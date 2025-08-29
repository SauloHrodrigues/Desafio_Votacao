package com.db.votacao.desafio_votacao.services.implementacoes;

import com.db.votacao.desafio_votacao.dtos.associado.AssociadoAtualizaDto;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoRequestDto;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoResponseDto;
import com.db.votacao.desafio_votacao.exceptions.associados.AssociadoJaExistenteException;
import com.db.votacao.desafio_votacao.exceptions.associados.AssociadoNaoExistenteException;
import com.db.votacao.desafio_votacao.mappers.AssociadoMapper;
import com.db.votacao.desafio_votacao.models.Associado;
import com.db.votacao.desafio_votacao.repositorys.AssociadoRepository;
import com.db.votacao.desafio_votacao.services.AssociadoService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssociadoServiceImpl implements AssociadoService {

    private final AssociadoRepository repository;
    private AssociadoMapper mapper= AssociadoMapper.INSTANCE;

    @Override
    public AssociadoResponseDto criarAssociado(AssociadoRequestDto dto) {
        validaAssociadoNaoExistente(dto.nome());

        Associado associado = repository.save(mapper.toEntity(dto));
        return mapper.toResponse(associado);
    }

    @Override
    public Page<AssociadoResponseDto> retornaListaDeAssociadosCadastrados(Pageable pageable) {
        Page<AssociadoResponseDto> associados = repository.findAll(pageable).map(mapper::toResponse);
        return associados;
    }

    @Override
    public AssociadoResponseDto buscaAssociadoPorId(Long id) {
        return mapper.toResponse(buscaAssociadoExistente(id));
    }

    @Override
    public AssociadoResponseDto atualizaDadosAssociado(Long id,AssociadoAtualizaDto dto) {
        Associado associado = buscaAssociadoExistente(id);
        mapper.updateAssociado(associado,dto);
        repository.save(associado);
        return mapper.toResponse(associado);
    }

    @Override
    public void apagaAssociado(Long id) {
        Associado associado = buscaAssociadoExistente(id);
        repository.delete(associado);
    }

    protected Associado buscaAssociadoExistente(Long id){
        Optional<Associado> associado= repository.findById(id);
        if(associado.isEmpty()){
            throw new AssociadoNaoExistenteException(id);
        }
        return associado.get();
    }

    protected Associado buscaAssociadoExistente(String nome){
        Optional<Associado> associado = repository.findByNome(nome.toLowerCase());
        if(associado.isEmpty()){
            throw new AssociadoJaExistenteException(nome);
        }
        return associado.get();
    }

    protected void validaAssociadoNaoExistente(String nome){
        Optional<Associado> associado= repository.findByNome(nome.toLowerCase());
        if(!associado.isEmpty()){
            throw new AssociadoNaoExistenteException(nome);
        }
    }
}