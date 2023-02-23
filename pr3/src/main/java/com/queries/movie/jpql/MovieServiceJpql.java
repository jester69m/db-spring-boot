package com.queries.movie.jpql;

import com.queries.movie.Movie;
import com.queries.movie.specificQuery.MyQuery;

import java.util.List;

public interface MovieServiceJpql {

    public List<Movie> getAllMovieByProducer(String producer);
    public List<Movie> getAllMovieByAllDirector(List<String> producers);
    public List<Movie> getAllMovieByAtLeastOneDirector(List<String> producers);
    public List<Movie> getAllMovieInCashierFeesDiapasonAndByGenres(long min, long max,List<String> genres);
    public List<Movie> getAllMovieByMinAge(int maxAge);
    public List<Movie> getAllMovieByMinAge(int minAge, int maxAge);
    public List<Movie> getAllMovieByTextFragment(String textFragment);

    public List<Movie> searchMovies(List<MyQuery> query);

}
