package com.db.votacao.desafio_votacao.exceptions.associados;

public class AssociadoJaExistenteException extends RuntimeException{
    public AssociadoJaExistenteException(String nome){
        super("O associado de nome: "+nome.toUpperCase()+", já é cadastrado no banco.");
    }
}