package com.db.votacao.desafio_votacao.models;

import com.db.votacao.desafio_votacao.enuns.PautaStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pautas")
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String titulo;

    @NonNull
    private String autor;

    @NonNull
    private String descricao;

    @Builder.Default
    private LocalDateTime dataDeCadastro = LocalDateTime.now();
    private LocalDateTime aberturaParaVotacao;
    private LocalDateTime fechamentoDaVotacao;

    @Setter(AccessLevel.PRIVATE)
    @Builder.Default
    @OneToMany(mappedBy = "pauta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @MapKeyColumn(name = "id")
    @JsonIgnore
    private List<Voto> votos = new ArrayList<>();

    private PautaStatusEnum status = PautaStatusEnum.FECHADA;

    public void adicionarVoto(Voto voto) {
        votos.add(voto);
    }

    public void iniciarVotacao(long duracaoEmMinutos) {
        this.aberturaParaVotacao = LocalDateTime.now();
        this.status = PautaStatusEnum.ABERTA;
        this.fechamentoDaVotacao = this.aberturaParaVotacao.plusMinutes(duracaoEmMinutos);
    }


    @Override
    public String toString() {
        return "Pauta{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataDeCadastro=" + dataDeCadastro +
                ", totalVotos=" + (votos != null ? votos.size() : 0) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pauta pauta)) return false;
        return Objects.equals(getId(), pauta.getId()) &&
                Objects.equals(getTitulo(), pauta.getTitulo()) &&
                Objects.equals(getDescricao(), pauta.getDescricao()) &&
                Objects.equals(getDataDeCadastro(), pauta.getDataDeCadastro());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitulo(), getDescricao(), getDataDeCadastro());
    }
}
