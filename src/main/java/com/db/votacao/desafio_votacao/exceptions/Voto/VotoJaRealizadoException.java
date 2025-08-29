package com.db.votacao.desafio_votacao.exceptions.Voto;

public class VotoJaRealizadoException extends RuntimeException{
    public VotoJaRealizadoException(String nome){
        super("O associado: "+nome.toUpperCase()+", já deu seu voto.");
    }
}