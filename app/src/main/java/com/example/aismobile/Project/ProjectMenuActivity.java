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
                Intent bukaProjectActivity = new Intent(ProjectMenuActivity.this, ProjectActivity.class);
                bukaProjectActivity.putExtra("menu", "0");
                startActivityForResult(bukaProjectActivity,1);
                finish();
            }
        });
        textViewWorkCompletion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaProjectActivity = new Intent(ProjectMenuActivity.this, ProjectActivity.class);
                bukaProjectActivity.putExtra("menu", "1");
                startActivityForResult(bukaProjectActivity,1);
                finish();
            }
        });
        textViewMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaProjectActivity = new Intent(ProjectMenuActivity.this, ProjectActivity.class);
                bukaProjectActivity.putExtra("menu", "2");
                startActivityForResult(bukaProjectActivity,1);
                finish();
            }
        });
        textViewTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaProjectActivity = new Intent(ProjectMenuActivity.this, ProjectActivity.class);
                bukaProjectActivity.putExtra("menu", "3");
                startActivityForResult(bukaProjectActivity,1);
                finish();
            }
        });
        textViewWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaProjectActivity = new Intent(ProjectMenuActivity.this, ProjectActivity.class);
                bukaProjectActivity.putExtra("menu", "4");
                startActivityForResult(bukaProjectActivity,1);
                finish();
            }
        });
        textViewPengambilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaProjectActivity = new Intent(ProjectMenuActivity.this, ProjectActivity.class);
                bukaProjectActivity.putExtra("menu", "5");
                startActivityForResult(bukaProjectActivity,1);
                finish();
            }
        });
        textViewSpkl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaProjectActivity = new Intent(ProjectMenuActivity.this, ProjectActivity.class);
                bukaProjectActivity.putExtra("menu", "6");
                startActivityForResult(bukaProjectActivity,1);
                finish();
            }
        });
        textViewProposed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaProjectActivity = new Intent(ProjectMenuActivity.this, ProjectActivity.class);
                bukaProjectActivity.putExtra("menu", "7");
                startActivityForResult(bukaProjectActivity,1);
                finish();
            }
        });
        textViewCashProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaProjectActivity = new Intent(ProjectMenuActivity.this, ProjectActivity.class);
                bukaProjectActivity.putExtra("menu", "8");
                startActivityForResult(bukaProjectActivity,1);
                finish();
            }
        });
        textViewTunjanganKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaProjectActivity = new Intent(ProjectMenuActivity.this, ProjectActivity.class);
                bukaProjectActivity.putExtra("menu", "9");
                startActivityForResult(bukaProjectActivity,1);
                finish();
            }
        });
        textViewTunjanganTemporary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaProjectActivity = new Intent(ProjectMenuActivity.this, ProjectActivity.class);
                bukaProjectActivity.putExtra("menu", "10");
                startActivityForResult(bukaProjectActivity,1);
                finish();
            }
        });
    }
}
