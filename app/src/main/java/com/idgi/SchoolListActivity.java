package com.idgi;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.idgi.core.School;
import com.idgi.services.Database;
import com.idgi.util.Navigation;
import com.idgi.util.SchoolListAdapter;

import java.util.ArrayList;
import java.util.List;

public class SchoolListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;

    private Database database = Database.getInstance();

    private ArrayList<School> schools = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);

        School s1 = database.getSchool("1");
        School s2 = database.getSchool("2");
        School s3 = database.getSchool("3");
        School s4 = database.getSchool("4");
        School s5 = database.getSchool("5");
        School s6 = database.getSchool("6");

        schools.add(s1);
        schools.add(s2);
        schools.add(s3);
        schools.add(s4);
        schools.add(s5);
        schools.add(s6);



        manager = new LinearLayoutManager(this);
        adapter = new SchoolListAdapter(this, schools);

        recycler = (RecyclerView) findViewById(R.id.school_list_recycler_view);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);

        toolbar = (Toolbar) findViewById(R.id.test_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Navigation.onMenuItemSelected(this, item);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
