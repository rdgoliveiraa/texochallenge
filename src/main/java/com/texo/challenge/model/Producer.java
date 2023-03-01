package com.texo.challenge.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "producers")
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "intervalOfWin")
    private Long intervalOfWin;

    @Column(name = "previousWin")
    private Long previousWin;

    @Column(name = "followingWin")
    private Long followingWin;

}
