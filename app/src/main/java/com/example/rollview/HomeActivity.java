package com.example.rollview;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(1, "Mamma Mia", R.drawable.mammamia));
        movieList.add(new Movie(2, "Spider-Man", R.drawable.spiderman));
        movieList.add(new Movie(3, "Harry Potter", R.drawable.harrypotter));
        movieList.add(new Movie(4, "Spider-man", R.drawable.spiderman));
        movieList.add(new Movie(5, "Harry potter", R.drawable.harrypotter));

        RecyclerView recyclerView = findViewById(R.id.trendingMovies);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        MovieAdapter adapter = new MovieAdapter(movieList);
        recyclerView.setAdapter(adapter);
    }
}