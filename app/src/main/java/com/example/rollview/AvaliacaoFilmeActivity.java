package com.example.rollview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AvaliacaoFilmeActivity extends AppCompatActivity {

    ImageButton btnBack;
    TextView btnSalvar;
    ImageView imgPoster;

    TextView txtTitulo;
    TextView txtAno;
    EditText inputData;
    EditText inputResenha;

    RatingBar ratingBar;

    String posterUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao_filme);

        btnBack = findViewById(R.id.btnBack);
        btnSalvar = findViewById(R.id.btnSalvar);
        imgPoster = findViewById(R.id.imgPoster);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtAno = findViewById(R.id.txtAno);

        inputData = findViewById(R.id.inputData);
        inputResenha = findViewById(R.id.inputResenha);

        ratingBar = findViewById(R.id.ratingBar);

        String titulo = getIntent().getStringExtra("titulo");
        String ano = getIntent().getStringExtra("ano");
        posterUrl = getIntent().getStringExtra("poster");
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        // NAVBAR
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if(id == R.id.nav_home) {
                return true;
            } else if(id == R.id.nav_profile){
                Intent intent = new Intent(AvaliacaoFilmeActivity.this, PerfilActivity.class);
                startActivity(intent);
                finish();
                return true;
            } else if(id == R.id.nav_search){
                Intent intent = new Intent(AvaliacaoFilmeActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
            } else if(id == R.id.nav_list){
                Intent intent = new Intent(AvaliacaoFilmeActivity.this, FavoriteActivity.class);
                startActivity(intent);
                finish();
            }
            return true;
        });

        String dataAtual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                .format(new Date());

        txtTitulo.setText(titulo);
        txtAno.setText(ano);
        inputData.setText(dataAtual);

        if (posterUrl != null && !posterUrl.isEmpty()) {
            Glide.with(this).load(posterUrl).into(imgPoster);
        }

        btnBack.setOnClickListener(v -> finish());

        btnSalvar.setOnClickListener(v -> {
            String texto = inputResenha.getText().toString().trim();
            float nota = ratingBar.getRating();
            String data = inputData.getText().toString().trim();

            Avaliacao avaliacao = new Avaliacao(
                    data,
                    texto,
                    nota,
                    posterUrl != null && !posterUrl.isEmpty() ? Uri.parse(posterUrl) : null
            );

            Sessao.avaliacoes.add(0, avaliacao);

            if (Sessao.filmeAtual != null) {
                boolean jaSalvo = false;
                for (TMDBMovieResponse f : Sessao.favorites) {
                    if (f.getId() == Sessao.filmeAtual.getId()) {
                        jaSalvo = true;
                        break;
                    }
                }
                if (!jaSalvo) {
                    Sessao.favorites.add(Sessao.filmeAtual);
                }
            }

            Intent intent = new Intent(AvaliacaoFilmeActivity.this, PerfilActivity.class);
            intent.putExtra("nome", Sessao.nomeUsuario);
            intent.putExtra("username", Sessao.username);
            startActivity(intent);

            finish();
        });
    }
}