package com.example.aismobile.Project.MaterialRequest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.aismobile.Data.Project.MaterialRequest;
import com.example.aismobile.R;

public class DetailMaterialRequestActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private MaterialRequest materialRequest;

    private TextView textNumber;
    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuPickup;
    private TextView menuCatatan;
    private TextView menuHistory;
    private ScrollView layoutDetail;
    private LinearLayout layoutPickup;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutHistory;

    private TextView textJobOrder;
    private TextView textKeterangan;
    private TextView textSqNumber;
    private TextView textTglPermintaan;
    private TextView textTglPenggunaan;
    private TextView textApproval1;
    private TextView textApproval2;
    private TextView textApproval3;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_material_request);

        Bundle bundle = getIntent().getExtras();
        materialRequest = bundle.getParcelable("detail");

        textJobOrder = (TextView) findViewById(R.id.textJobOrder);
        textKeterangan = (TextView) findViewById(R.id.textKeterangan);
        textSqNumber = (TextView) findViewById(R.id.textSqNumber);
        textTglPermintaan = (TextView) findViewById(R.id.textTglPermintaan);
        textTglPenggunaan = (TextView) findViewById(R.id.textTglPenggunaan);
        textApproval1 = (TextView) findViewById(R.id.textApproval1);
        textApproval2 = (TextView) findViewById(R.id.textApproval2);
        textApproval3 = (TextView) findViewById(R.id.textApproval3);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuPickup = (TextView) findViewById(R.id.menuPickup);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutPickup = (LinearLayout) findViewById(R.id.layoutPickup);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);

        textNumber.setText(materialRequest.getMaterial_request_number());
        textJobOrder.setText(materialRequest.getJob_order_id());
        textKeterangan.setText(materialRequest.getJob_order_description());
        textSqNumber.setText(materialRequest.getSales_quotation_id());
        textTglPermintaan.setText(materialRequest.getRequisition_date());
        textTglPenggunaan.setText(materialRequest.getUsage_date());
        textApproval1.setText(materialRequest.getApproval1() + "\n" + materialRequest.getApproval_date1() + "\n" + materialRequest.getComment1());
        textApproval2.setText(materialRequest.getApproval2() + "\n" + materialRequest.getApproval_date2() + "\n" + materialRequest.getComment2());
        textApproval3.setText(materialRequest.getApproval3() + "\n" + materialRequest.getApproval_date3() + "\n" + materialRequest.getComment3());
        textCatatan.setText(materialRequest.getNotes());
        textCreatedBy.setText(materialRequest.getCreated_by());
        textCreatedDate.setText(materialRequest.getCreated_date());
        textModifiedBy.setText(materialRequest.getModified_by());
        textModifiedDate.setText(materialRequest.getModified_date());

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
        menuPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutPickup.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutPickup.setLayoutParams(params);
                menuPickup.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuPickup.setTextColor(getResources().getColor(R.color.colorBlack));
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
        menuPickup.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuPickup.setTextColor(getResources().getColor(R.color.colorWhite));
        menuCatatan.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuCatatan.setTextColor(getResources().getColor(R.color.colorWhite));
        menuHistory.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuHistory.setTextColor(getResources().getColor(R.color.colorWhite));

        params = layoutDetail.getLayoutParams(); params.height = 0; layoutDetail.setLayoutParams(params);
        params = layoutPickup.getLayoutParams(); params.height = 0; layoutPickup.setLayoutParams(params);
        params = layoutCatatan.getLayoutParams(); params.height = 0; layoutCatatan.setLayoutParams(params);
        params = layoutHistory.getLayoutParams(); params.height = 0; layoutHistory.setLayoutParams(params);
    }
}