package com.example.aismobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class DashboardActivity extends AppCompatActivity {

    private LinearLayout dashboardProject;
    private LinearLayout dashboardPurchasing;
    private LinearLayout dashboardInventory;
    private LinearLayout dashboardFinance;
    private LinearLayout layoutProject;
    private LinearLayout layoutPurchasing;
    private LinearLayout layoutInventory;
    private LinearLayout layoutFinance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dashboardProject = (LinearLayout) findViewById(R.id.dashboardProject);
        dashboardPurchasing = (LinearLayout) findViewById(R.id.dashboardPurchasing);
        dashboardInventory = (LinearLayout) findViewById(R.id.dashboardInventory);
        dashboardFinance = (LinearLayout) findViewById(R.id.dashboardFinance);
        layoutProject = (LinearLayout) findViewById(R.id.layoutProject);
        layoutPurchasing = (LinearLayout) findViewById(R.id.layoutPurchasing);
        layoutInventory = (LinearLayout) findViewById(R.id.layoutInventory);
        layoutFinance = (LinearLayout) findViewById(R.id.layoutFinance);

        dashboardProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayout(layoutProject);
            }
        });
        dashboardPurchasing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayout(layoutPurchasing);
            }
        });
        dashboardInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayout(layoutInventory);
            }
        });
        dashboardFinance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayout(layoutFinance);
            }
        });
    }

    private void setLayout(LinearLayout linearLayout){
        ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
        if (params.height == 0)
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        else params.height = 0;
        linearLayout.setLayoutParams(params);
    }
}
