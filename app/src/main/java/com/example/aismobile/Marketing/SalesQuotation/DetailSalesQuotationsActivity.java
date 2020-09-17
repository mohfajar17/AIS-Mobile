package com.example.aismobile.Marketing.SalesQuotation;

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

import com.example.aismobile.Data.Marketing.SalesQuotation;
import com.example.aismobile.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DetailSalesQuotationsActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private SalesQuotation cashOnDelivery;

    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuHistory;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutHistory;

    private TextView textDepartemen;
    private TextView textKeterangan;
    private TextView textSQNumber;
    private TextView textSQDate;
    private TextView textCompletionDate;
    private TextView textPoNumber;
    private TextView textSqCategory;
    private TextView textPaymentDescription;
    private TextView textNilai;
    private TextView textWoAmount;
    private TextView textSupervisor;
    private TextView textPersetujuan;
    private TextView textKontak;
    private TextView textJobOrder;
    private TextView textKeteranganJo;
    private TextView textTglAwalJo;
    private TextView textTglAkhirJo;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

    private double todouble;
    private NumberFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sales_quotations);

        Bundle bundle = getIntent().getExtras();
        cashOnDelivery = bundle.getParcelable("detail");
        formatter = new DecimalFormat("#,###");

        textDepartemen = (TextView) findViewById(R.id.textDepartemen);
        textKeterangan = (TextView) findViewById(R.id.textKeterangan);
        textSQNumber = (TextView) findViewById(R.id.textSQNumber);
        textSQDate = (TextView) findViewById(R.id.textSQDate);
        textCompletionDate = (TextView) findViewById(R.id.textCompletionDate);
        textPoNumber = (TextView) findViewById(R.id.textPoNumber);
        textSqCategory = (TextView) findViewById(R.id.textSqCategory);
        textPaymentDescription = (TextView) findViewById(R.id.textPaymentDescription);
        textNilai = (TextView) findViewById(R.id.textNilai);
        textWoAmount = (TextView) findViewById(R.id.textWoAmount);
        textSupervisor = (TextView) findViewById(R.id.textSupervisor);
        textPersetujuan = (TextView) findViewById(R.id.textPersetujuan);
        textKontak = (TextView) findViewById(R.id.textKontak);
        textJobOrder = (TextView) findViewById(R.id.textJobOrder);
        textKeteranganJo = (TextView) findViewById(R.id.textKeteranganJo);
        textTglAwalJo = (TextView) findViewById(R.id.textTglAwalJo);
        textTglAkhirJo = (TextView) findViewById(R.id.textTglAkhirJo);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textDepartemen.setText("Departemen : "+ cashOnDelivery.getDepartment_name());
        textKeterangan.setText("Keterangan : "+ cashOnDelivery.getDescription());
        textSQNumber.setText(cashOnDelivery.getSales_quotation_number());
        textSQDate.setText(cashOnDelivery.getSq_date());
        textCompletionDate.setText(cashOnDelivery.getCompletion_date());
        textPoNumber.setText(cashOnDelivery.getClient_po_number());
        textSqCategory.setText(cashOnDelivery.getSales_quotation_category());
        textPaymentDescription.setText(cashOnDelivery.getPayment_desc());
        todouble = Double.valueOf(cashOnDelivery.getAmount());
        textNilai.setText("Rp. "+ formatter.format(Long.valueOf((int) todouble)));
        todouble = Double.valueOf(cashOnDelivery.getWo_amount());
        textWoAmount.setText("Rp. "+ formatter.format(Long.valueOf((int) todouble)));
        textSupervisor.setText(cashOnDelivery.getSupervisor());
        textPersetujuan.setText(cashOnDelivery.getApproval_assign_id());
        textKontak.setText(cashOnDelivery.getContact_id());
        textJobOrder.setText(cashOnDelivery.getJob_order_number());
        textKeteranganJo.setText(cashOnDelivery.getJob_order_description());
        textTglAwalJo.setText(cashOnDelivery.getBegin_date());
        textTglAkhirJo.setText(cashOnDelivery.getEnd_date());
        textCatatan.setText(cashOnDelivery.getNotes());
        textCreatedBy.setText(cashOnDelivery.getCreated_by());
        textCreatedDate.setText(cashOnDelivery.getCreated_date());
        textModifiedBy.setText(cashOnDelivery.getModified_by());
        textModifiedDate.setText(cashOnDelivery.getModified_date());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);

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
        menuHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutHistory.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutHistory.setLayoutParams(params);
                menuHistory.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuHistory.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
    }

    private void hiddenLayout(){
        menuDetail.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuDetail.setTextColor(getResources().getColor(R.color.colorWhite));
        menuCatatan.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuCatatan.setTextColor(getResources().getColor(R.color.colorWhite));
        menuHistory.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuHistory.setTextColor(getResources().getColor(R.color.colorWhite));

        params = layoutDetail.getLayoutParams(); params.height = 0; layoutDetail.setLayoutParams(params);
        params = layoutCatatan.getLayoutParams(); params.height = 0; layoutCatatan.setLayoutParams(params);
        params = layoutHistory.getLayoutParams(); params.height = 0; layoutHistory.setLayoutParams(params);
    }
}