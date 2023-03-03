package com.queries.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository1 extends JpaRepository<Movie1,Long> {

    @Query("select m from Movie1 m where m.producer = ?1 ")
    List<Movie1> findAllByProducer(String producerName);

    @Query("select m from Movie1 m where m.directors like %?1%")
    List<Movie1> findAllByGroupOfDirector(String directorsName);

    @Query("select m from Movie1 m where m.directors like %?1%")
    List<Movie1> findAllByOneFromGroupOfDirector(String directorName);

    @Query("select m from Movie1 m where m.cashierFees >= ?1 and m.cashierFees <= ?2 and m.genres like %?3%")
    List<Movie1> findAllByCashierFeesBetweenAndByGenres(long min, long max,String genres);

    @Query("select m from Movie1 m where m.minAge < ?1")
    List<Movie1> findAllByMinAgeLessThan(int minAge);

    @Query("select m from Movie1 m where m.minAge > ?1 AND m.minAge < ?2 ")
    List<Movie1> findAllByMinAgeBetween(int min, int max);

    @Query("select m from Movie1 m where m.name like %?1% or m.description like %?1% or m.producer like %?1% or m.directors like %?1% or m.genres like %?1% or m.distributor like %?1% or m.actors like %?1%")
    List<Movie1> findAllByTextFragment(String text);

}
