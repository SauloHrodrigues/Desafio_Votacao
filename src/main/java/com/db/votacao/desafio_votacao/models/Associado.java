package com.db.votacao.desafio_votacao.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Entity
@Table(name = "associados")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Associado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.PRIVATE)
    private Long id;
    private String nome;
    private String documento;

    @Setter(value = AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "associado", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Voto> votos = new ArrayList<>();

    @Override
    public String toString() {
        return "Associado{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", totalVotos=" + (votos != null ? votos.size() : 0) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Associado associado)) return false;
        return Objects.equals(getId(), associado.getId()) && Objects.equals(getNome(), associado.getNome()) && Objects.equals(getDocumento(), associado.getDocumento());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getDocumento());
    }
}
