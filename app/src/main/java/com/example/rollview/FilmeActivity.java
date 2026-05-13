package com.example.rollview;

import android.content.Intent;
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

    // Variáveis adicionadas para o Elenco
    private RecyclerView recyclerCast;
    private CastAdapter castAdapter;
    private List<Actor> listActor = new ArrayList<>();

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

        // Inicializando o RecyclerView e o Adapter do elenco
        recyclerCast = findViewById(R.id.recyclerCast); // Certifique-se de que o ID no XML é recyclerElenco
        recyclerCast.setLayoutManager(new LinearLayoutManager(this));
        castAdapter = new CastAdapter(listActor);
        recyclerCast.setAdapter(castAdapter);

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

        // Chamadas para carregar os dados
        carregarFilme(movieId);
        carregarElenco(movieId); // Chamada nova para preencher a lista
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

    // Método novo para carregar os atores da API
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
                    List<TMDBCast> castList = response.body().getCast();
                    listActor.clear(); // Limpa a lista antes de adicionar

                    // Pega até 10 atores para não deixar a lista gigantesca
                    int limite = Math.min(castList.size(), 10);

                    for (int i = 0; i < limite; i++) {
                        TMDBCast tmdbCast = castList.get(i);

                        String imageUrl = tmdbCast.getProfilePath() != null
                                ? IMAGE_BASE_URL + tmdbCast.getProfilePath()
                                : null;

                        listActor.add(new Actor(tmdbCast.getName(), tmdbCast.getCharacter(), imageUrl));
                    }

                    // Avisa o adapter que os dados chegaram para ele desenhar a tela
                    castAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TMDBCreditsResponse> call, Throwable t) {
                Toast.makeText(FilmeActivity.this, "Erro ao carregar elenco", Toast.LENGTH_SHORT).show();
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
                        // Verificamos se o vídeo é do YouTube e se é um Trailer ou Teaser
                        if (video.getSite().equalsIgnoreCase("YouTube") &&
                                (video.getType().equalsIgnoreCase("Trailer") || video.getType().equalsIgnoreCase("Teaser"))) {
                            youtubeKey = video.getKey(); // Salva a chave para o botão usar
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TMDBVideoResponse> call, Throwable t) {
                // Se falhar, a youtubeKey continuará vazia e o Toast avisará o usuário
            }
        });
    }
}