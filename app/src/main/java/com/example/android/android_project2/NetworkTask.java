package com.example.android.android_project2;

import android.os.AsyncTask;
import android.widget.GridView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/* AsyncTask class for making internet request */
public class NetworkTask extends AsyncTask<URL, Void, String> {

    /* class and member variables */
    private String results = null;

    private List<Movie> mMovies = new ArrayList<Movie>();
    private GridView mGridView;
    private MovieAdapter mMovieAdapter;


    @Override
    protected String doInBackground(URL... params) {

        URL url1 = params[0];

        results = NetworkUtil.goToWebsite(url1);

        /* this results go to onPostExecute() */
        return results;
    }

    @Override
    protected void onPostExecute(String results) {

        List<Movie> movies1 = StringUtil.stringToJson(results);

        if (mMovies.size() != 0) {
            mMovies.clear();
        }

        mMovies.addAll(movies1);
        /* SOURCE: http://androidadapternotifiydatasetchanged.blogspot.com/ */
        mMovieAdapter.notifyDataSetChanged();
    }

} // class NetworkTask
