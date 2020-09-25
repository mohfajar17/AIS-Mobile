package com.example.aismobile.Personalia.Pangkat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aismobile.Data.Personalia.JobTitle;
import com.example.aismobile.R;

public class DetailPangkatActivity extends AppCompatActivity {

    private JobTitle department;

    private ImageView buttonBack;
    private TextView textNumber;
    private TextView textNamaPangkat;
    private TextView textKodePangkat;
    private TextView textDeskripsi;
    private TextView textAktif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pangkat);

        Bundle bundle = getIntent().getExtras();
        department = bundle.getParcelable("detail");

        textNamaPangkat = (TextView) findViewById(R.id.textNamaPangkat);
        textKodePangkat = (TextView) findViewById(R.id.textKodePangkat);
        textDeskripsi = (TextView) findViewById(R.id.textDeskripsi);
        textAktif = (TextView) findViewById(R.id.textAktif);
        textNumber = (TextView) findViewById(R.id.textNumber);
        buttonBack = (ImageView) findViewById(R.id.buttonBack);

        textNamaPangkat.setText(department.getJobtitle_name());
        textKodePangkat.setText(department.getJobtitle_code());
        textDeskripsi.setText(department.getJobtitle_description());
        textAktif.setText(department.getIs_active());
        textNumber.setText("Pangkat " + department.getJobtitle_name());
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}