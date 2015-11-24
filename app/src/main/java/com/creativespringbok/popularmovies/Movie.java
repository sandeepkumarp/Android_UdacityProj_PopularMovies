package com.creativespringbok.popularmovies;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sandeep on 08-11-2015.
 */
public class Movie {
    public static final String MOVIE_BUNDLE_TAG = "com.creativespringbok.popularmovies.EXTRA_MOVIE";
    public static final String PARAM_ID = "id";
    public static final String PARAM_TITLE = "title";
    public static final String PARAM_OVERVIEW = "overview";
    public static final String PARAM_POSTER_PATH = "poster_path";
    public static final String PARAM_RELEASE_DATE = "release_date";
    public static final String PARAM_VOTE_AVERAGE = "vote_average";
    public static final String PARAM_VOTE_COUNT = "vote_count";

    public final Long id;
    public final String title;              //movie title
    public final String poster_path;        //poster image
    public final String overview;           //synopsis
    public final String release_date;       //release date
    public final Double vote_average;       //rating
    public final Long vote_count;           //votes count


    //constructor
    public Movie(Long id, String title, String poster_path, String overview, String release_date, Double vote_average, Long vote_count) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    public Movie(Bundle bundle) {
        this(
                bundle.getLong(PARAM_ID),
                bundle.getString(PARAM_TITLE),
                bundle.getString(PARAM_POSTER_PATH),
                bundle.getString(PARAM_OVERVIEW),
                bundle.getString(PARAM_RELEASE_DATE),
                bundle.getDouble(PARAM_VOTE_AVERAGE),
                bundle.getLong(PARAM_VOTE_COUNT)
        );
    }

    public static Movie objFromJsonData(JSONObject jsonObject) throws JSONException {
        //RETURNS DATA AS A MOVIE OBJECT FROM A INOUT JSON OBJECT
        return new Movie(
                jsonObject.getLong(PARAM_ID),
                jsonObject.getString(PARAM_TITLE),
                jsonObject.getString(PARAM_POSTER_PATH),
                jsonObject.getString(PARAM_OVERVIEW),
                jsonObject.getString(PARAM_RELEASE_DATE),
                jsonObject.getDouble(PARAM_VOTE_AVERAGE),
                jsonObject.getLong(PARAM_VOTE_COUNT)
        );
    }

    public Bundle convertToBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong(PARAM_ID, id);
        bundle.putString(PARAM_TITLE, title);
        bundle.putString(PARAM_POSTER_PATH, poster_path);
        bundle.putString(PARAM_OVERVIEW, overview);
        bundle.putString(PARAM_RELEASE_DATE, release_date);
        bundle.putDouble(PARAM_VOTE_AVERAGE, vote_average);
        bundle.putLong(PARAM_VOTE_COUNT, vote_count);
        return bundle;
    }

    @Override
    public String toString() {
        //return super.toString();
        String retString = " itemID: " + id.toString() + " itemTitle: " + title.toString() + " itemPoster: " + poster_path.toString() + " itemSynopsis: " + overview.toString() + "releaseDate: " + release_date.toString() + " itemRating: " + vote_average.toString() + " vote_count: " + vote_count + "\n";
        return retString;
    }

    public Uri buildCoverUri(String size) {
        final String BASE_URL = "http://image.tmdb.org/t/p/";
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(size)                               //passed as input  "w92", "w154", "w185", "w342", "w500", "w780", or "original"
                .appendEncodedPath(poster_path)
                .build();
        Log.v("buildCoverUri  : ", builtUri.toString());
        return builtUri;                                        //eg: http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
    }

}
