package com.example.rollview;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PerfilActivity extends AppCompatActivity {

    ImageView imgPerfil;
    ImageView imgBackground;

    TextView txtNome;
    TextView txtUsername;

    RecyclerView recyclerAvaliacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        imgPerfil = findViewById(R.id.imgPerfil);
        imgBackground = findViewById(R.id.imgBackground);

        txtNome = findViewById(R.id.txtNome);
        txtUsername = findViewById(R.id.txtUsername);

        recyclerAvaliacoes = findViewById(R.id.recyclerAvaliacoes);

        Uri fotoPerfil = null;

        List<Avaliacao> listaAvaliacoes = Sessao.avaliacoes;

        String nome =
                getIntent().getStringExtra("nome");

        String username =
                getIntent().getStringExtra("username");

        if(nome == null){
            nome = "";
        }

        if(username == null){
            username = "";
        }

        Usuario usuario = new Usuario(
                nome,
                username,
                fotoPerfil,
                listaAvaliacoes
        );

        preencherPerfil(usuario);

        AvaliacaoAdapter adapter =
                new AvaliacaoAdapter(usuario.getAvaliacoes(), usuario);

        recyclerAvaliacoes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerAvaliacoes.setAdapter(adapter);
    }

    private void preencherPerfil(Usuario usuario){

        txtNome.setText(usuario.getNome());
        txtUsername.setText(usuario.getUsername());

        if(usuario.getFotoPerfil() != null){
            imgPerfil.setImageURI(usuario.getFotoPerfil());
            imgBackground.setImageURI(usuario.getFotoPerfil());
        }
    }
}