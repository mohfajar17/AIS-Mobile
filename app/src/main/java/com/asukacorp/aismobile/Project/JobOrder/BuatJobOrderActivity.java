package com.asukacorp.aismobile.Project.JobOrder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BuatJobOrderActivity extends AppCompatActivity {

    private String jobOrderNumber;
    private ArrayAdapter<String> adapter;
    private SharedPrefManager sharedPrefManager;

    private ImageView buttonBack;
    private Button simpanJobOrderBaru;
    private EditText editJONomor;
    private EditText editJOKeterangan;
    private EditText editJOTglAwal;
    private EditText editJOTglAkhir;
    private EditText editJOClientPoNumber;
    private EditText editJONilai;
    private EditText editJOBudgeting;
    private EditText editJOMaterial;
    private EditText editJOTools;
    private EditText editJOManPower;
    private EditText editJOCod;
    private EditText editJOWorkOrder;
    private EditText editJOMaterialReturn;
    private EditText editJOProposed;
    private EditText editJOCashProject;
    private EditText editJOExpenses;
    private EditText editJOCatatan;

    private Spinner editJOSalesQuotation;
    private Spinner editJOType;
    private Spinner editJOCategory;
    private Spinner editJOPic;
    private Spinner editJOLokasi;
    private Spinner editJODepartemen;
    private Spinner editJOJobCodeStatus;
    private Spinner editJOSalesOrder;
    private Spinner editJOJenisPajak;

    private String[] JOType = {"Internal", "External"};
    private String[] JOCategory = {"-- Pilih Job Order Category --", "Repair", "Replacement", "Modification", "Fabrication", "Others"};
    private String[] JOJobCodeStatus = {"Progress", "Finish", "Complete", "Cancel"};
    private String[] JOJenisPajak = {"-- Pilih Jenis Pajak --", "PPN (10%)", "Bebas Pajak", "PPH 23 (2%)", "PPH 23 (4%)", "PPH 21 (2,5%)",
            "PPH 21 (3%)", "PPH Final PSL 4 (2) 3%", "Custom", "PPH Final 10%", "PPH Final 12%",
            "PPH Final PSL 4 (2) 4%", "PPH Final Pasal 4 Ayat (2) 2%", "PPH 22 (1,5%)", "PPN 1%"};

    private String[] joIdSalesQuotation;
    private String[] joIdPic;
    private String[] joIdDepartemen;
    private String[] joCodeDepartemen;
    private String[] joIdSalesOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_job_order);

        sharedPrefManager = new SharedPrefManager(this);

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        simpanJobOrderBaru = (Button) findViewById(R.id.simpanJobOrderBaru);
        editJONomor = (EditText) findViewById(R.id.editJONomor);
        editJOKeterangan = (EditText) findViewById(R.id.editJOKeterangan);
        editJOTglAwal = (EditText) findViewById(R.id.editJOTglAwal);
        editJOTglAkhir = (EditText) findViewById(R.id.editJOTglAkhir);
        editJONilai = (EditText) findViewById(R.id.editJONilai);
        editJOBudgeting = (EditText) findViewById(R.id.editJOBudgeting);
        editJOMaterial = (EditText) findViewById(R.id.editJOMaterial);
        editJOTools = (EditText) findViewById(R.id.editJOTools);
        editJOManPower = (EditText) findViewById(R.id.editJOManPower);
        editJOCod = (EditText) findViewById(R.id.editJOCod);
        editJOWorkOrder = (EditText) findViewById(R.id.editJOWorkOrder);
        editJOMaterialReturn = (EditText) findViewById(R.id.editJOMaterialReturn);
        editJOProposed = (EditText) findViewById(R.id.editJOProposed);
        editJOCashProject = (EditText) findViewById(R.id.editJOCashProject);
        editJOExpenses = (EditText) findViewById(R.id.editJOExpenses);
        editJOCatatan = (EditText) findViewById(R.id.editJOCatatan);

        editJOClientPoNumber = (EditText) findViewById(R.id.editJOClientPoNumber);
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

        simpanJobOrderBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createdJobOrder();
            }
        });

        editJOTglAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BuatJobOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editJOTglAwal.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
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
                        editJOTglAkhir.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        getJobOrderNumber();
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
                                JOSalesQuotation[0] = "-- Pilih Sales Quotation --";
                                joIdSalesQuotation = new String[jsonArray.length()+1];
                                joIdSalesQuotation[0] = "";
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JOSalesQuotation[i + 1] = jsonArray.getJSONObject(i).getString("sales_quotation_number") + " | " + jsonArray.getJSONObject(i).getString("description");
                                    joIdSalesQuotation[i + 1] = jsonArray.getJSONObject(i).getString("sales_quotation_id");
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
                                JOPic[0] = "-- Pilih PIC --";
                                joIdPic = new String[jsonArray.length()+1];
                                joIdPic[0] = "";
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JOPic[i + 1] = jsonArray.getJSONObject(i).getString("fullname") + " - " + jsonArray.getJSONObject(i).getString("job_grade_name");
                                    joIdPic[i + 1] = jsonArray.getJSONObject(i).getString("employee_id");
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
                                JOLokasi[0] = "-- Pilih Lokasi Job Order --";
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
                                JODepartmen[0] = "-- Pilih Departemen --";
                                joIdDepartemen = new String[jsonArray.length()+1];
                                joCodeDepartemen = new String[jsonArray.length()+1];
                                joIdDepartemen[0] = "1";
                                joCodeDepartemen[0] = "XXX";
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JODepartmen[i + 1] = jsonArray.getJSONObject(i).getString("department_name");
                                    joIdDepartemen[i + 1] = jsonArray.getJSONObject(i).getString("department_id");
                                    joCodeDepartemen[i + 1] = jsonArray.getJSONObject(i).getString("department_code");
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
                                JOSalesOrder[0] = "-- Pilih Sales Order --";
                                joIdSalesOrder = new String[jsonArray.length()+1];
                                joIdSalesOrder[0] = "";
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JOSalesOrder[i + 1] = jsonArray.getJSONObject(i).getString("sales_order_number")+" | "+jsonArray.getJSONObject(i).getString("short_description");
                                    joIdSalesOrder[i + 1] = jsonArray.getJSONObject(i).getString("sales_order_id");
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

    public void getJobOrderNumber(){
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_JOB_ORDER_NUMBER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            jobOrderNumber = jsonObject.getString("data");
                            editJONomor.setText("JO-XXX-" + jobOrderNumber);
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

    public void createdJobOrder(){
        if (editJOCategory.getSelectedItemPosition() == 0 || editJOPic.getSelectedItemPosition() == 0 ||
                editJOLokasi.getSelectedItemPosition() == 0 || editJOTglAwal.getText().toString().matches("") ||
                editJOTglAkhir.getText().toString().matches("") || editJOJenisPajak.getSelectedItemPosition() == 0) {
            Toast.makeText(BuatJobOrderActivity.this, "Failed, please check your data", Toast.LENGTH_LONG).show();
        } else if (editJODepartemen.getSelectedItemPosition() == 0){
            Toast.makeText(BuatJobOrderActivity.this, "Failed, please choose department", Toast.LENGTH_LONG).show();
        } else if (editJOSalesOrder.getSelectedItemPosition() == 0){
            Toast.makeText(BuatJobOrderActivity.this, "Failed, please choose sales order", Toast.LENGTH_LONG).show();
        } else if (editJOKeterangan.getText().toString().matches("")){
            Toast.makeText(BuatJobOrderActivity.this, "Failed, please input job order description", Toast.LENGTH_LONG).show();
        } else {
            String temp;
            final String joNumber = "JO-" + joCodeDepartemen[editJODepartemen.getSelectedItemPosition()] + "-" + jobOrderNumber;
            final String joType = editJOType.getSelectedItem().toString();
            final String joCategory = String.valueOf(editJOCategory.getSelectedItemPosition());
            if (editJOSalesQuotation.getSelectedItemPosition()==0)
                temp = "1";
            else temp = joIdSalesQuotation[editJOSalesQuotation.getSelectedItemPosition()];
            final String joSalesQuotation = temp;
            final String joPic = joIdPic[editJOPic.getSelectedItemPosition()];
            final String joKeterangan = editJOKeterangan.getText().toString();
            final String joLokasi = editJOLokasi.getSelectedItem().toString();
            final String joDepartemen = joIdDepartemen[editJODepartemen.getSelectedItemPosition()];
            final String joTglAwal = editJOTglAwal.getText().toString();
            final String joTglAkhir = editJOTglAkhir.getText().toString();
            final String joStatus = editJOJobCodeStatus.getSelectedItem().toString();
            final String joSalesOrder = joIdSalesOrder[editJOSalesOrder.getSelectedItemPosition()];
            if (editJOClientPoNumber.getText().toString().matches(""))
                temp = " ";
            else temp = editJOClientPoNumber.getText().toString();
            final String joClientPONumber = temp;
            final String joNilai = editJONilai.getText().toString();
            final String joBudgeting = editJOBudgeting.getText().toString();
            final String joJenisPajak = String.valueOf(editJOJenisPajak.getSelectedItemPosition());
            int joMaxPbAmount = 0;
            if (Long.valueOf(joNilai) < 10000000000L)
                joMaxPbAmount = 5000000;
            else joMaxPbAmount = 10000000;
            final int finalJoMaxPbAmount = joMaxPbAmount;
            final String joMaterial = editJOMaterial.getText().toString();
            final String joTools = editJOTools.getText().toString();
            final String joManPower = editJOManPower.getText().toString();
            final String joCod = editJOCod.getText().toString();
            final String joWorkOrder = editJOWorkOrder.getText().toString();
            final String joMaterialReturn = editJOMaterialReturn.getText().toString();
            final String joProposed = editJOProposed.getText().toString();
            final String joCashProject = editJOCashProject.getText().toString();
            final String joExpenses = editJOExpenses.getText().toString();
            final String joCatatan = editJOCatatan.getText().toString();

            StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CREATE_JOB_ORDER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        if(status==1){
                            Toast.makeText(BuatJobOrderActivity.this, "Success", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        } else {
                            Toast.makeText(BuatJobOrderActivity.this, "", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(BuatJobOrderActivity.this, "Failed add data", Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(BuatJobOrderActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param=new HashMap<>();
                    param.put("job_order_id", joNumber);
                    param.put("job_order_status", joStatus);
                    param.put("job_order_type", joType);
                    param.put("job_order_category_id", joCategory);
                    param.put("job_order_description", joKeterangan);
                    param.put("sales_quotation_id", joSalesQuotation);
                    param.put("department_id", joDepartemen);
                    param.put("supervisor", joPic);
                    param.put("job_order_location", joLokasi);
                    param.put("begin_date", joTglAwal);
                    param.put("end_date", joTglAkhir);
                    param.put("notes", joCatatan + " ");
                    param.put("created_by", sharedPrefManager.getUserId());
                    param.put("amount", joNilai);
                    param.put("budgeting_amount", joBudgeting);
                    param.put("max_pb_amount", "" + finalJoMaxPbAmount);
                    param.put("material_amount", joMaterial);
                    param.put("tools_amount", joTools);
                    param.put("man_power_amount", joManPower);
                    param.put("cod_amount", joCod);
                    param.put("wo_amount", joWorkOrder);
                    param.put("material_return_amount", joMaterialReturn);
                    param.put("pb_amount", joProposed);
                    param.put("cpr_amount", joCashProject);
                    param.put("expenses_amount", joExpenses);
                    param.put("client_po_number", joClientPONumber);
                    param.put("tax_type_id", joJenisPajak);
                    param.put("sales_order_id", joSalesOrder);
                    return param;
                }
            };
            Volley.newRequestQueue(this).add(request);
        }
    }
}