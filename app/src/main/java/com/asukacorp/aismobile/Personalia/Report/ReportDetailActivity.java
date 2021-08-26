package com.asukacorp.aismobile.Personalia.Report;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.Data.Personalia.EmployeeReport;
import com.asukacorp.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class ReportDetailActivity extends AppCompatActivity {
    private double toDouble = 0;
    private NumberFormat formatter;
    private EmployeeReport cashOnDelivery;
    private ProgressDialog progressDialog;

    private TextView textNumber;
    private ImageView buttonBack;

    private TextView textBulandanTahun;
    private TextView textKaryawan;
    private TextView textBadge;
    private TextView textJenjangKaryawan;
    private TextView textStatusKerja;
    private TextView textNPWP;
    private TextView textNoRek;
    private TextView textJobCode;
    private TextView textJobDescription;
    private TextView textLokasiKerja;
    private TextView textPerson;
    private TextView textDiubahOleh;
    private TextView textDiubahTgl;
    private TextView textGajiPokok;
    private TextView textTunjanganTransport;
    private TextView textTunjanganMakan;
    private TextView textTunjanganKesejahteraan;
    private TextView textLembur;
    private TextView textMakanLembur;
    private TextView textTunjanganLokasi;
    private TextView textTunjPerjalananDinas;
    private TextView textTunjanganSkill;
    private TextView textPanggilanDarurat;
    private TextView textBPJSKerja;
    private TextView textBPJSKesehatan;
    private TextView textJPK;
    private TextView textKoreksi;
    private TextView textAbsent;
    private TextView textJPKPaid;
    private TextView textPotBPJSKetenagakerjaan;
    private TextView textPotBPJSKesehatan;
    private TextView textJht;
    private TextView textBpjs;
    private TextView textJaminanPensiun;
    private TextView textPph1;
    private TextView textPph2;
    private TextView textPotonganLainlain;
    private TextView textMoneyBox;
    private TextView textPotonganKoperasi;
    private TextView textPotPinjamanKoperasi;
    private TextView textPotonganK3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);

        Bundle bundle = getIntent().getExtras();
        cashOnDelivery = bundle.getParcelable("detail");

        formatter = new DecimalFormat("#,###");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        textBulandanTahun = (TextView) findViewById(R.id.textBulandanTahun);
        textKaryawan = (TextView) findViewById(R.id.textKaryawan);
        textBadge = (TextView) findViewById(R.id.textBadge);
        textJenjangKaryawan = (TextView) findViewById(R.id.textJenjangKaryawan);
        textStatusKerja = (TextView) findViewById(R.id.textStatusKerja);
        textNPWP = (TextView) findViewById(R.id.textNPWP);
        textNoRek = (TextView) findViewById(R.id.textNoRek);
        textJobCode = (TextView) findViewById(R.id.textJobCode);
        textJobDescription = (TextView) findViewById(R.id.textJobDescription);
        textLokasiKerja = (TextView) findViewById(R.id.textLokasiKerja);
        textPerson = (TextView) findViewById(R.id.textPerson);
        textDiubahOleh = (TextView) findViewById(R.id.textDiubahOleh);
        textDiubahTgl = (TextView) findViewById(R.id.textDiubahTgl);
        textGajiPokok = (TextView) findViewById(R.id.textGajiPokok);
        textTunjanganTransport = (TextView) findViewById(R.id.textTunjanganTransport);
        textTunjanganMakan = (TextView) findViewById(R.id.textTunjanganMakan);
        textTunjanganKesejahteraan = (TextView) findViewById(R.id.textTunjanganKesejahteraan);
        textLembur = (TextView) findViewById(R.id.textLembur);
        textMakanLembur = (TextView) findViewById(R.id.textMakanLembur);
        textTunjanganLokasi = (TextView) findViewById(R.id.textTunjanganLokasi);
        textTunjPerjalananDinas = (TextView) findViewById(R.id.textTunjPerjalananDinas);
        textTunjanganSkill = (TextView) findViewById(R.id.textTunjanganSkill);
        textPanggilanDarurat = (TextView) findViewById(R.id.textPanggilanDarurat);
        textBPJSKerja = (TextView) findViewById(R.id.textBPJSKerja);
        textBPJSKesehatan = (TextView) findViewById(R.id.textBPJSKesehatan);
        textJPK = (TextView) findViewById(R.id.textJPK);
        textKoreksi = (TextView) findViewById(R.id.textKoreksi);
        textAbsent = (TextView) findViewById(R.id.textAbsent);
        textJPKPaid = (TextView) findViewById(R.id.textJPKPaid);
        textPotBPJSKetenagakerjaan = (TextView) findViewById(R.id.textPotBPJSKetenagakerjaan);
        textPotBPJSKesehatan = (TextView) findViewById(R.id.textPotBPJSKesehatan);
        textJht = (TextView) findViewById(R.id.textJht);
        textBpjs = (TextView) findViewById(R.id.textBpjs);
        textJaminanPensiun = (TextView) findViewById(R.id.textJaminanPensiun);
        textPph1 = (TextView) findViewById(R.id.textPph1);
        textPph2 = (TextView) findViewById(R.id.textPph2);
        textPotonganLainlain = (TextView) findViewById(R.id.textPotonganLainlain);
        textMoneyBox = (TextView) findViewById(R.id.textMoneyBox);
        textPotonganKoperasi = (TextView) findViewById(R.id.textPotonganKoperasi);
        textPotPinjamanKoperasi = (TextView) findViewById(R.id.textPotPinjamanKoperasi);
        textPotonganK3 = (TextView) findViewById(R.id.textPotonganK3);

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);

        textNumber.setText("Employee Report #" + cashOnDelivery.getEmployee_report_id());
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loadDetail();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_EMPLOYEE_REPORT_DETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        textBulandanTahun.setText(jsonArray.getJSONObject(0).getString("month_year"));
                        textKaryawan.setText(jsonArray.getJSONObject(0).getString("employee"));
                        textBadge.setText(jsonArray.getJSONObject(0).getString("employee_number"));
                        textJenjangKaryawan.setText(jsonArray.getJSONObject(0).getString("employee_grade"));
                        textStatusKerja.setText(jsonArray.getJSONObject(0).getString("employee_status"));
                        textNPWP.setText(jsonArray.getJSONObject(0).getString("npwp"));
                        textNoRek.setText(jsonArray.getJSONObject(0).getString("no_rek"));
                        textJobCode.setText(jsonArray.getJSONObject(0).getString("job_order_number"));
                        textJobDescription.setText(jsonArray.getJSONObject(0).getString("job_order_description"));
                        textLokasiKerja.setText(jsonArray.getJSONObject(0).getString("company_workbase_name"));
                        textPerson.setText(jsonArray.getJSONObject(0).getString("person"));
                        textDiubahOleh.setText(jsonArray.getJSONObject(0).getString("user_displayname"));
                        textDiubahTgl.setText(jsonArray.getJSONObject(0).getString("modified_date"));
                        toDouble = jsonArray.getJSONObject(0).getDouble("basic_salary");
                        textGajiPokok.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("transport_allowance");
                        textTunjanganTransport.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("meal_allowance");
                        textTunjanganMakan.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("welfare_allowance");
                        textTunjanganKesejahteraan.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("overtime");
                        textLembur.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("overtime_meal");
                        textMakanLembur.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("location_project_allowance");
                        textTunjanganLokasi.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("official_travel_allowance");
                        textTunjPerjalananDinas.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("profesional_allowance");
                        textTunjanganSkill.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("emergency_call");
                        textPanggilanDarurat.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("jamsostek_allowance");
                        textBPJSKerja.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("bpjs_allowance");
                        textBPJSKesehatan.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("jpk");
                        textJPK.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("less_payment");
                        textKoreksi.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("absent");
                        textAbsent.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("jpk_paid");
                        textJPKPaid.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("jamsostek_paid");
                        textPotBPJSKetenagakerjaan.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("bpjs_paid");
                        textPotBPJSKesehatan.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("jht");
                        textJht.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("bpjs");
                        textBpjs.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("jaminan_pensiun");
                        textJaminanPensiun.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("pph1");
                        textPph1.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("pph2");
                        textPph2.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("other_deduction");
                        textPotonganLainlain.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("moneybox");
                        textMoneyBox.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("cooperative");
                        textPotonganKoperasi.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("loan_cooperative");
                        textPotPinjamanKoperasi.setText(formatter.format((long) toDouble));
                        toDouble = jsonArray.getJSONObject(0).getDouble("deduction_k3_amount");
                        textPotonganK3.setText(formatter.format((long) toDouble));
                    } else {
                        Toast.makeText(ReportDetailActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(ReportDetailActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(ReportDetailActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("empId", "" + cashOnDelivery.getEmployee_report_id());
                return param;
            }
        };
        Volley.newRequestQueue(ReportDetailActivity.this).add(request);
    }
}