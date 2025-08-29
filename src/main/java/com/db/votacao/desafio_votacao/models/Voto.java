package com.db.votacao.desafio_votacao.models;

import com.db.votacao.desafio_votacao.enuns.VotoEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "votos")
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "associado_id")
    @ToString.Exclude
    private  Associado associado;

    @ManyToOne
    @JoinColumn(name = "id_da_pauta")
    private Pauta pauta;

    private VotoEnum voto;

    @Override
    public String toString() {
        return "Voto{" +
                "id=" + id +
                ", voto='" + voto + '\'' +
                ", associadoId=" + (associado != null ? associado.getId() : null) +
                '}';
    }

}
