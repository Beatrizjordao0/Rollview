package com.example.rollview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler; // Importação para o cronômetro
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2; // Importação do ViewPager

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ViewPager2 viewPagerShowcase;

    private MovieAdapter adapter;
    private ShowcaseAdapter showcaseAdapter;

    private List<Movie> movieList = new ArrayList<>();



    //  Carrossel
    private Handler sliderHandler = new Handler();
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            if (viewPagerShowcase.getAdapter() != null) {
                int nextItem = viewPagerShowcase.getCurrentItem() + 1;
                // Se chegou no último, volta pro primeiro
                if (nextItem >= viewPagerShowcase.getAdapter().getItemCount()) {
                    nextItem = 0;
                }
                viewPagerShowcase.setCurrentItem(nextItem, true);
            }
            // Repete essa ação a cada 3000 milissegundos (3 segundos)
            sliderHandler.postDelayed(this, 3000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // Lista Horizontal de Baixo
        recyclerView = findViewById(R.id.trendingMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new MovieAdapter(movieList);
        recyclerView.setAdapter(adapter);

        viewPagerShowcase = findViewById(R.id.viewPagerShowcase);
        showcaseAdapter = new ShowcaseAdapter(movieList);
        viewPagerShowcase.setAdapter(showcaseAdapter);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        // Chama a API
        fetchMoviesFromApi();



        // Lógica dos Botões do NavBar

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if(id == R.id.nav_home) {
                return true;
            } else if(id == R.id.nav_profile){
                Intent intent = new Intent(HomeActivity.this, PerfilActivity.class);
                startActivity(intent);
                finish();
                return true;
            } else if(id == R.id.nav_search){
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
            } else if(id == R.id.nav_list){
                Intent intent = new Intent(HomeActivity.this, FavoriteActivity.class);
                startActivity(intent);
                finish();
            }
            return true;
        });

    }

    private void fetchMoviesFromApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TmdbApi api = retrofit.create(TmdbApi.class);
        String apiKey = "290f87fc1ed22d42148dd9fef3dc8e7b";

        Call<MovieResponse> call = api.getPopularMovies(apiKey, "pt-BR");

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movieList.clear();
                    movieList.addAll(response.body().getResults());

                    adapter.notifyDataSetChanged();
                    showcaseAdapter.notifyDataSetChanged();

                    // Inicia o cronômetro para o slider começar a rodar
                    sliderHandler.postDelayed(sliderRunnable, 3000);
                } else {
                    Toast.makeText(HomeActivity.this, "Erro na API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!movieList.isEmpty()) {
            sliderHandler.postDelayed(sliderRunnable, 3000);
        }
    }
}