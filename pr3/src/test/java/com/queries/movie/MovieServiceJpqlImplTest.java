package com.queries.movie;

import com.queries.movie.jpql.MovieServiceJpqlImpl;
import com.queries.movie.specificQuery.MyQuery;
import com.queries.movie.specificQuery.SearchOperator;
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
public class MovieServiceJpqlImplTest {

    private static Movie1 movie0;
    private static Movie1 movie1;
    private static Movie1 movie2;
    private static Movie1 movie3;

    @Autowired
    private MovieServiceJpqlImpl movieServiceJpql;
    @Autowired
    private MovieRepository1 movieRepository;

    @BeforeEach
    void setUp(){
        movie0 = new Movie1(0,"movie0","some description0",16, "drama, comedy",
                "actor1, actor2","director01, director02","producer0",
                "distributor1, distributor2",1000000000,2020);
        movie1 = new Movie1(1,"movie1","some tor3 description1",10, "horror, thriller",
                "actor2, actor3","director02, director03","producer1",
                "distributor2, distributor3",2000000000,2018);
        movie2 =  new Movie1(2,"movie2","some description2",18, "drama, comedy",
                "actor3, actor4","director03, director04","producer2",
                "distributor3, distributor4",500000000,2016);
        movie3 = new Movie1(3,"movie3 tor3","some description3",3, "drama, comedy",
                "actor4, actor1","director01, director04","producer3",
                "distributor1, distributor4",200000000,2014);
    }
    @AfterEach
    void clearData(){
        movieRepository.deleteAll();
    }
    @Test
    void getAllMovieByProducerTest(){
        List<Movie1> savedMovie = movieRepository.saveAll(List.of(
           movie0,movie1,movie2,movie3
        ));
        List<Movie1> foundMovie = movieServiceJpql.getAllMovieByProducer("producer0");
        assertTrue(foundMovie.size() == 1);
        assertEquals(savedMovie.get(0), foundMovie.get(0));

    }
    @Test
    void getAllMovieFromGroupOfDirector(){
        List<Movie1> savedMovie = movieRepository.saveAll(List.of(
                movie0,movie1,movie2,movie3
        ));
        String directorsToFind = "director01, director04";
        List<Movie1> foundMovie = movieServiceJpql.getAllMovieByAllDirector(directorsToFind);
        assertTrue(foundMovie.size() == 1);
        assertEquals(savedMovie.get(3), foundMovie.get(0));
    }
    @Test
    void getAllMovieFromOneOfGroupOfDirector(){
        List<Movie1> savedMovie = movieRepository.saveAll(List.of(
                movie0,movie1,movie2,movie3
        ));
        List<String> directorsToFind = List.of("director01", " director04");
        List<Movie1> foundMovie = movieServiceJpql.getAllMovieByAtLeastOneDirector(directorsToFind);
        assertTrue(foundMovie.size() == 3);
        assertSame(savedMovie.get(0),foundMovie.get(0));
        assertSame(savedMovie.get(2),foundMovie.get(1));
        assertSame(savedMovie.get(3),foundMovie.get(2));
    }
    @Test
    void getAllMovieInCashierFeesDiapasonAndSpecificGenres(){
        List<Movie1> savedMovie = movieRepository.saveAll(List.of(
                movie0,movie1,movie2,movie3
        ));
        String genresToFind = "drama, comedy";
        List<Movie1> foundMovie = movieServiceJpql.getAllMovieInCashierFeesDiapasonAndByGenres(400000000,1200000000,genresToFind);
        assertTrue(foundMovie.size() == 2);
        assertSame(savedMovie.get(0),foundMovie.get(0));
        assertSame(savedMovie.get(2),foundMovie.get(1));
    }
    @Test
    void getAllMovieWithMinAgeSmallerThanGiven(){
        List<Movie1> savedMovie = movieRepository.saveAll(List.of(
                movie0,movie1,movie2,movie3
        ));
        List<Movie1> foundMovie = movieServiceJpql.getAllMovieByMinAge(4);
        assertTrue(foundMovie.size() == 1);
        assertSame(savedMovie.get(3),foundMovie.get(0));
    }
    @Test
    void getAllMovieWithMinAgeInDiapason(){
        List<Movie1> savedMovie = movieRepository.saveAll(List.of(
                movie0,movie1,movie2,movie3
        ));
        List<Movie1> foundMovie = movieServiceJpql.getAllMovieByMinAge(12,17);
        assertTrue(foundMovie.size() == 1);
    }
    @Test
    void getAllMovieByTextFragment(){
        List<Movie1> savedMovie = movieRepository.saveAll(List.of(
                movie0,movie1,movie2,movie3
        ));
        List<Movie1> foundMovie = movieServiceJpql.getAllMovieByTextFragment("tor3");
        assertTrue(foundMovie.size() == 2);
        assertSame(savedMovie.get(1),foundMovie.get(0));
        assertSame(savedMovie.get(3),foundMovie.get(1));
    }
    @Test
    void getAllByQueries(){
        List<Movie1> savedMovie = movieRepository.saveAll(List.of(
                movie0,movie1,movie2,movie3
        ));
        List<MyQuery> queryList = List.of(
                new MyQuery("name","movie0", SearchOperator.OR),
                new MyQuery("name","movie1", SearchOperator.OR),
                new MyQuery("yearOfRelease","2020", SearchOperator.AND)
        );
        List<Movie1> foundMovie = movieServiceJpql.searchMovies(queryList);
        System.out.println(foundMovie.toString());
        assertSame(foundMovie.get(0),savedMovie.get(0));
    }

}
