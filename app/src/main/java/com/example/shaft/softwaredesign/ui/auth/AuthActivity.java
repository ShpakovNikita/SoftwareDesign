package com.example.shaft.softwaredesign.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shaft.softwaredesign.MainActivity;
import com.example.shaft.softwaredesign.R;
import com.example.shaft.softwaredesign.firebaseAuth.AuthManager;
import com.example.shaft.softwaredesign.firebaseAuth.AuthProvider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class AuthActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (AuthManager.getInstance().isSignIn())
        {
            startMainActivity();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_fragment:
                NavController navController =
                        Navigation.findNavController(this, R.id.nav_host_fragment);

                int current = navController.getCurrentDestination().getId();

                switch (current)
                {
                    case R.id.loginFragment:
                        navController.navigate(R.id.action_loginFragment_to_about);
                        break;
                    case R.id.registerFragment:
                        navController.navigate(R.id.action_registerFragment_to_about);
                        break;
                }

                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        NavController navController =
                Navigation.findNavController(this, R.id.nav_host_fragment);

        int current = navController.getCurrentDestination().getId();

        switch (current) {
            case R.id.about:
                navController.navigate(R.id.action_about_to_loginFragment);
                break;

            default:
                super.onBackPressed();
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
