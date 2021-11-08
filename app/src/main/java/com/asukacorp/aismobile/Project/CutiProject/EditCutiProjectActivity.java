package com.asukacorp.aismobile.Project.CutiProject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditCutiProjectActivity extends AppCompatActivity {

    private ArrayList<String> arrayListPengganti;
    private int idPengganti = -1;
    private String[] cutiPenggantiId;
    private String[] cutiPenggantiName;

    private SharedPrefManager sharedPrefManager;
    private ProgressDialog progressDialog;
    private Dialog dialog;

    private EditText editCutiNo;
    private EditText editCutiKaryawan;
    private EditText editCutiKategori;
    private TextView editCutiPengganti;
    private EditText editCutiTglPengajuan;
    private EditText editCutiAwalCuti;
    private EditText editCutiTglCuti;
    private EditText editCutiWorkStartDate;
    private EditText editCutiLeaveAddress;
    private EditText editCutiPhone;
    private EditText editCutiNotes;
    private Button btnUpdate;

    private String idEmpLeave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cuti_project);

        Bundle bundle = getIntent().getExtras();
        idEmpLeave = bundle.getString("idEmpLeave");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        sharedPrefManager = SharedPrefManager.getInstance(this);
        arrayListPengganti = new ArrayList<>();

        editCutiNo = (EditText) findViewById(R.id.editCutiNo);
        editCutiKaryawan = (EditText) findViewById(R.id.editCutiKaryawan);
        editCutiKategori = (EditText) findViewById(R.id.editCutiKategori);
        editCutiPengganti = (TextView) findViewById(R.id.editCutiPengganti);
        editCutiTglPengajuan = (EditText) findViewById(R.id.editCutiTglPengajuan);
        editCutiAwalCuti = (EditText) findViewById(R.id.editCutiAwalCuti);
        editCutiTglCuti = (EditText) findViewById(R.id.editCutiTglCuti);
        editCutiWorkStartDate = (EditText) findViewById(R.id.editCutiWorkStartDate);
        editCutiLeaveAddress = (EditText) findViewById(R.id.editCutiLeaveAddress);
        editCutiPhone = (EditText) findViewById(R.id.editCutiPhone);
        editCutiNotes = (EditText) findViewById(R.id.editCutiNotes);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        editCutiPengganti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(EditCutiProjectActivity.this);
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                dialog.getWindow().setLayout(900, 1500);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                //initialize dialog variable
                EditText editTextSearch = dialog.findViewById(R.id.editTextSearch);
                ListView listViewSearch = dialog.findViewById(R.id.listViewSearch);
                final ArrayAdapter<String> newAdapter = new ArrayAdapter<>(EditCutiProjectActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayListPengganti);
                listViewSearch.setAdapter(newAdapter);
                editTextSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        newAdapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int count = 0;
                        while (count<cutiPenggantiName.length){
                            if (newAdapter.getItem(i).equals(cutiPenggantiName[count])){
                                idPengganti = count;
                                break;
                            } else count++;
                        }
                        editCutiPengganti.setText(newAdapter.getItem(i));
                        dialog.dismiss();
                    }
                });
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLeave();
            }
        });

        loadData();
    }

    private void loadData() {
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CUTI_UPDATE_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status")==1){
                        JSONObject jsonDataCuti = jsonObject.getJSONObject("data leave");
                        editCutiNo.setText(jsonDataCuti.getString("employee_leave_number"));
                        editCutiKaryawan.setText(jsonDataCuti.getString("employee"));
                        editCutiKategori.setText(jsonDataCuti.getString("leave_category_name"));
                        editCutiPengganti.setText(jsonDataCuti.getString("subtitute_on_leave"));
                        editCutiTglPengajuan.setText(jsonDataCuti.getString("proposed_date"));
                        editCutiAwalCuti.setText(jsonDataCuti.getString("start_leave"));
                        editCutiTglCuti.setText(jsonDataCuti.getString("date_leave"));
                        editCutiWorkStartDate.setText(jsonDataCuti.getString("work_date"));
                        editCutiLeaveAddress.setText(jsonDataCuti.getString("address_leave"));
                        editCutiPhone.setText(jsonDataCuti.getString("phone_leave"));
                        editCutiNotes.setText(jsonDataCuti.getString("notes"));

                        //set data pengganti
                        idPengganti = Integer.valueOf(jsonDataCuti.getString("subtitute_on_leave_id"));
                        JSONArray jsonArray = jsonObject.getJSONArray("data leave employee");
                        cutiPenggantiId = new String[jsonArray.length()];
                        cutiPenggantiName = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            cutiPenggantiId[i] = jsonArray.getJSONObject(i).getString("employee_id");
                            cutiPenggantiName[i] = jsonArray.getJSONObject(i).getString("fullname") + " - " + jsonArray.getJSONObject(i).getString("job_grade_name");
                            arrayListPengganti.add(jsonArray.getJSONObject(i).getString("fullname") + " - " + jsonArray.getJSONObject(i).getString("job_grade_name"));
                        }
                        Toast.makeText(EditCutiProjectActivity.this, "Success load data", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(EditCutiProjectActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(EditCutiProjectActivity.this, "Error load data", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(EditCutiProjectActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("id", idEmpLeave);
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void updateLeave() {
        progressDialog.show();
        final String penggantiId;
        if (idPengganti<0)
            penggantiId = "0";
        else penggantiId = cutiPenggantiId[idPengganti];

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CUTI_UPDATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if(status==1){
                        Toast.makeText(EditCutiProjectActivity.this, "Success update leave", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(EditCutiProjectActivity.this, "Filed update leave", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(EditCutiProjectActivity.this, "Error add data", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(EditCutiProjectActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("leaveId", idEmpLeave);
                param.put("subtituteLeave", penggantiId);
                param.put("startLeave", editCutiAwalCuti.getText().toString());
                param.put("dateLeave", editCutiTglCuti.getText().toString());
                param.put("workDate", editCutiWorkStartDate.getText().toString());
                param.put("addressLeave", editCutiLeaveAddress.getText().toString());
                param.put("phoneLeave", editCutiPhone.getText().toString());
                param.put("notes", editCutiNotes.getText().toString());
                param.put("userId", sharedPrefManager.getUserId());
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}