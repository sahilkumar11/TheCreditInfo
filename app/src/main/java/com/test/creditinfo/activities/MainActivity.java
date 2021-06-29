package com.test.creditinfo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.test.creditinfo.MySuggestionProvider;
import com.test.creditinfo.R;
import com.test.creditinfo.fragments.HomeFragment;
import com.test.creditinfo.fragments.MoreFragment;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity  {
    private DrawerLayout Dlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // nav dwawer
//
//        Toolbar toolbar = findViewById(R.id.homePageToolbar);
//        setSupportActionBar(toolbar);
//        Dlayout = findViewById(R.id.drawer_layout);
//        NavigationView navView = findViewById(R.id.nav_view);
//        navView.setNavigationItemSelectedListener(this);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, Dlayout, toolbar,
//                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        Dlayout.addDrawerListener(toggle);
//        toggle.syncState();


 ///////////////////// drwaer ends/////////////
        ListFragment fragment = new ListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,fragment);
        fragmentTransaction.commit();

//        Button addCustomerButton = findViewById(R.id.addCustomerBtn);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListner);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

        //Get the intent , verify the action and get the query
        final Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //doMySearch();
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this ,
                    MySuggestionProvider.AUTHORITY , MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query,null);

            //suggestions.clearHistory();
        }

        //addCustomerBtn on click Listner
//        addCustomerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(MainActivity.this, AddCustomerPage.class);
//                startActivity(intent1);
//                finish();
//            }
//        });

    }


    int f = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId())
                    {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            f =1;
                            break;

                        case R.id.nav_more:
                            selectedFragment = new MoreFragment();
                            f =1;
                            break;
                        case R.id.nav_cont:
                            f = 2;
                            break;
                    }
                    if(f==1){
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    }
                    else if(f==2) {
                        Intent intent = new Intent(MainActivity.this,AddCustomerPage.class);
//                        intent.addFlags(set.fl)
                        startActivity(intent);
                    }
                    return true;
                }
            };


    // for nav dwarwer
    @Override
    public void onBackPressed() {
        if (Dlayout.isDrawerOpen(GravityCompat.START)) {
            Dlayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
