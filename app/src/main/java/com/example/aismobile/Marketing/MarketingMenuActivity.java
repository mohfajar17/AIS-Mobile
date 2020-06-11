package com.example.aismobile.Marketing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aismobile.R;

public class MarketingMenuActivity extends AppCompatActivity {

    private TextView textViewSalesQuot;
    private TextView textViewSalesOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_menu);

        textViewSalesQuot = (TextView) findViewById(R.id.textViewSalesQuot);
        textViewSalesOrder = (TextView) findViewById(R.id.textViewSalesOrder);

        textViewSalesQuot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("0");
            }
        });
        textViewSalesOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("1");
            }
        });
    }

    private void bukaActivity(String menu){
        Intent bukaInventoryActivity = new Intent(MarketingMenuActivity.this, MarketingActivity.class);
        bukaInventoryActivity.putExtra("menu", menu);
        startActivityForResult(bukaInventoryActivity,1);
        finish();
    }
}
