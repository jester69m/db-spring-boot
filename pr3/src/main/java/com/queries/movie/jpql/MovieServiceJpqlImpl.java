package com.queries.movie.jpql;

import com.queries.movie.Movie;
import com.queries.movie.Movie1;
import com.queries.movie.MovieRepository;
import com.queries.movie.MovieRepository1;
import com.queries.movie.specificQuery.MyQuery;
import com.queries.movie.specificQuery.SearchOperator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceJpqlImpl implements MovieServiceJpql{

    private final MovieRepository1 repository;
    @PersistenceContext
    EntityManager em;

    public MovieServiceJpqlImpl(MovieRepository1 repository){
        this.repository = repository;
    }

    @Override
    public List<Movie1> getAllMovieByProducer(String producer) {
        return repository.findAllByProducer(producer);
    }

    @Override
    public List<Movie1> getAllMovieByAllDirector(String directors) {
        return repository.findAllByGroupOfDirector(directors);
    }

    @Override
    public List<Movie1> getAllMovieByAtLeastOneDirector(List<String> directors) {
        List<Movie1> res = new ArrayList<>();
        for(String s : directors){
            for(Movie1 m1 : getAllMovieByAllDirector(s))
                if(!res.contains(m1))
                    res.add(m1);
        }
        return res;
    }

    @Override
    public List<Movie1> getAllMovieInCashierFeesDiapasonAndByGenres(long min, long max,String genres) {
        return repository.findAllByCashierFeesBetweenAndByGenres(min,max,genres);
    }

    @Override
    public List<Movie1> getAllMovieByMinAge(int maxAge) {
        return repository.findAllByMinAgeLessThan(maxAge);
    }

    @Override
    public List<Movie1> getAllMovieByMinAge(int minAge, int maxAge) {
        return repository.findAllByMinAgeBetween(minAge,maxAge);
    }

    @Override
    public List<Movie1> getAllMovieByTextFragment(String textFragment) {
        return repository.findAllByTextFragment(textFragment);
    }

    public List<Movie1> searchMovies(List<MyQuery> queries) {
        List<Movie1> allMovies = repository.findAll(); // отримати усі фільми
        List<Movie1> filteredMovies = new ArrayList<>(); // список фільмів, що відповідають критеріям пошуку

        for (Movie1 movie : allMovies) {
            boolean isMatch = true;

            for (MyQuery myQuery : queries) {
                String fieldName = myQuery.getFieldName();
                String value = myQuery.getValue();
                SearchOperator operator = myQuery.getOperator();

                if (fieldName.equals("name")) {
                    if (operator == SearchOperator.AND) {
                        isMatch = isMatch && movie.getName().equalsIgnoreCase(value);
                    } else if (operator == SearchOperator.OR) {
                        isMatch = isMatch || movie.getName().equalsIgnoreCase(value);
                    }
                } else if (fieldName.equals("yearOfRelease")) {
                    int year = Integer.parseInt(value);

                    if (operator == SearchOperator.AND) {
                        isMatch = isMatch && movie.getYearOfRelease() == year;
                    } else if (operator == SearchOperator.OR) {
                        isMatch = isMatch || movie.getYearOfRelease() == year;
                    }
                } else if (fieldName.equals("minAge")) {
                    int minAge = Integer.parseInt(value);

                    if (operator == SearchOperator.AND) {
                        isMatch = isMatch && movie.getMinAge() <= minAge;
                    } else if (operator == SearchOperator.OR) {
                        isMatch = isMatch || movie.getMinAge() <= minAge;
                    }
                } else if (fieldName.equals("cashierFees")) {
                    long cashierFees = Long.parseLong(value);

                    if (operator == SearchOperator.AND) {
                        isMatch = isMatch && movie.getYearOfRelease() == cashierFees;
                    } else if (operator == SearchOperator.OR) {
                        isMatch = isMatch || movie.getYearOfRelease() == cashierFees;
                    }
                } else if (fieldName.equals("producer")) {
                    if (operator == SearchOperator.AND) {
                        isMatch = isMatch && movie.getProducer().equalsIgnoreCase(value);
                    } else if (operator == SearchOperator.OR) {
                        isMatch = isMatch || movie.getProducer().equalsIgnoreCase(value);
                    }
                } else if (fieldName.equals("actors")) {
                    if (operator == SearchOperator.AND) {
                        isMatch = isMatch && movie.getActors().contains(value);
                    } else if (operator == SearchOperator.OR) {
                        isMatch = isMatch || movie.getActors().contains(value);
                    }
                } else if (fieldName.equals("directors")) {
                    if (operator == SearchOperator.AND) {
                        isMatch = isMatch && movie.getDirectors().contains(value);
                    } else if (operator == SearchOperator.OR) {
                        isMatch = isMatch || movie.getDirectors().contains(value);
                    }
                } else if (fieldName.equals("distributors")) {
                    if (operator == SearchOperator.AND) {
                        isMatch = isMatch && movie.getDistributor().contains(value);
                    } else if (operator == SearchOperator.OR) {
                        isMatch = isMatch || movie.getDistributor().contains(value);
                    }
                } else if (fieldName.equals("genres")) {
                    if (operator == SearchOperator.AND) {
                        isMatch = isMatch && movie.getGenres().contains(value);
                    } else if (operator == SearchOperator.OR) {
                        isMatch = isMatch || movie.getGenres().contains(value);
                    }
                }
            }
            if (isMatch) {
                filteredMovies.add(movie);
            }
        }
        return filteredMovies;
    }

}
