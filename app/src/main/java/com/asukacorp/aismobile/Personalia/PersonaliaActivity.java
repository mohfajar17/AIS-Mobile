package com.asukacorp.aismobile.Personalia;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
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
import com.asukacorp.aismobile.Data.Personalia.Allowance;
import com.asukacorp.aismobile.Data.Personalia.Deduction;
import com.asukacorp.aismobile.Data.Personalia.Department;
import com.asukacorp.aismobile.Data.Personalia.Employee.Employee;
import com.asukacorp.aismobile.Data.Personalia.EmployeeAchievement;
import com.asukacorp.aismobile.Data.Personalia.EmployeeDeduction;
import com.asukacorp.aismobile.Data.Personalia.EmployeeGrade;
import com.asukacorp.aismobile.Data.Personalia.EmployeeGradeAllowance;
import com.asukacorp.aismobile.Data.Personalia.EmployeeNotice;
import com.asukacorp.aismobile.Data.Personalia.EmployeeReport;
import com.asukacorp.aismobile.Data.Personalia.Fullname;
import com.asukacorp.aismobile.Data.Personalia.HariLibur;
import com.asukacorp.aismobile.Data.Personalia.HistoryContract;
import com.asukacorp.aismobile.Data.Personalia.JobTitle;
import com.asukacorp.aismobile.Data.Personalia.Kabupaten;
import com.asukacorp.aismobile.Data.Personalia.LateDeduction;
import com.asukacorp.aismobile.Data.Personalia.MaritalStatus;
import com.asukacorp.aismobile.Data.Personalia.PersonNews;
import com.asukacorp.aismobile.Data.Personalia.Provinsi;
import com.asukacorp.aismobile.Data.Personalia.SalaryCorrection;
import com.asukacorp.aismobile.Data.Personalia.SalaryGrade;
import com.asukacorp.aismobile.Data.Project.TunjanganKaryawan;
import com.asukacorp.aismobile.Data.Project.TunjanganTemporary;
import com.asukacorp.aismobile.LoginActivity;
import com.asukacorp.aismobile.News.NewsActivity;
import com.asukacorp.aismobile.Personalia.Departemen.DepartemenFragment;
import com.asukacorp.aismobile.Personalia.JenjangKaryawan.JenjangKaryawanFragment;
import com.asukacorp.aismobile.Personalia.Karyawan.KaryawanFragment;
import com.asukacorp.aismobile.Personalia.Kerja.CheckClockFragment;
import com.asukacorp.aismobile.Personalia.Kerja.CutiFragment;
import com.asukacorp.aismobile.Personalia.Kerja.ExperienceFragment;
import com.asukacorp.aismobile.Personalia.Kerja.HariLiburFragment;
import com.asukacorp.aismobile.Personalia.Kerja.HistoryContactFragment;
import com.asukacorp.aismobile.Personalia.Kerja.KeluargaFragment;
import com.asukacorp.aismobile.Personalia.Kerja.KerjaFragment;
import com.asukacorp.aismobile.Personalia.Kerja.NoticeFragment;
import com.asukacorp.aismobile.Personalia.Kerja.PendidikanFragment;
import com.asukacorp.aismobile.Personalia.Kerja.PrestasiFragment;
import com.asukacorp.aismobile.Personalia.Kerja.RiwayatFragment;
import com.asukacorp.aismobile.Personalia.Kerja.TrainingFragment;
import com.asukacorp.aismobile.Personalia.Pangkat.PangkatFragment;
import com.asukacorp.aismobile.Personalia.Penggajian.GolonganGajiFragment;
import com.asukacorp.aismobile.Personalia.Penggajian.KabupatenFragment;
import com.asukacorp.aismobile.Personalia.Penggajian.LateDeductionFragment;
import com.asukacorp.aismobile.Personalia.Penggajian.PenggajianFragment;
import com.asukacorp.aismobile.Personalia.Penggajian.PotonganFragment;
import com.asukacorp.aismobile.Personalia.Penggajian.PotonganKaryawanFragment;
import com.asukacorp.aismobile.Personalia.Penggajian.ProvinsiFragment;
import com.asukacorp.aismobile.Personalia.Penggajian.SalaryCorrectionFragment;
import com.asukacorp.aismobile.Personalia.Penggajian.SisaCutiFragment;
import com.asukacorp.aismobile.Personalia.Penggajian.StatusKawinFragment;
import com.asukacorp.aismobile.Personalia.Penggajian.TunjanganFragment;
import com.asukacorp.aismobile.Personalia.Penggajian.TunjanganJenjangFragment;
import com.asukacorp.aismobile.Personalia.Penggajian.TunjanganKaryawanFragment;
import com.asukacorp.aismobile.Personalia.Penggajian.TunjanganTemporaryFragment;
import com.asukacorp.aismobile.Personalia.Report.ReportFragment;
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

