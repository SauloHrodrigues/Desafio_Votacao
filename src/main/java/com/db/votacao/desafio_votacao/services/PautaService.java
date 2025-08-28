package com.db.votacao.desafio_votacao.services;

import com.db.votacao.desafio_votacao.dtos.pauta.PautaRequesteDto;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaResponseDto;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaAtualizarRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PautaService {

    PautaResponseDto criarPauta(PautaRequesteDto dto);

    Page<PautaResponseDto> listarPautas(Pageable pageable);

    PautaResponseDto buscarPautaPorId(Long id);
    PautaResponseDto atualizarPautaPorId(Long id, PautaAtualizarRequestDto dto);
    void apagarPautaporId(Long id);

}
