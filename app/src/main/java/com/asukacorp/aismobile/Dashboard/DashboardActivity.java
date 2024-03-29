package com.asukacorp.aismobile.Dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.Dashboard.Advance.DashAdvanceActivity;
import com.asukacorp.aismobile.Dashboard.BankTransaction.DashBankTransactionActivity;
import com.asukacorp.aismobile.Dashboard.Budgeting.DashBudgetingActivity;
import com.asukacorp.aismobile.Dashboard.CashOnDelivery.DashCashOnDeliveryActivity;
import com.asukacorp.aismobile.Dashboard.CashProjectReport.DashCashProjectReportActivity;
import com.asukacorp.aismobile.Dashboard.Cuti.DashCutiActivity;
import com.asukacorp.aismobile.Dashboard.Expense.DashExpenseActivity;
import com.asukacorp.aismobile.Dashboard.MaterialRequest.DashMaterialRequestActivity;
import com.asukacorp.aismobile.Dashboard.MaterialReturn.DashMaterialReturnActivity;
import com.asukacorp.aismobile.Dashboard.PaymentSupplier.DashPaymentSupplierActivity;
import com.asukacorp.aismobile.Dashboard.ProposedBudget.DashProposedBudgetActivity;
import com.asukacorp.aismobile.Dashboard.PurchaseOrder.DashPurchaseOrderActivity;
import com.asukacorp.aismobile.Dashboard.Spkl.DashSpklActivity;
import com.asukacorp.aismobile.Dashboard.StockAdjustment.DashStockAdjustmentActivity;
import com.asukacorp.aismobile.Dashboard.TunjanganLokasi.DashTunjanganLokasiActivity;
import com.asukacorp.aismobile.Dashboard.TunjanganPerjalanan.DashTunjanganPerjalananActivity;
import com.asukacorp.aismobile.Dashboard.WorkOrder.DashWorkOrderActivity;
import com.asukacorp.aismobile.Dashboard.WorkRequest.DashWorkRequestActivity;
import com.asukacorp.aismobile.LoginActivity;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {

    private SharedPrefManager sharedPrefManager;
    private ProgressDialog progressDialog;

    private LinearLayout dashboardProject;
    private LinearLayout dashboardPurchasing;
    private LinearLayout dashboardInventory;
    private LinearLayout dashboardFinance;
    private LinearLayout layoutProject;
    private LinearLayout layoutPurchasing;
    private LinearLayout layoutInventory;
    private LinearLayout layoutFinance;

    private LinearLayout layoutMaterialRequest;
    private LinearLayout layoutWorkRequest;
    private LinearLayout layoutSpkl;
    private LinearLayout layoutProposedBudget;
    private LinearLayout layoutCashProjectReport;
    private LinearLayout layoutCutiKaryawan;
    private LinearLayout layoutTunjanganLokasi;
    private LinearLayout layoutTunjanganPerjalanan;
    private LinearLayout layoutPurchaseOrder;
    private LinearLayout layoutWorkOrder;
    private LinearLayout layoutCashOnDelivery;
    private LinearLayout layoutMaterialReturn;
    private LinearLayout layoutStockAdjustment;
    private LinearLayout layoutBudgeting;
    private LinearLayout layoutPaymentSupplier;
    private LinearLayout layoutBankTransaction;
    private LinearLayout layoutExpense;
    private LinearLayout layoutCashAdvance;

    private TextView textJmlProject;
    private TextView textJmlPurchsing;
    private TextView textJmlInventory;
    private TextView textJmlFinance;

    private TextView textMaterialRequest;
    private TextView textWorkRequest;
    private TextView textSPKL;
    private TextView textProposedBudget;
    private TextView textCashProjectReport;
    private TextView textCutiKaryawan;
    private TextView textTunjanganLokasi;
    private TextView textTunjanganPerjalananDinas;
    private TextView textPurchaseOrder;
    private TextView textWorkOrder;
    private TextView textCashOnDelivery;
    private TextView textMaterialReturn;
    private TextView textStockAdjustment;
    private TextView textBudgeting;
    private TextView textPaymentSupplier;
    private TextView textBankTransaction;
    private TextView textExpense;
    private TextView textCashAdvance;

    private FloatingActionButton fabRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sharedPrefManager = new SharedPrefManager(this);

        textMaterialRequest = (TextView) findViewById(R.id.textMaterialRequest);
        textWorkRequest = (TextView) findViewById(R.id.textWorkRequest);
        textSPKL = (TextView) findViewById(R.id.textSPKL);
        textProposedBudget = (TextView) findViewById(R.id.textProposedBudget);
        textCashProjectReport = (TextView) findViewById(R.id.textCashProjectReport);
        textCutiKaryawan = (TextView) findViewById(R.id.textCutiKaryawan);
        textTunjanganLokasi = (TextView) findViewById(R.id.textTunjanganLokasi);
        textTunjanganPerjalananDinas = (TextView) findViewById(R.id.textTunjanganPerjalananDinas);
        textPurchaseOrder = (TextView) findViewById(R.id.textPurchaseOrder);
        textWorkOrder = (TextView) findViewById(R.id.textWorkOrder);
        textCashOnDelivery = (TextView) findViewById(R.id.textCashOnDelivery);
        textMaterialReturn = (TextView) findViewById(R.id.textMaterialReturn);
        textStockAdjustment = (TextView) findViewById(R.id.textStockAdjustment);
        textBudgeting = (TextView) findViewById(R.id.textBudgeting);
        textPaymentSupplier = (TextView) findViewById(R.id.textPaymentSupplier);
        textBankTransaction = (TextView) findViewById(R.id.textBankTransaction);
        textExpense = (TextView) findViewById(R.id.textExpense);
        textCashAdvance = (TextView) findViewById(R.id.textCashAdvance);

        textJmlProject = (TextView) findViewById(R.id.textJmlProject);
        textJmlPurchsing = (TextView) findViewById(R.id.textJmlPurchsing);
        textJmlInventory = (TextView) findViewById(R.id.textJmlInventory);
        textJmlFinance = (TextView) findViewById(R.id.textJmlFinance);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        dashboardProject = (LinearLayout) findViewById(R.id.dashboardProject);
        dashboardPurchasing = (LinearLayout) findViewById(R.id.dashboardPurchasing);
        dashboardInventory = (LinearLayout) findViewById(R.id.dashboardInventory);
        dashboardFinance = (LinearLayout) findViewById(R.id.dashboardFinance);
        layoutProject = (LinearLayout) findViewById(R.id.layoutProject);
        layoutPurchasing = (LinearLayout) findViewById(R.id.layoutPurchasing);
        layoutInventory = (LinearLayout) findViewById(R.id.layoutInventory);
        layoutFinance = (LinearLayout) findViewById(R.id.layoutFinance);

        layoutMaterialRequest = (LinearLayout) findViewById(R.id.layoutMaterialRequest);
        layoutWorkRequest = (LinearLayout) findViewById(R.id.layoutWorkRequest);
        layoutSpkl = (LinearLayout) findViewById(R.id.layoutSpkl);
        layoutProposedBudget = (LinearLayout) findViewById(R.id.layoutProposedBudget);
        layoutCashProjectReport = (LinearLayout) findViewById(R.id.layoutCashProjectReport);
        layoutCutiKaryawan = (LinearLayout) findViewById(R.id.layoutCutiKaryawan);
        layoutTunjanganLokasi = (LinearLayout) findViewById(R.id.layoutTunjanganLokasi);
        layoutTunjanganPerjalanan = (LinearLayout) findViewById(R.id.layoutTunjanganPerjalanan);
        layoutPurchaseOrder = (LinearLayout) findViewById(R.id.layoutPurchaseOrder);
        layoutWorkOrder = (LinearLayout) findViewById(R.id.layoutWorkOrder);
        layoutCashOnDelivery = (LinearLayout) findViewById(R.id.layoutCashOnDelivery);
        layoutMaterialReturn = (LinearLayout) findViewById(R.id.layoutMaterialReturn);
        layoutStockAdjustment = (LinearLayout) findViewById(R.id.layoutStockAdjustment);
        layoutBudgeting = (LinearLayout) findViewById(R.id.layoutBudgeting);
        layoutPaymentSupplier = (LinearLayout) findViewById(R.id.layoutPaymentSupplier);
        layoutBankTransaction = (LinearLayout) findViewById(R.id.layoutBankTransaction);
        layoutExpense = (LinearLayout) findViewById(R.id.layoutExpense);
        layoutCashAdvance = (LinearLayout) findViewById(R.id.layoutCashAdvance);

        layoutMaterialRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Intent intent = new Intent(DashboardActivity.this, DashMaterialRequestActivity.class);
                startActivity(intent);
            }
        });
        layoutWorkRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Intent intent = new Intent(DashboardActivity.this, DashWorkRequestActivity.class);
                startActivity(intent);
            }
        });
        layoutSpkl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Intent intent = new Intent(DashboardActivity.this, DashSpklActivity.class);
                startActivity(intent);
            }
        });
        layoutProposedBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Intent intent = new Intent(DashboardActivity.this, DashProposedBudgetActivity.class);
                startActivity(intent);
            }
        });
        layoutCashProjectReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Intent intent = new Intent(DashboardActivity.this, DashCashProjectReportActivity.class);
                startActivity(intent);
            }
        });
        layoutCutiKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Intent intent = new Intent(DashboardActivity.this, DashCutiActivity.class);
                startActivity(intent);
            }
        });
        layoutTunjanganLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Intent intent = new Intent(DashboardActivity.this, DashTunjanganLokasiActivity.class);
                startActivity(intent);
            }
        });
        layoutTunjanganPerjalanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Intent intent = new Intent(DashboardActivity.this, DashTunjanganPerjalananActivity.class);
                startActivity(intent);
            }
        });
        layoutPurchaseOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Intent intent = new Intent(DashboardActivity.this, DashPurchaseOrderActivity.class);
                startActivity(intent);
            }
        });
        layoutWorkOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Intent intent = new Intent(DashboardActivity.this, DashWorkOrderActivity.class);
                startActivity(intent);
            }
        });
        layoutCashOnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Intent intent = new Intent(DashboardActivity.this, DashCashOnDeliveryActivity.class);
                startActivity(intent);
            }
        });
        layoutStockAdjustment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Intent intent = new Intent(DashboardActivity.this, DashStockAdjustmentActivity.class);
                startActivity(intent);
            }
        });
        layoutMaterialReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Intent intent = new Intent(DashboardActivity.this, DashMaterialReturnActivity.class);
                startActivity(intent);
            }
        });
        layoutBudgeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Intent intent = new Intent(DashboardActivity.this, DashBudgetingActivity.class);
                startActivity(intent);
            }
        });
        layoutPaymentSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Intent intent = new Intent(DashboardActivity.this, DashPaymentSupplierActivity.class);
                startActivity(intent);
            }
        });
        layoutBankTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Intent intent = new Intent(DashboardActivity.this, DashBankTransactionActivity.class);
                startActivity(intent);
            }
        });
        layoutExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Intent intent = new Intent(DashboardActivity.this, DashExpenseActivity.class);
                startActivity(intent);
            }
        });
        layoutCashAdvance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Intent intent = new Intent(DashboardActivity.this, DashAdvanceActivity.class);
                startActivity(intent);
            }
        });

        dashboardProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayout(layoutProject);
            }
        });
        dashboardPurchasing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayout(layoutPurchasing);
            }
        });
        dashboardInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayout(layoutInventory);
            }
        });
        dashboardFinance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayout(layoutFinance);
            }
        });

        fabRefresh = (FloatingActionButton) findViewById(R.id.fabRefresh);
        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDetail();
                getMobileIsActive(sharedPrefManager.getUserId());
            }
        });

        loadDetail();
        getMobileIsActive(sharedPrefManager.getUserId());
    }

    @Override
    public void onResume() {
        loadDetail();
        getMobileIsActive(sharedPrefManager.getUserId());

        super.onResume();
    }

    private void getMobileIsActive(final String userId) {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_MOBILE_IS_ACTIVE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int akses = jsonObject.getInt("is_mobile");
                    if (akses > 1){
                        Intent logout = new Intent(DashboardActivity.this, LoginActivity.class);
                        startActivity(logout);
                        sharedPrefManager.logout();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashboardActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", userId);
                return param;
            }
        };
        Volley.newRequestQueue(DashboardActivity.this).add(request);
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_TOTAL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    textMaterialRequest.setText(jsonObject.getString("MaterialRequisition"));
                    textWorkRequest.setText(jsonObject.getString("WorkOrder"));
                    textSPKL.setText(jsonObject.getString("Spkl"));
                    textProposedBudget.setText(jsonObject.getString("ProposedBudget"));
                    textCashProjectReport.setText(jsonObject.getString("CashProjectReport"));
                    textCutiKaryawan.setText(jsonObject.getString("CutiKaryawan"));
                    textTunjanganLokasi.setText(jsonObject.getString("TunjanganKaryawan"));
                    textTunjanganPerjalananDinas.setText(jsonObject.getString("TunjanganTemporary"));
                    textPurchaseOrder.setText(jsonObject.getString("PurchaseOrder"));
                    textWorkOrder.setText(jsonObject.getString("PurchaseService"));
                    textCashOnDelivery.setText(jsonObject.getString("CashOnDelivery"));
                    textMaterialReturn.setText(jsonObject.getString("MaterialReturn"));
                    textStockAdjustment.setText(jsonObject.getString("StockAdjustment"));
                    textBudgeting.setText(jsonObject.getString("Budgeting"));
                    textPaymentSupplier.setText(jsonObject.getString("PaymentSupplier"));
                    textBankTransaction.setText(jsonObject.getString("BankTransaction"));
                    textExpense.setText(jsonObject.getString("Expense"));
                    textCashAdvance.setText(jsonObject.getString("CashAdvance"));

                    int jml = 0;
                    jml = jsonObject.getInt("MaterialRequisition") + jsonObject.getInt("WorkOrder") + jsonObject.getInt("Spkl") + jsonObject.getInt("ProposedBudget") + jsonObject.getInt("CashProjectReport") + jsonObject.getInt("CutiKaryawan") + jsonObject.getInt("TunjanganKaryawan") + jsonObject.getInt("TunjanganTemporary");
                    textJmlProject.setText("" + jml);
                    jml = jsonObject.getInt("PurchaseOrder") + jsonObject.getInt("PurchaseService") + jsonObject.getInt("CashOnDelivery");
                    textJmlPurchsing.setText("" + jml);
                    jml = jsonObject.getInt("MaterialReturn") + jsonObject.getInt("StockAdjustment");
                    textJmlInventory.setText("" + jml);
                    jml = jsonObject.getInt("Budgeting") + jsonObject.getInt("PaymentSupplier") + jsonObject.getInt("BankTransaction") + jsonObject.getInt("Expense") + jsonObject.getInt("CashAdvance");
                    textJmlFinance.setText("" + jml);

                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashboardActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashboardActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        Volley.newRequestQueue(DashboardActivity.this).add(request);
    }

    private void setLayout(LinearLayout linearLayout){
        ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
        if (params.height == 0)
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        else params.height = 0;
        linearLayout.setLayoutParams(params);
    }
}