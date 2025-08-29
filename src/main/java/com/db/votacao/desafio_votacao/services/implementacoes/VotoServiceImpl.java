package com.db.votacao.desafio_votacao.services.implementacoes;

import com.db.votacao.desafio_votacao.dtos.voto.ConstroeVotoDto;
import com.db.votacao.desafio_votacao.dtos.voto.VotoRequestDto;
import com.db.votacao.desafio_votacao.dtos.voto.VotoResponseDto;
import com.db.votacao.desafio_votacao.enuns.VotoEnum;
import com.db.votacao.desafio_votacao.exceptions.Voto.VotoJaRealizadoException;
import com.db.votacao.desafio_votacao.mappers.VotoMapper;
import com.db.votacao.desafio_votacao.models.Associado;
import com.db.votacao.desafio_votacao.models.Pauta;
import com.db.votacao.desafio_votacao.models.Voto;
import com.db.votacao.desafio_votacao.repositorys.VotoRepository;
import com.db.votacao.desafio_votacao.services.VotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VotoServiceImpl implements VotoService {

    private final VotoRepository repository;
    private final AssociadoServiceImpl associadoService;
    private final PautaServiceImpl pautaService;

    private VotoMapper mapper = VotoMapper.INSTANCE;
    @Override
    public VotoResponseDto votar(VotoRequestDto dto) {
        Associado associado = associadoService.buscaAssociadoExistente(dto.idAssociado());
        Pauta pauta = pautaService.buscaPauta(dto.idPauta());
        validarVoto(pauta,associado);
        VotoEnum votoEnum = dto.voto();
        ConstroeVotoDto votoDto = new ConstroeVotoDto(associado, pauta, votoEnum);
        Voto voto = mapper.toEntity(votoDto);
        repository.save(voto);
        return mapper.toResponse(voto);
    }

    protected void validarVoto(Pauta pauta, Associado associado){
        for (Voto v : associado.getVotos()) {
            if (v.getPauta().getId().equals(pauta.getId())) {
                throw new VotoJaRealizadoException(associado.getNome());
            }
        }
    }
}