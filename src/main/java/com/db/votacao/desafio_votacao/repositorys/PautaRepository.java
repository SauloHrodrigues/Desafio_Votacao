package com.db.votacao.desafio_votacao.repositorys;


import com.db.votacao.desafio_votacao.models.Pauta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta,Long> {
    List<Pauta> findAllByAutor(String autor);

}
