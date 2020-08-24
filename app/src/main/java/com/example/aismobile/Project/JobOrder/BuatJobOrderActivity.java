package com.example.aismobile.Project.JobOrder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aismobile.Config;
import com.example.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class BuatJobOrderActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;

    private ImageView buttonBack;
    private EditText editJOTglAwal;
    private EditText editJOTglAkhir;

    private Spinner editJOSalesQuotation;
    private Spinner editJOType;
    private Spinner editJOCategory;
    private Spinner editJOPic;
    private Spinner editJOLokasi;
    private Spinner editJODepartemen;
    private Spinner editJOJobCodeStatus;
    private Spinner editJOSalesOrder;
    private Spinner editJOJenisPajak;

    private String[] JOType = {"-", "Internal", "External"};
    private String[] JOCategory = {"-", "Repair", "Replacement", "Modification", "Fabrication", "Others"};
    private String[] JOJobCodeStatus = {"-", "Progress", "Finish", "Complete", "Cancel"};
    private String[] JOJenisPajak = {"-", "PPN (10%)", "Bebas Pajak", "PPH 23 (2%)", "PPH 23 (4%)", "PPH 21 (2,5%)",
            "PPH 21 (3%)", "PPH Final PSL 4 (2) 3%", "Custom", "PPH Final 10%", "PPH Final 12%",
            "PPH Final PSL 4 (2) 4%", "PPH Final Pasal 4 Ayat (2) 2%", "PPH 22 (1,5%)", "PPN 1%"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_job_order);

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        editJOTglAwal = (EditText) findViewById(R.id.editJOTglAwal);
        editJOTglAkhir = (EditText) findViewById(R.id.editJOTglAkhir);
        editJOSalesQuotation = (Spinner) findViewById(R.id.editJOSalesQuotation);
        editJOType = (Spinner) findViewById(R.id.editJOType);
        editJOCategory = (Spinner) findViewById(R.id.editJOCategory);
        editJOPic = (Spinner) findViewById(R.id.editJOPic);
        editJOLokasi = (Spinner) findViewById(R.id.editJOLokasi);
        editJODepartemen = (Spinner) findViewById(R.id.editJODepartemen);
        editJOJobCodeStatus = (Spinner) findViewById(R.id.editJOJobCodeStatus);
        editJOSalesOrder = (Spinner) findViewById(R.id.editJOSalesOrder);
        editJOJenisPajak = (Spinner) findViewById(R.id.editJOJenisPajak);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        editJOTglAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BuatJobOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editJOTglAwal.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        editJOTglAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BuatJobOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editJOTglAkhir.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        getListSalesQuotation();
        adapter = new ArrayAdapter<String>(BuatJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JOType);
        editJOType.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(BuatJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JOCategory);
        editJOCategory.setAdapter(adapter);
        getListEmployee();
        getListWorkbase();
        getListDepartmen();
        adapter = new ArrayAdapter<String>(BuatJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JOJobCodeStatus);
        editJOJobCodeStatus.setAdapter(adapter);
        getListSalesOrder();
        adapter = new ArrayAdapter<String>(BuatJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JOJenisPajak);
        editJOJenisPajak.setAdapter(adapter);
    }

    public void getListSalesQuotation() {
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_LIST_SALES_QUOTATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                String[] JOSalesQuotation = new String[jsonArray.length()+1];
                                JOSalesQuotation[0] = "-";
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JOSalesQuotation[i + 1] = jsonArray.getJSONObject(i).getString("sales_quotation_number") + " | " + jsonArray.getJSONObject(i).getString("description");
                                }
                                adapter = new ArrayAdapter<String>(BuatJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JOSalesQuotation);
                                editJOSalesQuotation.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
        };
        Volley.newRequestQueue(BuatJobOrderActivity.this).add(request);
    }

    public void getListEmployee() {
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_LIST_EMPLOYEE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                String[] JOPic = new String[jsonArray.length()+1];
                                JOPic[0] = "-";
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JOPic[i + 1] = jsonArray.getJSONObject(i).getString("fullname");
                                }
                                adapter = new ArrayAdapter<String>(BuatJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JOPic);
                                editJOPic.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
        };
        Volley.newRequestQueue(BuatJobOrderActivity.this).add(request);
    }

    public void getListWorkbase() {
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_LIST_WORKBASE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                String[] JOLokasi = new String[jsonArray.length()+1];
                                JOLokasi[0] = "-";
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JOLokasi[i + 1] = jsonArray.getJSONObject(i).getString("company_workbase_name");
                                }
                                adapter = new ArrayAdapter<String>(BuatJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JOLokasi);
                                editJOLokasi.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
        };
        Volley.newRequestQueue(BuatJobOrderActivity.this).add(request);
    }

    public void getListDepartmen() {
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_LIST_DEPARTMEN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                String[] JODepartmen = new String[jsonArray.length()+1];
                                JODepartmen[0] = "-";
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JODepartmen[i + 1] = jsonArray.getJSONObject(i).getString("department_name");
                                }
                                adapter = new ArrayAdapter<String>(BuatJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JODepartmen);
                                editJODepartemen.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
        };
        Volley.newRequestQueue(BuatJobOrderActivity.this).add(request);
    }

    public void getListSalesOrder() {
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_LIST_SALES_ORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                String[] JOSalesOrder = new String[jsonArray.length()+1];
                                JOSalesOrder[0] = "-";
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JOSalesOrder[i + 1] = jsonArray.getJSONObject(i).getString("sales_order_number")+" | "+jsonArray.getJSONObject(i).getString("short_description");
                                }
                                adapter = new ArrayAdapter<String>(BuatJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JOSalesOrder);
                                editJOSalesOrder.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
        };
        Volley.newRequestQueue(BuatJobOrderActivity.this).add(request);
    }
}