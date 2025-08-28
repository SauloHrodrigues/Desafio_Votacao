package com.db.votacao.desafio_votacao.exceptions.pautas;

public class PautaJaExistenteException extends RuntimeException{
    public PautaJaExistenteException(String nome, String titulo,Long id){
        super(nome.toUpperCase()+", já tem uma pauta com titulo: "+
                titulo.toUpperCase()+". O Id é: "+id);
    }
}