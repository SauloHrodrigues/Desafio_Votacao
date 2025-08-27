package com.db.votacao.desafio_votacao.exceptions.associados;

public class AssociadoNaoExistenteException extends RuntimeException{
    public AssociadoNaoExistenteException(String nome){
        super("O associado de nome: "+nome.toUpperCase()+", não foi encontrado no banco.");
    }
    public AssociadoNaoExistenteException(Long id){
        super("O associado de ID: "+id+", não foi encontrado no banco.");
    }
}