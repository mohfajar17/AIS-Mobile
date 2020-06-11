package com.example.aismobile.Project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aismobile.R;

public class ProjectMenuActivity extends AppCompatActivity {

    private TextView textViewJobOrder;
    private TextView textViewWorkCompletion;
    private TextView textViewMaterial;
    private TextView textViewTools;
    private TextView textViewWork;
    private TextView textViewPengambilan;
    private TextView textViewSpkl;
    private TextView textViewProposed;
    private TextView textViewCashProject;
    private TextView textViewTunjanganKaryawan;
    private TextView textViewTunjanganTemporary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_menu);

        textViewJobOrder = (TextView) findViewById(R.id.textViewJobOrder);
        textViewWorkCompletion = (TextView) findViewById(R.id.textViewWorkCompletion);
        textViewMaterial = (TextView) findViewById(R.id.textViewMaterial);
        textViewTools = (TextView) findViewById(R.id.textViewTools);
        textViewWork = (TextView) findViewById(R.id.textViewWork);
        textViewPengambilan = (TextView) findViewById(R.id.textViewPengambilan);
        textViewSpkl = (TextView) findViewById(R.id.textViewSpkl);
        textViewProposed = (TextView) findViewById(R.id.textViewProposed);
        textViewCashProject = (TextView) findViewById(R.id.textViewCashProject);
        textViewTunjanganKaryawan = (TextView) findViewById(R.id.textViewTunjanganKaryawan);
        textViewTunjanganTemporary = (TextView) findViewById(R.id.textViewTunjanganTemporary);

        textViewJobOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("0");
            }
        });
        textViewWorkCompletion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("1");
            }
        });
        textViewMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("2");
            }
        });
        textViewTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("3");
            }
        });
        textViewWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("4");
            }
        });
        textViewPengambilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("5");
            }
        });
        textViewSpkl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("6");
            }
        });
        textViewProposed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("7");
            }
        });
        textViewCashProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("8");
            }
        });
        textViewTunjanganKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("9");
            }
        });
        textViewTunjanganTemporary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("10");
            }
        });
    }
    private void bukaActivity(String menu){
        Intent bukaProjectActivity = new Intent(ProjectMenuActivity.this, ProjectActivity.class);
        bukaProjectActivity.putExtra("menu", menu);
        startActivityForResult(bukaProjectActivity,1);
        finish();
    }
}
