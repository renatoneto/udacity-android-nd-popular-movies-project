package net.renatoneto.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    protected int id;
    protected String originalTitle;
    protected String overview;
    protected String posterPath;
    protected String backdropPath;
    protected double voteAverage;
    protected String releaseDate;

    public Movie(Parcel in) {

        if (in != null) {

            setId(in.readInt());
            setOriginalTitle(in.readString());
            setOverview(in.readString());
            setPosterPath(in.readString());
            setVoteAverage(in.readDouble());
            setReleaseDate(in.readString());
            setBackdropPath(in.readString());

        }

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalTitle() {
        return this.originalTitle;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOverview() {
        return this.overview;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getVoteAverage() {
        return this.voteAverage;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterPath(String format) {
        return getPath(this.posterPath, format);
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getBackdropPath(String format) {
        return getPath(this.backdropPath, format);
    }

    protected String getPath(String path, String format) {

        if (path == null || path.equals("null")) {
            return "";
        }

        if (format == null) {
            format = "w185";
        }

        return "http://image.tmdb.org/t/p/" + format + path;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.originalTitle);
        dest.writeString(this.overview);
        dest.writeString(this.posterPath);
        dest.writeDouble(this.voteAverage);
        dest.writeString(this.releaseDate);
        dest.writeString(this.backdropPath);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }

    };

}
