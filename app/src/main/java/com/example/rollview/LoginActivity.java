package com.example.rollview;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity{

    private EditText inputEmail, inputSenha;
    private Button buttonLogin;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        buttonLogin = findViewById(R.id.bottonlogin);
        inputEmail = findViewById(R.id.inputEmail);
        inputSenha = findViewById(R.id.inputsenha);

        buttonLogin.setOnClickListener(v -> {
            String email = inputEmail.getText().toString();
            String senha = inputSenha.getText().toString();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Preecnha email e senha!", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            String userID = mAuth.getCurrentUser().getUid();
                            db.collection("usuarios").document(userID)
                                    .get()
                                    .addOnSuccessListener(documentSnapshot -> {
                                        if (documentSnapshot.exists()) {
                                            Sessao.nomeUsuario = documentSnapshot.getString("nome");
                                            Sessao.username = documentSnapshot.getString("username");

                                            db.collection("usuarios").document(userID).collection("favorites")
                                                            .get()
                                                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                                                Sessao.favorites.clear();

                                                                for (com.google.firebase.firestore.DocumentSnapshot doc : queryDocumentSnapshots){
                                                                    TMDBMovieResponse filmeSalvo = doc.toObject(TMDBMovieResponse.class);
                                                                    if(filmeSalvo != null){
                                                                        Sessao.favorites.add(filmeSalvo);
                                                                    }
                                                                }
                                                            });

                                            Toast.makeText(LoginActivity.this, "Bem-vindo de volta!", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                        } else {
                            Toast.makeText(LoginActivity.this, "Email ou senha incorretos!", Toast.LENGTH_SHORT).show();
                        }
                        });
        });
    }
}
