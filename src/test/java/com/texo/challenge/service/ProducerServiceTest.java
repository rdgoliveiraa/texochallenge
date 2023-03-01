package com.texo.challenge.service;

import com.texo.challenge.exception.ResourceNotFoundException;
import com.texo.challenge.model.Movie;
import com.texo.challenge.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieService movieService;

    Mockito mockito = new Mockito();

    @Test
    void ifIdOkReturnMovie() {
        Movie movie = new Movie();
        movie.setId(1L);

        doReturn(Optional.of(movie)).when(movieRepository).findById(any());

        Assertions.assertEquals(movie.getId(), movieService.findById(movie.getId()).getId());

    }

    @Test
    void ifInvalidSearchIdShouldThrowException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> movieService.findById(1L));
    }

    @Test
    void seValidoDeveBuscarTodasPessoasComSucesso() {
        Movie movie = new Movie();
        movie.setId(1L);

        var movieList =List.of(movie);

        Mockito.when(movieRepository.findAll()).thenReturn(List.of(movie));

        Assertions.assertEquals(movieList.get(0).getId(), movieService.findAll().get(0).getId());
    }

}