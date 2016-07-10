package net.renatoneto.popularmovies.parser;

import net.renatoneto.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DiscoverParser {

    protected JSONObject jsonObject;

    public DiscoverParser(String json) throws JSONException {
        jsonObject = new JSONObject(json);
    }

    public ArrayList<Movie> parse() throws JSONException {

        ArrayList movies = new ArrayList<Movie>();
        JSONArray result = jsonObject.getJSONArray("results");

        for (int i = 0; i < result.length(); i++) {

            Movie parsedMovie = new Movie(null);

            parsedMovie.setId(getId(i));
            parsedMovie.setOriginalTitle(getOriginalTitle(i));
            parsedMovie.setOverview(getOverview(i));
            parsedMovie.setPosterPath(getPosterPath(i));
            parsedMovie.setBackdropPath(getBackdropPath(i));
            parsedMovie.setVoteAverage(getVoteAverage(i));
            parsedMovie.setReleaseDate(getReleaseDate(i));

            movies.add(i, parsedMovie);

        }

        return movies;

    }

    public int getId(int position) throws JSONException {
        return getResult(position).getInt("id");
    }

    public String getOriginalTitle(int position) throws JSONException {
        return getResult(position).getString("original_title");
    }

    public String getOverview(int position) throws JSONException {
        return getResult(position).getString("overview");
    }

    public String getPosterPath(int position) throws JSONException {
        return getResult(position).getString("poster_path");
    }

    public String getBackdropPath(int position) throws JSONException {
        return getResult(position).getString("backdrop_path");
    }

    public double getVoteAverage(int position) throws JSONException {
        return getResult(position).getDouble("vote_average");
    }

    public String getReleaseDate(int position) throws JSONException {
        return getResult(position).getString("release_date");
    }

    protected JSONObject getResult(int position) throws JSONException {
        return jsonObject.getJSONArray("results").getJSONObject(position);
    }

}
