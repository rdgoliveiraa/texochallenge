package com.texo.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.texo.challenge.exception.ResourceNotFoundException;
import com.texo.challenge.model.vo.MovieVO;
import com.texo.challenge.model.vo.ProducerVO;
import com.texo.challenge.repository.MovieRepository;
import com.texo.challenge.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService service;
    @MockBean
    private MovieRepository repository;

    MovieVO movie;

    private ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setup() {
        movie = new MovieVO();
        movie.setId(1);
        movie.setTitle("Can't Stop the Music");
        movie.setProducers(List.of(new ProducerVO()));
        movie.setStudio("Associated Film Distribution");
        movie.setYear(1980L);
        movie.setWinner("yes");
    }

    @Test
    void ifIdOkReturnSuccess () throws Exception {

        doReturn(movie).when(service).findById(any());

        mockMvc.perform(get("/api/v1/movie/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'title':'Can\\'t Stop the Music','studio':'Associated Film Distribution','producers':[{'producer':null,'previousWin':null,'followingWin':null,'intervalOfWin':null}],'winner':'yes','year':1980}"));

    }

    @Test
    void ifIdNotOkReturnError () throws Exception {

        when(service.findById(any())).thenThrow(ResourceNotFoundException.class);

        RequestBuilder requestBuilder = get("/api/v1/movie/1");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

    }

    @Test
    void ifOkReturnSuccess () throws Exception {

        mockMvc.perform(get("/api/v1/movies"))
                .andExpect(status().isOk());
    }

    @Test
    void ifURLInvalidThrowsException () throws Exception {

        when(service.findById(any())).thenThrow(HttpServerErrorException.InternalServerError.class);
        RequestBuilder requestBuilder = get("/api/v1/movie/1");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }

}