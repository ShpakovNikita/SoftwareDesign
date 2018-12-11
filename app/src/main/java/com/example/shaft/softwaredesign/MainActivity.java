package com.example.shaft.softwaredesign;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public final class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);

        BottomNavigationView navigation =
                (BottomNavigationView) findViewById(R.id.bottom_navigation);

        NavController navController =
                Navigation.findNavController(this, R.id.nav_host_fragment);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int current = navController.getCurrentDestination().getId();

                switch (menuItem.getItemId()) {
                    case R.id.about_fragment:
                        if (current == R.id.first_blank_fragment) {
                            navController.navigate(R.id.action_firstBlankFragment_to_about);
                        }
                        if (current == R.id.second_blank_fragment) {
                            navController.navigate(R.id.action_secondBlankFragment_to_about);
                        }
                        break;
                    case R.id.first_blank_fragment:
                        if (current == R.id.about_fragment) {
                            navController.navigate(R.id.action_about_to_firstBlankFragment);
                        }
                        if (current == R.id.second_blank_fragment) {
                            navController.navigate(R.id.action_secondBlankFragment_to_firstBlankFragment);
                        }
                        break;
                    case R.id.second_blank_fragment:
                        if (current == R.id.first_blank_fragment) {
                            navController.navigate(R.id.action_firstBlankFragment_to_secondBlankFragment);
                        }
                        if (current == R.id.about_fragment) {
                            navController.navigate(R.id.action_about_to_secondBlankFragment);
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

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.about:
                final NavController navController =
                        Navigation.findNavController(this, R.id.nav_host_fragment);
                navController.navigate(R.id.action_account_to_about);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */
}
