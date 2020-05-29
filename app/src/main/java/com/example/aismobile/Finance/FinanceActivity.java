package com.example.aismobile.Finance;

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

public class FinanceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);

        Toolbar toolbar = findViewById(R.id.toolbar_finance);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout_finance);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_finance);
        navigationView.setNavigationItemSelectedListener(this);

        int menu = Integer.valueOf(getIntent().getStringExtra("menu"));
        if (menu == 0)
            swapFragment(R.id.nav_customerinvoice);
        else if (menu == 1)
            swapFragment(R.id.nav_banktransaction);
        else if (menu == 2)
            swapFragment(R.id.nav_expenses);
        else if (menu == 3)
            swapFragment(R.id.nav_cashadvance);
        else if (menu == 4)
            swapFragment(R.id.nav_budgeting);
        else if (menu == 5)
            swapFragment(R.id.nav_paymentsuppliers);
        else if (menu == 6)
            swapFragment(R.id.nav_daftarakun);
        else swapFragment(R.id.nav_customerinvoice);
    }

    private void swapFragment(int id) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_customerinvoice) {
            CustomerInvoiceFragment mainFragment = CustomerInvoiceFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_banktransaction) {
            BankTransactionFragment mainFragment = BankTransactionFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_expenses) {
            ExpensesFragment mainFragment = ExpensesFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_cashadvance) {
            CashAdvanceFragment mainFragment = CashAdvanceFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_budgeting) {
            BudgetingFragment mainFragment = BudgetingFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_paymentsuppliers) {
            PaymentSuppliersFragment mainFragment = PaymentSuppliersFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_daftarakun) {
            DaftarAkunFragment mainFragment = DaftarAkunFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else {
            CustomerInvoiceFragment mainFragment = CustomerInvoiceFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment,mainFragment);
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
}
