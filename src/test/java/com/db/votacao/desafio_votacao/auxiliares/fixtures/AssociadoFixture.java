package com.db.votacao.desafio_votacao.auxiliares.fixtures;

import com.db.votacao.desafio_votacao.dtos.associado.AssociadoAtualizaDto;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoRequestDto;
import com.db.votacao.desafio_votacao.dtos.associado.AssociadoResponseDto;
import com.db.votacao.desafio_votacao.models.Associado;

public class AssociadoFixture {
    private static Long indice = 0L;

    public static AssociadoRequestDto dtoRequestFixture(String nome, String documento) {
        return new AssociadoRequestDto(
                nome, documento
        );
    }

    public static AssociadoAtualizaDto dtoAtualizarFixture(String nome, String documento){
        return new AssociadoAtualizaDto(nome, documento);
    }

    public static Associado associadoFixture(AssociadoRequestDto dto) {
        return associadoFixture(dto.nome(), dto.documento());
    }

    public static Associado associadoFixture(String nome, String documento) {
        indice = +1L;
        return Associado.builder()
                .id(indice)
                .nome(nome)
                .documento(documento)
                .build();
    }

    public static AssociadoResponseDto responseDto(Associado associado){
        return new AssociadoResponseDto(
                associado.getId(), associado.getNome(), associado.getDocumento()
        );
    }
    public static AssociadoResponseDto responseDto(Associado associado, AssociadoAtualizaDto atualizacoes){
        String nome = associado.getNome();
        String documento = associado.getDocumento();

        if(atualizacoes.nome() != null){
            nome = atualizacoes.nome();
        }
        if(atualizacoes.documento() != null){
            documento = atualizacoes.documento();
        }

        return new AssociadoResponseDto(
                associado.getId(), nome, documento);
    }
}
