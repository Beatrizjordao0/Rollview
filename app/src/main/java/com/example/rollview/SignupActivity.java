package com.example.rollview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    EditText inputUsuario;
    Button bottoncadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        inputUsuario = findViewById(R.id.inputusuario);

        bottoncadastro = findViewById(R.id.bottoncadastro);

        bottoncadastro.setOnClickListener(v -> {

            String nomeUsuario =
                    inputUsuario.getText().toString().trim();

            Sessao.nomeUsuario = nomeUsuario;

            Sessao.username = "@" + nomeUsuario;

            Intent intent =
                    new Intent(
                            SignupActivity.this,
                            PerfilActivity.class
                    );

            intent.putExtra(
                    "nome",
                    Sessao.nomeUsuario
            );

            intent.putExtra(
                    "username",
                    Sessao.username
            );

            startActivity(intent);

        });
    }
}
