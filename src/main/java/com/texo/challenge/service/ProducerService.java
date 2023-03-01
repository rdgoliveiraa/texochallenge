package com.texo.challenge.service;


import com.texo.challenge.config.converter.DozerConverter;
import com.texo.challenge.model.Producer;

import com.texo.challenge.model.vo.ProducerVO;
import com.texo.challenge.repository.MovieRepository;
import com.texo.challenge.repository.ProducerRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
public class ProducerService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ProducerRespository producerRespository;

    public Map<String, List<ProducerVO>> findWinnersProducers() {

        List<Producer> producersWinner = producerRespository.findAll().stream().filter(producer -> producer.getIntervalOfWin() != null).toList();

        Optional<Producer> minWin = producersWinner.stream().min(Comparator.comparingLong(Producer::getIntervalOfWin));
        List<Producer> minWinProducers = producersWinner.stream().filter(producer -> producer.getIntervalOfWin().equals(minWin.get().getIntervalOfWin())).toList();


        Optional<Producer> maxWin = producersWinner.stream().max(Comparator.comparingLong(Producer::getIntervalOfWin));
        List<Producer> maxWinProducers = producersWinner.stream().filter(producer -> producer.getIntervalOfWin().equals(maxWin.get().getIntervalOfWin())).toList();

        Map<String, List<ProducerVO>> mapProducers = new HashMap<>();
        mapProducers.put("min", DozerConverter.parseListObjects(minWinProducers, ProducerVO.class));
        mapProducers.put("max", DozerConverter.parseListObjects(maxWinProducers, ProducerVO.class));

        return mapProducers;
    }
}
