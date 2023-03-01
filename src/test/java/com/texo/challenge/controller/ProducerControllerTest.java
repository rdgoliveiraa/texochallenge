package com.texo.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.texo.challenge.exception.ResourceNotFoundException;
import com.texo.challenge.model.vo.ProducerVO;
import com.texo.challenge.repository.MovieRepository;
import com.texo.challenge.service.ProducerService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProducerController.class)
class ProducerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProducerService service;
    @MockBean
    private MovieRepository repository;

    ProducerVO producerMinWin;

    ProducerVO producerMaxWin;

    Map<String, List<ProducerVO>> mapProducers = new HashMap<String, List<ProducerVO>>();

    private ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setup() {
        producerMinWin = new ProducerVO();
        producerMinWin.setId(1L);
        producerMinWin.setName("Allan Carr");
        producerMinWin.setIntervalOfWin(0L);
        producerMinWin.setPreviousWin(1980L);
        producerMinWin.setFollowingWin(1980L);

        producerMaxWin = new ProducerVO();
        producerMaxWin.setId(2L);
        producerMaxWin.setName("Bo Derek");
        producerMaxWin.setIntervalOfWin(6L);
        producerMaxWin.setPreviousWin(1984L);
        producerMaxWin.setFollowingWin(1990L);

        mapProducers.put("min", List.of(producerMinWin));
        mapProducers.put("max", List.of(producerMaxWin));
    }

    @Test
    void ifOkReturnSuccess () throws Exception {

        doReturn(mapProducers).when(service).findWinnersProducers();

        mockMvc.perform(get("/api/v1/producers"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'min':[{'producer':'Allan Carr','previousWin':1980,'followingWin':1980,'intervalOfWin':0}],'max':[{'producer':'Bo Derek','previousWin':1984,'followingWin':1990,'intervalOfWin':6}]}"));

    }

    @Test
    void ifIdNotOkReturnError () throws Exception {

        when(service.findWinnersProducers()).thenThrow(ResourceNotFoundException.class);

        RequestBuilder requestBuilder = get("/api/v1/producers");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

    }

    @Test
    void ifURLInvalidThrowsException () throws Exception {

        when(service.findWinnersProducers()).thenThrow(HttpServerErrorException.InternalServerError.class);
        RequestBuilder requestBuilder = get("/api/v1/producers");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }

}