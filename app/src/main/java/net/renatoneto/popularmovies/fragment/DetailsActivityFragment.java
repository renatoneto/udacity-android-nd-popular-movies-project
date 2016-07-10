package net.renatoneto.popularmovies.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.renatoneto.popularmovies.R;
import net.renatoneto.popularmovies.model.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {

    final String TAG = DetailsActivityFragment.class.getSimpleName();

    private TextView mTitleView;
    private TextView mOverviewView;
    private TextView mReleaseDateView;
    private ImageView mBackgropPathView;
    private RatingBar mRatingBarView;

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        Intent intent = getActivity().getIntent();

        if (intent != null) {

            Movie movie = (Movie) intent.getParcelableExtra("movie");

            mTitleView = (TextView) rootView.findViewById(R.id.movie_details_title);
            mOverviewView = (TextView) rootView.findViewById(R.id.movie_details_overview);
            mReleaseDateView = (TextView) rootView.findViewById(R.id.movie_details_release_date);
            mBackgropPathView = (ImageView) rootView.findViewById(R.id.movie_details_poster_path);
            mRatingBarView = (RatingBar) rootView.findViewById(R.id.movie_details_rating_bar);

            String backgropPath = movie.getBackdropPath("w500");

            Log.v(TAG, backgropPath);

            if (backgropPath != null && backgropPath.length() > 0) {
                Picasso.with(getContext())
                        .load(backgropPath)
                        .placeholder(R.drawable.progress_animation)
                        .into(mBackgropPathView, new Callback() {

                            @Override
                            public void onSuccess() {
                                mBackgropPathView.setVisibility(View.GONE);
                                mBackgropPathView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                mBackgropPathView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError() {

                            }

                        });
            }

            mTitleView.setText(movie.getOriginalTitle());
            mOverviewView.setText(movie.getOverview());
            mReleaseDateView.setText(movie.getReleaseDate());
            mRatingBarView.setRating((float) movie.getVoteAverage());

        }

        return rootView;
    }
}
