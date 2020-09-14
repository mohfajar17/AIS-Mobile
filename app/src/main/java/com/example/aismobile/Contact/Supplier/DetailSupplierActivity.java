package com.example.aismobile.Contact.Supplier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.aismobile.Data.Contact.Supplier;
import com.example.aismobile.R;

public class DetailSupplierActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private Supplier supplier;

    private TextView textName;
    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuSupplierInvoice;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutSupplierInvoice;
    private ImageView downloadAtachment;

    private TextView textNamaSupplier;
    private TextView textSupplierType;
    private TextView textEmail;
    private TextView textWebsite;
    private TextView textSupplierContact;
    private TextView textLastLogin;
    private TextView textAlamatKantor1;
    private TextView textAlamatNPWP;
    private TextView textTeleponKantor;
    private TextView textFaxKantor;
    private TextView textNoHP;
    private TextView textNPWP;
    private TextView textSPPKPNumber;
    private TextView textNamaBank;
    private TextView textPemilikAkun;
    private TextView textRekBank;
    private TextView textCatatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_supplier);

        Bundle bundle = getIntent().getExtras();
        supplier = bundle.getParcelable("detail");

        textNamaSupplier = (TextView) findViewById(R.id.textNamaSupplier);
        textSupplierType = (TextView) findViewById(R.id.textSupplierType);
        textEmail = (TextView) findViewById(R.id.textEmail);
        textWebsite = (TextView) findViewById(R.id.textWebsite);
        textSupplierContact = (TextView) findViewById(R.id.textSupplierContact);
        textLastLogin = (TextView) findViewById(R.id.textLastLogin);
        textAlamatKantor1 = (TextView) findViewById(R.id.textAlamatKantor1);
        textAlamatNPWP = (TextView) findViewById(R.id.textAlamatNPWP);
        textTeleponKantor = (TextView) findViewById(R.id.textTeleponKantor);
        textFaxKantor = (TextView) findViewById(R.id.textFaxKantor);
        textNoHP = (TextView) findViewById(R.id.textNoHP);
        textNPWP = (TextView) findViewById(R.id.textNPWP);
        textSPPKPNumber = (TextView) findViewById(R.id.textSPPKPNumber);
        textNamaBank = (TextView) findViewById(R.id.textNamaBank);
        textPemilikAkun = (TextView) findViewById(R.id.textPemilikAkun);
        textRekBank = (TextView) findViewById(R.id.textRekBank);
        textCatatan = (TextView) findViewById(R.id.textCatatan);

        textNamaSupplier.setText(supplier.getSupplier_name());
        textSupplierType.setText(supplier.getSupplier_type());
        textEmail.setText(supplier.getEmail_address());
        textWebsite.setText(supplier.getWebsite());
        textSupplierContact.setText(supplier.getSupplier_contact());
        textLastLogin.setText(supplier.getSupplier_lastlogin());
        textAlamatKantor1.setText(supplier.getOffice_address1());
        textAlamatNPWP.setText(supplier.getOffice_address2());
        textTeleponKantor.setText(supplier.getOffice_phone());
        textFaxKantor.setText(supplier.getOffice_fax());
        textNoHP.setText(supplier.getPhone());
        textNPWP.setText(supplier.getNpwp());
        textSPPKPNumber.setText(supplier.getSppkp_number());
        textNamaBank.setText(supplier.getBank_name());
        textPemilikAkun.setText(supplier.getName_of_bank_account());
        textRekBank.setText(supplier.getBank_account());
        textCatatan.setText(supplier.getDescription());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textName = (TextView) findViewById(R.id.textName);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuSupplierInvoice = (TextView) findViewById(R.id.menuSupplierInvoice);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutSupplierInvoice = (LinearLayout) findViewById(R.id.layoutSupplierInvoice);
        downloadAtachment = (ImageView) findViewById(R.id.downloadAtachment);

        textName.setText("#"+ supplier.getSupplier_name());
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        menuDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutDetail.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutDetail.setLayoutParams(params);
                menuDetail.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuDetail.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        menuCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutCatatan.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutCatatan.setLayoutParams(params);
                menuCatatan.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuCatatan.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        menuSupplierInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutSupplierInvoice.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutSupplierInvoice.setLayoutParams(params);
                menuSupplierInvoice.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuSupplierInvoice.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        downloadAtachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/supplier/"+ supplier.getSupplier_file_name());
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });
    }

    private void hiddenLayout(){
        menuDetail.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuDetail.setTextColor(getResources().getColor(R.color.colorWhite));
        menuCatatan.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuCatatan.setTextColor(getResources().getColor(R.color.colorWhite));
        menuSupplierInvoice.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuSupplierInvoice.setTextColor(getResources().getColor(R.color.colorWhite));

        params = layoutDetail.getLayoutParams(); params.height = 0; layoutDetail.setLayoutParams(params);
        params = layoutCatatan.getLayoutParams(); params.height = 0; layoutCatatan.setLayoutParams(params);
        params = layoutSupplierInvoice.getLayoutParams(); params.height = 0; layoutSupplierInvoice.setLayoutParams(params);
    }
}