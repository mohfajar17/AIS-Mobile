package com.example.aismobile.Finance.CashAdvance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aismobile.Data.FinanceAccounting.CashAdvance;
import com.example.aismobile.R;

public class DetailCashAdvanceActivity extends AppCompatActivity {

    private CashAdvance bankTransaction;

    private ImageView buttonBack;
    private TextView textCaNumber;
    private TextView textCaDate;
    private TextView textNilai;
    private TextView textAdvancedFor;
    private TextView textReceivedBy;
    private TextView textApprovedStatus;
    private TextView textApprovedBy;
    private TextView textApprovedDate;
    private TextView textApprovedComment;
    private TextView textReconciledBy;
    private TextView textReconciledDate;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cash_advance);

        Bundle bundle = getIntent().getExtras();
        bankTransaction = bundle.getParcelable("detail");

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textCaNumber = (TextView) findViewById(R.id.textCaNumber);
        textCaDate = (TextView) findViewById(R.id.textCaDate);
        textNilai = (TextView) findViewById(R.id.textNilai);
        textAdvancedFor = (TextView) findViewById(R.id.textAdvancedFor);
        textReceivedBy = (TextView) findViewById(R.id.textReceivedBy);
        textApprovedStatus = (TextView) findViewById(R.id.textApprovedStatus);
        textApprovedBy = (TextView) findViewById(R.id.textApprovedBy);
        textApprovedDate = (TextView) findViewById(R.id.textApprovedDate);
        textApprovedComment = (TextView) findViewById(R.id.textApprovedComment);
        textReconciledBy = (TextView) findViewById(R.id.textReconciledBy);
        textReconciledDate = (TextView) findViewById(R.id.textReconciledDate);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textCaNumber.setText(bankTransaction.getAdvanced_number());
        textCaDate.setText(bankTransaction.getAdvanced_date());
        textNilai.setText(bankTransaction.getAmount());
        textAdvancedFor.setText(bankTransaction.getAdvanced_for());
        textReceivedBy.setText(bankTransaction.getReceived_by());
        textApprovedStatus.setText(bankTransaction.getApproved_status());
        textApprovedBy.setText(bankTransaction.getApproved_by());
        textApprovedDate.setText(bankTransaction.getApproved_date());
        textApprovedComment.setText(bankTransaction.getApproved_comment());
        textReconciledBy.setText(bankTransaction.getReconciled_by());
        textReconciledDate.setText(bankTransaction.getReconciled_date());
        textCatatan.setText(bankTransaction.getNotes());
        textCreatedBy.setText(bankTransaction.getCreated_by());
        textCreatedDate.setText(bankTransaction.getCreated_date());
        textModifiedBy.setText(bankTransaction.getModified_by());
        textModifiedDate.setText(bankTransaction.getModified_date());

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}