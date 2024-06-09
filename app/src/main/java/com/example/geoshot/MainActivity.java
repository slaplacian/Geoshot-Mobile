package com.example.geoshot;

import android.content.Intent;
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

import com.example.geoshot.utils.APIClient;
import com.example.geoshot.utils.PostLogin;
import com.example.geoshot.utils.User;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText senha;


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
        Log.d("ResponseLogin", response);

        JSONObject json;
        String status;
        String message="";
        try {
            json = new JSONObject(response);
            status = json.getString("status");
            if(status.equals("error")){
                message = json.getString("message");
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        if(status.equals("success")){
            Toast.makeText(this, "Deu certo!", Toast.LENGTH_LONG).show();
        }
        else if (status.equals("error")){
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    public void cadastrarse(View view) {
        Intent intent = new Intent(this, TelaCadastro.class);
        startActivity(intent);
    }
}