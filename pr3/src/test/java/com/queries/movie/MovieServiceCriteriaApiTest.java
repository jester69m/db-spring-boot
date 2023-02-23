package com.queries.movie;

import com.queries.movie.criteriaApi.MovieCriteriaApiImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MovieServiceCriteriaApiTest {

    private static Movie movie0;
    private static Movie movie1;
    private static Movie movie2;
    private static Movie movie3;

    @Autowired
    private MovieCriteriaApiImpl movieCriteriaApi;
    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp(){
        movie0 = new Movie(0,"movie0","some description0",16, Set.of("drama","comedy"),
                Set.of("actor1","actor2"),Set.of("director01","director02"),"producer0",
                Set.of("distributor1","distributor2"),1000000000,2020);
        movie1 = new Movie(1,"movie1","some tor3 description1",10, Set.of("horror","thriller"),
                Set.of("actor2","actor3"),Set.of("director02","director03"),"producer1",
                Set.of("distributor2","distributor3"),2000000000,2018);
        movie2 =  new Movie(2,"movie2","some description2",18, Set.of("drama","comedy"),
                Set.of("actor3","actor4"),Set.of("director03","director04"),"producer2",
                Set.of("distributor3","distributor4"),500000000,2016);
        movie3 = new Movie(3,"movie3 tor3","some description3",3, Set.of("drama","comedy"),
                Set.of("actor4","actor1"),Set.of("director04","director01"),"producer3",
                Set.of("distributor1","distributor4"),200000000,2014);
    }
    @AfterEach
    void clearData(){
        movieRepository.deleteAll();
    }
    @Test
    void getAllMovieByProducerTest(){
        List<Movie> savedMovie = movieRepository.saveAll(List.of(
                movie0,movie1,movie2,movie3
        ));
        List<Movie> foundMovie = movieCriteriaApi.getAllByProducer("producer0");
        assertTrue(foundMovie.size() == 1);
        assertEquals(savedMovie.get(0), foundMovie.get(0));
    }
    @Test
    void getAllMovieFromGroupOfDirector(){
        List<Movie> savedMovie = movieRepository.saveAll(List.of(
                movie0,movie1,movie2,movie3
        ));
        List<String> directorsToFind = List.of("director01","director02");
        List<Movie> foundMovie = movieCriteriaApi.getAllByGroupOfDirector(directorsToFind);
        assertTrue(foundMovie.size() == 1);
        assertEquals(savedMovie.get(0), foundMovie.get(0));
    }
    @Test
    void getAllMovieFromOneOfGroupOfDirector(){
        List<Movie> savedMovie = movieRepository.saveAll(List.of(
                movie0,movie1,movie2,movie3
        ));
        List<String> directorsToFind = List.of("director01","director04");
        List<Movie> foundMovie = movieCriteriaApi.getAllByOneGroupOfDirector(directorsToFind);
        assertTrue(foundMovie.size() == 3);
        assertSame(savedMovie.get(0),foundMovie.get(0));
        assertSame(savedMovie.get(2),foundMovie.get(1));
        assertSame(savedMovie.get(3),foundMovie.get(2));
    }
    @Test
    void getAllMovieInCashierFeesDiapasonAndSpecificGenres(){
        List<Movie> savedMovie = movieRepository.saveAll(List.of(
                movie0,movie1,movie2,movie3
        ));
        List<String> genresToFind = List.of("drama","comedy");
        List<Movie> foundMovie = movieCriteriaApi.getAllByCashierFeesWithGivenGenres(400000000,1200000000,genresToFind);
        assertTrue(foundMovie.size() == 2);
        assertSame(savedMovie.get(0),foundMovie.get(0));
        assertSame(savedMovie.get(2),foundMovie.get(1));
    }
    @Test
    void getAllMovieWithMinAgeSmallerThanGiven(){
        List<Movie> savedMovie = movieRepository.saveAll(List.of(
                movie0,movie1,movie2,movie3
        ));
        List<Movie> foundMovie = movieCriteriaApi.getAllByMinAgeSmaller(4);
        assertTrue(foundMovie.size() == 1);
        assertSame(savedMovie.get(3),foundMovie.get(0));
    }
    @Test
    void getAllMovieWithMinAgeInDiapason(){
        List<Movie> savedMovie = movieRepository.saveAll(List.of(
                movie0,movie1,movie2,movie3
        ));
        List<Movie> foundMovie = movieCriteriaApi.getAllByMinAgeBetween(12,17);
        assertTrue(foundMovie.size() == 1);
    }
    @Test
    void getAllMovieByTextFragment(){
        List<Movie> savedMovie = movieRepository.saveAll(List.of(
                movie0,movie1,movie2,movie3
        ));
        List<Movie> foundMovie = movieCriteriaApi.getAllByText("tor3");
        assertTrue(foundMovie.size() == 2);
        assertSame(savedMovie.get(1),foundMovie.get(0));
        assertSame(savedMovie.get(3),foundMovie.get(1));
    }

}
