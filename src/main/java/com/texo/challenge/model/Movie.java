package com.texo.challenge.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "movies")
@Setter
@Getter
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "studio")
    private String studio;

    @OneToMany
    @JoinColumn
    private List<Producer> producers;

    @Column(name = "winner")
    private String winner;

    @Column(name = "year_movie")
    private Long year;

}
