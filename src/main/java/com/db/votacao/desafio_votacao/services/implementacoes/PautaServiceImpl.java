package com.db.votacao.desafio_votacao.services.implementacoes;

import com.db.votacao.desafio_votacao.dtos.pauta.PautaAtualizarRequestDto;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaRequesteDto;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaResponseDto;
import com.db.votacao.desafio_votacao.exceptions.pautas.PautaInexistenteException;
import com.db.votacao.desafio_votacao.exceptions.pautas.PautaJaExistenteException;
import com.db.votacao.desafio_votacao.mappers.PautaMapper;
import com.db.votacao.desafio_votacao.models.Pauta;
import com.db.votacao.desafio_votacao.repositorys.PautaRepository;
import com.db.votacao.desafio_votacao.services.PautaService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PautaServiceImpl implements PautaService {

    private final PautaRepository repository;

    private PautaMapper mapper = PautaMapper.INSTANCE;

    @Override
    public PautaResponseDto criarPauta(PautaRequesteDto dto) {
        validaPautaInexistente(dto.titulo(), dto.autor());
        Pauta pauta = repository.save(mapper.toEntity(dto));
        System.out.println(pauta.getDataDeCadastro());
        return mapper.toResponse(pauta);
    }

    @Override
    public Page<PautaResponseDto> listarPautas(Pageable pageable) {
        Page<PautaResponseDto> pautas = repository.findAll(pageable).map(mapper::toResponse);
        return pautas;
    }

    @Override
    public PautaResponseDto buscarPautaPorId(Long id) {
        return buscarPautaPorId(id);
    }

    @Override
    public PautaResponseDto atualizarPautaPorId(Long id, PautaAtualizarRequestDto dto) {
       Pauta pauta = buscaPauta(id);
        System.out.println("antes: "+ pauta);
        mapper.update(pauta,dto);
        System.out.println("depois "+pauta);
        repository.save(pauta);
        return mapper.toResponse(pauta);
    }

    @Override
    public void apagarPautaporId(Long id) {
        Pauta pauta = buscaPauta(id);
        repository.delete(pauta);
    }

    protected Pauta buscaPauta(Long id){
        Optional<Pauta> pauta = repository.findById(id);

        if(pauta.isEmpty()){
            throw new PautaInexistenteException(id);
        }else
            return pauta.get();
    }

    protected void validaPautaInexistente(String tituloBuscado, String autorBuscado){
        List<Pauta> pautas = repository.findAllByAutor(autorBuscado.toLowerCase());
        for(Pauta pauta:pautas){
            if(pauta.getTitulo().equalsIgnoreCase(tituloBuscado)){
                throw new PautaJaExistenteException(pauta.getAutor(), pauta.getTitulo(), pauta.getId());
            }
        }

    }
}
