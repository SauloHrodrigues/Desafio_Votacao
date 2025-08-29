package com.db.votacao.desafio_votacao.services;

import com.db.votacao.desafio_votacao.dtos.voto.VotoRequestDto;
import com.db.votacao.desafio_votacao.dtos.voto.VotoResponseDto;

public interface VotoService {

    VotoResponseDto votar(VotoRequestDto votoRequestDto);
}