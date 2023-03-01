package com.texo.challenge.service;


import com.texo.challenge.config.converter.DozerConverter;
import com.texo.challenge.exception.ResourceNotFoundException;
import com.texo.challenge.model.vo.MovieVO;
import com.texo.challenge.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public List<MovieVO> findAll() {

        return DozerConverter.parseListObjects(movieRepository.findAll(), MovieVO.class);
    }

    public MovieVO findById(Long id) {
        var entity = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this"));

        return DozerConverter.parseObject(entity, MovieVO.class);
    }
}
