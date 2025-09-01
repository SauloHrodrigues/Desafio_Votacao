package com.db.votacao.desafio_votacao.auxiliares.fixtures;

import com.db.votacao.desafio_votacao.dtos.pauta.PautaAtualizarRequestDto;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaRequesteDto;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaResponseDto;
import com.db.votacao.desafio_votacao.models.Pauta;
import java.time.LocalDateTime;

public class PautaFixture {
    private static Long indice = 0L;
    public static PautaRequesteDto requesteDto(String autor, String titulo, String descricao){
        return new PautaRequesteDto(
                autor, titulo, descricao);
    }

    public static Pauta pauta(PautaRequesteDto dto) {
        return pauta(dto.autor(), dto.titulo(),dto.descricao());
    }

    public static Pauta pauta(String autor, String titulo, String descricao){

        indice++;
        return Pauta.builder()
                .id(indice)
                .titulo(titulo)
                .autor(autor)
                .descricao(descricao)
                .dataDeCadastro(LocalDateTime.now())
                .build();
    }

    public static PautaResponseDto responseDto(Pauta pauta){
        return new PautaResponseDto(
                pauta.getId(),
                pauta.getAutor(),
                pauta.getTitulo(),
                pauta.getDescricao(),
                pauta.getDataDeCadastro(),
                pauta.getVotos()
        );
    }

    public static PautaResponseDto responseDto(Pauta pauta, PautaAtualizarRequestDto atualizacoes){
        String titulo = atualizacoes.titulo() != null ? atualizacoes.titulo() : pauta.getTitulo();
        String autor = atualizacoes.autor() != null ? atualizacoes.autor() : pauta.getAutor();
        String descricao = atualizacoes.descricao() != null ? atualizacoes.descricao() : pauta.getDescricao();

        return new PautaResponseDto(
                pauta.getId(),
                autor,
                titulo,descricao,pauta.getDataDeCadastro(),pauta.getVotos());
    }
}
