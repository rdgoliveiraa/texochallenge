package com.texo.challenge.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@JsonPropertyOrder({"producer", "interval", "previousWin", "followingWin"})
public class ProducerVO implements Serializable {

    @JsonIgnore
    private Long id;

    @JsonProperty("producer")
    private String name;

    @Column(name = "interval")
    private Long intervalOfWin;

    @Column(name = "previousWin")
    private Long previousWin;

    @Column(name = "followingWin")
    private Long followingWin;
}
