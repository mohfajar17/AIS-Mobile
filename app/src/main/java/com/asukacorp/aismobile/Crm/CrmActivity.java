package com.asukacorp.aismobile.Crm;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.Crm.CustomerFeedback.CustomerFeedbackFragment;
import com.asukacorp.aismobile.Crm.Event.EventsFragment;
import com.asukacorp.aismobile.Crm.Followup.FollowupsCustomerFragment;
import com.asukacorp.aismobile.Crm.Followup.FollowupsLeadFragment;
import com.asukacorp.aismobile.Crm.Kuesioner.KuesionerFragment;
import com.asukacorp.aismobile.Crm.Lead.LeadsFragment;
import com.asukacorp.aismobile.Crm.Monitoring.MonitoringFragment;
import com.asukacorp.aismobile.Crm.Question.QuestionFragment;
import com.asukacorp.aismobile.Crm.ScheduleVisit.ScheduleFragment;
import com.asukacorp.aismobile.Data.CRM.CustomerFeedback;
import com.asukacorp.aismobile.Data.CRM.Event;
import com.asukacorp.aismobile.Data.CRM.Followup;
import com.asukacorp.aismobile.Data.CRM.Kuesioner;
import com.asukacorp.aismobile.Data.CRM.Lead;
import com.asukacorp.aismobile.Data.CRM.Monitoring;
import com.asukacorp.aismobile.Data.CRM.Question;
import com.asukacorp.aismobile.Data.CRM.ScheduleVisit;
import com.asukacorp.aismobile.LoginActivity;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crm);

        Toolbar toolbar = findViewById(R.id.toolbar_crm);
        setSupportActionBar(toolbar);

        myDialog = new Dialog(this);
        sharedPrefManager = new SharedPrefManager(this);

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
        getMobileIsActive(sharedPrefManager.getUserId());

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

    private void getMobileIsActive(final String userId) {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_MOBILE_IS_ACTIVE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int akses = jsonObject.getInt("is_mobile");
                    if (akses > 1){
                        Intent logout = new Intent(CrmActivity.this, LoginActivity.class);
                        startActivity(logout);
                        sharedPrefManager.logout();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(CrmActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", userId);
                return param;
            }
        };
        Volley.newRequestQueue(CrmActivity.this).add(request);
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
