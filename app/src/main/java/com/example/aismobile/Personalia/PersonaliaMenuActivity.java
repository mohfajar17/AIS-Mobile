package com.example.aismobile.Personalia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aismobile.R;

public class PersonaliaMenuActivity extends AppCompatActivity {

    private TextView textViewKerja;
    private TextView textViewPenggajian;
    private TextView textViewKalender;
    private TextView textViewKaryawan;
    private TextView textViewDepartemen;
    private TextView textViewJenjangKaryawan;
    private TextView textViewPangkat;
    private TextView textViewJabatan;
    private TextView textViewNews;
    private TextView textViewReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalia_menu);

        textViewKerja = (TextView) findViewById(R.id.textViewKerja);
        textViewPenggajian = (TextView) findViewById(R.id.textViewPenggajian);
        textViewKalender = (TextView) findViewById(R.id.textViewKalender);
        textViewKaryawan = (TextView) findViewById(R.id.textViewKaryawan);
        textViewDepartemen = (TextView) findViewById(R.id.textViewDepartemen);
        textViewJenjangKaryawan = (TextView) findViewById(R.id.textViewJenjangKaryawan);
        textViewPangkat = (TextView) findViewById(R.id.textViewPangkat);
        textViewJabatan = (TextView) findViewById(R.id.textViewJabatan);
        textViewNews = (TextView) findViewById(R.id.textViewNews);
        textViewReport = (TextView) findViewById(R.id.textViewReport);

        textViewKerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("0");
            }
        });

        textViewPenggajian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("1");
            }
        });

        textViewKalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("2");
            }
        });

        textViewKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("3");
            }
        });

        textViewDepartemen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("4");
            }
        });

        textViewJenjangKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("5");
            }
        });

        textViewPangkat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("6");
            }
        });

        textViewJabatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("7");
            }
        });

        textViewNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("8");
            }
        });

        textViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("9");
            }
        });
    }
    private void bukaActivity(String menu){
        Intent bukaPersonaliaActivity = new Intent(PersonaliaMenuActivity.this, PersonaliaActivity.class);
        bukaPersonaliaActivity.putExtra("menu", menu);
        startActivityForResult(bukaPersonaliaActivity,1);
        finish();
    }
}
