package com.example.aismobile.Personalia;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.aismobile.R;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PersonaliaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalia);

        Toolbar toolbar = findViewById(R.id.toolbar_personalia);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout_personalia);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_personalia);
        navigationView.setNavigationItemSelectedListener(this);

        int menu = Integer.valueOf(getIntent().getStringExtra("menu"));
        if (menu == 0)
            swapFragment(R.id.nav_kerja);
        else if (menu == 1)
            swapFragment(R.id.nav_penggajian);
        else if (menu == 2)
            swapFragment(R.id.nav_kalender);
        else if (menu == 3)
            swapFragment(R.id.nav_karyawan);
        else if (menu == 4)
            swapFragment(R.id.nav_departemen);
        else if (menu == 5)
            swapFragment(R.id.nav_jenjangkaryawan);
        else if (menu == 6)
            swapFragment(R.id.nav_pangkat);
        else if (menu == 7)
            swapFragment(R.id.nav_jabatan);
        else if (menu == 8)
            swapFragment(R.id.nav_news);
        else if (menu == 9)
            swapFragment(R.id.nav_report);
        else swapFragment(R.id.nav_kerja);
    }

    private void swapFragment(int id) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_kerja) {
            KerjaFragment mainFragment = KerjaFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_penggajian) {
            PenggajianFragment mainFragment = PenggajianFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_kalender) {
            KalenderFragment mainFragment = KalenderFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_karyawan) {
            KaryawanFragment mainFragment = KaryawanFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_departemen) {
            DepartemenFragment mainFragment = DepartemenFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_jenjangkaryawan) {
            JenjangKaryawanFragment mainFragment = JenjangKaryawanFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_pangkat) {
            PangkatFragment mainFragment = PangkatFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_jabatan) {
            JabatanFragment mainFragment = JabatanFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_news) {
            NewsFragment mainFragment = NewsFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_report) {
            ReportFragment mainFragment = ReportFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else {
            KerjaFragment mainFragment = KerjaFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment,mainFragment);
        }
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.personalia, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        swapFragment(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_personalia);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
