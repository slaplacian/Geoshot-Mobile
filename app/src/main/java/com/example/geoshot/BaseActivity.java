package com.example.geoshot;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.geoshot.ui.create_challenge.CreateChallengeFragment;
import com.example.geoshot.ui.home.HomeFragment;
import com.example.geoshot.ui.perfil.PerfilFragment;
import com.example.geoshot.ui.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationBarView;

public class BaseActivity extends AppCompatActivity {
    private final HomeFragment homeFragment = new HomeFragment();
    private final SearchFragment searchFragment = new SearchFragment();
    private final CreateChallengeFragment createChallengeFragment = new CreateChallengeFragment();
    private final PerfilFragment perfilFragment = new PerfilFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        String loggedUsername = getIntent().getStringExtra("username");
        Log.d("Depurando", "BaseActivity -> onCreate -> LoggedUser -> " + loggedUsername);

        Bundle bundle = new Bundle();
        bundle.putString("username", loggedUsername);
        homeFragment.setArguments(bundle);
        searchFragment.setArguments(bundle);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        loadFragment(homeFragment, true);
        Log.d("Depurando", "BaseActivity -> onCreate -> Navegando para home...");

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.search) {
                    loadFragment(searchFragment, false);
                }
                else if (itemId == R.id.createChallenge) {
                    loadFragment(createChallengeFragment, false);
                }
                else if (itemId == R.id.perfil) {
                    loadFragment(perfilFragment, false);
                }
                else{
                    loadFragment(homeFragment, false);
                }

                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment, boolean isAppNotInitialized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(isAppNotInitialized){
            fragmentTransaction.add(R.id.container, fragment);
        }
        else{
            fragmentTransaction.replace(R.id.container, fragment);
        }

//        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}