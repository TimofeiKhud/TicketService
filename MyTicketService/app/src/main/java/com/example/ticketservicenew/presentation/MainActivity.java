package com.example.ticketservicenew.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ticketservicenew.R;
import com.example.ticketservicenew.presentation.auth.login.view.LoginFragment;
import com.example.ticketservicenew.presentation.eventlist.view.EventListFragment;
import com.example.ticketservicenew.presentation.auth.registration.view.RegFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            navigateToEventListFragment();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.menuBackground), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        NavigationView navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.main_layout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case (R.id.nav_auth) : {
                        navigateToLoginOutFragment();
                        break;
                    }
                    case (R.id.nav_events) : {
                        navigateToEventListFragment();
                        break;
                    }
                    case (R.id.nav_shopping_card) : {

                    }
                    case (R.id.nav_halls_scheme) : {

                    }
                    case (R.id.nav_about_us) : {
                        break;
                    }
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }

    private void navigateToLoginOutFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new LoginFragment())
                .commit();
    }

    private void navigateToEventListFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new EventListFragment())
                .commit();
    }
}
