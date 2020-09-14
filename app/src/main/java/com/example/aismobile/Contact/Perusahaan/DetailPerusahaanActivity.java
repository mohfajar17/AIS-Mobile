package com.example.aismobile.Contact.Perusahaan;

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

import com.example.aismobile.Data.Contact.Company;
import com.example.aismobile.R;

public class DetailPerusahaanActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private Company cashOnDelivery;

    private TextView textName;
    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuSupplierInvoice;
    private TextView menuCountInvoice;
    private TextView menuCustomer;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutSupplierInvoice;
    private LinearLayout layoutCustomer;
    private LinearLayout layoutCountInvoice;

    private TextView textNamaPerusahaan;
    private TextView textCode;
    private TextView textNPWP;
    private TextView textAlamat;
    private TextView textAddressSPPKP;
    private TextView textKota;
    private TextView textPropinsi;
    private TextView textNegara;
    private TextView textKodepos;
    private TextView textTelepon1;
    private TextView textTelepon2;
    private TextView textFax;
    private TextView textEMail;
    private TextView textWebsite;
    private TextView textCatatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_perusahaan);

        Bundle bundle = getIntent().getExtras();
        cashOnDelivery = bundle.getParcelable("detail");

        textNamaPerusahaan = (TextView) findViewById(R.id.textNamaPerusahaan);
        textCode = (TextView) findViewById(R.id.textCode);
        textNPWP = (TextView) findViewById(R.id.textNPWP);
        textAlamat = (TextView) findViewById(R.id.textAlamat);
        textAddressSPPKP = (TextView) findViewById(R.id.textAddressSPPKP);
        textKota = (TextView) findViewById(R.id.textKota);
        textPropinsi = (TextView) findViewById(R.id.textPropinsi);
        textNegara = (TextView) findViewById(R.id.textNegara);
        textKodepos = (TextView) findViewById(R.id.textKodepos);
        textTelepon1 = (TextView) findViewById(R.id.textTelepon1);
        textTelepon2 = (TextView) findViewById(R.id.textTelepon2);
        textFax = (TextView) findViewById(R.id.textFax);
        textEMail = (TextView) findViewById(R.id.textEMail);
        textWebsite = (TextView) findViewById(R.id.textWebsite);
        textCatatan = (TextView) findViewById(R.id.textCatatan);

        textNamaPerusahaan.setText(cashOnDelivery.getCompany_name());
        textCode.setText(cashOnDelivery.getCompany_code());
        textNPWP.setText(cashOnDelivery.getNpwp());
        textAlamat.setText(cashOnDelivery.getCompany_address());
        textAddressSPPKP.setText(cashOnDelivery.getCompany_address_sppkp());
        textKota.setText(cashOnDelivery.getCompany_city());
        textPropinsi.setText(cashOnDelivery.getCompany_state());
        textNegara.setText(cashOnDelivery.getCompany_country());
        textKodepos.setText(cashOnDelivery.getCompany_zipcode());
        textTelepon1.setText(cashOnDelivery.getCompany_phone1());
        textTelepon2.setText(cashOnDelivery.getCompany_phone2());
        textFax.setText(cashOnDelivery.getCompany_fax());
        textEMail.setText(cashOnDelivery.getCompany_email());
        textWebsite.setText(cashOnDelivery.getCompany_www());
        textCatatan.setText(cashOnDelivery.getCompany_notes());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textName = (TextView) findViewById(R.id.textName);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuSupplierInvoice = (TextView) findViewById(R.id.menuSupplierInvoice);
        menuCountInvoice = (TextView) findViewById(R.id.menuCountInvoice);
        menuCustomer = (TextView) findViewById(R.id.menuCustomer);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutSupplierInvoice = (LinearLayout) findViewById(R.id.layoutSupplierInvoice);
        layoutCustomer = (LinearLayout) findViewById(R.id.layoutCustomer);
        layoutCountInvoice = (LinearLayout) findViewById(R.id.layoutCountInvoice);

        textName.setText("#"+ cashOnDelivery.getCompany_name());
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
        menuCountInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutCountInvoice.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutCountInvoice.setLayoutParams(params);
                menuCountInvoice.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuCountInvoice.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        menuCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutCustomer.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutCustomer.setLayoutParams(params);
                menuCustomer.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuCustomer.setTextColor(getResources().getColor(R.color.colorBlack));
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
        menuCountInvoice.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuCountInvoice.setTextColor(getResources().getColor(R.color.colorWhite));
        menuCustomer.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuCustomer.setTextColor(getResources().getColor(R.color.colorWhite));

        params = layoutDetail.getLayoutParams(); params.height = 0; layoutDetail.setLayoutParams(params);
        params = layoutCatatan.getLayoutParams(); params.height = 0; layoutCatatan.setLayoutParams(params);
        params = layoutSupplierInvoice.getLayoutParams(); params.height = 0; layoutSupplierInvoice.setLayoutParams(params);
        params = layoutCustomer.getLayoutParams(); params.height = 0; layoutCustomer.setLayoutParams(params);
        params = layoutCountInvoice.getLayoutParams(); params.height = 0; layoutCountInvoice.setLayoutParams(params);
    }
}