package com.db.votacao.desafio_votacao.enuns;

public enum VotoEnum {
    SIM("Sim"),
    NAO("Não"),

    NULO("Nulo"),
    BRANCO("Branco");

    private final String voto;

    VotoEnum(String voto){
        this.voto = voto;
    }
}
