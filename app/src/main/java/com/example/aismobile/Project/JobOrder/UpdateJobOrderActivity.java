package com.example.aismobile.Project.JobOrder;

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
import com.example.aismobile.Config;
import com.example.aismobile.Data.Project.JobOrder;
import com.example.aismobile.R;
import com.example.aismobile.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UpdateJobOrderActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private SharedPrefManager sharedPrefManager;
    private JobOrder jobOrder;

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

    private String[] JOType = {"-- Pilih Tipe Job Order --", "Internal", "External"};
    private String[] JOCategory = {"-- Pilih Job Order Category --", "Repair", "Replacement", "Modification", "Fabrication", "Others"};
    private String[] JOJobCodeStatus = {"-- Pilih Job Code Status --", "Progress", "Finish", "Complete", "Cancel"};
    private String[] JOJenisPajak = {"-- Pilih Jenis Pajak --", "PPN (10%)", "Bebas Pajak", "PPH 23 (2%)", "PPH 23 (4%)", "PPH 21 (2,5%)",
            "PPH 21 (3%)", "PPH Final PSL 4 (2) 3%", "Custom", "PPH Final 10%", "PPH Final 12%",
            "PPH Final PSL 4 (2) 4%", "PPH Final Pasal 4 Ayat (2) 2%", "PPH 22 (1,5%)", "PPN 1%"};

    private String[] joIdSalesQuotation;
    private String[] joIdPic;
    private String[] joIdDepartemen;
    private String[] joCodeDepartemen;
    private String[] joIdSalesOrder;

    private String valueJoNumber;
    private String valueJoStatus;
    private String valueJoType;
    private String valueJoLocation;
    private int valueJoCategori;
    private int valueSalesQuotation;
    private int valueDepartemen;
    private int valueSupervisor;
    private int valueTaxType;
    private int valueSalesOrder;

    private int tempJoStatus, tempJoType, tempJoLocation, tempJoCategori, tempSalesQuotation, tempDepartemen, tempSupervisor, tempTaxType, tempSalesOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_job_order);

        Bundle bundle = getIntent().getExtras();
        jobOrder = bundle.getParcelable("detail");
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

        editJONomor.setText(jobOrder.getJob_order_number());
        editJOKeterangan.setText(jobOrder.getJob_order_description());
        editJOTglAwal.setText(jobOrder.getBegin_date());
        editJOTglAkhir.setText(jobOrder.getEnd_date());
        editJONilai.setText(jobOrder.getAmount());
        editJOBudgeting.setText(jobOrder.getBudgeting_amount());
        editJOMaterial.setText(jobOrder.getMaterial_amount());
        editJOTools.setText(jobOrder.getTools_amount());
        editJOManPower.setText(jobOrder.getMan_power_amount());
        editJOCod.setText(jobOrder.getCod_amount());
        editJOWorkOrder.setText(jobOrder.getWo_amount());
        editJOMaterialReturn.setText(jobOrder.getMaterial_return_amount());
        editJOProposed.setText(jobOrder.getPb_amount());
        editJOCashProject.setText(jobOrder.getCpr_amount());
        editJOExpenses.setText(jobOrder.getExpenses_amount());
        editJOCatatan.setText(jobOrder.getNotes());
        editJOClientPoNumber.setText(jobOrder.getClient_po_number());

        editJOTglAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateJobOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateJobOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editJOTglAkhir.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        simpanJobOrderBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chaeckUpdateJobOrder();
            }
        });

        loadJobOrder();
    }

    private void chaeckUpdateJobOrder() {
        if (editJOCategory.getSelectedItemPosition() == 0 || editJOPic.getSelectedItemPosition() == 0 ||
                editJOJobCodeStatus.getSelectedItemPosition() == 0 || editJOLokasi.getSelectedItemPosition() == 0 ||
                editJOTglAwal.getText().toString().matches("") || editJOTglAkhir.getText().toString().matches("") ||
                editJOJenisPajak.getSelectedItemPosition() == 0) {
            Toast.makeText(UpdateJobOrderActivity.this, "Failed, please check your data", Toast.LENGTH_LONG).show();
        } else if (editJODepartemen.getSelectedItemPosition() == 0){
            Toast.makeText(UpdateJobOrderActivity.this, "Failed, please choose department", Toast.LENGTH_LONG).show();
        } else if (editJOSalesOrder.getSelectedItemPosition() == 0){
            Toast.makeText(UpdateJobOrderActivity.this, "Failed, please choose sales order", Toast.LENGTH_LONG).show();
        } else if (editJOKeterangan.getText().toString().matches("")){
            Toast.makeText(UpdateJobOrderActivity.this, "Failed, please input job order description", Toast.LENGTH_LONG).show();
        } else {
            if (valueDepartemen == Integer.valueOf(joIdDepartemen[editJODepartemen.getSelectedItemPosition()]))
                valueJoNumber = jobOrder.getJob_order_number();
            else {
                String[] splitNumber = jobOrder.getJob_order_number().split("-");
                valueJoNumber = splitNumber[0] + "-" + joCodeDepartemen[editJODepartemen.getSelectedItemPosition()] +
                        "-" + splitNumber[2] + "-"  + splitNumber[3];
            }

            String temp;
            if (editJOSalesQuotation.getSelectedItemPosition()==0)
                temp = "1";
            else temp = joIdSalesQuotation[editJOSalesQuotation.getSelectedItemPosition()];
            final String joSalesQuotation = temp;
            final String joDepartemen = joIdDepartemen[editJODepartemen.getSelectedItemPosition()];
            final String joPic = joIdPic[editJOPic.getSelectedItemPosition()];
            final String joSalesOrder = joIdSalesOrder[editJOSalesOrder.getSelectedItemPosition()];

            StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_UPDATE_JOB_ORDER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        if(status==1){
                            Toast.makeText(UpdateJobOrderActivity.this, "Success", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        } else {
                            Toast.makeText(UpdateJobOrderActivity.this, "", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(UpdateJobOrderActivity.this, "Failed add data", Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(UpdateJobOrderActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param=new HashMap<>();
                    param.put("job_order_id", "" + jobOrder.getJob_order_id());
                    param.put("job_order_number", "" + valueJoNumber);
                    param.put("job_order_status", "" + editJOJobCodeStatus.getSelectedItem().toString());
                    param.put("job_order_type", "" + editJOType.getSelectedItem().toString());
                    param.put("job_order_category_id", "" + editJOCategory.getSelectedItemPosition());
                    param.put("job_order_description", "" + editJOKeterangan.getText().toString());
                    param.put("sales_quotation_id", "" + joSalesQuotation);
                    param.put("department_id", "" + joDepartemen);
                    param.put("supervisor", "" + joPic);
                    param.put("job_order_location", "" + editJOLokasi.getSelectedItem().toString());
                    param.put("begin_date", "" + editJOTglAwal.getText().toString());
                    param.put("end_date", "" + editJOTglAkhir.getText().toString());
                    param.put("notes", "" + editJOCatatan.getText().toString());
                    param.put("modified_by", "" + sharedPrefManager.getUserId());
                    param.put("amount", "" + editJONilai.getText().toString());
                    param.put("budgeting_amount", "" + editJOBudgeting.getText().toString());
                    param.put("material_amount", "" + editJOMaterial.getText().toString());
                    param.put("tools_amount", "" + editJOTools.getText().toString());
                    param.put("man_power_amount", "" + editJOManPower.getText().toString());
                    param.put("cod_amount", "" + editJOCod.getText().toString());
                    param.put("wo_amount", "" + editJOWorkOrder.getText().toString());
                    param.put("material_return_amount", editJOMaterialReturn.getText().toString());
                    param.put("pb_amount", "" + editJOProposed.getText().toString());
                    param.put("cpr_amount", "" + editJOCashProject.getText().toString());
                    param.put("expenses_amount", "" + editJOExpenses.getText().toString());
                    param.put("client_po_number", "" + editJOClientPoNumber.getText().toString());
                    param.put("tax_type_id", "" + editJOJenisPajak.getSelectedItemPosition());
                    param.put("sales_order_id", "" + joSalesOrder);
                    return param;
                }
            };
            Volley.newRequestQueue(this).add(request);
        }
    }

    public void loadJobOrder(){

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_DATA_JOB_ORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        valueJoStatus = jsonArray.getJSONObject(0).getString("job_order_status");
                        valueJoType = jsonArray.getJSONObject(0).getString("job_order_type");
                        valueJoLocation = jsonArray.getJSONObject(0).getString("job_order_location");
                        valueJoCategori = jsonArray.getJSONObject(0).getInt("job_order_category_id");
                        valueSalesQuotation = jsonArray.getJSONObject(0).getInt("sales_quotation_id");
                        valueDepartemen = jsonArray.getJSONObject(0).getInt("department_id");
                        valueSupervisor = jsonArray.getJSONObject(0).getInt("supervisor");
                        valueTaxType = jsonArray.getJSONObject(0).getInt("tax_type_id");
                        valueSalesOrder = jsonArray.getJSONObject(0).getInt("sales_order_id");

                        getListSalesQuotation();
                        adapter = new ArrayAdapter<String>(UpdateJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JOType);
                        editJOType.setAdapter(adapter);
                        adapter = new ArrayAdapter<String>(UpdateJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JOCategory);
                        editJOCategory.setAdapter(adapter);
                        getListEmployee();
                        getListWorkbase();
                        getListDepartmen();
                        adapter = new ArrayAdapter<String>(UpdateJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JOJobCodeStatus);
                        editJOJobCodeStatus.setAdapter(adapter);
                        getListSalesOrder();
                        adapter = new ArrayAdapter<String>(UpdateJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JOJenisPajak);
                        editJOJenisPajak.setAdapter(adapter);
                        editJOJenisPajak.setSelection(valueTaxType);
                    } else {
                        Toast.makeText(UpdateJobOrderActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    getListSalesQuotation();
                    adapter = new ArrayAdapter<String>(UpdateJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JOType);
                    editJOType.setAdapter(adapter);
                    adapter = new ArrayAdapter<String>(UpdateJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JOCategory);
                    editJOCategory.setAdapter(adapter);
                    getListEmployee();
                    getListWorkbase();
                    getListDepartmen();
                    adapter = new ArrayAdapter<String>(UpdateJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JOJobCodeStatus);
                    editJOJobCodeStatus.setAdapter(adapter);
                    getListSalesOrder();
                    adapter = new ArrayAdapter<String>(UpdateJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JOJenisPajak);
                    editJOJenisPajak.setAdapter(adapter);
                    editJOJenisPajak.setSelection(valueTaxType);

                    Toast.makeText(UpdateJobOrderActivity.this, "Error load data", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(UpdateJobOrderActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("jobOrder", "" + jobOrder.getJob_order_id());
                return param;
            }
        };
        Volley.newRequestQueue(UpdateJobOrderActivity.this).add(request);
    }

    public void getListSalesQuotation() {
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_LIST_SALES_QUOTATION_UPDATE,
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

                                    if (valueSalesQuotation == jsonArray.getJSONObject(i).getInt("sales_quotation_id"))
                                        tempSalesQuotation = i+1;
                                }
                                adapter = new ArrayAdapter<String>(UpdateJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JOSalesQuotation);
                                editJOSalesQuotation.setAdapter(adapter);
                                editJOSalesQuotation.setSelection(tempSalesQuotation);
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
        Volley.newRequestQueue(UpdateJobOrderActivity.this).add(request);
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

                                    if (valueSupervisor == jsonArray.getJSONObject(i).getInt("employee_id"))
                                        tempSupervisor = i+1;
                                }
                                adapter = new ArrayAdapter<String>(UpdateJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JOPic);
                                editJOPic.setAdapter(adapter);
                                editJOPic.setSelection(tempSupervisor);
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
        Volley.newRequestQueue(UpdateJobOrderActivity.this).add(request);
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

                                    if (valueJoLocation.toLowerCase().equals(jsonArray.getJSONObject(i).getString("company_workbase_name").toLowerCase()))
                                        tempJoLocation = i+1;
                                }
                                adapter = new ArrayAdapter<String>(UpdateJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JOLokasi);
                                editJOLokasi.setAdapter(adapter);
                                editJOLokasi.setSelection(tempJoLocation);
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
        Volley.newRequestQueue(UpdateJobOrderActivity.this).add(request);
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

                                    if (valueDepartemen == jsonArray.getJSONObject(i).getInt("department_id"))
                                        tempDepartemen = i+1;
                                }
                                adapter = new ArrayAdapter<String>(UpdateJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JODepartmen);
                                editJODepartemen.setAdapter(adapter);
                                editJODepartemen.setSelection(tempDepartemen);
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
        Volley.newRequestQueue(UpdateJobOrderActivity.this).add(request);
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

                                    if (valueSalesOrder == jsonArray.getJSONObject(i).getInt("sales_order_id"))
                                        tempSalesOrder = i+1;
                                }
                                adapter = new ArrayAdapter<String>(UpdateJobOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, JOSalesOrder);
                                editJOSalesOrder.setAdapter(adapter);
                                editJOSalesOrder.setSelection(tempSalesOrder);
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
        Volley.newRequestQueue(UpdateJobOrderActivity.this).add(request);
    }
}