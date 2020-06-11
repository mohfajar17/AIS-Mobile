package com.example.aismobile.Purchasing;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;

import com.example.aismobile.R;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PurchasingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchasing);

        Toolbar toolbar = findViewById(R.id.toolbar_purchasing);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout_purchasing);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_purchasing);
        navigationView.setNavigationItemSelectedListener(this);

        int menu = Integer.valueOf(getIntent().getStringExtra("menu"));
        if (menu == 0)
            swapFragment(R.id.nav_purchaseorder);
        else if (menu == 1)
            swapFragment(R.id.nav_workorder);
        else if (menu == 2)
            swapFragment(R.id.nav_cod);
        else if (menu == 3)
            swapFragment(R.id.nav_kontrakperjanjian);
        else if (menu == 4)
            swapFragment(R.id.nav_goodsrecivednote);
        else if (menu == 5)
            swapFragment(R.id.nav_workhandover);
        else if (menu == 6)
            swapFragment(R.id.nav_servicesreceipt);
        else swapFragment(R.id.nav_purchaseorder);
    }

    private void swapFragment(int id) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_purchaseorder) {
            PurchaseOrdersFragment mainFragment = PurchaseOrdersFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_workorder) {
            WorkOrdersFragment mainFragment = WorkOrdersFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_cod) {
            CashOnDeliveryFragment mainFragment = CashOnDeliveryFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_kontrakperjanjian) {
            KontrakPerjanjianFragment mainFragment = KontrakPerjanjianFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_goodsrecivednote) {
            GoodsRecivedNoteFragment mainFragment = GoodsRecivedNoteFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_workhandover) {
            WorkHandoverFragment mainFragment = WorkHandoverFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_servicesreceipt) {
            ServicesReceiptFragment mainFragment = ServicesReceiptFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else {
            PurchaseOrdersFragment mainFragment = PurchaseOrdersFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment,mainFragment);
        }
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.purchasing, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        swapFragment(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_purchasing);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
