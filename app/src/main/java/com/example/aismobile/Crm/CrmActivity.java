package com.example.aismobile.Crm;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CrmActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crm);

        Toolbar toolbar = findViewById(R.id.toolbar_crm);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout_crm);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_crm);
        navigationView.setNavigationItemSelectedListener(this);

        int menu = Integer.valueOf(getIntent().getStringExtra("menu"));
        if (menu == 0)
            swapFragment(R.id.nav_customerfeedback);
        else if (menu == 1)
            swapFragment(R.id.nav_question);
        else if (menu == 2)
            swapFragment(R.id.nav_kuesioner);
        else if (menu == 3)
            swapFragment(R.id.nav_grafikkuesioner);
        else if (menu == 4)
            swapFragment(R.id.nav_monitoring);
        else if (menu == 5)
            swapFragment(R.id.nav_leads);
        else if (menu == 6)
            swapFragment(R.id.nav_followup);
        else if (menu == 7)
            swapFragment(R.id.nav_events);
        else if (menu == 8)
            swapFragment(R.id.nav_schedule_visit);
        else swapFragment(R.id.nav_customerfeedback);
    }

    private void swapFragment(int id) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_customerfeedback) {
            CustomerFeedbackFragment mainFragment = CustomerFeedbackFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_question) {
            QuestionFragment mainFragment = QuestionFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_kuesioner) {
            KuesionerFragment mainFragment = KuesionerFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_grafikkuesioner) {
            GrafikKuesionerFragment mainFragment = GrafikKuesionerFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_monitoring) {
            MonitoringFragment mainFragment = MonitoringFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_leads) {
            LeadsFragment mainFragment = LeadsFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_followup) {
            FollowupsFragment mainFragment = FollowupsFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_events) {
            EventsFragment mainFragment = EventsFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_schedule_visit) {
            ScheduleFragment mainFragment = ScheduleFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else {
            CustomerFeedbackFragment mainFragment = CustomerFeedbackFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        }
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crm, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        swapFragment(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_crm);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
