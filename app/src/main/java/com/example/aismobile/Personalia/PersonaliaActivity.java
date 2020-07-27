package com.example.aismobile.Personalia;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.aismobile.Data.Personalia.Department;
import com.example.aismobile.Data.Personalia.Employee;
import com.example.aismobile.Data.Personalia.EmployeeAchievement;
import com.example.aismobile.Data.Personalia.EmployeeGrade;
import com.example.aismobile.Data.Personalia.EmployeeNotice;
import com.example.aismobile.Data.Personalia.Fullname;
import com.example.aismobile.Data.Personalia.HariLibur;
import com.example.aismobile.Data.Personalia.HistoryContract;
import com.example.aismobile.Personalia.Kerja.CheckClockFragment;
import com.example.aismobile.Personalia.Kerja.CutiFragment;
import com.example.aismobile.Personalia.Kerja.ExperienceFragment;
import com.example.aismobile.Personalia.Kerja.HariLiburFragment;
import com.example.aismobile.Personalia.Kerja.HistoryContactFragment;
import com.example.aismobile.Personalia.Kerja.KeluargaFragment;
import com.example.aismobile.Personalia.Kerja.KerjaFragment;
import com.example.aismobile.Personalia.Kerja.NoticeFragment;
import com.example.aismobile.Personalia.Kerja.PendidikanFragment;
import com.example.aismobile.Personalia.Kerja.PrestasiFragment;
import com.example.aismobile.Personalia.Kerja.RiwayatFragment;
import com.example.aismobile.Personalia.Kerja.TrainingFragment;
import com.example.aismobile.Personalia.Penggajian.PenggajianFragment;
import com.example.aismobile.Personalia.Penggajian.PotonganKaryawanFragment;
import com.example.aismobile.R;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PersonaliaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        CheckClockFragment.OnListFragmentInteractionListener, CutiFragment.OnListFragmentInteractionListener,
        PendidikanFragment.OnListFragmentInteractionListener, KeluargaFragment.OnListFragmentInteractionListener,
        TrainingFragment.OnListFragmentInteractionListener, RiwayatFragment.OnListFragmentInteractionListener,
        ExperienceFragment.OnListFragmentInteractionListener, PrestasiFragment.OnListFragmentInteractionListener,
        NoticeFragment.OnListFragmentInteractionListener, HistoryContactFragment.OnListFragmentInteractionListener,
        HariLiburFragment.OnListFragmentInteractionListener, KaryawanFragment.OnListFragmentInteractionListener,
        DepartemenFragment.OnListFragmentInteractionListener, JenjangKaryawanFragment.OnListFragmentInteractionListener {

    FragmentTransaction fragmentTransaction;
    String access = "";
    private Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalia);

        Toolbar toolbar = findViewById(R.id.toolbar_personalia);
        setSupportActionBar(toolbar);

        myDialog = new Dialog(this);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout_personalia);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_personalia);
        navigationView.setNavigationItemSelectedListener(this);

        access = getIntent().getStringExtra("access");
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
        if (id == R.id.nav_kerja && access.toLowerCase().contains("attendance".toLowerCase())) {
            KerjaFragment mainFragment = KerjaFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_penggajian && access.toLowerCase().contains("payroll".toLowerCase())) {
            PenggajianFragment mainFragment = PenggajianFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_kalender && access.toLowerCase().contains("calendar".toLowerCase())) {
            KalenderFragment mainFragment = KalenderFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_karyawan && access.toLowerCase().contains("employee".toLowerCase())) {
            KaryawanFragment mainFragment = KaryawanFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_departemen && access.toLowerCase().contains("department".toLowerCase())) {
            DepartemenFragment mainFragment = DepartemenFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_jenjangkaryawan && access.toLowerCase().contains("employee_grade".toLowerCase())) {
            JenjangKaryawanFragment mainFragment = JenjangKaryawanFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_pangkat && access.toLowerCase().contains("job_title".toLowerCase())) {
            PangkatFragment mainFragment = PangkatFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_jabatan && access.toLowerCase().contains("job_grade".toLowerCase())) {
            JabatanFragment mainFragment = JabatanFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_news && access.toLowerCase().contains("news".toLowerCase())) {
            NewsFragment mainFragment = NewsFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_report && access.toLowerCase().contains("employee_report".toLowerCase())) {
            ReportFragment mainFragment = ReportFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else {
            KaryawanFragment mainFragment = KaryawanFragment.newInstance();
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
    public void onListFragmentInteraction(Fullname item) {

    }

    @Override
    public void onListFragmentInteraction(EmployeeAchievement item) {

    }

    @Override
    public void onListFragmentInteraction(EmployeeNotice item) {

    }

    @Override
    public void onListFragmentInteraction(HistoryContract item) {

    }

    @Override
    public void onListFragmentInteraction(HariLibur item) {

    }

    @Override
    public void onListFragmentInteraction(Employee item) {

    }

    @Override
    public void onListFragmentInteraction(Department item) {

    }

    @Override
    public void onListFragmentInteraction(EmployeeGrade item) {

    }
}
