package com.creativespringbok.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        if (intent != null) {
            Bundle extras = intent.getExtras();

            String titleStr = extras.getString("ITEM_TITLE");
            if (titleStr != null) {
                ((TextView) rootView.findViewById(R.id.movie_item_title)).setText(titleStr);
            }
            Integer posterStr = extras.getInt("ITEM_POSTER");
            if (posterStr != null) {
                ((ImageView) rootView.findViewById(R.id.movie_item_poster)).setImageResource(posterStr);
            }
            String ratingStr = extras.getString("ITEM_RATING");
            if (ratingStr != null) {
                ((TextView) rootView.findViewById(R.id.movie_item_rating)).setText(ratingStr);
            }
            String rel_date_Str = extras.getString("ITEM_RELEASE_DATE");
            if (rel_date_Str != null) {
                ((TextView) rootView.findViewById(R.id.movie_item_release_date)).setText(rel_date_Str);
            }
            String synopsisStr = extras.getString("ITEM_SYNOPSIS");
            if (synopsisStr != null) {
                ((TextView) rootView.findViewById(R.id.movie_item_synopsis)).setText(synopsisStr);
            }


        }

        return rootView;
    }
}
