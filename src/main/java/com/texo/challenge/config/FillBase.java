package com.texo.challenge.config;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.texo.challenge.model.Movie;
import com.texo.challenge.model.csv.MovieCSV;
import com.texo.challenge.model.Producer;
import com.texo.challenge.repository.MovieRepository;
import com.texo.challenge.repository.ProducerRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Component
@Slf4j
public class FillBase {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ProducerRespository producerRespository;

    @Value("${path-file}")
    String path;
    @PostConstruct
    public void readCSV () throws FileNotFoundException {

        List<MovieCSV> csvParsed;

        BufferedReader reader = new BufferedReader(new FileReader(path));
            CsvToBean<MovieCSV> csvToBean = new CsvToBeanBuilder<MovieCSV>(reader)
                    .withType(MovieCSV.class)
                    .withSeparator(';')
                    .withSkipLines(0)
                    .build();

            csvParsed = new ArrayList<>(csvToBean.parse());

        fillBaseH2(csvParsed);
    }

    private void fillBaseH2(List<MovieCSV> csvParsed) {

        parseToMovie(csvParsed);



    }

    private void parseToMovie(List<MovieCSV> csvParsed) {

        for (MovieCSV movieCSV : csvParsed) {
            Movie movie = new Movie();
            movie.setYear(movieCSV.getYear());
            movie.setTitle(movieCSV.getTitle());
            movie.setWinner(movieCSV.getWinner() == null ? "no" : movieCSV.getWinner());
            movie.setProducers(parseProducer(movieCSV.getProducers(), movie));
            movie.setStudio(movieCSV.getStudio());
            movieRepository.saveAndFlush(movie);
            log.info("Filme " + movie.getTitle() + " gravado com sucesso.");
        }

    }

    private List<Producer> parseProducer(String producerName, Movie movie) {
        Stream<String> stream = splitNameProducers(producerName);
        List<String> producersName = new ArrayList<>(stream.toList());

        Stream<String> existProducers = producersName.stream().filter(p -> producerRespository.getProducerByName(p) != null);
        List<String> existProducersNameList = existProducers.toList();

        if(!existProducersNameList.isEmpty()) {
            producersName.removeAll(existProducersNameList);
        }

        List<Producer> producers = new ArrayList<>();

        if (!producersName.isEmpty()) {

            insertNewProducer(movie, producersName, producers);
        } else {
            List<Producer> existProducersList = new ArrayList<>();
            existProducersNameList.forEach(existProducer -> existProducersList.add(producerRespository.getProducerByName(existProducer)));
            updateProducer(movie, existProducersList);
            producers.addAll(existProducersList);
        }

        /*
            Criar lógica para preencher os campos de anos de premiações e intervalos entre anos
         */

        return producers;
    }

    private void updateProducer(Movie movie, List<Producer> existProducersList) {
        existProducersList.forEach(producer -> {
            if(!movie.getWinner().isEmpty()) {
                populatePreviousWin(movie, producer);

                populateFollowingWin(movie, producer);

                producer.setIntervalOfWin(producer.getFollowingWin() - producer.getPreviousWin());
                producerRespository.save(producer);
                log.info("Produtor " + producer.getName() + " atualizado com sucesso!");
            }
        });
    }

    private static void populateFollowingWin(Movie movie, Producer producer) {
        if (producer.getFollowingWin() != null) {
            if (producer.getFollowingWin() < movie.getYear()) {
                producer.setFollowingWin(movie.getYear());
            }
        } else {
            producer.setFollowingWin(movie.getYear());
        }
    }

    private static void populatePreviousWin(Movie movie, Producer producer) {
        if (producer.getPreviousWin() != null) {
            if (producer.getPreviousWin() > movie.getYear()) {
                producer.setPreviousWin(movie.getYear());
            }
        } else {
            producer.setPreviousWin(movie.getYear());
        }
    }

    private void insertNewProducer(Movie movie, List<String> producersName, List<Producer> producers) {
        for (String name: producersName) {
            Producer producer = new Producer();
            producer.setName(name);
            if(!movie.getWinner().isEmpty()) {
                producer.setFollowingWin(movie.getYear());
                producer.setPreviousWin(movie.getYear());
                producer.setIntervalOfWin(producer.getFollowingWin() - producer.getPreviousWin());
            }
            producers.add(producer);

            producerRespository.save(producer);
            log.info("Produtor " + producer.getName() + " gravado com sucesso!");
        }
    }

    private static Stream<String> splitNameProducers(String producerName) {
        return Stream.of(producerName)
                .flatMap(Pattern.compile(", and ")::splitAsStream)
                .flatMap(Pattern.compile(",")::splitAsStream)
                .flatMap(Pattern.compile(" and ")::splitAsStream);
    }
}
