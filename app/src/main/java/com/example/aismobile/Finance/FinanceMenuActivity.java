package com.example.aismobile.Finance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aismobile.R;

public class FinanceMenuActivity extends AppCompatActivity {

    private TextView textViewCustomerInvoice;
    private TextView textViewBankTransaction;
    private TextView textViewExpenses;
    private TextView textViewCashAdvance;
    private TextView textViewBudgeting;
    private TextView textViewPaymentSuppliers;
    private TextView textViewDaftarAkun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_menu);

        textViewCustomerInvoice = (TextView) findViewById(R.id.textViewCustomerInvoice);
        textViewBankTransaction = (TextView) findViewById(R.id.textViewBankTransaction);
        textViewExpenses = (TextView) findViewById(R.id.textViewExpenses);
        textViewCashAdvance = (TextView) findViewById(R.id.textViewCashAdvance);
        textViewBudgeting = (TextView) findViewById(R.id.textViewBudgeting);
        textViewPaymentSuppliers = (TextView) findViewById(R.id.textViewPaymentSuppliers);
        textViewDaftarAkun = (TextView) findViewById(R.id.textViewDaftarAkun);

        textViewCustomerInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("0");
            }
        });
        textViewBankTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("1");
            }
        });
        textViewExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("2");
            }
        });
        textViewCashAdvance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("3");
            }
        });
        textViewBudgeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("4");
            }
        });
        textViewPaymentSuppliers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("5");
            }
        });
        textViewDaftarAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("6");
            }
        });
    }

    private void bukaActivity(String menu){
        Intent bukaPersonaliaActivity = new Intent(FinanceMenuActivity.this, FinanceActivity.class);
        bukaPersonaliaActivity.putExtra("menu", menu);
        startActivityForResult(bukaPersonaliaActivity,1);
        finish();
    }
}