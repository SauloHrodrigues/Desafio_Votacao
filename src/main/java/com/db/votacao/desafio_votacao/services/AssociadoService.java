package com.db.votacao.desafio_votacao.services;

import com.db.votacao.desafio_votacao.dtos.associado.AssociadoAtualizaDto;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoRequestDto;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface AssociadoService {
    AssociadoResponseDto criarAssociado(AssociadoRequestDto dto);

    Page<AssociadoResponseDto> retornaListaDeAssociadosCadastrados(Pageable pageable);

    AssociadoResponseDto buscaAssociadoPorId(Long id);

    AssociadoResponseDto atualizaDadosAssociado(Long id,AssociadoAtualizaDto dto);

    void apagaAssociado(Long id);
}
