package com.example.android.android_project2;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/* AsyncTask class for making internet request */
public class NetworkTask extends AsyncTask<URL, Void, String> {

    /* class and member variables */
    private String results = null;
//    private MovieAdapter mMovieAdapter = new MovieAdapter();
    private MovieAdapter mMovieAdapter;

    /* Constructor */
    public NetworkTask() {
    }

    @Override
//    protected String doInBackground(Object... params) {
    protected String doInBackground(URL... params) {

        URL url1 = (URL) params[0];
//        mMovies = (List<Movie>) params[1];
//        mMovieAdapter = (MovieAdapter) params[2];

        /* go get data from server */
        results = NetworkUtil.goToWebsite(url1); // >> Strings[]
//        LogUtil.logStuff(results.toString());

        /* this results go to onPostExecute() */
        return results;
    }

    @Override
    protected void onPostExecute(String results) {

        List<Movie> movies1 = StringUtil.stringToJson(results);

//        if (mMovies.size() != 0) {
//            mMovies.clear();
//        }

//        mMovies.addAll(movies1);
        mMovieAdapter.setmMovies(movies1);

        /* SOURCE: http://androidadapternotifiydatasetchanged.blogspot.com/ */
//        mMovieAdapter.notifyDataSetChanged();

    }

} // class NetworkTask
