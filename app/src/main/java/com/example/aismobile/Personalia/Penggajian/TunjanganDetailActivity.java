package com.example.aismobile.Personalia.Penggajian;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aismobile.Data.Personalia.Allowance;
import com.example.aismobile.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TunjanganDetailActivity extends AppCompatActivity {

    private Allowance allowance;

    private ImageView buttonBack;
    private TextView textTunjangan;
    private TextView textKodeLaporan;
    private TextView textJenis;
    private TextView textKelompok;
    private TextView textKeterangan;
    private TextView textMataUang;
    private TextView textNilai;
    private TextView textPenyesuaian;
    private TextView textSatuanTunjangan;
    private TextView textHitungTHR;
    private TextView textAktif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tunjangan_detail);

        Bundle bundle = getIntent().getExtras();
        allowance = bundle.getParcelable("detail");

        textTunjangan = (TextView) findViewById(R.id.textTunjangan);
        textKodeLaporan = (TextView) findViewById(R.id.textKodeLaporan);
        textJenis = (TextView) findViewById(R.id.textJenis);
        textKelompok = (TextView) findViewById(R.id.textKelompok);
        textKeterangan = (TextView) findViewById(R.id.textKeterangan);
        textMataUang = (TextView) findViewById(R.id.textMataUang);
        textNilai = (TextView) findViewById(R.id.textNilai);
        textPenyesuaian = (TextView) findViewById(R.id.textPenyesuaian);
        textSatuanTunjangan = (TextView) findViewById(R.id.textSatuanTunjangan);
        textHitungTHR = (TextView) findViewById(R.id.textHitungTHR);
        textAktif = (TextView) findViewById(R.id.textAktif);

        textTunjangan.setText(allowance.getAllowance_name());
        textKodeLaporan.setText(allowance.getReport_code());
        textJenis.setText(allowance.getAllowance_type_name());
        textKelompok.setText(allowance.getAllowance_group_name());
        textKeterangan.setText(allowance.getDescription());
        textMataUang.setText(allowance.getCurrency_code());
        textPenyesuaian.setText(allowance.getAdjustment());
        textSatuanTunjangan.setText(allowance.getAllowance_unit());
        textHitungTHR.setText(allowance.getIs_thr());
        textAktif.setText(allowance.getIs_active());
        NumberFormat formatter = new DecimalFormat("#,###");
        double toDouble = Double.valueOf(allowance.getValue());
        textNilai.setText(formatter.format((long) toDouble));

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}