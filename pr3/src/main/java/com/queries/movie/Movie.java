package com.queries.movie;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 50,nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column
    @PositiveOrZero
    private int minAge;

    @ElementCollection
    private Set<String> genres;

    @ElementCollection
    private Set<String> actors;

    @ElementCollection
    private Set<String> directors;

    private String producer;

    @ElementCollection
    private Set<String> distributor;

    @PositiveOrZero
    private long cashierFees;

    @Positive
    private int yearOfRelease;
}
