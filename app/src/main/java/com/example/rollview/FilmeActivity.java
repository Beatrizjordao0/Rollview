package com.example.rollview;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
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
    String youtubeKey = "";
    LinearLayout btnTrailer;

    private RecyclerView recyclerCast;
    private CastAdapter castAdapter;
    private List<TMDBCast> listaAtoresGlobais = new ArrayList<>();
    private List<TMDBCast> listaDiretoresGlobais = new ArrayList<>();
    private List<TMDBGender> listaGeneroGlobais = new ArrayList<>();
    private GenderAdapter genderAdapter;

    private TMDBMovieResponse currentMovie;
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

        btnTrailer = findViewById(R.id.Trailer);
        btnTrailer.setOnClickListener(v -> {
            if (!youtubeKey.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + youtubeKey));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Trailer não disponível", Toast.LENGTH_SHORT).show();
            }
        });

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

        recyclerCast = findViewById(R.id.recyclerCast);

        recyclerCast.setLayoutManager(new LinearLayoutManager(this));
        castAdapter = new CastAdapter(listaAtoresGlobais);
        recyclerCast.setAdapter(castAdapter);

        BottomNavigationView bottomNav = findViewById(R.id.navbar);
        bottomNav.getMenu().setGroupCheckable(0, true, false);

        TextView tvCast = findViewById(R.id.tabCast);
        TextView tvDirection = findViewById(R.id.tabDirection);
        TextView tvGender = findViewById(R.id.tabGender);

        tvCast.setOnClickListener(v -> {
            recyclerCast.setAdapter(castAdapter);
            if (castAdapter != null) {
                castAdapter.atualizarLista(listaAtoresGlobais);
            }

            tvCast.setTextColor(Color.parseColor("#944264"));
            tvDirection.setTextColor(Color.WHITE);
            tvGender.setTextColor(Color.WHITE);
        });

        tvDirection.setOnClickListener(v -> {
            if (castAdapter != null) {
                recyclerCast.setAdapter(castAdapter);
                castAdapter.atualizarLista(listaDiretoresGlobais);
            }

            tvDirection.setTextColor(Color.parseColor("#944264"));
            tvCast.setTextColor(Color.WHITE);
            tvGender.setTextColor(Color.WHITE);
        });

        tvGender.setOnClickListener(v -> {
            if (genderAdapter != null) recyclerCast.setAdapter(genderAdapter);

            tvGender.setTextColor(Color.parseColor("#944264")); // Cor ativa
            tvCast.setTextColor(Color.WHITE); // Cor inativa
            tvDirection.setTextColor(Color.WHITE); // Cor inativa
        });

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Intent intent = new Intent(FilmeActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                return true;
            }

            if (id == R.id.nav_search) {
                Intent intent = new Intent(FilmeActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
                return true;
            }

            if (id == R.id.nav_list) {
                Intent intent = new Intent(FilmeActivity.this, FavoriteActivity.class);
                startActivity(intent);
                finish();
                return true;
            }

            if (id == R.id.nav_profile) {
                Intent intent = new Intent(FilmeActivity.this, PerfilActivity.class);
                startActivity(intent);
                finish();
                return true;
            }

            return false;
        });

        btnBack.setOnClickListener(v -> finish());

        btnAvaliar.setOnClickListener(v -> {
            if (currentMovie != null) {
                Sessao.filmeAtual = currentMovie;
            }

            Intent intent = new Intent(FilmeActivity.this, AvaliacaoFilmeActivity.class);
            intent.putExtra("titulo", txtTitulo.getText().toString());
            intent.putExtra("ano", txtInfo.getText().toString());
            intent.putExtra("poster", posterUrl);

            startActivity(intent);
        });

        int movieId = getIntent().getIntExtra("movie_id", 269149);

        carregarFilme(movieId);
        carregarElenco(movieId);
        carregarTrailer(movieId);
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
                    preencherTela(response.body()); // Passa o objeto do filme
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

    private void carregarElenco(int movieId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TmdbApi api = retrofit.create(TmdbApi.class);

        api.getMovieCredits(movieId, API_KEY, "pt-BR").enqueue(new Callback<TMDBCreditsResponse>() {
            @Override
            public void onResponse(Call<TMDBCreditsResponse> call, Response<TMDBCreditsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    listaAtoresGlobais.clear();
                    listaDiretoresGlobais.clear();

                    List<TMDBCast> castList = response.body().getCast();
                    int limite = Math.min(castList.size(), 10);

                    for (int i = 0; i < limite; i++) {
                        listaAtoresGlobais.add(castList.get(i));
                    }

                    if (response.body().getCrew() != null) {
                        for (TMDBCast pessoa : response.body().getCrew()) {
                            if (pessoa.getJob() != null && (pessoa.getJob().equals("Director") || pessoa.getJob().equals("Diretor"))) {
                                listaDiretoresGlobais.add(pessoa);
                            }
                        }
                    }

                    castAdapter = new CastAdapter(listaAtoresGlobais);
                    recyclerCast.setAdapter(castAdapter);
                }
            }

            @Override
            public void onFailure(Call<TMDBCreditsResponse> call, Throwable t) {
                Toast.makeText(FilmeActivity.this, "Erro ao carregar elenco", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void preencherTela(TMDBMovieResponse movie) {
        this.currentMovie = movie;

        txtTitulo.setText(movie.getTitle());

        String ano = "";
        if (movie.getReleaseDate() != null && movie.getReleaseDate().length() >= 4) {
            ano = movie.getReleaseDate().substring(0, 4);
        }

        txtInfo.setText(ano);
        txtDuracao.setText(movie.getRuntime() + " mins");
        txtNota.setText(String.format(Locale.US, "%.1f", movie.getVoteAverage()));
        txtSinopse.setText(movie.getOverview());

        ratingBar.setRating((float) movie.getVoteAverage() / 2);

        if (movie.getPosterPath() != null) {
            posterUrl = IMAGE_BASE_URL + movie.getPosterPath();
            Glide.with(this)
                    .load(posterUrl)
                    .into(imgPoster);
        }

        if (movie.getBackdropPath() != null) {
            Glide.with(this)
                    .load(IMAGE_BASE_URL + movie.getBackdropPath())
                    .into(imgBackground);
        }

        if (movie.getGenres() != null) {
            listaGeneroGlobais = movie.getGenres();
            genderAdapter = new GenderAdapter(listaGeneroGlobais);
        }
    }

    private void carregarTrailer(int movieId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TmdbApi api = retrofit.create(TmdbApi.class);

        api.getMovieVideos(movieId, API_KEY, "pt-BR").enqueue(new Callback<TMDBVideoResponse>() {
            @Override
            public void onResponse(Call<TMDBVideoResponse> call, Response<TMDBVideoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TMDBVideoResponse.TMDBVideo> videos = response.body().getResults();
                    for (TMDBVideoResponse.TMDBVideo video : videos) {
                        if (video.getSite().equalsIgnoreCase("YouTube") &&
                                (video.getType().equalsIgnoreCase("Trailer") || video.getType().equalsIgnoreCase("Teaser"))) {
                            youtubeKey = video.getKey();
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TMDBVideoResponse> call, Throwable t) {
            }
        });
    }
}