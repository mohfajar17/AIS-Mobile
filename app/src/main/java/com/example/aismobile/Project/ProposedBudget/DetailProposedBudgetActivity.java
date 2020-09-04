package com.example.aismobile.Project.ProposedBudget;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.aismobile.Data.Project.ProposedBudget;
import com.example.aismobile.R;

public class DetailProposedBudgetActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private ProposedBudget proposedBudget;

    private TextView textNumber;
    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuHistory;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutHistory;

    private TextView textJobCode;
    private TextView textKeteranganJobCode;
    private TextView textPenanggungJwb;
    private TextView textRequestDate;
    private TextView textPaymentDate;
    private TextView textDueDate;
    private TextView textAccount;
    private TextView textCategory;
    private TextView textDone;
    private TextView textCheckedBy;
    private TextView textCheckedDate;
    private TextView textPenerima;
    private TextView textApproval1;
    private TextView textApproval2;
    private TextView textApproval3;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;
    private TextView textGrandTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_proposed_budget);

        Bundle bundle = getIntent().getExtras();
        proposedBudget = bundle.getParcelable("detail");

        textJobCode = (TextView) findViewById(R.id.textJobCode);
        textKeteranganJobCode = (TextView) findViewById(R.id.textKeteranganJobCode);
        textPenanggungJwb = (TextView) findViewById(R.id.textPenanggungJwb);
        textRequestDate = (TextView) findViewById(R.id.textRequestDate);
        textPaymentDate = (TextView) findViewById(R.id.textPaymentDate);
        textDueDate = (TextView) findViewById(R.id.textDueDate);
        textAccount = (TextView) findViewById(R.id.textAccount);
        textCategory = (TextView) findViewById(R.id.textCategory);
        textDone = (TextView) findViewById(R.id.textDone);
        textCheckedBy = (TextView) findViewById(R.id.textCheckedBy);
        textCheckedDate = (TextView) findViewById(R.id.textCheckedDate);
        textPenerima = (TextView) findViewById(R.id.textPenerima);
        textApproval1 = (TextView) findViewById(R.id.textApproval1);
        textApproval2 = (TextView) findViewById(R.id.textApproval2);
        textApproval3 = (TextView) findViewById(R.id.textApproval3);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);
        textGrandTotal = (TextView) findViewById(R.id.textGrandTotal);

        textJobCode.setText(proposedBudget.getJob_order_id());
        textKeteranganJobCode.setText("");
        textPenanggungJwb.setText(proposedBudget.getPerson_in_charge());
        textRequestDate.setText(proposedBudget.getRequisition_date());
        textPaymentDate.setText(proposedBudget.getPayment_date());
        textDueDate.setText(proposedBudget.getDue_date());
        textAccount.setText("");
        textCategory.setText("");
        textDone.setText(proposedBudget.getDone());
        textCheckedBy.setText(proposedBudget.getChecked_by());
        textCheckedDate.setText(proposedBudget.getChecked_date());
        textPenerima.setText(proposedBudget.getRecipient_by());
        textApproval1.setText(proposedBudget.getApproval1() + "\n" + proposedBudget.getApproval_date1() + "\n" + proposedBudget.getApproval_comment1());
        textApproval2.setText(proposedBudget.getApproval2() + "\n" + proposedBudget.getApproval_date2() + "\n" + proposedBudget.getApproval_comment2());
        textApproval3.setText(proposedBudget.getApproval3() + "\n" + proposedBudget.getApproval_date3() + "\n" + proposedBudget.getApproval_comment3());
        textCatatan.setText(proposedBudget.getNotes());
        textCreatedBy.setText(proposedBudget.getCreated_by());
        textCreatedDate.setText(proposedBudget.getCreated_date());
        textModifiedBy.setText(proposedBudget.getModified_by());
        textModifiedDate.setText(proposedBudget.getModified_date());

        textNumber = (TextView) findViewById(R.id.textNumber);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);

        textNumber.setText(proposedBudget.getCash_advance_number());
        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);
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