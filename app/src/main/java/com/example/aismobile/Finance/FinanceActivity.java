package com.example.aismobile.Finance;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aismobile.Config;
import com.example.aismobile.Data.FinanceAccounting.BankAccount;
import com.example.aismobile.Data.FinanceAccounting.BankTransaction;
import com.example.aismobile.Data.FinanceAccounting.Budgeting;
import com.example.aismobile.Data.FinanceAccounting.CashAdvance;
import com.example.aismobile.Data.FinanceAccounting.CustomerInvoice;
import com.example.aismobile.Data.FinanceAccounting.CustomerReceives;
import com.example.aismobile.Data.FinanceAccounting.DaftarAkun;
import com.example.aismobile.Data.FinanceAccounting.Expense;
import com.example.aismobile.Data.FinanceAccounting.PaymentSupplier;
import com.example.aismobile.Data.FinanceAccounting.SupplierInvoice;
import com.example.aismobile.Finance.BankAccount.BankAccountsFragment;
import com.example.aismobile.Finance.BankTransaction.BankTransactionFragment;
import com.example.aismobile.Finance.Budgeting.BudgetingFragment;
import com.example.aismobile.Finance.CashAdvance.CashAdvanceFragment;
import com.example.aismobile.Finance.CustomerInvoice.CustomerInvoiceFragment;
import com.example.aismobile.Finance.CustomerReceives.CustomerReceivesFragment;
import com.example.aismobile.Finance.DaftarAkun.DaftarAkunFragment;
import com.example.aismobile.Finance.Ekspedisi.EkspedisiFragment;
import com.example.aismobile.Finance.Expenses.ExpensesFragment;
import com.example.aismobile.Finance.PaymentSupplier.PaymentSuppliersFragment;
import com.example.aismobile.Finance.SupplierInvoice.SupplierInvoiceFragment;
import com.example.aismobile.LoginActivity;
import com.example.aismobile.R;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.aismobile.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FinanceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        SupplierInvoiceFragment.OnListFragmentInteractionListener,
        CustomerInvoiceFragment.OnListFragmentInteractionListener,
        BankTransactionFragment.OnListFragmentInteractionListener,
        ExpensesFragment.OnListFragmentInteractionListener,
        CashAdvanceFragment.OnListFragmentInteractionListener,
        BudgetingFragment.OnListFragmentInteractionListener,
        PaymentSuppliersFragment.OnListFragmentInteractionListener,
        BankAccountsFragment.OnListFragmentInteractionListener,
        DaftarAkunFragment.OnListFragmentInteractionListener,
        CustomerReceivesFragment.OnListFragmentInteractionListener {

    FragmentTransaction fragmentTransaction;
    String access = "";
    private Dialog myDialog;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);

        Toolbar toolbar = findViewById(R.id.toolbar_finance);
        setSupportActionBar(toolbar);

        myDialog = new Dialog(this);
        sharedPrefManager = new SharedPrefManager(this);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout_finance);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_finance);
        navigationView.setNavigationItemSelectedListener(this);

        access = getIntent().getStringExtra("access");
        int menu = Integer.valueOf(getIntent().getStringExtra("menu"));
        if (menu == 0)
            swapFragment(R.id.nav_supplierinvoice);
        else if (menu == 1)
            swapFragment(R.id.nav_customerinvoice);
        else if (menu == 2)
            swapFragment(R.id.nav_banktransaction);
        else if (menu == 3)
            swapFragment(R.id.nav_expenses);
        else if (menu == 4)
            swapFragment(R.id.nav_cashadvance);
        else if (menu == 5)
            swapFragment(R.id.nav_budgeting);
        else if (menu == 6)
            swapFragment(R.id.nav_paymentsuppliers);
//        else if (menu == 7)
//            swapFragment(R.id.nav_installment);
        else if (menu == 7)
            swapFragment(R.id.nav_bankaccount);
//        else if (menu == 9)
//            swapFragment(R.id.nav_taxreports);
        else if (menu == 8)
            swapFragment(R.id.nav_daftarakun);
