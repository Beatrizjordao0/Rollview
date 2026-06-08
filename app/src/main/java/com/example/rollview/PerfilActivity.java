package com.example.rollview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class PerfilActivity extends AppCompatActivity {

    ImageView imgPerfil;
    ImageView imgBackground;
    TextView txtNome;
    TextView txtUsername;
    private AvaliacaoAdapter adapter;
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
        BottomNavigationView bottomNav = findViewById(R.id.navbar);
        bottomNav.setSelectedItemId(R.id.nav_profile);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_profile){
                return true;
            } else if (id == R.id.nav_home){
                startActivity(new Intent(PerfilActivity.this, HomeActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_search){
                startActivity(new Intent(PerfilActivity.this, SearchActivity.class));
                finish();
                return true;
            } else if(id == R.id.nav_list){
                startActivity(new Intent(PerfilActivity.this, FavoriteActivity.class));
                finish();
                return true;
            }

            return false;
        });

        Uri fotoPerfil = null;

        List<Avaliacao> listaAvaliacoes = Sessao.avaliacoes;

        String nome = Sessao.nomeUsuario;
        String username = Sessao.username;

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

        adapter = new AvaliacaoAdapter(usuario.getAvaliacoes(), usuario);

        recyclerAvaliacoes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerAvaliacoes.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        carregarAvaliacoesDoFirebase();

    }
    private void preencherPerfil(Usuario usuario){

        txtNome.setText(usuario.getNome());
        txtUsername.setText(usuario.getUsername());

        if(usuario.getFotoPerfil() != null){
            imgPerfil.setImageURI(usuario.getFotoPerfil());
            imgBackground.setImageURI(usuario.getFotoPerfil());
        }
    }

    private void carregarAvaliacoesDoFirebase(){
        com.google.firebase.auth.FirebaseAuth auth = com.google.firebase.auth.FirebaseAuth.getInstance();
        com.google.firebase.firestore.FirebaseFirestore db = com.google.firebase.firestore.FirebaseFirestore.getInstance();

        if(auth.getCurrentUser() != null){
            String userID = auth.getCurrentUser().getUid();
            db.collection("usuarios").document(userID).collection("avaliacoes")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        Sessao.avaliacoes.clear();
                       for (com.google.firebase.firestore.QueryDocumentSnapshot doc : queryDocumentSnapshots){
                           String data = doc.getString("data");
                           String texto = doc.getString("texto");
                           String poster = doc.getString("posterUrl");
                           Double notaDouble = doc.getDouble("nota");
                           float nota = (notaDouble != null) ? notaDouble.floatValue() : 0f;

                           Avaliacao avaliacao = new Avaliacao(
                                   data != null ? data : "",
                                   texto != null ? texto : "",
                                   nota,
                                   poster != null && !poster.isEmpty() ? android.net.Uri.parse(poster) : null
                           );

                           Sessao.avaliacoes.add(avaliacao);
                       }

                       if(adapter != null){
                           adapter.notifyDataSetChanged();
                       }


                    });
        }
    }
}