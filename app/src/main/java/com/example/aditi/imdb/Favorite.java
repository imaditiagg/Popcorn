package com.example.aditi.imdb;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

public class Favorite {


    public static void addMovieToFav(Context context,Movie movie) {
      MovieDatabase  database = Room.databaseBuilder(context, MovieDatabase.class, "movies_db").allowMainThreadQueries().build();
      MovieDao  movieDao = database.getMovieDao();
      movieDao.addMovie(movie);

    }

    public static boolean isMovieFav(Context context,int movieId){

        MovieDatabase  database = Room.databaseBuilder(context, MovieDatabase.class, "movies_db").allowMainThreadQueries().build();
        MovieDao  movieDao = database.getMovieDao();
        Movie movie= movieDao.search(movieId);
        if(movie==null){
            return false;
        }

        else {
            return true;
        }
    }

    public static void removeMovieFromFav(Context context,int id){
        MovieDatabase  database = Room.databaseBuilder(context, MovieDatabase.class, "movies_db").allowMainThreadQueries().build();
        MovieDao  movieDao = database.getMovieDao();
        Log.i("Remove movie","fn called");
        movieDao.deleteMovie(id);
    }
}
