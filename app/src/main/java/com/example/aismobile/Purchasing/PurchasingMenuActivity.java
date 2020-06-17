package com.example.aismobile.Purchasing;

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

public class PurchasingMenuActivity extends AppCompatActivity {

    private TextView textViewPurchaseOrder;
    private TextView textViewWorkOrders;
    private TextView textViewCOD;
    private TextView textViewKontrakPerjanjian;
    private TextView textViewGRN;
    private TextView textViewWorkHandover;
    private TextView textViewServicesReceipt;

    private Dialog myDialog;
    private String access = "";
    private SharedPrefManager sharedPrefManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchasing_menu);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Refresh Data");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        myDialog = new Dialog(this);

        textViewPurchaseOrder = (TextView) findViewById(R.id.textViewPurchaseOrder);
        textViewWorkOrders = (TextView) findViewById(R.id.textViewWorkOrders);
        textViewCOD = (TextView) findViewById(R.id.textViewCOD);
        textViewKontrakPerjanjian = (TextView) findViewById(R.id.textViewKontrakPerjanjian);
        textViewGRN = (TextView) findViewById(R.id.textViewGRN);
        textViewWorkHandover = (TextView) findViewById(R.id.textViewWorkHandover);
        textViewServicesReceipt = (TextView) findViewById(R.id.textViewServicesReceipt);

        textViewPurchaseOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("purchase_order".toLowerCase())){
                    bukaActivity("0");
                } else ShowPopup("Purchase Order");
            }
        });
        textViewWorkOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("purchase_service".toLowerCase())){
                    bukaActivity("1");
                } else ShowPopup("Work Order");
            }
        });
        textViewCOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("cash_on_delivery".toLowerCase())){
                    bukaActivity("2");
                } else ShowPopup("Cash On Delivery");
            }
        });
        textViewKontrakPerjanjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("contract_agreement".toLowerCase())){
                    bukaActivity("3");
                } else ShowPopup("Kontrak Perjanjian");
            }
        });
        textViewGRN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("good_received_note".toLowerCase())){
                    bukaActivity("4");
                } else ShowPopup("Goods Recived Note");
            }
        });
        textViewWorkHandover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("work_handover".toLowerCase())){
                    bukaActivity("5");
                } else ShowPopup("Work Handover");
            }
        });
        textViewServicesReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("services_receipt".toLowerCase())){
                    bukaActivity("6");
                } else ShowPopup("Services Receipt");
            }
        });

        getAccessModul();
    }

    private void bukaActivity(String menu){
        Intent bukaActivity = new Intent(PurchasingMenuActivity.this, PurchasingActivity.class);
        bukaActivity.putExtra("menu", menu);
        bukaActivity.putExtra("access", access);
        startActivityForResult(bukaActivity,1);
        finish();
    }

    private void getAccessModul() {
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_ACCESS_PURCHASING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if(status==1){
                        JSONObject jsonData = jsonObject.getJSONObject("data");
                        if (Integer.valueOf(jsonData.getString("purchase_order")) == 1)
                            access = access+"purchase_order, ";
                        if (Integer.valueOf(jsonData.getString("purchase_service")) == 1)
                            access = access+"purchase_service, ";
                        if (Integer.valueOf(jsonData.getString("cash_on_delivery")) == 1)
                            access = access+"cash_on_delivery, ";
                        if (Integer.valueOf(jsonData.getString("contract_agreement")) == 1)
                            access = access+"contract_agreement, ";
                        if (Integer.valueOf(jsonData.getString("good_received_note")) == 1)
                            access = access+"good_received_note, ";
                        if (Integer.valueOf(jsonData.getString("work_handover")) == 1)
                            access = access+"work_handover, ";
                        if (Integer.valueOf(jsonData.getString("services_receipt")) == 1)
                            access = access+"services_receipt, ";
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
                Toast.makeText(PurchasingMenuActivity.this, "Your network is broken, please check your network", Toast.LENGTH_LONG).show();
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
