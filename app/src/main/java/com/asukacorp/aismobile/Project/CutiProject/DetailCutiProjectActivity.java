package com.asukacorp.aismobile.Project.CutiProject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.Data.Project.Cuti;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DetailCutiProjectActivity extends AppCompatActivity {

    private Cuti cutis;

    private TextView detailCutiNumber;
    private TextView detailCutiNamaKaryawan;
    private TextView detailCutiTglPengajuan;
    private TextView detailCutiAlamat;
    private TextView detailCutiPhone;
    private TextView detailCutiStatus;
    private TextView detailCutiTglDisetujui;
    private TextView detailCutiSisa;
    private TextView detailCutiKaryawan;
    private TextView detailCutiKategori;
    private TextView detailCutiTglAwal;
    private TextView detailCutiTglAkhir;
    private TextView detailCutiTglKerja;
    private TextView detailCutiPengganti;
    private TextView detailCutiApproved;
    private Spinner editApproval1;
    private TextView detailCutiNotes;
    private TextView detailCutiApprovedBy;
    private TextView detailCutiApprovedDate;
    private TextView detailCutiProcessedBy;
    private TextView detailCutiProcessedDate;
    private TextView detailCutiCreateBy;
    private TextView detailCutiCreateDate;
    private TextView detailCutiModifiedBy;
    private TextView detailCutiModifiedDate;
    private RelativeLayout layoutSisaCuta;
    private ImageView buttonBack;

    private int sisaCuti = 0;
    private int code = 0, akses1 = 0, akses2 = 0, approval = 0, approve1 = 0, approve2 = 0;
    private ArrayAdapter<String> adapterApproval;
    private LinearLayout layoutApproval;
    private TextView btnApprove1;
//    private TextView btnApprove2;
    private TextView btnSaveApprove;
    private SharedPrefManager sharedPrefManager;
    private ProgressDialog progressDialog;
    private ViewGroup.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cuti_project);

        Bundle bundle = getIntent().getExtras();
        cutis = bundle.getParcelable("detail");
        code = bundle.getInt("code");
        sharedPrefManager = new SharedPrefManager(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        layoutApproval = (LinearLayout) findViewById(R.id.layoutApproval);
        if (code > 0){
            params = layoutApproval.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutApproval.setLayoutParams(params);
            loadAccessApproval();
        }

        layoutSisaCuta = (RelativeLayout) findViewById(R.id.layoutSisaCuta);
        if (!cutis.getLeave_category_name().matches("Cuti Tahunan") || Integer.valueOf(cutis.getIs_approved())!=1 || code > 0){
            params = layoutSisaCuta.getLayoutParams();
            params.height = 0;
            layoutSisaCuta.setLayoutParams(params);
        }

        detailCutiNumber = (TextView) findViewById(R.id.detailCutiNumber);
        detailCutiNamaKaryawan = (TextView) findViewById(R.id.detailCutiNamaKaryawan);
        detailCutiTglPengajuan = (TextView) findViewById(R.id.detailCutiTglPengajuan);
        detailCutiAlamat = (TextView) findViewById(R.id.detailCutiAlamat);
        detailCutiPhone = (TextView) findViewById(R.id.detailCutiPhone);
        detailCutiStatus = (TextView) findViewById(R.id.detailCutiStatus);
        detailCutiTglDisetujui = (TextView) findViewById(R.id.detailCutiTglDisetujui);
        detailCutiSisa = (TextView) findViewById(R.id.detailCutiSisa);
        detailCutiKaryawan = (TextView) findViewById(R.id.detailCutiKaryawan);
        detailCutiKategori = (TextView) findViewById(R.id.detailCutiKategori);
        detailCutiTglAwal = (TextView) findViewById(R.id.detailCutiTglAwal);
        detailCutiTglAkhir = (TextView) findViewById(R.id.detailCutiTglAkhir);
        detailCutiTglKerja = (TextView) findViewById(R.id.detailCutiTglKerja);
        detailCutiPengganti = (TextView) findViewById(R.id.detailCutiPengganti);
        detailCutiApproved = (TextView) findViewById(R.id.detailCutiApproved);
        detailCutiNotes = (TextView) findViewById(R.id.detailCutiNotes);
        detailCutiApprovedBy = (TextView) findViewById(R.id.detailCutiApprovedBy);
        detailCutiApprovedDate = (TextView) findViewById(R.id.detailCutiApprovedDate);
        detailCutiProcessedBy = (TextView) findViewById(R.id.detailCutiProcessedBy);
        detailCutiProcessedDate = (TextView) findViewById(R.id.detailCutiProcessedDate);
        detailCutiCreateBy = (TextView) findViewById(R.id.detailCutiCreateBy);
        detailCutiCreateDate = (TextView) findViewById(R.id.detailCutiCreateDate);
        detailCutiModifiedBy = (TextView) findViewById(R.id.detailCutiModifiedBy);
        detailCutiModifiedDate = (TextView) findViewById(R.id.detailCutiModifiedDate);
        buttonBack = (ImageView) findViewById(R.id.buttonBack);

        detailCutiNumber.setText(cutis.getEmployee_leave_number());
        detailCutiNamaKaryawan.setText(cutis.getEmployee());
        detailCutiTglPengajuan.setText(cutis.getProposed_date());
        detailCutiAlamat.setText(cutis.getAddress_leave());
        detailCutiPhone.setText(cutis.getPhone_leave());
        detailCutiStatus.setText(cutis.getStatus());
        detailCutiTglDisetujui.setText(cutis.getApproved_date());
        if (Integer.valueOf(cutis.getSisa_cuti())>0)
            sisaCuti = Integer.valueOf(cutis.getSisa_cuti());
        detailCutiSisa.setText(sisaCuti + " Hari");
        detailCutiKaryawan.setText(cutis.getEmployee());
        detailCutiKategori.setText(cutis.getLeave_category_name());
        detailCutiTglAwal.setText(cutis.getStart_leave());
        detailCutiTglAkhir.setText(cutis.getDate_leave());
        detailCutiTglKerja.setText(cutis.getWork_date());
        detailCutiPengganti.setText(cutis.getSubtitute_on_leave());
        if (Integer.valueOf(cutis.getIs_approved())==1)
            detailCutiApproved.setText("Ya");
        else if (Integer.valueOf(cutis.getIs_approved())==2)
            detailCutiApproved.setText("Tidak");
        else detailCutiApproved.setText("-");
        detailCutiNotes.setText(cutis.getNotes());
        detailCutiApprovedBy.setText(cutis.getApprover());
        detailCutiApprovedDate.setText(cutis.getApprover_date());
        detailCutiProcessedBy.setText(cutis.getProcessed_by());
        detailCutiProcessedDate.setText(cutis.getProcessed_date());
        detailCutiCreateBy.setText(cutis.getCreated_by());
        detailCutiCreateDate.setText(cutis.getCreated_date());
        detailCutiModifiedBy.setText(cutis.getModified_by());
        detailCutiModifiedDate.setText(cutis.getModified_date());

        btnApprove1 = (TextView) findViewById(R.id.btnApprove1);
//        btnApprove2 = (TextView) findViewById(R.id.btnApprove2);
        editApproval1 = (Spinner) findViewById(R.id.editApproval1);

        final String[] approve = {"Approved", "Reject", "-"};
        adapterApproval = new ArrayAdapter<String>(DetailCutiProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, approve);
        editApproval1.setAdapter(adapterApproval);

        btnApprove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                if (approval != 1){
                    btnApprove1.setBackgroundResource(R.drawable.circle_red);
                    approval = 1;

                    params =  editApproval1.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    editApproval1.setLayoutParams(params);
                    params =  detailCutiApproved.getLayoutParams();
                    params.height = 0;
                    detailCutiApproved.setLayoutParams(params);
                } else {
                    approval = 0;

                    params =  editApproval1.getLayoutParams();
                    params.height = 0;
                    editApproval1.setLayoutParams(params);
                    params =  detailCutiApproved.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    detailCutiApproved.setLayoutParams(params);
                }
            }
        });
