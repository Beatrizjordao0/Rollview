package com.example.rollview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText inputUsuario;
    EditText inputEmail;
    EditText inputSenha;
    Button bottoncadastro;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        inputUsuario = findViewById(R.id.inputusuario);

        inputEmail = findViewById(R.id.inputemail);

        inputSenha = findViewById(R.id.inputsenha);

        bottoncadastro = findViewById(R.id.bottoncadastro);

        bottoncadastro.setOnClickListener(v -> {

            String nomeUsuario = inputUsuario.getText().toString().trim();
            String email = inputEmail .getText().toString().trim();
            String senha = inputSenha.getText().toString().trim();

            if(nomeUsuario.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Preencha todos os Campos!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(senha.length() < 6 ){
                Toast.makeText(SignupActivity.this, "A senha deve ter no mínimo 6 caracteres.", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            String userID = mAuth.getCurrentUser().getUid();

                            Map<String, Object> usuario = new HashMap<>();
                            usuario.put("nome", nomeUsuario);
                            usuario.put("email", email);
                            usuario.put("username", "@" + nomeUsuario);

                            db.collection("usuarios").document(userID)
                                    .set(usuario)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(SignupActivity.this, "Conta Criada com sucesso!", Toast.LENGTH_SHORT).show();

                                        Sessao.nomeUsuario = nomeUsuario;

                                        Sessao.username = "@" + nomeUsuario;

                                        Intent intent = new Intent(SignupActivity.this, PerfilActivity.class);

                                        intent.putExtra("nome", Sessao.nomeUsuario);

                                        intent.putExtra("username", Sessao.username);

                                        startActivity(intent);

                                    });
                        } else {
                            Toast.makeText(SignupActivity.this, "Erro: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
