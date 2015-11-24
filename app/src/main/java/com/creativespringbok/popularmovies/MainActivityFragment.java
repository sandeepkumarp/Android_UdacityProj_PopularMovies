package com.creativespringbok.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    private String LOADING_MESSAGE = "Downloading Data";
    private String LOADING_ERROR_MESSAGE = "Error! No Internet Connection!";


    //    ArrayList<Movie> fetchedMovies = new ArrayList<Movie>(Arrays.asList(movies));
//    ArrayList<Movie> fetchedMovies = new ArrayList<Movie>();

    private MovieAdapter mMovieAdapter;
    private GridView mGridView;

    private boolean mIsPageLoading = false;
    private int mPageNosLoaded = 0;
    private TextView mLoading;

    public MainActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mMovieAdapter = new MovieAdapter(getActivity());
        mLoading = (TextView) rootView.findViewById(R.id.loading);
        initializeGrid(rootView);
        startLoading();
        return rootView;
    }

    @Override
    public void onResume() {


        super.onResume();
        mMovieAdapter.clear();
        mPageNosLoaded = 0;
        startLoading();

    }

    private void initializeGrid(View rootView) {
        mGridView = (GridView) rootView.findViewById(R.id.movies_grid);
        if (mGridView == null) {
            Log.v(LOG_TAG, "Grid View NOT FOUND!! ");
            return;
        }
        mGridView.setAdapter(mMovieAdapter);

        Log.v("STATUS REP : ", "ADAPTER WAS SET TO GRID VIEW !");


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie) (parent.getAdapter()).getItem(position);
                if (movie == null) {
                    Log.v(LOG_TAG, "CANNOT FIND MOVIE OBJECT ON ITEM CLICK EVENT");
                    return;
                }

                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Movie.MOVIE_BUNDLE_TAG, movie.convertToBundle());  //PASSING THE METHOD PROPERTIES AS BUNDLE TO THE DETAIL ACTIVITY
                startActivity(intent);
            }
        });

        mGridView.setOnScrollListener(
                new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {

                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        int lastInScreen = firstVisibleItem + visibleItemCount;
                        if (lastInScreen == totalItemCount) {
                            startLoading();
                        }
                    }
                }

        );
    }

    private void startLoading() {
        if (mIsPageLoading) {
            return;
        }

        mIsPageLoading = true;
        if (mLoading != null) {
            mLoading.setText(LOADING_MESSAGE);
            mLoading.setVisibility((View.VISIBLE));
        }

        new FetchMovieTask().execute(mPageNosLoaded + 1);
    }

    private void errorLoading() {
        if (mLoading != null) {
//            mLoading.setText(LOADING_ERROR_MESSAGE);
            mLoading.setVisibility((View.VISIBLE));
        }
    }

    private void stopLoading() {
        if (!mIsPageLoading) {
            return;
        }

        mIsPageLoading = false;
        if (mLoading != null) {

            mLoading.setVisibility(View.GONE);
        }
    }

    public class FetchMovieTask extends AsyncTask<Integer, Void, Collection<Movie>> {
        private final String LOG_TAG = this.getClass().getSimpleName();

        @Override
        protected Collection<Movie> doInBackground(Integer... params) {
            Log.v("STATUS REP : " + LOG_TAG + " : ", "ASYNC TASK STARTED : doing in background now !");
            if (params.length == 0) {
                Log.v("STATUS REP : " + LOG_TAG + " : ", "ZERO PARAMS IN ASYNC TASK");
                return null;
            }

            int pageNo = params[0];

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            SharedPreferences sharedPref;
            sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());


            // Will contain the raw JSON response as a string.
            int numEntries = 40;

            String movieJsonStr = null;
            String orderByVal = sharedPref.getString("sort_order", "desc");
            String sortByVal = sharedPref.getString("sort_by", "popularity");
            String apiKeyVal = "api_key_here";

            try {
                final String TMDb_BASE_URI = "http://api.themoviedb.org/3/discover/movie";
//                final String TMDb_BASE_URI = "http://private-anon-e737b691b-themoviedb.apiary-mock.com/3/discover/movie";
                final String SORT_BY_PARAM = "sort_by";
                final String PAGE_PARAM = "page";
                final String API_KEY_PARAM = "api_key";

                Uri builtUri = Uri.parse(TMDb_BASE_URI).buildUpon()
                        .appendQueryParameter(PAGE_PARAM, String.valueOf(pageNo))
                        .appendQueryParameter(SORT_BY_PARAM, sortByVal + "." + orderByVal)
                        .appendQueryParameter(API_KEY_PARAM, apiKeyVal)
                        .build();

                URL url = new URL(builtUri.toString());
                Log.v("STATUS REP : " + LOG_TAG + " : ", "FETCHING URL : " + builtUri.toString());


                //Create request to TMDb and open connection

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    movieJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    movieJsonStr = null;
                }
                movieJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the  data, there's no point in attempting
                // to parse it.
                Log.v(LOG_TAG, "NO DATA WAS FETCHED");
                e.printStackTrace();
                movieJsonStr = null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                        e.printStackTrace();
                    }
                }
            }
            try {
                return getMovieDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
                errorLoading();
            }
            return null;
        }

        private Collection<Movie> getMovieDataFromJson(String movieJsonStr)
                throws JSONException, NullPointerException {
            Log.v("STATUS REP : " + LOG_TAG + " : ", "getMoviedataFromJson called !!");

            JSONObject movieJsonObj = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJsonObj.getJSONArray("results");
            ArrayList arrayList = new ArrayList<>();


            for (int j = 0; j < movieArray.length(); j++) {
//                Log.v("STATUS REP : " + LOG_TAG + " : ", "ENTERED JSON PARSING LOOP");
                arrayList.add(Movie.objFromJsonData(movieArray.getJSONObject(j)));

                Log.v(LOG_TAG + " new obj added : ", (Movie.objFromJsonData(movieArray.getJSONObject(j)).toString()));
            }

            return arrayList;
        }

        @Override
        protected void onPostExecute(Collection<Movie> movies) {
//            super.onPostExecute(movies);
            if (movies == null) {
                Toast.makeText(getActivity(), LOADING_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
                stopLoading();
                return;
            }
            mPageNosLoaded++;
            stopLoading();
            mMovieAdapter.addAll(movies);


        }


    }
}