package com.db.votacao.desafio_votacao.repositorys;

import com.db.votacao.desafio_votacao.models.Associado;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado,Long> {
    Optional<Associado> findByNome(String nome);
}
