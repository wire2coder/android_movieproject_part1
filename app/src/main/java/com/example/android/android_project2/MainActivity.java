/*
 * Terry S Android Nano Degree project 2
 */

package com.example.android.android_project2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import static com.example.android.android_project2.LogUtil.logStuff;


public class MainActivity extends AppCompatActivity {

    String TAG = MainActivity.this.getClass().getSimpleName();

    private static String BASE_URL_POPULAR = "https://api.themoviedb.org/3/movie/popular";
    private static String BASE_URL_POPULAR_HIGHEST_RATE = "https://api.themoviedb.org/3/movie/top_rated";

    /* Don't forget to initialize with new ArrayList<data type>(); */
    private List<Movie> mMovies = new ArrayList<Movie>();
    private MovieAdapter mMovieAdapter;
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridView = (GridView) findViewById(R.id.gridview1);

        /* 'attach' click listener to the GridView */
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            /* https://stackoverflow.com/questions/13927601/how-to-show-toast-in-a-class-extended-by-baseadapter-get-view-method */
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                Movie movie1 = mMovies.get(position);

                /* starting another activity */
                Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);

                detailIntent.putExtra("position_of_the_view", String.valueOf(position));

                detailIntent.putExtra("title", movie1.getTitle());
                detailIntent.putExtra("release_date", movie1.getRelease_date());
                detailIntent.putExtra("poster_path", movie1.getPoster_path());
                detailIntent.putExtra("vote_average", movie1.getVote_average());
                detailIntent.putExtra("overview", movie1.getOverview());

                /* start DetailActivity */
                MainActivity.this.startActivity(detailIntent);

                /* notifyDataset() make the gridView redraw itself
                 * and gridView call getView() again */
                mMovieAdapter.notifyDataSetChanged();

            }

        }); // setOnItemClick


        /* make a new MovieAdapter */
        mMovieAdapter = new MovieAdapter(MainActivity.this, mMovies);

        /* set data source */
        mGridView.setAdapter(mMovieAdapter);

        /* make a URL */
        URL url = NetworkUtil.makeUrl(BASE_URL_POPULAR);

        /* run the AsyncTask */
        runNetworkTask(url);


    } // onCreate

    /* helper: for running AsyncTask */
    private void runNetworkTask(URL url) {

        NetworkTask networkTask = new NetworkTask();
        networkTask.execute(url);

    }

    /* AsyncTask class for making internet request */
    public class NetworkTask extends AsyncTask<URL, Void, String> {

        private String results = null;

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


    /* MENU logic stuff */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.mi_most_popular:

                Toast.makeText(MainActivity.this, "mi_most_popular", Toast.LENGTH_SHORT).show();

                URL url_most_popular = NetworkUtil.makeUrl(BASE_URL_POPULAR);
                NetworkTask task = new NetworkTask();
                task.execute(url_most_popular);

                return true; // clickEvent data is 'consumed'

            case R.id.mi_highest_rate:

                Toast.makeText(MainActivity.this, "mi_top_rated", Toast.LENGTH_SHORT).show();

                URL url_toprated = NetworkUtil.makeUrl(BASE_URL_POPULAR_HIGHEST_RATE);
                NetworkTask task2 = new NetworkTask();
                task2.execute(url_toprated);

                return true; // clickEvent data is 'consumed'

            default:
                return super.onOptionsItemSelected(item);
        }

    }

} // class MainActivity
