package com.example.geoshot;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.geoshot.generalUtilities.User;
import com.example.geoshot.generalUtilities.post.PostSignup;

import org.json.JSONException;
import org.json.JSONObject;


public class TelaCadastro extends AppCompatActivity {
    private EditText nomeCompleto;
    private EditText username;
    private EditText email;
    private EditText senha;
    private EditText confirmarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nomeCompleto = findViewById(R.id.nomeCompleto);
        username = findViewById(R.id.usernameProfile);
        email = findViewById(R.id.email);
        senha = findViewById(R.id.senha);
        confirmarSenha = findViewById(R.id.confirmarSenha);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void cadastrarUsuario(View v) {
        String nome = this.nomeCompleto.getText().toString();
        String username = this.username.getText().toString();
        String email = this.email.getText().toString();
        String senha = this.senha.getText().toString();
        String confirmarSenha = this.confirmarSenha.getText().toString();
        User user = new User(nome, username, email, senha, confirmarSenha);

        if (senha.equals(confirmarSenha)) {

            String response = PostSignup.post(nome,username,email,senha,confirmarSenha);
            Log.d("Resposta do Cadastro",response);
            JSONObject json;
            boolean someerror = false;
            String error = "";
            String message;
            try {
                json = new JSONObject(response);
                message = json.getString("status");
                if(json.getString("no-password").equals("true")) {
                    Toast.makeText(this, "Digite a Senha!", Toast.LENGTH_LONG).show();
                } else if(json.getString("non-equals-password").equals("true")) {
                    Toast.makeText(this, "As Senhas não são iguais!", Toast.LENGTH_LONG).show();
                } else if(json.getString("already-username").equals("true")) {
                    someerror = true;
                    error += "Usuário já cadastrado!";
                } else if(json.getString("already-email").equals("true")) {
                    if(someerror) error += "\n";
                    error += "E-mail já cadastrado!";
                    someerror = true;
                }

                if(someerror) {
                    Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            if(message.equals("success")){
                finish();
            }
        }
    }

    public void goToLogin(View v) {
        finish();
    }
}