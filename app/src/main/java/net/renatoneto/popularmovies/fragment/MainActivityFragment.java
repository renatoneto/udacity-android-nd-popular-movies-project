package net.renatoneto.popularmovies.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import net.renatoneto.popularmovies.R;
import net.renatoneto.popularmovies.adapter.MovieAdapter;
import net.renatoneto.popularmovies.model.Movie;

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
}
