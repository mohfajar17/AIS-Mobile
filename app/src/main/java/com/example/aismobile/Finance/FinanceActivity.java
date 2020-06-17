package com.example.aismobile.Finance;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.aismobile.Finance.CustomerInvoice.CustomerInvoiceFragment;
import com.example.aismobile.R;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class FinanceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction fragmentTransaction;
    String access = "";
    private Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);

        Toolbar toolbar = findViewById(R.id.toolbar_finance);
        setSupportActionBar(toolbar);

        myDialog = new Dialog(this);

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
        else if (menu == 7)
            swapFragment(R.id.nav_installment);
        else if (menu == 8)
            swapFragment(R.id.nav_bankaccount);
        else if (menu == 9)
            swapFragment(R.id.nav_taxreports);
        else if (menu == 10)
            swapFragment(R.id.nav_daftarakun);
        else if (menu == 11)
            swapFragment(R.id.nav_employeesalary);
        else swapFragment(R.id.nav_supplierinvoice);
    }

    private void swapFragment(int id) {
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
        } else if (id == R.id.nav_installment && access.toLowerCase().contains("installment".toLowerCase())) {
            InstallmentFragment mainFragment = InstallmentFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_bankaccount && access.toLowerCase().contains("bank_account".toLowerCase())) {
            BankAccountsFragment mainFragment = BankAccountsFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_taxreports && access.toLowerCase().contains("tax_report".toLowerCase())) {
            TaxReportsFragment mainFragment = TaxReportsFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_daftarakun && access.toLowerCase().contains("chart_of_account".toLowerCase())) {
            DaftarAkunFragment mainFragment = DaftarAkunFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_employeesalary && access.toLowerCase().contains("employee_salary".toLowerCase())) {
            EmployeeSalaryFragment mainFragment = EmployeeSalaryFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else {
            ShowPopup();
        }
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
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
}
