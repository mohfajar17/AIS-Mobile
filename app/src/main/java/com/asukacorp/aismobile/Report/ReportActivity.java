package com.asukacorp.aismobile.Report;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.asukacorp.aismobile.R;

public class ReportActivity extends AppCompatActivity {

    private LinearLayout linearLayoutCargo;
    private LinearLayout listCargo;
    private LinearLayout linearLayoutFinance;
    private LinearLayout listFinance;
    private LinearLayout linearLayoutPurchasing;
    private LinearLayout listPurchasing;
    private LinearLayout linearLayoutProject;
    private LinearLayout listProject;
    private LinearLayout linearLayoutHR;
    private LinearLayout listHR;
    private LinearLayout linearLayoutInventory;
    private LinearLayout listInventory;
    private LinearLayout linearLayoutMarketing;
    private LinearLayout listMarketing;
    private LinearLayout linearLayoutCRM;
    private LinearLayout listCRM;
    private LinearLayout linearLayoutSafety;
    private LinearLayout listSafety;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        linearLayoutCargo = (LinearLayout) findViewById(R.id.linearLayoutCargo);
        linearLayoutCargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAllList();
                ViewGroup.LayoutParams params = listCargo.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                listCargo.setLayoutParams(params);
            }
        });
        listCargo = (LinearLayout) findViewById(R.id.listCargo);
        linearLayoutFinance = (LinearLayout) findViewById(R.id.linearLayoutFinance);
        linearLayoutFinance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAllList();
                ViewGroup.LayoutParams params = listFinance.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                listFinance.setLayoutParams(params);
            }
        });
        listFinance = (LinearLayout) findViewById(R.id.listFinance);
        linearLayoutPurchasing = (LinearLayout) findViewById(R.id.linearLayoutPurchasing);
        linearLayoutPurchasing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAllList();
                ViewGroup.LayoutParams params = listPurchasing.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                listPurchasing.setLayoutParams(params);
            }
        });
        listPurchasing = (LinearLayout) findViewById(R.id.listPurchasing);
        linearLayoutProject = (LinearLayout) findViewById(R.id.linearLayoutProject);
        linearLayoutProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAllList();
                ViewGroup.LayoutParams params = listProject.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                listProject.setLayoutParams(params);
            }
        });
        listProject = (LinearLayout) findViewById(R.id.listProject);
        linearLayoutHR = (LinearLayout) findViewById(R.id.linearLayoutHR);
        linearLayoutHR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAllList();
                ViewGroup.LayoutParams params = listHR.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                listHR.setLayoutParams(params);
            }
        });
        listHR = (LinearLayout) findViewById(R.id.listHR);
        linearLayoutInventory = (LinearLayout) findViewById(R.id.linearLayoutInventory);
        linearLayoutInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAllList();
                ViewGroup.LayoutParams params = listInventory.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                listInventory.setLayoutParams(params);
            }
        });
        listInventory = (LinearLayout) findViewById(R.id.listInventory);
        linearLayoutMarketing = (LinearLayout) findViewById(R.id.linearLayoutMarketing);
        linearLayoutMarketing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAllList();
                ViewGroup.LayoutParams params = listMarketing.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                listMarketing.setLayoutParams(params);
            }
        });
        listMarketing = (LinearLayout) findViewById(R.id.listMarketing);
        linearLayoutCRM = (LinearLayout) findViewById(R.id.linearLayoutCRM);
        linearLayoutCRM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAllList();
                ViewGroup.LayoutParams params = listCRM.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                listCRM.setLayoutParams(params);
            }
        });
        listCRM = (LinearLayout) findViewById(R.id.listCRM);
        linearLayoutSafety = (LinearLayout) findViewById(R.id.linearLayoutSafety);
        linearLayoutSafety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAllList();
                ViewGroup.LayoutParams params = listSafety.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                listSafety.setLayoutParams(params);
            }
        });
        listSafety = (LinearLayout) findViewById(R.id.listSafety);
    }

    public void closeAllList(){
        ViewGroup.LayoutParams params;
        params = listCargo.getLayoutParams();
        params.height = 0;
        listCargo.setLayoutParams(params);

        params = listFinance.getLayoutParams();
        params.height = 0;
        listFinance.setLayoutParams(params);

        params = listPurchasing.getLayoutParams();
        params.height = 0;
        listPurchasing.setLayoutParams(params);

        params = listProject.getLayoutParams();
        params.height = 0;
        listProject.setLayoutParams(params);

        params = listHR.getLayoutParams();
        params.height = 0;
        listHR.setLayoutParams(params);

        params = listInventory.getLayoutParams();
        params.height = 0;
        listInventory.setLayoutParams(params);

        params = listMarketing.getLayoutParams();
        params.height = 0;
        listMarketing.setLayoutParams(params);

        params = listCRM.getLayoutParams();
        params.height = 0;
        listCRM.setLayoutParams(params);

        params = listSafety.getLayoutParams();
        params.height = 0;
        listSafety.setLayoutParams(params);
    }
}