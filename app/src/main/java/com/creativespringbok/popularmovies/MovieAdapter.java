package com.creativespringbok.popularmovies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Sandeep on 08-11-2015.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    public static final String LOG_CAT = MovieAdapter.class.getSimpleName();

    //custom constructor
    public MovieAdapter(Activity context, List<Movie> movies) {
        super(context, 0, movies);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get movie object from adapter
        Movie movie = getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false
            );
        }

        ImageView posterView = (ImageView) convertView.findViewById(R.id.movie_poster_view);
        posterView.setImageResource(movie.itemPoster);

        return convertView;
    }
}
