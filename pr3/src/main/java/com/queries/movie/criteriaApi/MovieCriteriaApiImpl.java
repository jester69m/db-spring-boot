package com.queries.movie.criteriaApi;

import com.queries.movie.Movie;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieCriteriaApiImpl {

    @PersistenceContext
    EntityManager em;

    public MovieCriteriaApiImpl() {
    }

    public List<Movie> getAllByProducer(String producer){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);

        Root<Movie> movie = cq.from(Movie.class);
        cq.where(cb.equal(movie.get("producer"),producer));

        TypedQuery<Movie> query = em.createQuery(cq);
        return query.getResultList();
    }

    public List<Movie> getAllByGroupOfDirector(List<String> directors){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> root = cq.from(Movie.class);

        List<Predicate> directorPredicates = new ArrayList<>();
        for (String director : directors) {
            directorPredicates.add(cb.isMember(director, root.get("directors")));
        }

        Predicate directorsPredicate = cb.and(directorPredicates.toArray(new Predicate[directorPredicates.size()]));

        cq.where(directorsPredicate);

        TypedQuery<Movie> query = em.createQuery(cq);
        return query.getResultList();
    }

    public List<Movie> getAllByOneGroupOfDirector(List<String> directors){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> root = cq.from(Movie.class);

        Subquery<String> subquery = cq.subquery(String.class);
        Root<Movie> subRoot = subquery.correlate(root);
        Join<Movie, String> directorJoin = subRoot.join("directors");
        subquery.select(directorJoin);
        subquery.where(cb.isTrue(directorJoin.in(directors)));
        cq.where(cb.exists(subquery));

        TypedQuery<Movie> query = em.createQuery(cq);
        return query.getResultList();
    }

    public List<Movie> getAllByCashierFeesWithGivenGenres(long min, long max, List<String> genres){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);

        Root<Movie> movie = cq.from(Movie.class);
        Predicate cashierFeesLimPredicate = cb.between(movie.get("cashierFees"), min,max);
        List<Predicate> genresPredicates = new ArrayList<>();
        for (String genre : genres) {
            genresPredicates.add(cb.isMember(genre, movie.get("genres")));
        }
        Predicate genresPredicate = cb.and(genresPredicates.toArray(new Predicate[genresPredicates.size()]));
        cq.where(cashierFeesLimPredicate, genresPredicate);

        TypedQuery<Movie> query = em.createQuery(cq);
        return query.getResultList();
    }
    public List<Movie> getAllByMinAgeSmaller(int minAge){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);

        Root<Movie> movie = cq.from(Movie.class);
        cq.select(movie).where(cb.lt(movie.get("minAge"), minAge));

        TypedQuery<Movie> query = em.createQuery(cq);
        return query.getResultList();
    }
    public List<Movie> getAllByMinAgeBetween(int min,int max){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);

        Root<Movie> movie = cq.from(Movie.class);
        cq.select(movie).where(cb.between(movie.get("minAge"), min,max));

        TypedQuery<Movie> query = em.createQuery(cq);
        return query.getResultList();
    }


    public List<Movie> getAllByText(String text) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);

        Root<Movie> movie = cq.from(Movie.class);
        List<Predicate> predicates = new ArrayList<>();

        if(text != null){
            predicates.add(cb.like(movie.get("name"), "%" + text + "%"));
            predicates.add(cb.like(movie.get("description"), "%" + text + "%"));
            predicates.add(cb.like(movie.get("producer"), "%" + text + "%"));
//            predicates[3] = (cb.like(movie.join("directors"), "%" + text + "%"));
//            predicates[4] = (cb.like(movie.join("actors"), "%" + text + "%"));
//            predicates[5] = (cb.like(movie.join("distributors"), "%" + text + "%"));
//            predicates[6] = (cb.like(movie.join("genres"), "%" + text + "%"));
        }
        cq.select(movie).where(cb.or(predicates.toArray(predicates.toArray(new Predicate[0]))));

        return em.createQuery(cq).getResultList();
    }

}
