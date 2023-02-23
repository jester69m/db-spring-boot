package com.queries.movie.jpql;

import com.queries.movie.Movie;
import com.queries.movie.MovieRepository;
import com.queries.movie.specificQuery.MyQuery;
import com.queries.movie.specificQuery.SearchOperator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceJpqlImpl implements MovieServiceJpql{

    private final MovieRepository repository;

    public MovieServiceJpqlImpl(MovieRepository repository){
        this.repository = repository;
    }

    @Override
    public List<Movie> getAllMovieByProducer(String producer) {
        return repository.findAllByProducer(producer);
    }

    @Override
    public List<Movie> getAllMovieByAllDirector(List<String> directors) {
        List<Movie> result = new ArrayList<>();
        for(Movie m : repository.findAll()){
            if(m.getDirectors().containsAll(directors))
                result.add(m);
        }
        return result;
    }

    @Override
    public List<Movie> getAllMovieByAtLeastOneDirector(List<String> directors) {
        List<Movie> result = new ArrayList<>();
        for(Movie m : repository.findAll()){
            for(String s : directors){
                if(m.getDirectors().contains(s)){
                    result.add(m);
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public List<Movie> getAllMovieInCashierFeesDiapasonAndByGenres(long min, long max,List<String> genres) {
        List<Movie> result = new ArrayList<>();
        for(Movie m : repository.findAllByCashierFeesBetween(min,max)){
            if(m.getGenres().containsAll(genres))
                result.add(m);
        }
        return result;
    }

    @Override
    public List<Movie> getAllMovieByMinAge(int maxAge) {
        return repository.findAllByMinAgeLessThan(maxAge);
    }

    @Override
    public List<Movie> getAllMovieByMinAge(int minAge, int maxAge) {
        return repository.findAllByMinAgeBetween(minAge,maxAge);
    }

    @Override
    public List<Movie> getAllMovieByTextFragment(String textFragment) {
        List<Movie> result = repository.findAllByTextFragment(textFragment);
        for(Movie m : repository.findAll()){
            if(m.getGenres().stream().anyMatch(n -> n.matches("[\\w]*"+textFragment+"[\\w]*")))
                if(!result.contains(m))
                    result.add(m);
            if(m.getActors().stream().anyMatch(n -> n.matches("[\\w]*"+textFragment+"[\\w]*")))
                if(!result.contains(m))
                    result.add(m);
            if(m.getDirectors().stream().anyMatch(n -> n.matches("[\\w]*"+textFragment+"[\\w]*")))
                if(!result.contains(m))
                    result.add(m);
            if(m.getDistributor().stream().anyMatch(n -> n.matches("[\\w]*"+textFragment+"[\\w]*")))
                if(!result.contains(m))
                    result.add(m);
        }
        return repository.findAllByTextFragment(textFragment);
    }

    public List<Movie> searchMovies(List<MyQuery> queries) {
        List<Movie> allMovies = repository.findAll(); // отримати усі фільми
        List<Movie> filteredMovies = new ArrayList<>(); // список фільмів, що відповідають критеріям пошуку

        for (Movie movie : allMovies) {
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
