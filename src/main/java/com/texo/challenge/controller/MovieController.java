package com.texo.challenge.controller;

import com.texo.challenge.model.vo.MovieVO;
import com.texo.challenge.service.MovieService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class MovieController {

    @Autowired
    MovieService movieService;


    @ApiOperation(value = "Listando filmes indicados e com premiação da categoria de pior filme do prêmio Golden Raspberry Awards")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Filmes encontrados com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao processar requisição")
    })
    @GetMapping("/movies")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<MovieVO>> getAllMovies() {
        List<MovieVO> movies = movieService.findAll();
        return ResponseEntity.ok().body(movies);
    }

    @ApiOperation(value = "Busca filme indicado ou com premiação da categoria de pior filme do prêmio Golden Raspberry Awards")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Filme encontrado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao processar requisição")
    })
    @GetMapping("/movie/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MovieVO> getMovieById(@PathVariable("id") Long id) {
        MovieVO movie = movieService.findById(id);
        return ResponseEntity.ok().body(movie);
    }

}
