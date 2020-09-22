package com.example.aismobile.Finance.BankTransaction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aismobile.Data.FinanceAccounting.BankTransaction;
import com.example.aismobile.R;

public class DetailBankTransactionActivity extends AppCompatActivity {

    private BankTransaction bankTransaction;

    private ImageView buttonBack;
    private ImageView downloadAtachment;
    private TextView textNumber;
    private TextView textBtNumber;
    private TextView textKeterangan;
    private TextView textRekBank;
    private TextView textAccountNumber;
    private TextView textTransactionNumber;
    private TextView textTotalAmount;
    private TextView textTglTrans;
    private TextView textReconciledBy;
    private TextView textTglRekon;
    private TextView textCheckedBy;
    private TextView textCheckedDate;
    private TextView textCheckedComment;
    private TextView textApproval1;
    private TextView textApproval1Date;
    private TextView textComment1;
    private TextView textApproval2;
    private TextView textApproval2Date;
    private TextView textComment2;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bank_transaction);

        Bundle bundle = getIntent().getExtras();
        bankTransaction = bundle.getParcelable("detail");

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        downloadAtachment = (ImageView) findViewById(R.id.downloadAtachment);
        textNumber = (TextView) findViewById(R.id.textNumber);
        textBtNumber = (TextView) findViewById(R.id.textBtNumber);
        textKeterangan = (TextView) findViewById(R.id.textKeterangan);
        textRekBank = (TextView) findViewById(R.id.textRekBank);
        textAccountNumber = (TextView) findViewById(R.id.textAccountNumber);
        textTransactionNumber = (TextView) findViewById(R.id.textTransactionNumber);
        textTotalAmount = (TextView) findViewById(R.id.textTotalAmount);
        textTglTrans = (TextView) findViewById(R.id.textTglTrans);
        textReconciledBy = (TextView) findViewById(R.id.textReconciledBy);
        textTglRekon = (TextView) findViewById(R.id.textTglRekon);
        textCheckedBy = (TextView) findViewById(R.id.textCheckedBy);
        textCheckedDate = (TextView) findViewById(R.id.textCheckedDate);
        textCheckedComment = (TextView) findViewById(R.id.textCheckedComment);
        textApproval1 = (TextView) findViewById(R.id.textApproval1);
        textApproval1Date = (TextView) findViewById(R.id.textApproval1Date);
        textComment1 = (TextView) findViewById(R.id.textComment1);
        textApproval2 = (TextView) findViewById(R.id.textApproval2);
        textApproval2Date = (TextView) findViewById(R.id.textApproval2Date);
        textComment2 = (TextView) findViewById(R.id.textComment2);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textNumber.setText("Bank Transaction #" + bankTransaction.getBank_transaction_number());
        textBtNumber.setText(bankTransaction.getBank_transaction_number());
        textKeterangan.setText(bankTransaction.getBank_transaction_description());
        textRekBank.setText(bankTransaction.getBank_name());
        textAccountNumber.setText(bankTransaction.getBank_account_number());
        textTransactionNumber.setText(bankTransaction.getBank_transaction_number());
        textTotalAmount.setText(bankTransaction.getTotal_amount());
        textTglTrans.setText(bankTransaction.getTransaction_date());
        textReconciledBy.setText(bankTransaction.getReconciled_by());
        textTglRekon.setText(bankTransaction.getReconciled_date());
        textCheckedBy.setText(bankTransaction.getChecked_by());
        textCheckedDate.setText(bankTransaction.getChecked_date());
        textCheckedComment.setText(bankTransaction.getChecked_comment());
        textApproval1.setText(bankTransaction.getApproval1());
        textApproval1Date.setText(bankTransaction.getApproval_date1());
        textComment1.setText(bankTransaction.getApproval_comment1());
        textApproval2.setText(bankTransaction.getApproval2());
        textApproval2Date.setText(bankTransaction.getApproval_date2());
        textComment2.setText(bankTransaction.getApproval_comment2());
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
        downloadAtachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/bankTransaction/"+ bankTransaction.getBank_transaction_file_name());
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });
    }
}