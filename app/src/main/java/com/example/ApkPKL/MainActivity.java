package com.example.ApkPKL;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private final AppCompatActivity activity = MainActivity.this;
    private TextView username;
    private TextView email;
    private ImageView img;
    private DatabaseHelper myDb;



    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_map, R.id.nav_history,  R.id.nav_profile, R.id.nav_about_us)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        initObjects();

        View headerView = navigationView.getHeaderView(0);

        username = headerView.findViewById(R.id.username_nav);
        email = headerView.findViewById(R.id.email_nav);
        img = headerView.findViewById(R.id.profileFoto);
        String emailFromIntent = getIntent().getStringExtra("USERNAME");
        username.setText(emailFromIntent);

        email.setText(myDb.getEmail(emailFromIntent));

        if(myDb.getJenisKelamin(emailFromIntent).equals("Laki - Laki")){
            img.setImageResource(R.drawable.male);
        }else{
            img.setImageResource(R.drawable.female);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void initObjects() {
        myDb = new DatabaseHelper(activity);
    }
}
