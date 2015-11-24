package com.creativespringbok.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Movie.MOVIE_BUNDLE_TAG)) {

            Movie movie = new Movie(intent.getBundleExtra(Movie.MOVIE_BUNDLE_TAG));

            String titleStr = movie.title;
            if (titleStr != null) {
                ((TextView) rootView.findViewById(R.id.movie_item_title)).setText(titleStr);
            }
            String posterStr = movie.poster_path;
            if (posterStr != null) {
                final String BASE_URL = "http://image.tmdb.org/t/p/";
                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        // TODO: 14-11-2015 make this size dynamic 
                        .appendPath("w342")                               //passed as input  "w92", "w154", "w185", "w342", "w500", "w780", or "original"
                        .appendEncodedPath(posterStr)
                        .build();

                Picasso.with(getContext()).load(builtUri.toString()).into((ImageView) rootView.findViewById(R.id.movie_item_poster));
//                ((ImageView) rootView.findViewById(R.id.movie_item_poster)).setImageResource(posterStr);
            }
            String ratingStr = movie.vote_average.toString();
            if (ratingStr != null) {
                ((TextView) rootView.findViewById(R.id.movie_item_rating)).setText(ratingStr.toString());
            }

            String rel_date_Str = movie.release_date.toString();
            if (rel_date_Str != null) {
                ((TextView) rootView.findViewById(R.id.movie_item_release_date)).setText(rel_date_Str);
            }
            String synopsisStr = movie.overview;
            if (synopsisStr != null) {
                ((TextView) rootView.findViewById(R.id.movie_item_synopsis)).setText(synopsisStr);
            }


        }

        return rootView;
    }
}
