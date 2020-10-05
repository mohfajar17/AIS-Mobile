package com.example.aismobile.Personalia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aismobile.Config;
import com.example.aismobile.R;
import com.example.aismobile.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PersonaliaMenuActivity extends AppCompatActivity {

    private TextView textViewKerja;
    private TextView textViewPenggajian;
    private TextView textViewKalender;
    private TextView textViewKaryawan;
    private TextView textViewDepartemen;
    private TextView textViewJenjangKaryawan;
    private TextView textViewPangkat;
    private TextView textViewJabatan;
    private TextView textViewNews;
    private TextView textViewReport;

    private Dialog myDialog;
    private String access = "";
    private SharedPrefManager sharedPrefManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalia_menu);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Refresh Data");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        myDialog = new Dialog(this);

        textViewKerja = (TextView) findViewById(R.id.textViewKerja);
        textViewPenggajian = (TextView) findViewById(R.id.textViewPenggajian);
//        textViewKalender = (TextView) findViewById(R.id.textViewKalender);
        textViewKaryawan = (TextView) findViewById(R.id.textViewKaryawan);
        textViewDepartemen = (TextView) findViewById(R.id.textViewDepartemen);
        textViewJenjangKaryawan = (TextView) findViewById(R.id.textViewJenjangKaryawan);
        textViewPangkat = (TextView) findViewById(R.id.textViewPangkat);
        textViewJabatan = (TextView) findViewById(R.id.textViewJabatan);
        textViewNews = (TextView) findViewById(R.id.textViewNews);
        textViewReport = (TextView) findViewById(R.id.textViewReport);

        textViewKerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("attendance".toLowerCase())){
                    bukaActivity("0");
                } else ShowPopup("Kerja");
            }
        });
        textViewPenggajian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("payroll".toLowerCase())){
                    bukaActivity("1");
                } else ShowPopup("Penggajian");
            }
        });
//        textViewKalender.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (access.toLowerCase().contains("calendar".toLowerCase())){
//                    bukaActivity("2");
//                } else ShowPopup("Kalender");
//            }
//        });
        textViewKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("employee".toLowerCase())){
                    bukaActivity("2");
                } else ShowPopup("Karyawan");
            }
        });
        textViewDepartemen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("department".toLowerCase())){
                    bukaActivity("3");
                } else ShowPopup("Departemen");
            }
        });
        textViewJenjangKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("employee_grade".toLowerCase())){
                    bukaActivity("4");
                } else ShowPopup("Jenjang Karyawan");
            }
        });
        textViewPangkat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("job_title".toLowerCase())){
                    bukaActivity("5");
                } else ShowPopup("Pangkat");
            }
        });
        textViewJabatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("job_grade".toLowerCase())){
                    bukaActivity("6");
                } else ShowPopup("Jabatan");
            }
        });
        textViewNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("news".toLowerCase())){
                    bukaActivity("7");
                } else ShowPopup("News");
            }
        });
        textViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("employee_report".toLowerCase())){
                    bukaActivity("8");
                } else ShowPopup("Report");
            }
        });

        getAccessModul();
    }

    private void bukaActivity(String menu){
        Intent bukaActivity = new Intent(PersonaliaMenuActivity.this, PersonaliaActivity.class);
        bukaActivity.putExtra("menu", menu);
        bukaActivity.putExtra("access", access);
        startActivityForResult(bukaActivity,1);
        finish();
    }

    private void getAccessModul() {
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_ACCESS_HRD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if(status==1){
                        JSONObject jsonData = jsonObject.getJSONObject("data");
                        if (Integer.valueOf(jsonData.getString("attendance")) == 1)
                            access = access+"attendance, ";
                        if (Integer.valueOf(jsonData.getString("payroll")) == 1)
                            access = access+"payroll, ";
                        if (Integer.valueOf(jsonData.getString("calendar")) == 1)
                            access = access+"calendar, ";
                        if (Integer.valueOf(jsonData.getString("employee")) == 1)
                            access = access+"employee, ";
                        if (Integer.valueOf(jsonData.getString("department")) == 1)
                            access = access+"department, ";
                        if (Integer.valueOf(jsonData.getString("employee_grade")) == 1)
                            access = access+"employee_grade, ";
                        if (Integer.valueOf(jsonData.getString("job_title")) == 1)
                            access = access+"job_title, ";
                        if (Integer.valueOf(jsonData.getString("job_grade")) == 1)
                            access = access+"job_grade, ";
                        if (Integer.valueOf(jsonData.getString("news")) == 1)
                            access = access+"news, ";
                        if (Integer.valueOf(jsonData.getString("employee_report")) == 1)
                            access = access+"employee_report, ";
                    } else {
                        access = access+"";
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    onBackPressed();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                onBackPressed();
                Toast.makeText(PersonaliaMenuActivity.this, "Your network is broken, please check your network", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", sharedPrefManager.getUserId());
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    public void ShowPopup(String massage) {
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
        textViewWarning.setText("You can't access menu " + massage);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}
