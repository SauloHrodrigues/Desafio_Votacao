package com.db.votacao.desafio_votacao.exceptions.pautas;

public class PautaInexistenteException extends RuntimeException{
    public PautaInexistenteException(String titulo){
        super("A pauta com titulo: "+titulo.toUpperCase()+", não existe no banco.");
    }
    public PautaInexistenteException(Long id){
        super("A pauta com Id: "+id+", não existe no banco.");
    }
}