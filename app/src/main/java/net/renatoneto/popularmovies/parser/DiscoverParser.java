package net.renatoneto.popularmovies.parser;

import net.renatoneto.popularmovies.model.Movie;

import org.json.JSONException;
import org.json.JSONObject;

public class DiscoverParser {

    protected JSONObject jsonObject;

    public DiscoverParser(String json) throws JSONException {
        jsonObject = new JSONObject(json);
    }

    public Movie parse(int position) throws JSONException {

        Movie parsedMovie = new Movie(
            getId(position),
            getOriginalTitle(position),
            getOverview(position),
            getPosterPath(position)
        );

        return parsedMovie;

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
        return jsonObject.getJSONArray("result").getJSONObject(position);
    }

}
