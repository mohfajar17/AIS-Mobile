package com.example.aismobile.Marketing;

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
import com.example.aismobile.LoginActivity;
import com.example.aismobile.R;
import com.example.aismobile.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MarketingMenuActivity extends AppCompatActivity {

    private TextView textViewSalesQuot;
    private TextView textViewSalesOrder;

    private Dialog myDialog;
    private String access = "";
    private SharedPrefManager sharedPrefManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_menu);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Refresh Data");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        myDialog = new Dialog(this);

        textViewSalesQuot = (TextView) findViewById(R.id.textViewSalesQuot);
        textViewSalesOrder = (TextView) findViewById(R.id.textViewSalesOrder);

        textViewSalesQuot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("sales_quotation".toLowerCase())){
                    bukaActivity("0");
                } else ShowPopup("Sales Quotation");
            }
        });
        textViewSalesOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("sales_order".toLowerCase())){
                    bukaActivity("1");
                } else ShowPopup("Sales Order");
            }
        });

        getAccessModul();
    }

    private void bukaActivity(String menu){
        Intent bukaActivity = new Intent(MarketingMenuActivity.this, MarketingActivity.class);
        bukaActivity.putExtra("menu", menu);
        bukaActivity.putExtra("access", access);
        startActivityForResult(bukaActivity,1);
        finish();
    }

    private void getAccessModul() {
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_ACCESS_MARKETING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if (jsonObject.getInt("is_mobile") > 1){
                        Intent logout = new Intent(MarketingMenuActivity.this, LoginActivity.class);
                        startActivity(logout);
                        sharedPrefManager.logout();
                        finish();
                    } else {
                        if(status==1){
                            JSONObject jsonData = jsonObject.getJSONObject("data");
                            if (Integer.valueOf(jsonData.getString("sales_quotation")) == 1)
                                access = access+"sales_quotation, ";
                            if (Integer.valueOf(jsonData.getString("sales_order")) == 1)
                                access = access+"sales_order, ";
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
                Toast.makeText(MarketingMenuActivity.this, "Your network is broken, please check your network", Toast.LENGTH_LONG).show();
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
