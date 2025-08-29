package com.db.votacao.desafio_votacao.repositorys;


import com.db.votacao.desafio_votacao.models.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends JpaRepository<Voto,Long> {

}