package com.example.rollview;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity{

    Button buttonLogin;
    EditText inputEmail;
    EditText inputSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        buttonLogin = findViewById(R.id.bottonlogin);
        inputEmail = findViewById(R.id.inputEmail);
        inputSenha = findViewById(R.id.inputsenha);

        buttonLogin.setOnClickListener(v -> {
            String emailDigitado = inputEmail.getText().toString();
            String senhaDigitada = inputSenha.getText().toString();

            if (emailDigitado.equals("avena@gmail.com") && senhaDigitada.equals("AvenaJABAL")) {
                // Se estiver certo, loga
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            } else {
                // Se estiver errado, avisamos o usuário
                Toast.makeText(LoginActivity.this, "Dados incorretos!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
