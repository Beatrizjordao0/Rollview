package com.example.rollview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.app.ActivityOptionsCompat;

public class MainActivity extends AppCompatActivity {

    // variáveis
    Button buttonLogin;
    Button buttonSignup;
    TextView skipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Adiciona o activity_main.xml como tela inicial
        setContentView(R.layout.activity_main);

        // Puxa os Ids das views do xml e guarda em variáveis
        skipButton = findViewById(R.id.skip);
        buttonLogin = findViewById(R.id.login);
        buttonSignup = findViewById(R.id.signup);

        // Função
        skipButton.setOnClickListener(v -> {
            com.google.firebase.auth.FirebaseAuth.getInstance().signOut();
            Sessao.nomeUsuario = "Visitante";
            Sessao.username = "@visitante";
            Sessao.filmeAtual = null;

            if(Sessao.favorites != null){
                Sessao.favorites.clear();
            }
            if(Sessao.avaliacoes != null){
                Sessao.avaliacoes.clear();
            }

            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        buttonLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    MainActivity.this,
                    findViewById(R.id.linearLayout),
                    "transicao_card");
            startActivity(intent, options.toBundle());
        });

        buttonSignup.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    MainActivity.this,
                    findViewById(R.id.linearLayout),
                    "transicao_card");
            startActivity(intent, options.toBundle());
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}