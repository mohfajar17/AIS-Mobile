package com.asukacorp.aismobile.Safety;

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
import com.asukacorp.aismobile.Data.Safety.GenbaSafety;
import com.asukacorp.aismobile.Data.Safety.JobOrderSafety;
import com.asukacorp.aismobile.Data.Safety.WorkAccident;
import com.asukacorp.aismobile.LoginActivity;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.Safety.GenbaSafety.GenbaSafetyFragment;
import com.asukacorp.aismobile.Safety.SafetyFileReport.SafetyFileReportFragment;
import com.asukacorp.aismobile.Safety.WorkAccident.WorkAccidentsFragment;
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

public class SafetyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        WorkAccidentsFragment.OnListFragmentInteractionListener,
        GenbaSafetyFragment.OnListFragmentInteractionListener,
        SafetyFileReportFragment.OnListFragmentInteractionListener {

    FragmentTransaction fragmentTransaction;
    private String access = "";
    private Dialog myDialog;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety);

        Toolbar toolbar = findViewById(R.id.toolbar_safety);
        setSupportActionBar(toolbar);

        myDialog = new Dialog(this);
        sharedPrefManager = new SharedPrefManager(this);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout_safety);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_safety);
        navigationView.setNavigationItemSelectedListener(this);

        access = getIntent().getStringExtra("access");
        int menu = Integer.valueOf(getIntent().getStringExtra("menu"));
        if (menu == 0)
            swapFragment(R.id.nav_workaccidents);
        else if (menu == 1)
            swapFragment(R.id.nav_genbasafety);
        else if (menu == 2)
            swapFragment(R.id.nav_safetyfile);
        else swapFragment(R.id.nav_sales_quotation);
    }

    private void swapFragment(int id) {
        getMobileIsActive(sharedPrefManager.getUserId());

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_workaccidents && access.toLowerCase().contains("work_accident".toLowerCase())) {
            WorkAccidentsFragment mainFragment = WorkAccidentsFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_genbasafety && access.toLowerCase().contains("genba_safety".toLowerCase())) {
            GenbaSafetyFragment mainFragment = GenbaSafetyFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_safetyfile && access.toLowerCase().contains("safety_file_report".toLowerCase())) {
            SafetyFileReportFragment mainFragment = SafetyFileReportFragment.newInstance();
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
                        Intent logout = new Intent(SafetyActivity.this, LoginActivity.class);
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
                Toast.makeText(SafetyActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", userId);
                return param;
            }
        };
        Volley.newRequestQueue(SafetyActivity.this).add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.safety, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        swapFragment(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_safety);
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
    public void onListFragmentInteraction(WorkAccident item) {

    }

    @Override
    public void onListFragmentInteraction(GenbaSafety item) {

    }

    @Override
    public void onListFragmentInteraction(JobOrderSafety item) {

    }
}
