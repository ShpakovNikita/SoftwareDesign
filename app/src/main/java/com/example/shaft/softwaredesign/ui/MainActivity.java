package com.example.shaft.softwaredesign.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shaft.softwaredesign.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public final class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation =
                (BottomNavigationView) findViewById(R.id.bottom_navigation);

        NavController navController =
                Navigation.findNavController(this, R.id.nav_host_fragment);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int current = navController.getCurrentDestination().getId();

                switch (menuItem.getItemId()) {
                    case R.id.account_fragment:
                        if (current == R.id.first_blank_fragment) {
                            navController.navigate(R.id.action_first_blank_fragment_to_account_fragment);
                        }
                        if (current == R.id.second_blank_fragment) {
                            navController.navigate(R.id.action_second_blank_fragment_to_account_fragment);
                        }
                        break;
                    case R.id.first_blank_fragment:
                        if (current == R.id.account_fragment) {
                            navController.navigate(R.id.action_account_fragment_to_first_blank_fragment);
                        }
                        if (current == R.id.second_blank_fragment) {
                            navController.navigate(R.id.action_secondBlankFragment_to_firstBlankFragment);
                        }
                        break;
                    case R.id.second_blank_fragment:
                        if (current == R.id.first_blank_fragment) {
                            navController.navigate(R.id.action_firstBlankFragment_to_secondBlankFragment);
                        }
                        if (current == R.id.account_fragment) {
                            navController.navigate(R.id.action_account_fragment_to_second_blank_fragment);
                        }
                        break;
                }
                return true;
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
                    case R.id.account_fragment:
                        navController.navigate(R.id.action_account_fragment_to_about_fragment);
                        break;
                    case R.id.edit_account_fragment:
                        navController.navigate(R.id.action_edit_account_fragment_to_about_fragment);
                        break;
                    case R.id.first_blank_fragment:
                        navController.navigate(R.id.action_first_blank_fragment_to_about_fragment);
                        break;
                    case R.id.second_blank_fragment:
                        navController.navigate(R.id.action_second_blank_fragment_to_about_fragment);
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
            case R.id.about_fragment:
                navController.navigate(R.id.action_about_fragment_to_account_fragment);
                break;

            case R.id.edit_account_fragment:
                navController.navigate(R.id.action_edit_account_fragment_to_account_fragment);
                break;

            default:
                super.onBackPressed();
        }
    }

}
