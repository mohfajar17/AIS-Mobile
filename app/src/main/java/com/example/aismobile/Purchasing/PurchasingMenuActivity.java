package com.example.aismobile.Purchasing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aismobile.R;

public class PurchasingMenuActivity extends AppCompatActivity {

    private TextView textViewPurchaseOrder;
    private TextView textViewWorkOrders;
    private TextView textViewCOD;
    private TextView textViewKontrakPerjanjian;
    private TextView textViewGRN;
    private TextView textViewWorkHandover;
    private TextView textViewServicesReceipt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchasing_menu);

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
                bukaActivity("0");
            }
        });
        textViewWorkOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("1");
            }
        });
        textViewCOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("2");
            }
        });
        textViewKontrakPerjanjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("3");
            }
        });
        textViewGRN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("4");
            }
        });
        textViewWorkHandover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("5");
            }
        });
        textViewServicesReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("6");
            }
        });
    }

    private void bukaActivity(String menu){
        Intent bukaPersonaliaActivity = new Intent(PurchasingMenuActivity.this, PurchasingActivity.class);
        bukaPersonaliaActivity.putExtra("menu", menu);
        startActivityForResult(bukaPersonaliaActivity,1);
        finish();
    }
}
