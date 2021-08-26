package com.asukacorp.aismobile.Project;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.Data.Project.CashProjectReport;
import com.asukacorp.aismobile.Data.Project.JobOrder;
import com.asukacorp.aismobile.Data.Project.MaterialRequest;
import com.asukacorp.aismobile.Data.Project.Pickup;
import com.asukacorp.aismobile.Data.Project.ProposedBudget;
import com.asukacorp.aismobile.Data.Project.ResourcesRequest;
import com.asukacorp.aismobile.Data.Project.Spkl;
import com.asukacorp.aismobile.Data.Project.TunjanganKaryawan;
import com.asukacorp.aismobile.Data.Project.TunjanganTemporary;
import com.asukacorp.aismobile.Data.Project.WorkCompletion;
import com.asukacorp.aismobile.Data.Project.WorkOrder;
import com.asukacorp.aismobile.LoginActivity;
import com.asukacorp.aismobile.OnFragmentInteractionListener;
import com.asukacorp.aismobile.Project.CashProject.CashProjectFragment;
import com.asukacorp.aismobile.Project.JobOrder.JobOrderFragment;
import com.asukacorp.aismobile.Project.MaterialRequest.MaterialReqFragment;
import com.asukacorp.aismobile.Project.Pengambilan.PengambilanFragment;
import com.asukacorp.aismobile.Project.ProposedBudget.ProposedBudgetFragment;
import com.asukacorp.aismobile.Project.Spkl.SpklFragment;
import com.asukacorp.aismobile.Project.ToolsRequests.ToolsReqFragment;
import com.asukacorp.aismobile.Project.TunjanganKaryawan.TunKaryawanFragment;
import com.asukacorp.aismobile.Project.TunjanganTemporary.TunTemporaryFragment;
import com.asukacorp.aismobile.Project.WorkCompletion.WorkCompletionFragment;
import com.asukacorp.aismobile.Project.WorkRequests.WorkReqFragment;
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

public class ProjectActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        OnFragmentInteractionListener,
        JobOrderFragment.OnListFragmentInteractionListener,
        WorkCompletionFragment.OnListFragmentInteractionListener,
        MaterialReqFragment.OnListFragmentInteractionListener,
        WorkReqFragment.OnListFragmentInteractionListener,
        PengambilanFragment.OnListFragmentInteractionListener,
        SpklFragment.OnListFragmentInteractionListener,
        ProposedBudgetFragment.OnListFragmentInteractionListener,
        CashProjectFragment.OnListFragmentInteractionListener,
        TunKaryawanFragment.OnListFragmentInteractionListener,
        TunTemporaryFragment.OnListFragmentInteractionListener,
        ToolsReqFragment.OnListFragmentInteractionListener {

    FragmentTransaction fragmentTransaction;
    String access = "";
    private Dialog myDialog;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        Toolbar toolbar = findViewById(R.id.toolbar_project);
        setSupportActionBar(toolbar);

        myDialog = new Dialog(this);
        sharedPrefManager = new SharedPrefManager(this);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout_project);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_project);
        navigationView.setNavigationItemSelectedListener(this);

        access = access + getIntent().getStringExtra("access");
        int menu = Integer.valueOf(getIntent().getStringExtra("menu"));
        if (menu == 0)
            swapFragment(R.id.nav_job_order);
        else if (menu == 1)
            swapFragment(R.id.nav_work_completion);
        else if (menu == 2)
            swapFragment(R.id.nav_material_req);
        else if (menu == 3)
            swapFragment(R.id.nav_tools_request);
        else if (menu == 4)
            swapFragment(R.id.nav_work_request);
        else if (menu == 5)
            swapFragment(R.id.nav_pengambilan);
        else if (menu == 6)
            swapFragment(R.id.nav_spkl);
        else if (menu == 7)
            swapFragment(R.id.nav_proposed_budget);
        else if (menu == 8)
            swapFragment(R.id.nav_cash_project_report);
        else if (menu == 9)
            swapFragment(R.id.nav_tunjangan_karyawan);
        else if (menu == 10)
            swapFragment(R.id.nav_tunjangan_temporary);
        else swapFragment(R.id.nav_customerinvoice);
    }

    private void swapFragment(int id) {
        getMobileIsActive(sharedPrefManager.getUserId());

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_job_order && access.toLowerCase().contains("job_order".toLowerCase())) {
            JobOrderFragment mainFragment = JobOrderFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_work_completion && access.toLowerCase().contains("job_progress_report".toLowerCase())) {
            WorkCompletionFragment mainFragment = WorkCompletionFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_material_req && access.toLowerCase().contains("material_request".toLowerCase())) {
            MaterialReqFragment mainFragment = MaterialReqFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_tools_request && access.toLowerCase().contains("resources_request".toLowerCase())) {
            ToolsReqFragment mainFragment = ToolsReqFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_work_request && access.toLowerCase().contains("work_order".toLowerCase())) {
            WorkReqFragment mainFragment = WorkReqFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_pengambilan && access.toLowerCase().contains("pickup".toLowerCase())) {
            PengambilanFragment mainFragment = PengambilanFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_spkl && access.toLowerCase().contains("spkl".toLowerCase())) {
            SpklFragment mainFragment = SpklFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_proposed_budget && access.toLowerCase().contains("cash_advance".toLowerCase())) {
            ProposedBudgetFragment mainFragment = ProposedBudgetFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_cash_project_report && access.toLowerCase().contains("respons_advance".toLowerCase())) {
            CashProjectFragment mainFragment = CashProjectFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_tunjangan_karyawan && access.toLowerCase().contains("employee_allowance".toLowerCase())) {
            TunKaryawanFragment mainFragment = TunKaryawanFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_tunjangan_temporary && access.toLowerCase().contains("employee_allowance_temp".toLowerCase())) {
            TunTemporaryFragment mainFragment = TunTemporaryFragment.newInstance();
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
                        Intent logout = new Intent(ProjectActivity.this, LoginActivity.class);
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
                Toast.makeText(ProjectActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", userId);
                return param;
            }
        };
        Volley.newRequestQueue(ProjectActivity.this).add(request);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        swapFragment(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_project);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentChanged(int id) {

    }

    @Override
    public void onListFragmentInteraction(JobOrder item) {

    }

    @Override
    public void onListFragmentInteraction(WorkCompletion item) {

    }

    @Override
    public void onListFragmentInteraction(MaterialRequest item) {

    }

    @Override
    public void onListFragmentInteraction(WorkOrder item) {

    }

    @Override
    public void onListFragmentInteraction(Pickup item) {

    }

    @Override
    public void onListFragmentInteraction(Spkl item) {

    }

    @Override
    public void onListFragmentInteraction(ProposedBudget item) {

    }

    @Override
    public void onListFragmentInteraction(CashProjectReport item) {

    }

    @Override
    public void onListFragmentInteraction(TunjanganKaryawan item) {

    }

    @Override
    public void onListFragmentInteraction(TunjanganTemporary item) {

    }

    @Override
    public void onListFragmentInteraction(ResourcesRequest item) {

    }
}
