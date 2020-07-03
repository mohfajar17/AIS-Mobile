package com.example.aismobile.Purchasing;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.aismobile.Data.Purchasing.PurchaseOrder;
import com.example.aismobile.Purchasing.PurchaseOrder.PurchaseOrdersFragment;
import com.example.aismobile.R;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PurchasingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        PurchaseOrdersFragment.OnListFragmentInteractionListener {

    FragmentTransaction fragmentTransaction;
    String access = "";
    private Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchasing);

        Toolbar toolbar = findViewById(R.id.toolbar_purchasing);
        setSupportActionBar(toolbar);

        myDialog = new Dialog(this);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout_purchasing);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_purchasing);
        navigationView.setNavigationItemSelectedListener(this);

        access = getIntent().getStringExtra("access");
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
        if (id == R.id.nav_purchaseorder && access.toLowerCase().contains("purchase_order".toLowerCase())) {
            PurchaseOrdersFragment mainFragment = PurchaseOrdersFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_workorder && access.toLowerCase().contains("purchase_service".toLowerCase())) {
            WorkOrdersFragment mainFragment = WorkOrdersFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_cod && access.toLowerCase().contains("cash_on_delivery".toLowerCase())) {
            CashOnDeliveryFragment mainFragment = CashOnDeliveryFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_kontrakperjanjian && access.toLowerCase().contains("contract_agreement".toLowerCase())) {
            KontrakPerjanjianFragment mainFragment = KontrakPerjanjianFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_goodsrecivednote && access.toLowerCase().contains("good_received_note".toLowerCase())) {
            GoodsRecivedNoteFragment mainFragment = GoodsRecivedNoteFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_workhandover && access.toLowerCase().contains("work_handover".toLowerCase())) {
            WorkHandoverFragment mainFragment = WorkHandoverFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_servicesreceipt && access.toLowerCase().contains("services_receipt".toLowerCase())) {
            ServicesReceiptFragment mainFragment = ServicesReceiptFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else {
            ShowPopup();
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
    public void onListFragmentInteraction(PurchaseOrder item) {

    }
}
