package com.example.aismobile.Crm;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.aismobile.Crm.CustomerFeedback.CustomerFeedbackFragment;
import com.example.aismobile.Crm.Event.EventsFragment;
import com.example.aismobile.Crm.Followup.FollowupsCustomerFragment;
import com.example.aismobile.Crm.Followup.FollowupsLeadFragment;
import com.example.aismobile.Crm.Kuesioner.KuesionerFragment;
import com.example.aismobile.Crm.Lead.LeadsFragment;
import com.example.aismobile.Crm.Monitoring.MonitoringFragment;
import com.example.aismobile.Crm.Question.QuestionFragment;
import com.example.aismobile.Crm.ScheduleVisit.ScheduleFragment;
import com.example.aismobile.Data.CRM.CustomerFeedback;
import com.example.aismobile.Data.CRM.Event;
import com.example.aismobile.Data.CRM.Followup;
import com.example.aismobile.Data.CRM.Kuesioner;
import com.example.aismobile.Data.CRM.Lead;
import com.example.aismobile.Data.CRM.Monitoring;
import com.example.aismobile.Data.CRM.Question;
import com.example.aismobile.Data.CRM.ScheduleVisit;
import com.example.aismobile.R;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CrmActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        MonitoringFragment.OnListFragmentInteractionListener,
        CustomerFeedbackFragment.OnListFragmentInteractionListener,
        QuestionFragment.OnListFragmentInteractionListener,
        KuesionerFragment.OnListFragmentInteractionListener,
        LeadsFragment.OnListFragmentInteractionListener,
        EventsFragment.OnListFragmentInteractionListener,
        ScheduleFragment.OnListFragmentInteractionListener,
        FollowupsLeadFragment.OnListFragmentInteractionListener,
        FollowupsCustomerFragment.OnListFragmentInteractionListener {

    FragmentTransaction fragmentTransaction;
    String access = "";
    private Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crm);

        Toolbar toolbar = findViewById(R.id.toolbar_crm);
        setSupportActionBar(toolbar);

        myDialog = new Dialog(this);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout_crm);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_crm);
        navigationView.setNavigationItemSelectedListener(this);

        access = getIntent().getStringExtra("access");
        int menu = Integer.valueOf(getIntent().getStringExtra("menu"));
        if (menu == 0)
            swapFragment(R.id.nav_customerfeedback);
        else if (menu == 1)
            swapFragment(R.id.nav_question);
        else if (menu == 2)
            swapFragment(R.id.nav_kuesioner);
        else if (menu == 3)
            swapFragment(R.id.nav_monitoring);
        else if (menu == 4)
            swapFragment(R.id.nav_leads);
        else if (menu == 5)
            swapFragment(R.id.nav_followup);
        else if (menu == 6)
            swapFragment(R.id.nav_events);
        else if (menu == 7)
            swapFragment(R.id.nav_schedule_visit);
        else swapFragment(R.id.nav_customerfeedback);
    }

    private void swapFragment(int id) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_customerfeedback && access.toLowerCase().contains("customer_feedback".toLowerCase())) {
            CustomerFeedbackFragment mainFragment = CustomerFeedbackFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_question && access.toLowerCase().contains("question".toLowerCase())) {
            QuestionFragment mainFragment = QuestionFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_kuesioner && access.toLowerCase().contains("kuesioner".toLowerCase())) {
            KuesionerFragment mainFragment = KuesionerFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_monitoring && access.toLowerCase().contains("sales_quotation".toLowerCase())) {
            MonitoringFragment mainFragment = MonitoringFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_leads && access.toLowerCase().contains("lead".toLowerCase())) {
            LeadsFragment mainFragment = LeadsFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_followup && access.toLowerCase().contains("followup".toLowerCase())) {
            FollowupsLeadFragment mainFragment = FollowupsLeadFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_events && access.toLowerCase().contains("event".toLowerCase())) {
            EventsFragment mainFragment = EventsFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_schedule_visit && access.toLowerCase().contains("schedule_visits".toLowerCase())) {
            ScheduleFragment mainFragment = ScheduleFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else {
            ShowPopup();
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

    public void ShowPopup() {
        TextView textViewWarning;
        TextView closeDialog;
        myDialog.setContentView(R.layout.custom_popup);
        textViewWarning = (TextView) myDialog.findViewById(R.id.textViewWarning);
        closeDialog = (TextView) myDialog.findViewById(R.id.closeDialog);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        textViewWarning.setText("You can't access this menu");
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    @Override
    public void onListFragmentInteraction(Monitoring item) {

    }

    @Override
    public void onListFragmentInteraction(CustomerFeedback item) {

    }

    @Override
    public void onListFragmentInteraction(Question item) {

    }

    @Override
    public void onListFragmentInteraction(Kuesioner item) {

    }

    @Override
    public void onListFragmentInteraction(Lead item) {

    }

    @Override
    public void onListFragmentInteraction(Event item) {

    }

    @Override
    public void onListFragmentInteraction(ScheduleVisit item) {

    }

    @Override
    public void onListFragmentInteraction(Followup item) {

    }
}
