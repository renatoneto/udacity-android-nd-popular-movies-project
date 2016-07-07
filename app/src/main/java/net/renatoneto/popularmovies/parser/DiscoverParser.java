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

            Movie parsedMovie = new Movie(
                    getId(i),
                    getOriginalTitle(i),
                    getOverview(i),
                    getPosterPath(i)
            );

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

    protected JSONObject getResult(int position) throws JSONException {
        return jsonObject.getJSONArray("results").getJSONObject(position);
    }

}
