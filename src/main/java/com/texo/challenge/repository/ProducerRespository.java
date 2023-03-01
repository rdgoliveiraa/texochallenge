package com.texo.challenge.repository;

import com.texo.challenge.model.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRespository extends JpaRepository<Producer, Long> {

    Producer getProducerByName(String name);
}
