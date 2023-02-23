package com.queries.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

    List<Movie> findAllByProducer(String producerName);

    List<Movie> findAllByCashierFeesBetween(long min, long max);

    List<Movie> findAllByMinAgeLessThan(int minAge);

    List<Movie> findAllByMinAgeBetween(int min, int max);

    @Query("select m from Movie m where m.name like %?1% or m.description like %?1% or m.producer like %?1%")
    List<Movie> findAllByTextFragment(String text);

}
