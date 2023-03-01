package com.texo.challenge.controller;

import com.texo.challenge.model.vo.ProducerVO;
import com.texo.challenge.service.ProducerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ProducerController {

    @Autowired
    ProducerService producerService;


    @ApiOperation(value = "Buscando produtor com premiação com o maior intervalo entre dois prêmios consecutivos" +
            "e o produto com o menor intervalo entre prêmios consecutivos")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Produtores encontrados com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao processar requisição")
    })
    @GetMapping("/producers")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, List<ProducerVO>>> getTaskById() {
        Map<String, List<ProducerVO>> producers = producerService.findWinnersProducers();
        return ResponseEntity.ok().body(producers);
    }

}
