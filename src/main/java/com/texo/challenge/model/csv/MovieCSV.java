package com.texo.challenge.model.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class MovieCSV {

    @CsvBindByName(column = "title")
    private String title;

    @CsvBindByName(column = "studios")
    private String studio;

    @CsvBindByName(column = "producers")
    private String producers;

    @CsvBindByName(column = "winner")
    private String winner;

    @CsvBindByName(column = "year")
    private Long year;
}
