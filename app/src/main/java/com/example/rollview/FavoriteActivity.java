package com.example.rollview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerFavorites;
    private FavoriteAdapter adapter;
    private TextView txtContagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerFavorites = findViewById(R.id.recyclerFavorites);
        txtContagem = findViewById(R.id.txtContagem);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setSelectedItemId(R.id.nav_list);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if(id == R.id.nav_home) {
                startActivity(new Intent(FavoriteActivity.this, HomeActivity.class));
                finish();
                return true;
            } else if(id == R.id.nav_profile){
                startActivity(new Intent(FavoriteActivity.this, PerfilActivity.class));
                finish();
                return true;
            } else if(id == R.id.nav_search){
                startActivity(new Intent(FavoriteActivity.this, SearchActivity.class));
                finish();
                return true;
            } else if(id == R.id.nav_list){
                return true;
            }

            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerFavorites.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FavoriteAdapter(Sessao.favorites);
        recyclerFavorites.setAdapter(adapter);

        int totalFavoritos = Sessao.favorites.size();
        if (totalFavoritos == 0) {
            txtContagem.setText("Nenhum filme salvo");
        } else if (totalFavoritos == 1) {
            txtContagem.setText("1 filme salvo");
        } else {
            txtContagem.setText(totalFavoritos + " filmes salvos");
        }
    }
}