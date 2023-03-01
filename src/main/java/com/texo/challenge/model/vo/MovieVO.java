package com.texo.challenge.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class MovieVO implements Serializable {
    @JsonIgnore
    private long id;

    private String title;

    private String studio;

    private List<ProducerVO> producers;

    private String winner;

    private Long year;

}
