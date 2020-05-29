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
                Intent bukaPersonaliaActivity = new Intent(FinanceMenuActivity.this, FinanceActivity.class);
                bukaPersonaliaActivity.putExtra("menu", "0");
                startActivityForResult(bukaPersonaliaActivity,1);
                finish();
            }
        });
        textViewBankTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaPersonaliaActivity = new Intent(FinanceMenuActivity.this, FinanceActivity.class);
                bukaPersonaliaActivity.putExtra("menu", "1");
                startActivityForResult(bukaPersonaliaActivity,1);
                finish();
            }
        });
        textViewExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaPersonaliaActivity = new Intent(FinanceMenuActivity.this, FinanceActivity.class);
                bukaPersonaliaActivity.putExtra("menu", "2");
                startActivityForResult(bukaPersonaliaActivity,1);
                finish();
            }
        });
        textViewCashAdvance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaPersonaliaActivity = new Intent(FinanceMenuActivity.this, FinanceActivity.class);
                bukaPersonaliaActivity.putExtra("menu", "3");
                startActivityForResult(bukaPersonaliaActivity,1);
                finish();
            }
        });
        textViewBudgeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaPersonaliaActivity = new Intent(FinanceMenuActivity.this, FinanceActivity.class);
                bukaPersonaliaActivity.putExtra("menu", "4");
                startActivityForResult(bukaPersonaliaActivity,1);
                finish();
            }
        });
        textViewPaymentSuppliers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaPersonaliaActivity = new Intent(FinanceMenuActivity.this, FinanceActivity.class);
                bukaPersonaliaActivity.putExtra("menu", "5");
                startActivityForResult(bukaPersonaliaActivity,1);
                finish();
            }
        });
        textViewDaftarAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaPersonaliaActivity = new Intent(FinanceMenuActivity.this, FinanceActivity.class);
                bukaPersonaliaActivity.putExtra("menu", "6");
                startActivityForResult(bukaPersonaliaActivity,1);
                finish();
            }
        });
    }
}