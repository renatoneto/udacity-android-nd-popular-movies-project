package net.renatoneto.popularmovies.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.renatoneto.popularmovies.R;
import net.renatoneto.popularmovies.model.Movie;
import net.renatoneto.popularmovies.picasso.CircleTransform;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private static final String TAG = MovieAdapter.class.getSimpleName();

    public MovieAdapter(Activity context, List<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_movie, parent, false);
        }

        ImageView posterPathView = (ImageView) convertView.findViewById(R.id.image_movie_list);
        String posterPath        = movie.getPosterPath(null);

        Log.v(TAG, posterPath);

        if (posterPath != null && posterPath.length() > 0) {
            Picasso.with(getContext())
                    .load(posterPath)
                    .resize(150, 150)
                    .centerCrop()
                    .transform(new CircleTransform())
                    .into(posterPathView);
        }

        TextView originalTitleView = (TextView) convertView.findViewById(R.id.text_movie_list);
        originalTitleView.setText(movie.getOriginalTitle());

        return convertView;
    }

}
