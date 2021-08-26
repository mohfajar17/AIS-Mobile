package com.asukacorp.aismobile.Purchasing.KontrakPerjanjian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asukacorp.aismobile.Data.Purchasing.ContractAgreement;
import com.asukacorp.aismobile.R;

public class KontrakPerjanjianDetailActivity extends AppCompatActivity {
    private ContractAgreement contractAgreement;

    private ImageView downloadAtachment;
    private ImageView buttonBack;
    private TextView textNumber;
    private TextView textSupplier;
    private TextView textBerkaitanKontrak;
    private TextView textTglKontrak;
    private TextView textTglAwal;
    private TextView textTglAkhir;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontrak_perjanjian_detail);

        Bundle bundle = getIntent().getExtras();
        contractAgreement = bundle.getParcelable("detail");

        downloadAtachment = (ImageView) findViewById(R.id.downloadAtachment);
        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);
        textSupplier = (TextView) findViewById(R.id.textSupplier);
        textBerkaitanKontrak = (TextView) findViewById(R.id.textBerkaitanKontrak);
        textTglKontrak = (TextView) findViewById(R.id.textTglKontrak);
        textTglAwal = (TextView) findViewById(R.id.textTglAwal);
        textTglAkhir = (TextView) findViewById(R.id.textTglAkhir);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textSupplier.setText(contractAgreement.getSupplier_id());
        textBerkaitanKontrak.setText(contractAgreement.getRelated_agreement());
        textTglKontrak.setText(contractAgreement.getAgreement_date());
        textTglAwal.setText(contractAgreement.getBegin_date());
        textTglAkhir.setText(contractAgreement.getEnd_date());
        textCatatan.setText(contractAgreement.getNotes());
        textCreatedBy.setText(contractAgreement.getCreated_by());
        textCreatedDate.setText(contractAgreement.getCreated_date());
        textModifiedBy.setText(contractAgreement.getModified_by());
        textModifiedDate.setText(contractAgreement.getModified_date());

        textNumber.setText("Followup Customer #" + contractAgreement.getAgreement_number());
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (!contractAgreement.getAgreement_file_name().matches("null")){
            downloadAtachment.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorAsukaRed));
            downloadAtachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/contractAgreement/"+ contractAgreement.getAgreement_file_name());
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }
            });
        }
    }
}