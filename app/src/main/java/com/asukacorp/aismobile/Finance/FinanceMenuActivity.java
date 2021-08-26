package com.asukacorp.aismobile.Finance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.LoginActivity;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FinanceMenuActivity extends AppCompatActivity {

    private TextView textViewSupplierInvoices;
    private TextView textViewCustomerInvoice;
    private TextView textViewBankTransaction;
    private TextView textViewExpenses;
    private TextView textViewCashAdvance;
    private TextView textViewBudgeting;
    private TextView textViewPaymentSuppliers;
//    private TextView textViewInstallment;
    private TextView textViewBankAccounts;
//    private TextView textViewTaxReports;
    private TextView textViewDaftarAkun;
//    private TextView textViewEmployeeSalary;
    private TextView textViewEkspedisi;
    private TextView textViewCustomerReceive;

    private Dialog myDialog;
    private String access = "";
    private SharedPrefManager sharedPrefManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_menu);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Refresh Data");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        myDialog = new Dialog(this);

        textViewSupplierInvoices = (TextView) findViewById(R.id.textViewSupplierInvoices);
        textViewCustomerInvoice = (TextView) findViewById(R.id.textViewCustomerInvoice);
        textViewBankTransaction = (TextView) findViewById(R.id.textViewBankTransaction);
        textViewExpenses = (TextView) findViewById(R.id.textViewExpenses);
        textViewCashAdvance = (TextView) findViewById(R.id.textViewCashAdvance);
        textViewBudgeting = (TextView) findViewById(R.id.textViewBudgeting);
        textViewPaymentSuppliers = (TextView) findViewById(R.id.textViewPaymentSuppliers);
//        textViewInstallment = (TextView) findViewById(R.id.textViewInstallment);
        textViewBankAccounts = (TextView) findViewById(R.id.textViewBankAccounts);
//        textViewTaxReports = (TextView) findViewById(R.id.textViewTaxReports);
        textViewDaftarAkun = (TextView) findViewById(R.id.textViewDaftarAkun);
//        textViewEmployeeSalary = (TextView) findViewById(R.id.textViewEmployeeSalary);
        textViewEkspedisi = (TextView) findViewById(R.id.textViewEkspedisi);
        textViewCustomerReceive = (TextView) findViewById(R.id.textViewCustomerReceive);

        textViewSupplierInvoices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("supplier_invoice".toLowerCase())){
                    bukaActivity("0");
                } else ShowPopup("Supplier Invoice");
            }
        });
        textViewCustomerInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("sales_invoice".toLowerCase())){
                    bukaActivity("1");
                } else ShowPopup("Customer Invoice");
            }
        });
        textViewBankTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("bank_transaction".toLowerCase())){
                    bukaActivity("2");
                } else ShowPopup("Bank Transaction");
            }
        });
        textViewExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access. toLowerCase().contains("expenses".toLowerCase())){
                    bukaActivity("3");
                } else ShowPopup("Expenses");
            }
        });
        textViewCashAdvance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("advanced".toLowerCase())){
                    bukaActivity("4");
                } else ShowPopup("Advanced");
            }
        });
        textViewBudgeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("budgeting".toLowerCase())){
                    bukaActivity("5");
                } else ShowPopup("Budgeting");
            }
        });
        textViewPaymentSuppliers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("payment_supplier".toLowerCase())){
                    bukaActivity("6");
                } else ShowPopup("Payment Supplier");
            }
        });
//        textViewInstallment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (access.toLowerCase().contains("installment".toLowerCase())){
//                    bukaActivity("7");
//                } else ShowPopup("Installment");
//            }
//        });
        textViewBankAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("bank_account".toLowerCase())){
                    bukaActivity("7");
                } else ShowPopup("Bank Account");
            }
        });
//        textViewTaxReports.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (access.toLowerCase().contains("tax_report".toLowerCase())){
//                    bukaActivity("9");
//                } else ShowPopup("Tax Report");
//            }
//        });
        textViewDaftarAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access.toLowerCase().contains("chart_of_account".toLowerCase())){
                    bukaActivity("8");
                } else ShowPopup("Daftar Akun");
            }
        });
//        textViewEmployeeSalary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (access.toLowerCase().contains("employee_salary".toLowerCase())){
//                    bukaActivity("11");
//                } else ShowPopup("Employee Salary");
//            }
//        });
        textViewEkspedisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("9");
            }
        });
        textViewCustomerReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("10");
            }
        });

        getAccessModul();
    }

    private void bukaActivity(String menu){
        Intent bukaActivity = new Intent(FinanceMenuActivity.this, FinanceActivity.class);
        bukaActivity.putExtra("menu", menu);
        bukaActivity.putExtra("access", access);
        startActivityForResult(bukaActivity,1);
        finish();
    }

    private void getAccessModul() {
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_ACCESS_FINANCE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if (jsonObject.getInt("is_mobile") > 1){
                        Intent logout = new Intent(FinanceMenuActivity.this, LoginActivity.class);
                        startActivity(logout);
                        sharedPrefManager.logout();
                        finish();
                    } else {
                        if(status==1){
                            JSONObject jsonData = jsonObject.getJSONObject("data");
                            if (Integer.valueOf(jsonData.getString("supplier_invoice")) == 1)
                                access = access+"supplier_invoice, ";
                            if (Integer.valueOf(jsonData.getString("sales_invoice")) == 1)
                                access = access+"sales_invoice, ";
                            if (Integer.valueOf(jsonData.getString("bank_transaction")) == 1)
                                access = access+"bank_transaction, ";
                            if (Integer.valueOf(jsonData.getString("expenses")) == 1)
                                access = access+"expenses, ";
                            if (Integer.valueOf(jsonData.getString("advanced")) == 1)
                                access = access+"advanced, ";
                            if (Integer.valueOf(jsonData.getString("budgeting")) == 1)
                                access = access+"budgeting, ";
                            if (Integer.valueOf(jsonData.getString("payment_supplier")) == 1)
                                access = access+"payment_supplier, ";
                            if (Integer.valueOf(jsonData.getString("installment")) == 1)
                                access = access+"installment, ";
                            if (Integer.valueOf(jsonData.getString("bank_account")) == 1)
                                access = access+"bank_account, ";
//                        if (Integer.valueOf(jsonData.getString("tax_report")) == 1)
//                            access = access+"tax_report, ";
                            if (Integer.valueOf(jsonData.getString("chart_of_account")) == 1)
                                access = access+"chart_of_account, ";
                            if (Integer.valueOf(jsonData.getString("employee_salary")) == 1)
                                access = access+"employee_salary, ";
                        } else {
                            access = access+"";
                        }
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
//                    onBackPressed();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                onBackPressed();
                Toast.makeText(FinanceMenuActivity.this, "Your network is broken, please check your network", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", sharedPrefManager.getUserId());
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    public void ShowPopup(String massage) {
        TextView textViewWarning;
        TextView closeDialog;
        myDialog.setContentView(R.layout.custom_popup);
        textViewWarning = (TextView) myDialog.findViewById(R.id.textViewWarning);
        closeDialog = (TextView) myDialog.findViewById(R.id.closeDialog);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        textViewWarning.setText("You can't access menu " + massage);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}