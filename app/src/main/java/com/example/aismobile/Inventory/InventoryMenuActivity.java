package com.example.aismobile.Inventory;

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

public class InventoryMenuActivity extends AppCompatActivity {

    private TextView textViewItem;
    private TextView textViewAset;
    private TextView textViewAsetRental;
    private TextView textViewStock;
    private TextView textViewHarga;
    private TextView textViewMaterial;

    private Dialog myDialog;
    private String access = "";
    private SharedPrefManager sharedPrefManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_menu);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Refresh Data");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        myDialog = new Dialog(this);

        textViewItem = (TextView) findViewById(R.id.textViewItem);
        textViewAset = (TextView) findViewById(R.id.textViewAset);
        textViewAsetRental = (TextView) findViewById(R.id.textViewAsetRental);
        textViewStock = (TextView) findViewById(R.id.textViewStock);
//        textViewHarga = (TextView) findViewById(R.id.textViewHarga);
        textViewMaterial = (TextView) findViewById(R.id.textViewMaterial);

        textViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("item".toLowerCase())){
                    bukaActivity("0");
                } else ShowPopup("Item");
            }
        });
        textViewAset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("asset".toLowerCase())){
                    bukaActivity("1");
                } else ShowPopup("Asset");
            }
        });
        textViewAsetRental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("aset_rental".toLowerCase())){
                    bukaActivity("2");
                } else ShowPopup("Asset Rental");
            }
        });
        textViewStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("stock_adjustment".toLowerCase())){
                    bukaActivity("3");
                } else ShowPopup("Stock Adjustment");
            }
        });
//        textViewHarga.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (access.toLowerCase().contains("master_item_price".toLowerCase())){
//                    bukaActivity("4");
//                } else ShowPopup("Daftar Harga");
//            }
//        });
        textViewMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("material_return".toLowerCase())){
                    bukaActivity("4");
                } else ShowPopup("Material Return");
            }
        });

        getAccessModul();
    }

    private void bukaActivity(String menu){
        Intent bukaActivity = new Intent(InventoryMenuActivity.this, InventoryActivity.class);
        bukaActivity.putExtra("menu", menu);
        bukaActivity.putExtra("access", access);
        startActivityForResult(bukaActivity,1);
        finish();
    }

    private void getAccessModul() {
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_ACCESS_INVENTORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if(status==1){
                        JSONObject jsonData = jsonObject.getJSONObject("data");
                        if (Integer.valueOf(jsonData.getString("item")) == 1)
                            access = access+"item, ";
                        if (Integer.valueOf(jsonData.getString("asset")) == 1)
                            access = access+"asset, ";
                        if (Integer.valueOf(jsonData.getString("asset_rental")) == 1)
                            access = access+"aset_rental, ";
                        if (Integer.valueOf(jsonData.getString("stock_adjustment")) == 1)
                            access = access+"stock_adjustment, ";
                        if (Integer.valueOf(jsonData.getString("master_item_price")) == 1)
                            access = access+"master_item_price, ";
                        if (Integer.valueOf(jsonData.getString("material_return")) == 1)
                            access = access+"material_return, ";
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
                Toast.makeText(InventoryMenuActivity.this, "Your network is broken, please check your network", Toast.LENGTH_LONG).show();
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
