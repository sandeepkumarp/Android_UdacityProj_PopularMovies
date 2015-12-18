package com.creativespringbok.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Sandeep on 08-11-2015.
 */
public class MovieAdapter extends BaseAdapter {

    public static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    private final ArrayList<Movie> mMovies;
    private Context mContext;

    public MovieAdapter(Context context) {
        mContext = context;
        mMovies = new ArrayList<>();
//        mMovies.add(new Movie(7410L,"TITLE1","http://image.tmdb.org/t/p/w154//oAISjx6DvR2yUn9dxj00vP8OcJJ.jpg","OVERVIEW1",10.5,55L));
    }

    @Override
    public int getCount() {

        return mMovies.size();
    }

    @Override
    public Movie getItem(int position) {
        if (position < 0 || position >= mMovies.size()) {
            Log.v(LOG_TAG, "Adapter Array List Empty");
            return null;
        }
        return mMovies.get(position);

    }

    @Override
    public long getItemId(int position) {
        Movie movie = getItem(position);
        if (movie == null) {
            Log.v(LOG_TAG, "NO MOVIE OBJECT FOUND");
            return -1L;
        }
        return movie.id;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        int height = size.y;

//        CHECK IF MOVIE OBJECT IS PASSED
        Movie movie = getItem(position);
        if (movie == null) {
            Log.v(LOG_TAG, "NO MOVIE OBJECT FOUND");
            return null;
        }
        ImageView imageView;
        if (convertView == null) {
//            IF NOT BEING RECYCLED, INITIALIZE
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT, GridView.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


        } else {
//            ELSE ASSIGN THE IMAGE VIEW
            imageView = (ImageView) convertView;
        }

//        ASSIGN RESOURCE TO IMAGE VIEW
        // TODO: 12-11-2015 replace hardcoded size with string.xml reference and make it dynamic according to screen size/device type.
        Uri builtUri = movie.buildCoverUri("w500");
        Picasso.with(mContext)
                .load(builtUri)
                .placeholder(R.drawable.sample_2)
                .into(imageView);
        imageView.setAdjustViewBounds(true);

//        Log.v("Picasso  : ", builtUri.toString());

        return imageView;

    }

    public void addAll(Collection<Movie> xs) {
        mMovies.addAll(xs);
        notifyDataSetChanged();
    }

    public void clear() {
        mMovies.clear();
        notifyDataSetChanged();
    }


}
