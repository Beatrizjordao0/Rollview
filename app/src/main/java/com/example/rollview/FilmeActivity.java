package com.example.rollview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FilmeActivity extends AppCompatActivity {

    private static final String API_KEY = "290f87fc1ed22d42148dd9fef3dc8e7b";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

    ImageView imgPoster, imgBackground;
    ImageButton btnBack;
    LinearLayout btnAvaliar;

    TextView txtTitulo, txtInfo, txtDuracao, txtNota, txtSinopse;
    RatingBar ratingBar;

    String posterUrl = "";

    public void abrirTelaAvaliacao(View view) {
        Intent intent = new Intent(FilmeActivity.this, AvaliacaoFilmeActivity.class);

        intent.putExtra("titulo", txtTitulo.getText().toString());
        intent.putExtra("ano", txtInfo.getText().toString());
        intent.putExtra("poster", posterUrl);

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme);

        imgPoster = findViewById(R.id.imgPoster);
        imgBackground = findViewById(R.id.imgBackground);
        btnBack = findViewById(R.id.btnBack);
        btnAvaliar = findViewById(R.id.btnAvaliar);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtInfo = findViewById(R.id.txtInfo);
        txtDuracao = findViewById(R.id.txtDuracao);
        txtNota = findViewById(R.id.txtNota);
        txtSinopse = findViewById(R.id.txtSinopse);
        ratingBar = findViewById(R.id.ratingBar);

        btnBack.setOnClickListener(v -> finish());

        btnAvaliar.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            FilmeActivity.this,
                            AvaliacaoFilmeActivity.class
                    );

            intent.putExtra(
                    "titulo",
                    txtTitulo.getText().toString()
            );

            intent.putExtra(
                    "ano",
                    txtInfo.getText().toString()
            );

            intent.putExtra(
                    "poster",
                    posterUrl
            );

            startActivity(intent);

        });

        int movieId = getIntent().getIntExtra("movie_id", 269149);
        carregarFilme(movieId);
    }

    private void carregarFilme(int movieId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TmdbApi api = retrofit.create(TmdbApi.class);

        api.getMovieDetails(movieId, API_KEY, "pt-BR").enqueue(new Callback<TMDBMovieResponse>() {
            @Override
            public void onResponse(Call<TMDBMovieResponse> call, Response<TMDBMovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    preencherTela(response.body());
                } else {
                    Toast.makeText(FilmeActivity.this, "Erro ao carregar filme", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TMDBMovieResponse> call, Throwable t) {
                Toast.makeText(FilmeActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void preencherTela(TMDBMovieResponse filme) {
        txtTitulo.setText(filme.getTitle());

        String ano = "";
        if (filme.getReleaseDate() != null && filme.getReleaseDate().length() >= 4) {
            ano = filme.getReleaseDate().substring(0, 4);
        }

        txtInfo.setText(ano);
        txtDuracao.setText(filme.getRuntime() + " mins");
        txtNota.setText(String.format(Locale.US, "%.1f", filme.getVoteAverage()));
        txtSinopse.setText(filme.getOverview());

        ratingBar.setRating((float) filme.getVoteAverage() / 2);

        if (filme.getPosterPath() != null) {
            posterUrl = IMAGE_BASE_URL + filme.getPosterPath();

            Glide.with(this)
                    .load(posterUrl)
                    .into(imgPoster);
        }

        if (filme.getBackdropPath() != null) {
            Glide.with(this)
                    .load(IMAGE_BASE_URL + filme.getBackdropPath())
                    .into(imgBackground);
        }
    }
}