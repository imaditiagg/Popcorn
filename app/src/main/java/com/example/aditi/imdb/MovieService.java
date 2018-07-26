package com.example.aditi.imdb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("movie/{path}")
    Call<FetchedMovie> getMovies(@Path("path") String path,@Query("api_key") String api_key,@Query(("page")) int page);

    @GET("movie/{id}")
    Call<Movie> getDetails(@Path("id") int  id, @Query("api_key") String api_key);

    @GET("movie/{id}/similar")
    Call<FetchedMovie> getSimilarMovies(@Path("id") int id,@Query("api_key") String api_key,@Query(("page")) int page);

    @GET("movie/{id}/reviews")
    Call<FetchedReview> getReviews(@Path("id") int id, @Query("api_key") String api_key,@Query("page") int page);

    @GET("movie/{id}/credits")
    Call<FetchedCast> getCast(@Path("id") int id,@Query("api_key") String api_key);

    @GET("person/{id}")
    Call<Person> getPersonDetails(@Path("id") long id,@Query("api_key") String api_key);

    @GET("person/{id}/movie_credits")
    Call<MovieCastOfPerson> getMovieCastOfPerson(@Path("id") long id,@Query("api_key") String api_key);

    @GET("person/{id}/tv_credits")
    Call<TVCastOfPerson> getTVCastOfPerson(@Path("id") long id,@Query("api_key") String api_key);

    @GET("movie/{id}/videos")
    Call<FetchedVideo> getVideos(@Path("id") int id,@Query("api_key") String api_key);

    @GET("search/multi")
    Call<SearchResponse> search(@Query("api_key") String key, @Query("query") String query);

    @GET("tv/{path}")
    Call<FetchedTVshows> getShows(@Path("path") String path,@Query("api_key") String api_key,@Query(("page")) int page);



}
