package com.example.rollview;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {
    private EditText etBusca;
    private androidx.cardview.widget.CardView btnBuscar;
    private RecyclerView recyclerSearch;

    private MovieAdapter movieAdapter;
    private List<Movie> listMovies = new ArrayList<>();

    private static final String API_KEY = "290f87fc1ed22d42148dd9fef3dc8e7b";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        etBusca = findViewById(R.id.etBusca);
        btnBuscar = findViewById(R.id.btnBuscar);
        recyclerSearch = findViewById(R.id.recyclerSearch);

        recyclerSearch.setLayoutManager(new GridLayoutManager(this, 2));
        movieAdapter = new MovieAdapter(listMovies);
        recyclerSearch.setAdapter(movieAdapter);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setSelectedItemId(R.id.nav_search);



        btnBuscar.setOnClickListener(v -> {
            String textoDigitado = etBusca.getText().toString().trim();

            if(!textoDigitado.isEmpty()) {
                searchMovieApi(textoDigitado);
            } else {
                Toast.makeText(SearchActivity.this, "Digite o nome de um Filme!", Toast.LENGTH_SHORT).show();
            }

            // Lógica NAVBAR


        });


    }
    private void searchMovieApi(String query) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TmdbApi api = retrofit.create(TmdbApi.class);
        api.searchMovies(API_KEY, "pt-BR", query).enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<Movie> results = response.body().getResults();
                    if(results.isEmpty()) {
                        Toast.makeText(SearchActivity.this, "Nenhum filme encontrado.", Toast.LENGTH_SHORT).show();

                    }
                    movieAdapter.atualizarLista(results);
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                    Toast.makeText(SearchActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
