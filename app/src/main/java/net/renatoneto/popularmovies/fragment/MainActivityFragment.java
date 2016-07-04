package net.renatoneto.popularmovies.fragment;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private MovieAdapter mMovieAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Movie[] movies = {
                new Movie(
                        550,
                        "Fight Club",
                        "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
                        "/811DjJTon9gD6hZ8nCjSitaIXFQ.jpg"
                ),
                new Movie(
                        551,
                        "The Poseidon Adventure",
                        "The Poseidon Adventure was one of the first Catastrophe films and began the Disaster Film genre. Director Neame tells the story of a group of people that must fight for their lives aboard a sinking ship. Based on the novel by Paul Gallico.",
                        "/3Ypk0OLrECSp7tqQFLMppGBrHfo.jpg"
                )
        };

        mMovieAdapter = new MovieAdapter(getActivity(), Arrays.asList(movies));

        ListView listView = (ListView) rootView.findViewById(R.id.list_movies);
        listView.setAdapter(mMovieAdapter);

        return rootView;
    }

    public class DiscoverTask extends AsyncTask<String, Void, ArrayList> {

        private final String TAG = DiscoverTask.class.getSimpleName();

        @Override
        protected ArrayList doInBackground(String[] params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String discoveredJson = null;

            try {

                final String API_BASE_URL =
                        "https://api.themoviedb.org/3/discover/movie?";

                final String APP_ID_PARAM = "app_id";

                Uri builtUrl = Uri.parse(API_BASE_URL).buildUpon()
                        .appendQueryParameter(APP_ID_PARAM, "your_api_key_here")
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

                ArrayList movies = new ArrayList<Movie>();
                for(int i = 0; i < discoveredJson.length(); i++) {

                    Movie movie = parser.parse(i);
                    movies.add(i, movie);

                }

                return movies;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage(), e);
                return null;
            }

        }

        @Override
        protected void onPostExecute(ArrayList result) {

            /*if (result != null) {

                mMovieAdapter.clear();

                for (int i = 0; i < result.size(); i++) {
                    mMovieAdapter.add(result.get(i));
                }

            }*/

            super.onPostExecute(result);
        }

    }

}
