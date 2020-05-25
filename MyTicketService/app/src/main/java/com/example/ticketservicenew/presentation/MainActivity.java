package com.example.ticketservicenew.presentation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;

import com.example.ticketservicenew.R;
import com.example.ticketservicenew.presentation.auth.login.view.LoginFragment;
import com.example.ticketservicenew.presentation.eventlist.view.EventListFragment;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.main_layout);

//        if(savedInstanceState == null){
//            navigateToEventListFragment();
//        }

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        //final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        //upArrow.setColorFilter(getResources().getColor(R.color.dark_grey_color), PorterDuff.Mode.SRC_ATOP);
        //getSupportActionBar().setHomeAsUpIndicator(upArrow);

        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if(destination.getId() == controller.getGraph().getStartDestination()){
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }else{
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        //drawerLayout = findViewById(R.id.main_layout);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case (R.id.nav_auth) : {
//                        navigateToLoginOutFragment();
//                        break;
//                    }
//                    case (R.id.nav_events) : {
//                        navigateToEventListFragment();
//                        break;
//                    }
//                    case (R.id.nav_shopping_card) : {
//
//                    }
//                    case (R.id.nav_halls_scheme) : {
//
//                    }
//                    case (R.id.nav_about_us) : {
//                        break;
//                    }
//                }
//                drawerLayout.closeDrawer(GravityCompat.START);
//                return true;
//            }
//        });
        NavigationUI.setupWithNavController(navigationView, navController);

    }

//    private void navigateToLoginOutFragment(){
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id, new LoginFragment())
//                .commit();
//    }
//
//    private void navigateToEventListFragment(){
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, new EventListFragment())
//                .commit();
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        return NavigationUI.navigateUp(navController, drawerLayout);
    }
}
