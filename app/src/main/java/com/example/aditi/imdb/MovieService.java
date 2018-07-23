package com.example.aditi.imdb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("{path}")
    Call<FetchedMovie> getMovies(@Path("path") String path,@Query("api_key") String api_key,@Query(("page")) int page);

    @GET("{id}")
    Call<Movie> getDetails(@Path("id") int  id, @Query("api_key") String api_key);

    @GET("{id}/similar")
    Call<FetchedMovie> getSimilarMovies(@Path("id") int id,@Query("api_key") String api_key,@Query(("page")) int page);

    @GET("{id}/reviews")
    Call<FetchedReview> getReviews(@Path("id") int id, @Query("api_key") String api_key,@Query("page") int page);

    @GET("{id}/credits")
    Call<FetchedCast> getCast(@Path("id") int id,@Query("api_key") String api_key);

    @GET("https://api.themoviedb.org/3/person/{id}")
    Call<Person> getPersonDetails(@Path("id") long id,@Query("api_key") String api_key);

    @GET("https://api.themoviedb.org/3/person/{id}/movie_credits")
    Call<MovieCastOfPerson> getMovieCastOfPerson(@Path("id") long id,@Query("api_key") String api_key);

    @GET("https://api.themoviedb.org/3/person/{id}/tv_credits")
    Call<TVCastOfPerson> getTVCastOfPerson(@Path("id") long id,@Query("api_key") String api_key);
}
