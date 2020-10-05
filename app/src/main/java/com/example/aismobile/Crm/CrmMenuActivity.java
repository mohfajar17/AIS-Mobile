package com.example.aismobile.Crm;

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

public class CrmMenuActivity extends AppCompatActivity {

    private TextView textViewCustomerFeedback;
    private TextView textViewQuestion;
    private TextView textViewKuesioner;
    private TextView textViewMonitoring;
    private TextView textViewLeads;
    private TextView textViewFollowups;
    private TextView textViewEvents;
    private TextView textViewSchedule;

    private Dialog myDialog;
    private String access = "";
    private SharedPrefManager sharedPrefManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crm_menu);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Refresh Data");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        myDialog = new Dialog(this);

        textViewCustomerFeedback = (TextView) findViewById(R.id.textViewCustomerFeedback);
        textViewQuestion = (TextView) findViewById(R.id.textViewQuestion);
        textViewKuesioner = (TextView) findViewById(R.id.textViewKuesioner);
        textViewMonitoring = (TextView) findViewById(R.id.textViewMonitoring);
        textViewLeads = (TextView) findViewById(R.id.textViewLeads);
        textViewFollowups = (TextView) findViewById(R.id.textViewFollowups);
        textViewEvents = (TextView) findViewById(R.id.textViewEvents);
        textViewSchedule = (TextView) findViewById(R.id.textViewSchedule);

        textViewCustomerFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("customer_feedback".toLowerCase())){
                    bukaActivity("0");
                } else ShowPopup("Customer Feedback");
            }
        });
        textViewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("question".toLowerCase())){
                    bukaActivity("1");
                } else ShowPopup("Question");
            }
        });
        textViewKuesioner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("kuesioner".toLowerCase())){
                    bukaActivity("2");
                } else ShowPopup("Kuesioner");
            }
        });
        textViewMonitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("sales_quotation".toLowerCase())){
                    bukaActivity("3");
                } else ShowPopup("Sales Quotation");
            }
        });
        textViewLeads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("lead".toLowerCase())){
                    bukaActivity("4");
                } else ShowPopup("Leads");
            }
        });
        textViewFollowups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("followup".toLowerCase())){
                    bukaActivity("5");
                } else ShowPopup("Followup");
            }
        });
        textViewEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("event".toLowerCase())){
                    bukaActivity("6");
                } else ShowPopup("Events");
            }
        });
        textViewSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("schedule_visits".toLowerCase())){
                    bukaActivity("7");
                } else ShowPopup("Schedule Visits");
            }
        });

        getAccessModul();
    }

    private void bukaActivity(String menu){
        Intent bukaActivity = new Intent(CrmMenuActivity.this, CrmActivity.class);
        bukaActivity.putExtra("menu", menu);
        bukaActivity.putExtra("access", access);
        startActivityForResult(bukaActivity,1);
        finish();
    }

    private void getAccessModul() {
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_ACCESS_CRM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if(status==1){
                        JSONObject jsonData = jsonObject.getJSONObject("data");
                        if (Integer.valueOf(jsonData.getString("customer_feedback")) == 1)
                            access = access+"customer_feedback, ";
                        if (Integer.valueOf(jsonData.getString("question")) == 1)
                            access = access+"question, ";
                        if (Integer.valueOf(jsonData.getString("kuesioner")) == 1)
                            access = access+"kuesioner, ";
                        if (Integer.valueOf(jsonData.getString("grafik_kuesioner")) == 1)
                            access = access+"grafik_kuesioner, ";
                        if (Integer.valueOf(jsonData.getString("sales_quotation")) == 1)
                            access = access+"sales_quotation, ";
                        if (Integer.valueOf(jsonData.getString("lead")) == 1)
                            access = access+"lead, ";
                        if (Integer.valueOf(jsonData.getString("followup")) == 1)
                            access = access+"followup, ";
                        if (Integer.valueOf(jsonData.getString("event")) == 1)
                            access = access+"event, ";
                        if (Integer.valueOf(jsonData.getString("schedule_visits")) == 1)
                            access = access+"schedule_visits, ";
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
                Toast.makeText(CrmMenuActivity.this, "Your network is broken, please check your network", Toast.LENGTH_LONG).show();
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
