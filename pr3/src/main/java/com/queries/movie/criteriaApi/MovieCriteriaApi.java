package com.queries.movie.criteriaApi;

import com.queries.movie.Movie;

import java.util.List;

public interface MovieCriteriaApi {

    public List<Movie> getAllByProducer(String producer);
    public List<Movie> getAllByGroupOfDirector(List<String> directors);
    public List<Movie> getAllByOneGroupOfDirector(List<String> directors);
    public List<Movie> getAllByCashierFeesWithGivenGenres(long min, long max, List<String> genres);
    public List<Movie> getAllByMinAgeSmaller(int minAge);
    public List<Movie> getAllByMinAgeBetween(int min,int max);
    public List<Movie> getAllByText(String text);
}