//        else if (menu == 11)
//            swapFragment(R.id.nav_employeesalary);
        else if (menu == 9)
            swapFragment(R.id.nav_ekspedisi);
        else if (menu == 10)
            swapFragment(R.id.nav_customerreceive);
        else swapFragment(R.id.nav_supplierinvoice);
    }

    private void swapFragment(int id) {
        getMobileIsActive(sharedPrefManager.getUserId());

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_supplierinvoice && access.toLowerCase().contains("supplier_invoice".toLowerCase())) {
            SupplierInvoiceFragment mainFragment = SupplierInvoiceFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_customerinvoice && access.toLowerCase().contains("sales_invoice".toLowerCase())) {
            CustomerInvoiceFragment mainFragment = CustomerInvoiceFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_banktransaction && access.toLowerCase().contains("bank_transaction".toLowerCase())) {
            BankTransactionFragment mainFragment = BankTransactionFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_expenses && access. toLowerCase().contains("expenses".toLowerCase())) {
            ExpensesFragment mainFragment = ExpensesFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_cashadvance && access.toLowerCase().contains("advanced".toLowerCase())) {
            CashAdvanceFragment mainFragment = CashAdvanceFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_budgeting && access.toLowerCase().contains("budgeting".toLowerCase())) {
            BudgetingFragment mainFragment = BudgetingFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_paymentsuppliers && access.toLowerCase().contains("payment_supplier".toLowerCase())) {
            PaymentSuppliersFragment mainFragment = PaymentSuppliersFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
//        } else if (id == R.id.nav_installment && access.toLowerCase().contains("installment".toLowerCase())) {
//            InstallmentFragment mainFragment = InstallmentFragment.newInstance();
//            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_bankaccount && access.toLowerCase().contains("bank_account".toLowerCase())) {
            BankAccountsFragment mainFragment = BankAccountsFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
//        } else if (id == R.id.nav_taxreports && access.toLowerCase().contains("tax_report".toLowerCase())) {
//            TaxReportsFragment mainFragment = TaxReportsFragment.newInstance();
//            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_daftarakun && access.toLowerCase().contains("chart_of_account".toLowerCase())) {
            DaftarAkunFragment mainFragment = DaftarAkunFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
//        } else if (id == R.id.nav_employeesalary && access.toLowerCase().contains("employee_salary".toLowerCase())) {
//            EmployeeSalaryFragment mainFragment = EmployeeSalaryFragment.newInstance();
//            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_ekspedisi) {
            EkspedisiFragment mainFragment = EkspedisiFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_customerreceive) {
            CustomerReceivesFragment mainFragment = CustomerReceivesFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else {
            ShowPopup();
        }
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }

    private void getMobileIsActive(final String userId) {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_MOBILE_IS_ACTIVE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int akses = jsonObject.getInt("is_mobile");
                    if (akses > 1){
                        Intent logout = new Intent(FinanceActivity.this, LoginActivity.class);
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
                Toast.makeText(FinanceActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", userId);
                return param;
            }
        };
        Volley.newRequestQueue(FinanceActivity.this).add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.finance, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        swapFragment(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_finance);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void ShowPopup() {
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
        textViewWarning.setText("You can't access this menu");
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    @Override
    public void onListFragmentInteraction(SupplierInvoice item) {

    }

    @Override
    public void onListFragmentInteraction(CustomerInvoice item) {

    }

    @Override
    public void onListFragmentInteraction(BankTransaction item) {

    }

    @Override
    public void onListFragmentInteraction(Expense item) {

    }

    @Override
    public void onListFragmentInteraction(CashAdvance item) {

    }

    @Override
    public void onListFragmentInteraction(Budgeting item) {

    }

    @Override
    public void onListFragmentInteraction(PaymentSupplier item) {

    }

    @Override
    public void onListFragmentInteraction(BankAccount item) {

    }

    @Override
    public void onListFragmentInteraction(DaftarAkun item) {

    }

    @Override
    public void onListFragmentInteraction(CustomerReceives item) {

    }
}
