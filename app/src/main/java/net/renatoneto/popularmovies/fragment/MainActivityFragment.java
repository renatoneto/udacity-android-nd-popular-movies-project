package net.renatoneto.popularmovies.fragment;

import android.app.ProgressDialog;
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
import android.widget.GridView;

import net.renatoneto.popularmovies.R;
import net.renatoneto.popularmovies.adapter.MovieAdapter;
import net.renatoneto.popularmovies.model.Movie;
import net.renatoneto.popularmovies.parser.DiscoverParser;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private MovieAdapter mMovieAdapter;

    private ProgressDialog mProgressDialog;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mMovieAdapter = new MovieAdapter(getActivity(), new ArrayList<Movie>());

        GridView gridView = (GridView) rootView.findViewById(R.id.grid_movies);
        gridView.setAdapter(mMovieAdapter);

        discoverMovies();

        return rootView;
    }

    protected void discoverMovies() {

        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());

        String order = preferences.getString(
                (String) getText(R.string.pref_movies_order_key),
                (String) getText(R.string.pref_movies_order_default)
        );

        DiscoverTask discoverTask = new DiscoverTask();
        discoverTask.execute(order);

    }

    public class DiscoverTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        private final String TAG = DiscoverTask.class.getSimpleName();

        @Override
        protected void onPreExecute() {

            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(getContext());
                mProgressDialog.setTitle("Loading");
                mProgressDialog.setMessage("Loading movies...");
            }

            mProgressDialog.show();

        }

        @Override
        protected ArrayList<Movie> doInBackground(String[] params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String discoveredJson = null;

            try {

                final String API_BASE_URL =
                        "https://api.themoviedb.org/3/discover/movie?";

                final String API_KEY_PARAM = "api_key";
                final String SORT_BY_PARAM = "sort_by";

                Uri builtUrl = Uri.parse(API_BASE_URL).buildUpon()
                        .appendQueryParameter(API_KEY_PARAM, "")
                        .appendQueryParameter(SORT_BY_PARAM, params[0])
                        .build();

                URL url = new URL(builtUrl.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
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
                    return null;
                }

                discoveredJson = buffer.toString();

            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }

            try {

                DiscoverParser parser = new DiscoverParser(discoveredJson);
                ArrayList<Movie> movies = parser.parse();

                return movies;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage(), e);
                return null;
            }

        }

        @Override
        protected void onPostExecute(ArrayList<Movie> result) {

            if (result != null) {

                mMovieAdapter.clear();

                for (int i = 0; i < result.size(); i++) {
                    mMovieAdapter.add(result.get(i));
                }

            }

            mProgressDialog.dismiss();

            super.onPostExecute(result);
        }

    }

}
