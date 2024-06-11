package com.example.geoshot;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.geoshot.utils.APIClient;
import com.example.geoshot.utils.PostLogin;
import com.example.geoshot.utils.User;
import com.example.geoshot.utils.sqlite.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText senha;
    private TextView errorMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username = findViewById(R.id.username);
        senha = findViewById(R.id.senha);
        errorMessage = findViewById(R.id.errorMessage);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void doLogin(View view){
        String username = this.username.getText().toString();
        String pass = senha.getText().toString();
        User user = new User(username, pass);

        APIClient api = new APIClient();
        api.setPostStrategy(new PostLogin(user));
        String response = api.postRequest();
        Log.d("Depurando", "Resposta da API: " + response);

        JSONObject json;
        String status = "";
        String message = "";
        String userOnSuccess = "";
        try {
            json = new JSONObject(response);
            status = json.getString("status");
            userOnSuccess = json.getString("username");
            if(status.equals("error")){
                message = json.getString("message");
            }
        } catch (JSONException e) {
            Log.d("Depurando", "Erro em doLogin MainActivity: JSONException: " + e.getMessage());
        }

        if (status.equals("error")){
            errorMessage.setText(R.string.invalid_login);
            this.username.setText("");
            senha.setText("");
        }
        else {
            Intent intent = new Intent(this, BaseActivity.class);
            SessionManager.saveSession(this,userOnSuccess);
            //intent.putExtra("username", userOnSuccess);
            startActivity(intent);
        }
    }

    public void cadastrarse(View view) {
        Intent intent = new Intent(this, TelaCadastro.class);
        startActivity(intent);
    }
}