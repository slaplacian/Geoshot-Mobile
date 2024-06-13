package com.example.geoshot;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.geoshot.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        String loggedUsername = getIntent().getStringExtra("username");
        Log.d("Depurando", "BaseActivity -> onCreate -> LoggedUser -> " + loggedUsername);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.container);
        NavController navController = navHostFragment.getNavController();

        // Configuração padrão do BottomNavigationView
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // Adiciona um listener para interceptar os cliques nos itens do menu
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                NavOptions navOptions = new NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setRestoreState(true)
                        .setPopUpTo(item.getItemId(), true) // Limpa a pilha até o destino atual
                        .build();

                if (item.getItemId() == R.id.home) {
                    navController.navigate(R.id.home_fragment, null, navOptions);
                    return true;
                } else if (item.getItemId() == R.id.search) {
                    navController.navigate(R.id.search_fragment, null, navOptions);
                    return true;
                } else if (item.getItemId() == R.id.createChallenge) {
                    navController.navigate(R.id.createChallenge_fragment, null, navOptions);
                    return true;
                } else if (item.getItemId() == R.id.perfil) {
                    navController.navigate(R.id.perfil_fragment, null, navOptions);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
}