//        btnApprove2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Integer.valueOf(cutis.getIs_approved())>0){
//                    changeColor();
//                    if (approval != 2){
//                        btnApprove2.setBackgroundResource(R.drawable.circle_red);
//                        approval = 2;
//                    } else approval = 0;
//
//                } else Toast.makeText(DetailCutiProjectActivity.this, "", Toast.LENGTH_LONG).show();
//            }
//        });

        btnSaveApprove = (TextView) findViewById(R.id.btnSaveApprove);
        btnSaveApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editApproval1.getSelectedItemPosition()<2){
                    if (akses1 > 0){
                        if (akses2 > 0){
                            if (editApproval1.getSelectedItemPosition()==0)
                                approve1 = 1;
                            else approve1 = 2;
                            updateApproval();
                        } else Toast.makeText(DetailCutiProjectActivity.this, "Only for Director", Toast.LENGTH_LONG).show();
                    } else Toast.makeText(DetailCutiProjectActivity.this, "You are not Head Department from this employee", Toast.LENGTH_LONG).show();
                } else Toast.makeText(DetailCutiProjectActivity.this, "You didn't change anything", Toast.LENGTH_LONG).show();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void changeColor(){
        btnApprove1.setBackgroundResource(R.drawable.circle_green);
    }

    private void loadAccessApproval() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_ALLOW, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    akses1 = jsonObject.getInt("access1");
                    akses2 = jsonObject.getInt("access2");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailCutiProjectActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user", sharedPrefManager.getUserId());
                param.put("id", "" + cutis.getEmployee_leave_id());
                param.put("code", "8");
                return param;
            }
        };
        Volley.newRequestQueue(DetailCutiProjectActivity.this).add(request);
    }

    private void updateApproval() {
        sisaCuti = sisaCuti - 1;
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_APPROVAL_CUTI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        params =  editApproval1.getLayoutParams();
                        params.height = 0;
                        editApproval1.setLayoutParams(params);
                        params =  detailCutiApproved.getLayoutParams();
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                        detailCutiApproved.setLayoutParams(params);

                        detailCutiApproved.setText(editApproval1.getSelectedItem().toString());
                        detailCutiApprovedBy.setText(sharedPrefManager.getUserDisplayName());
                        Date currentTime = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                        detailCutiApprovedDate.setText(df.format(currentTime));

                        Toast.makeText(DetailCutiProjectActivity.this,"Success approve employee leave", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DetailCutiProjectActivity.this,"Filed approve employee leave", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(DetailCutiProjectActivity.this,"Error update data", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(DetailCutiProjectActivity.this,"Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("id", cutis.getEmployee_leave_id());
                param.put("approve", "" + approve1);
                param.put("user", sharedPrefManager.getUserId());
                param.put("sisaCuti", "" + sisaCuti);
                return param;
            }
        };
        Volley.newRequestQueue(DetailCutiProjectActivity.this).add(request);
    }
}