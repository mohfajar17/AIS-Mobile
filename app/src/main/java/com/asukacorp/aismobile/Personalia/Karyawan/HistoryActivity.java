package com.asukacorp.aismobile.Personalia.Karyawan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asukacorp.aismobile.Data.Personalia.Employee.Employee;
import com.asukacorp.aismobile.R;

public class HistoryActivity extends AppCompatActivity {

    private Employee employee;
    private ImageView buttonBack;
    private TextView textKaryawan;
    private TextView menuDetail;
    private TextView menuKerja;
    private TextView menuFile;
    private TextView menuLeave;
    private TextView menuAllowanceDeduction;
    private TextView menuEducationHistory;
    private TextView menuWorkExperience;
    private TextView menuTraining;
    private TextView menuAchievement;
    private TextView menuHistory;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Bundle bundle = getIntent().getExtras();
        employee = bundle.getParcelable("detail");

        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textCreatedBy.setText(employee.getCreated_by());
        textCreatedDate.setText(employee.getCreated_date());
        textModifiedBy.setText(employee.getModified_by());
        textModifiedDate.setText(employee.getModified_date());

        textKaryawan = (TextView) findViewById(R.id.textKaryawan);
        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuKerja = (TextView) findViewById(R.id.menuKerja);
        menuFile = (TextView) findViewById(R.id.menuFile);
        menuLeave = (TextView) findViewById(R.id.menuLeave);
        menuAllowanceDeduction = (TextView) findViewById(R.id.menuAllowanceDeduction);
        menuEducationHistory = (TextView) findViewById(R.id.menuEducationHistory);
        menuWorkExperience = (TextView) findViewById(R.id.menuWorkExperience);
        menuTraining = (TextView) findViewById(R.id.menuTraining);
        menuAchievement = (TextView) findViewById(R.id.menuAchievement);
        menuHistory = (TextView) findViewById(R.id.menuHistory);

        textKaryawan.setText("Karyawan : " + employee.getFullname());
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        menuDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, DetailKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuKerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, KerjaKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, FileKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, LeaveKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuAllowanceDeduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, AllowanceKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuEducationHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, EducationKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuWorkExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, ExperienceKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, TrainingKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, AchievementKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, HistoryActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
    }
}