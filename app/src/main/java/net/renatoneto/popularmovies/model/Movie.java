package net.renatoneto.popularmovies.model;

public class Movie {

    protected int id;
    protected String originalTitle;
    protected String overview;
    protected String posterPath;

    public Movie(int id, String originalTitle, String overview, String posterPath) {

        this.id            = id;
        this.originalTitle = originalTitle;
        this.overview      = overview;
        this.posterPath    = posterPath;

    }

    public int getId() {
        return this.id;
    }

    public String getOriginalTitle() {
        return this.originalTitle;
    }

    public String getOverview() {
        return this.overview;
    }

    public String getPosterPath(String format) {

        if (format == null) {
            format = "w185";
        }

        return "http://image.tmdb.org/t/p/" + format + this.posterPath;
    }

}
