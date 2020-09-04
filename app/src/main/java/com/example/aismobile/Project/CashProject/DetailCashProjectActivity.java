package com.example.aismobile.Project.CashProject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.aismobile.Data.Project.CashProjectReport;
import com.example.aismobile.R;

public class DetailCashProjectActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private CashProjectReport workOrder;

    private TextView textNumber;
    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuHistory;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutHistory;

    private TextView textJobOrder;
    private TextView textKeterangan;
    private TextView textProposedBudget;
    private TextView textTglAwal;
    private TextView textTglAkhir;
    private TextView textAccount;
    private TextView textCategory;
    private TextView textDone;
    private TextView textCheckedBy;
    private TextView textCheckedDate;
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
        setContentView(R.layout.activity_detail_cash_project);

        Bundle bundle = getIntent().getExtras();
        workOrder = bundle.getParcelable("detail");

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);

        textJobOrder = (TextView) findViewById(R.id.textJobOrder);
        textKeterangan = (TextView) findViewById(R.id.textKeterangan);
        textProposedBudget = (TextView) findViewById(R.id.textProposedBudget);
        textTglAwal = (TextView) findViewById(R.id.textTglAwal);
        textTglAkhir = (TextView) findViewById(R.id.textTglAkhir);
        textAccount = (TextView) findViewById(R.id.textAccount);
        textCategory = (TextView) findViewById(R.id.textCategory);
        textDone = (TextView) findViewById(R.id.textDone);
        textCheckedBy = (TextView) findViewById(R.id.textCheckedBy);
        textCheckedDate = (TextView) findViewById(R.id.textCheckedDate);
        textApproval1 = (TextView) findViewById(R.id.textApproval1);
        textApproval2 = (TextView) findViewById(R.id.textApproval2);
        textApproval3 = (TextView) findViewById(R.id.textApproval3);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textJobOrder.setText(workOrder.getJob_order_id());
        textKeterangan.setText("");
        textProposedBudget.setText(workOrder.getCash_advance_id());
        textTglAwal.setText(workOrder.getBegin_date());
        textTglAkhir.setText(workOrder.getEnd_date());
        textAccount.setText("");
        textCategory.setText("");
        textDone.setText(workOrder.getDone());
        textCheckedBy.setText(workOrder.getChecked_by());
        textCheckedDate.setText(workOrder.getChecked_date());
        textApproval1.setText(workOrder.getApproval1() + "\n" + workOrder.getApproval_date1() + "\n" + workOrder.getApproval_comment1());
        textApproval2.setText(workOrder.getApproval2() + "\n" + workOrder.getApproval_date2() + "\n" + workOrder.getApproval_comment2());
        textApproval3.setText(workOrder.getApproval3() + "\n" + workOrder.getApproval_date3() + "\n" + workOrder.getApproval_comment3());
        textCatatan.setText(workOrder.getNotes());
        textCreatedBy.setText(workOrder.getCreated_by());
        textCreatedDate.setText(workOrder.getCreated_date());
        textModifiedBy.setText(workOrder.getModified_by());
        textModifiedDate.setText(workOrder.getModified_date());

        textNumber.setText(workOrder.getResponsbility_advance_number());
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

//        loadDetail();
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