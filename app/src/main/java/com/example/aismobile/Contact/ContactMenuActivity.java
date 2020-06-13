package com.example.aismobile.Contact;

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

public class ContactMenuActivity extends AppCompatActivity {

    private TextView textViewContacts;
    private TextView textViewSupplier;
    private TextView textViewPerusahaan;
    private TextView textViewAccess;

    private Dialog myDialog;
    private String access = "";
    private SharedPrefManager sharedPrefManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_menu);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Refresh Data");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        myDialog = new Dialog(this);

        textViewContacts = (TextView) findViewById(R.id.textViewContacts);
        textViewSupplier = (TextView) findViewById(R.id.textViewSupplier);
        textViewPerusahaan = (TextView) findViewById(R.id.textViewPerusahaan);
        textViewAccess = (TextView) findViewById(R.id.textViewAccess);

        textViewContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("contact".toLowerCase())){
                    bukaActivity("0");
                } else ShowPopup("Contact");
            }
        });
        textViewSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("supplier".toLowerCase())){
                    bukaActivity("1");
                } else ShowPopup("Supplier");
            }
        });
        textViewPerusahaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("company".toLowerCase())){
                    bukaActivity("2");
                } else ShowPopup("Perusahaan");
            }
        });
        textViewAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("3");
            }
        });

        getAccessModul();
    }

    private void bukaActivity(String menu){
        Intent bukaInventoryActivity = new Intent(ContactMenuActivity.this, ContactActivity.class);
        bukaInventoryActivity.putExtra("menu", menu);
        bukaInventoryActivity.putExtra("access", access);
        startActivityForResult(bukaInventoryActivity,1);
        finish();
    }

    private void getAccessModul() {
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_ACCESS_CONTACT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if(status==1){
                        JSONObject jsonData = jsonObject.getJSONObject("data");
                        if (Integer.valueOf(jsonData.getString("contact")) == 1)
                            access = access+"contact, ";
                        if (Integer.valueOf(jsonData.getString("supplier")) == 1)
                            access = access+"supplier, ";
                        if (Integer.valueOf(jsonData.getString("company")) == 1)
                            access = access+"company, ";
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
                Toast.makeText(ContactMenuActivity.this, "Your network is broken, please check your network", Toast.LENGTH_LONG).show();
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
