package com.example.aismobile.Project;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;

import com.example.aismobile.Project.JobOrder.JobOrderFragment;
import com.example.aismobile.R;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProjectActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        Toolbar toolbar = findViewById(R.id.toolbar_project);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout_project);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_project);
        navigationView.setNavigationItemSelectedListener(this);

        int menu = Integer.valueOf(getIntent().getStringExtra("menu"));
        if (menu == 0)
            swapFragment(R.id.nav_customerinvoice);
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
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_job_order) {
            JobOrderFragment mainFragment = JobOrderFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_work_completion) {
            WorkCompletionFragment mainFragment = WorkCompletionFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_material_req) {
            MaterialReqFragment mainFragment = MaterialReqFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_tools_request) {
            ToolsReqFragment mainFragment = ToolsReqFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_work_request) {
            WorkReqFragment mainFragment = WorkReqFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_pengambilan) {
            PengambilanFragment mainFragment = PengambilanFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_spkl) {
            SpklFragment mainFragment = SpklFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_proposed_budget) {
            ProposedBudgetFragment mainFragment = ProposedBudgetFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_cash_project_report) {
            CashProjectFragment mainFragment = CashProjectFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_tunjangan_karyawan) {
            TunKaryawanFragment mainFragment = TunKaryawanFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_tunjangan_temporary) {
            TunTemporaryFragment mainFragment = TunTemporaryFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else {
            JobOrderFragment mainFragment = JobOrderFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment,mainFragment);
        }
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.project, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        swapFragment(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_project);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
