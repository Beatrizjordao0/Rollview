package com.example.rollview;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        recyclerView = findViewById(R.id.trendingMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapter = new MovieAdapter(movieList);
        recyclerView.setAdapter(adapter);

        // Chama a função que vai na internet buscar os dados
        fetchMoviesFromApi();
    }

    private void fetchMoviesFromApi() {
        // Configura o Retrofit (o motor que faz o download)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TmdbApi api = retrofit.create(TmdbApi.class);

        String apiKey = "a83c1caa03c2bc3241bc62845df9e242";

        // Prepara a chamada passando a chave e o idioma (Português)
        Call<MovieResponse> call = api.getPopularMovies(apiKey, "pt-BR");

        // Executa o download em segundo plano para não travar o app
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Limpa a lista vazia e adiciona os filmes que chegaram da internet!
                    movieList.clear();
                    movieList.addAll(response.body().getResults());

                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(HomeActivity.this, "Erro na API. Verifique a chave.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // Se não tiver internet ou der erro, mostra essa mensagem
                Toast.makeText(HomeActivity.this, "Sem conexão com a internet" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}