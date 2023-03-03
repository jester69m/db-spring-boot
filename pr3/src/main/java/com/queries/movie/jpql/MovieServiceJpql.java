package com.queries.movie.jpql;

import com.queries.movie.Movie1;
import com.queries.movie.specificQuery.MyQuery;

import java.util.List;

public interface MovieServiceJpql {

    public List<Movie1> getAllMovieByProducer(String producer);
    public List<Movie1> getAllMovieByAllDirector(String producers);
    public List<Movie1> getAllMovieByAtLeastOneDirector(List<String> producers);
    public List<Movie1> getAllMovieInCashierFeesDiapasonAndByGenres(long min, long max,String genres);
    public List<Movie1> getAllMovieByMinAge(int maxAge);
    public List<Movie1> getAllMovieByMinAge(int minAge, int maxAge);
    public List<Movie1> getAllMovieByTextFragment(String textFragment);
    public List<Movie1> searchMovies(List<MyQuery> query);

}
