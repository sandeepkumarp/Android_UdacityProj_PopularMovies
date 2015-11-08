package com.creativespringbok.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    Movie[] movies = {
            new Movie("Movie_m0", R.drawable.sample_0, "Syn_0", "R0_m0", "05052015_m0"),
            new Movie("Movie_m1", R.drawable.sample_1, "Syn_1", "R1_m1", "05052015_m1"),
            new Movie("Movie_m2", R.drawable.sample_0, "Syn_2", "R5_m2", "05052015_m2"),
            new Movie("Movie_m3", R.drawable.sample_1, "Syn_3", "R4_m3", "05052015_m3"),
            new Movie("Movie_m4", R.drawable.sample_0, "Syn_4", "R5_m4", "05052015_m4"),
            new Movie("Movie_m5", R.drawable.sample_1, "Syn_5", "R2_m5", "05052015_m5"),
            new Movie("Movie_m6", R.drawable.sample_0, "Syn_6", "R3_m6", "05052015_m6"),
            new Movie("Movie_m7", R.drawable.sample_1, "Syn_7", "R4_m7", "05052015_m7"),

    };
    private MovieAdapter movieAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movieAdapter = new MovieAdapter(getActivity(), Arrays.asList(movies));

        GridView gridview = (GridView) rootView.findViewById(R.id.movies_grid);
        gridview.setAdapter(movieAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra("ITEM_TITLE", movies[i].itemTitle)
                        .putExtra("ITEM_POSTER", movies[i].itemPoster)
                        .putExtra("ITEM_RATING", movies[i].itemRating)
                        .putExtra("ITEM_RELEASE_DATE", movies[i].itemReleaseDate)
                        .putExtra("ITEM_SYNOPSIS", movies[i].itemSynopsis);
                startActivity(intent);
            }
        });
        return rootView;
    }


}
