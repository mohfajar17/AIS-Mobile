package com.asukacorp.aismobile.Project;

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
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.LoginActivity;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProjectMenuActivity extends AppCompatActivity {

    private TextView textViewJobOrder;
    private TextView textViewWorkCompletion;
    private TextView textViewMaterial;
    private TextView textViewTools;
    private TextView textViewWork;
    private TextView textViewPengambilan;
    private TextView textViewSpkl;
    private TextView textViewProposed;
    private TextView textViewCashProject;
    private TextView textViewCutiKaryawan;
    private TextView textViewTunjanganKaryawan;
    private TextView textViewTunjanganTemporary;

    private Dialog myDialog;
    private String access = "";
    private SharedPrefManager sharedPrefManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_menu);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Refresh Data");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        myDialog = new Dialog(this);

        textViewJobOrder = (TextView) findViewById(R.id.textViewJobOrder);
        textViewWorkCompletion = (TextView) findViewById(R.id.textViewWorkCompletion);
        textViewMaterial = (TextView) findViewById(R.id.textViewMaterial);
        textViewTools = (TextView) findViewById(R.id.textViewTools);
        textViewWork = (TextView) findViewById(R.id.textViewWork);
        textViewPengambilan = (TextView) findViewById(R.id.textViewPengambilan);
        textViewSpkl = (TextView) findViewById(R.id.textViewSpkl);
        textViewProposed = (TextView) findViewById(R.id.textViewProposed);
        textViewCashProject = (TextView) findViewById(R.id.textViewCashProject);
        textViewCutiKaryawan = (TextView) findViewById(R.id.textViewCutiKaryawan);
        textViewTunjanganKaryawan = (TextView) findViewById(R.id.textViewTunjanganKaryawan);
        textViewTunjanganTemporary = (TextView) findViewById(R.id.textViewTunjanganTemporary);

        textViewJobOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("job_order".toLowerCase())){
                    bukaActivity("0");
                } else ShowPopup("Job Order");
            }
        });
        textViewWorkCompletion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("job_progress_report".toLowerCase())){
                    bukaActivity("1");
                } else ShowPopup("Work Completion");
            }
        });
        textViewMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("material_request".toLowerCase())){
                    bukaActivity("2");
                } else ShowPopup("Material Request");
            }
        });
        textViewTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("resources_request".toLowerCase())){
                    bukaActivity("3");
                } else ShowPopup("Tools Request");
            }
        });
        textViewWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("work_order".toLowerCase())){
                    bukaActivity("4");
                } else ShowPopup("Work Request");
            }
        });
        textViewPengambilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("pickup".toLowerCase())){
                    bukaActivity("5");
                } else ShowPopup("Pengambilan");
            }
        });
        textViewSpkl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("spkl".toLowerCase())){
                    bukaActivity("6");
                } else ShowPopup("Surat Perintah Kerja Lembut");
            }
        });
        textViewProposed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("cash_advance".toLowerCase())){
                    bukaActivity("7");
                } else ShowPopup("Proposed Budget");
            }
        });
        textViewCashProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("respons_advance".toLowerCase())){
                    bukaActivity("8");
                } else ShowPopup("Cash Project Report");
            }
        });
        textViewCutiKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("employee_leave".toLowerCase())){
                    bukaActivity("9");
                } else ShowPopup("Cuti Karyawan");
            }
        });
        textViewTunjanganKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("employee_allowance".toLowerCase())){
                    bukaActivity("10");
                } else ShowPopup("Tunjangan Karyawan");
            }
        });
        textViewTunjanganTemporary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("employee_allowance_temp".toLowerCase())){
                    bukaActivity("11");
                } else ShowPopup("Tunjangan Temporary");
            }
        });

        getAccessModul();
    }

    private void bukaActivity(String menu){
        Intent bukaActivity = new Intent(ProjectMenuActivity.this, ProjectActivity.class);
        bukaActivity.putExtra("menu", menu);
        bukaActivity.putExtra("access", access);
        startActivityForResult(bukaActivity,1);
        finish();
    }

    private void getAccessModul() {
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_ACCESS_PROJECT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if (jsonObject.getInt("is_mobile") > 1){
                        Intent logout = new Intent(ProjectMenuActivity.this, LoginActivity.class);
                        startActivity(logout);
                        sharedPrefManager.logout();
                        finish();
                    } else {
                        if(status==1){
                            JSONObject jsonData = jsonObject.getJSONObject("data");
                            if (Integer.valueOf(jsonData.getString("job_order")) == 1)
                                access = access+"job_order, ";
                            if (Integer.valueOf(jsonData.getString("job_progress_report")) == 1)
                                access = access+"job_progress_report, ";
                            if (Integer.valueOf(jsonData.getString("material_request")) == 1)
                                access = access+"material_request, ";
                            if (Integer.valueOf(jsonData.getString("resources_request")) == 1)
                                access = access+"resources_request, ";
                            if (Integer.valueOf(jsonData.getString("work_order")) == 1)
                                access = access+"work_order, ";
                            if (Integer.valueOf(jsonData.getString("pickup")) == 1)
                                access = access+"pickup, ";
                            if (Integer.valueOf(jsonData.getString("spkl")) == 1)
                                access = access+"spkl, ";
                            if (Integer.valueOf(jsonData.getString("cash_advance")) == 1)
                                access = access+"cash_advance, ";
                            if (Integer.valueOf(jsonData.getString("respons_advance")) == 1)
                                access = access+"respons_advance, ";
                            if (Integer.valueOf(jsonData.getString("employee_allowance")) == 1)
                                access = access+"employee_allowance, ";
                            if (Integer.valueOf(jsonData.getString("employee_allowance_temp")) == 1)
                                access = access+"employee_allowance_temp, ";
                            access = access+"employee_leave, ";
                        } else {
                            access = access+"";
                        }
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
                Toast.makeText(ProjectMenuActivity.this, "Your network is broken, please check your network", Toast.LENGTH_LONG).show();
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