public class PersonaliaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        CheckClockFragment.OnListFragmentInteractionListener, CutiFragment.OnListFragmentInteractionListener,
        PendidikanFragment.OnListFragmentInteractionListener, KeluargaFragment.OnListFragmentInteractionListener,
        TrainingFragment.OnListFragmentInteractionListener, RiwayatFragment.OnListFragmentInteractionListener,
        ExperienceFragment.OnListFragmentInteractionListener, PrestasiFragment.OnListFragmentInteractionListener,
        NoticeFragment.OnListFragmentInteractionListener, HistoryContactFragment.OnListFragmentInteractionListener,
        HariLiburFragment.OnListFragmentInteractionListener, KaryawanFragment.OnListFragmentInteractionListener,
        DepartemenFragment.OnListFragmentInteractionListener, JenjangKaryawanFragment.OnListFragmentInteractionListener,
        PangkatFragment.OnListFragmentInteractionListener, JabatanFragment.OnListFragmentInteractionListener,
        NewsFragment.OnListFragmentInteractionListener, ReportFragment.OnListFragmentInteractionListener,
        PotonganKaryawanFragment.OnListFragmentInteractionListener, TunjanganKaryawanFragment.OnListFragmentInteractionListener,
        TunjanganTemporaryFragment.OnListFragmentInteractionListener, TunjanganJenjangFragment.OnListFragmentInteractionListener,
        PotonganFragment.OnListFragmentInteractionListener, TunjanganFragment.OnListFragmentInteractionListener,
        GolonganGajiFragment.OnListFragmentInteractionListener, StatusKawinFragment.OnListFragmentInteractionListener,
        SalaryCorrectionFragment.OnListFragmentInteractionListener, LateDeductionFragment.OnListFragmentInteractionListener,
        SisaCutiFragment.OnListFragmentInteractionListener, KabupatenFragment.OnListFragmentInteractionListener,
        ProvinsiFragment.OnListFragmentInteractionListener {

    FragmentTransaction fragmentTransaction;
    String access = "";
    private Dialog myDialog;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalia);

        Toolbar toolbar = findViewById(R.id.toolbar_personalia);
        setSupportActionBar(toolbar);

        myDialog = new Dialog(this);
        sharedPrefManager = new SharedPrefManager(this);

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
//        else if (menu == 2)
//            swapFragment(R.id.nav_kalender);
        else if (menu == 2)
            swapFragment(R.id.nav_karyawan);
        else if (menu == 3)
            swapFragment(R.id.nav_departemen);
        else if (menu == 4)
            swapFragment(R.id.nav_jenjangkaryawan);
        else if (menu == 5)
            swapFragment(R.id.nav_pangkat);
        else if (menu == 6)
            swapFragment(R.id.nav_jabatan);
        else if (menu == 7)
            swapFragment(R.id.nav_news);
        else if (menu == 8)
            swapFragment(R.id.nav_report);
        else swapFragment(R.id.nav_kerja);
    }

    private void swapFragment(int id) {
        getMobileIsActive(sharedPrefManager.getUserId());

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_kerja && access.toLowerCase().contains("attendance".toLowerCase())) {
            KerjaFragment mainFragment = KerjaFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_penggajian && access.toLowerCase().contains("payroll".toLowerCase())) {
            PenggajianFragment mainFragment = PenggajianFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
//        } else if (id == R.id.nav_kalender && access.toLowerCase().contains("calendar".toLowerCase())) {
//            KalenderFragment mainFragment = KalenderFragment.newInstance();
//            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
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
            Intent bukaMenuActivity = new Intent(PersonaliaActivity.this, NewsActivity.class);
            startActivityForResult(bukaMenuActivity,1);
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

    private void getMobileIsActive(final String userId) {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_MOBILE_IS_ACTIVE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int akses = jsonObject.getInt("is_mobile");
                    if (akses > 1){
                        Intent logout = new Intent(PersonaliaActivity.this, LoginActivity.class);
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
                Toast.makeText(PersonaliaActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", userId);
                return param;
            }
        };
        Volley.newRequestQueue(PersonaliaActivity.this).add(request);
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

    @Override
    public void onListFragmentInteraction(JobTitle item) {

    }

    @Override
    public void onListFragmentInteraction(PersonNews item) {

    }

    @Override
    public void onListFragmentInteraction(EmployeeReport item) {

    }

    @Override
    public void onListFragmentInteraction(EmployeeDeduction item) {

    }

    @Override
    public void onListFragmentInteraction(TunjanganKaryawan item) {

    }

    @Override
    public void onListFragmentInteraction(TunjanganTemporary item) {

    }

    @Override
    public void onListFragmentInteraction(EmployeeGradeAllowance item) {

    }

    @Override
    public void onListFragmentInteraction(Deduction item) {

    }

    @Override
    public void onListFragmentInteraction(Allowance item) {

    }

    @Override
    public void onListFragmentInteraction(SalaryGrade item) {

    }

    @Override
    public void onListFragmentInteraction(MaritalStatus item) {

    }

    @Override
    public void onListFragmentInteraction(SalaryCorrection item) {

    }

    @Override
    public void onListFragmentInteraction(LateDeduction item) {

    }

    @Override
    public void onListFragmentInteraction(Kabupaten item) {

    }

    @Override
    public void onListFragmentInteraction(Provinsi item) {

    }
}
