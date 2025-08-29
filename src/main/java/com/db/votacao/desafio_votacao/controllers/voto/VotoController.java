package com.db.votacao.desafio_votacao.controllers.voto;

import com.db.votacao.desafio_votacao.dtos.pauta.PautaRequesteDto;
import com.db.votacao.desafio_votacao.dtos.pauta.PautaResponseDto;
import com.db.votacao.desafio_votacao.dtos.voto.VotoRequestDto;
import com.db.votacao.desafio_votacao.dtos.voto.VotoResponseDto;
import com.db.votacao.desafio_votacao.services.VotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/voto")
public class VotoController {

    private final VotoService service;
    @PostMapping
    public ResponseEntity<VotoResponseDto> votar(@Valid @RequestBody VotoRequestDto dto) {
        VotoResponseDto respostaDto = service.votar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respostaDto);
    }
}
