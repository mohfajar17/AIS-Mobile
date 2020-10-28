package com.example.aismobile.Purchasing;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aismobile.Config;
import com.example.aismobile.Data.Purchasing.CashOnDelivery;
import com.example.aismobile.Data.Purchasing.ContractAgreement;
import com.example.aismobile.Data.Purchasing.GoodReceivedNote;
import com.example.aismobile.Data.Purchasing.PurchaseOrder;
import com.example.aismobile.Data.Purchasing.PurchaseService;
import com.example.aismobile.Data.Purchasing.ServicesReceipt;
import com.example.aismobile.Data.Purchasing.WorkHandover;
import com.example.aismobile.LoginActivity;
import com.example.aismobile.Purchasing.CashOnDelivery.CashOnDeliveryFragment;
import com.example.aismobile.Purchasing.GoodReceivedNote.GoodsRecivedNoteFragment;
import com.example.aismobile.Purchasing.KontrakPerjanjian.KontrakPerjanjianFragment;
import com.example.aismobile.Purchasing.PurchaseOrder.PurchaseOrdersFragment;
import com.example.aismobile.Purchasing.ServicesReceipt.ServicesReceiptFragment;
import com.example.aismobile.Purchasing.WorkHandover.WorkHandoverFragment;
import com.example.aismobile.Purchasing.WorkOrder.WorkOrdersFragment;
import com.example.aismobile.R;
import com.example.aismobile.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PurchasingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        PurchaseOrdersFragment.OnListFragmentInteractionListener,
        WorkOrdersFragment.OnListFragmentInteractionListener,
        CashOnDeliveryFragment.OnListFragmentInteractionListener,
        KontrakPerjanjianFragment.OnListFragmentInteractionListener,
        GoodsRecivedNoteFragment.OnListFragmentInteractionListener,
        WorkHandoverFragment.OnListFragmentInteractionListener,
        ServicesReceiptFragment.OnListFragmentInteractionListener {

    FragmentTransaction fragmentTransaction;
    String access = "";
    private Dialog myDialog;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchasing);

        Toolbar toolbar = findViewById(R.id.toolbar_purchasing);
        setSupportActionBar(toolbar);

        myDialog = new Dialog(this);
        sharedPrefManager = new SharedPrefManager(this);

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
        getMobileIsActive(sharedPrefManager.getUserId());

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

    private void getMobileIsActive(final String userId) {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_MOBILE_IS_ACTIVE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int akses = jsonObject.getInt("is_mobile");
                    if (akses > 1){
                        Intent logout = new Intent(PurchasingActivity.this, LoginActivity.class);
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
                Toast.makeText(PurchasingActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", userId);
                return param;
            }
        };
        Volley.newRequestQueue(PurchasingActivity.this).add(request);
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

    @Override
    public void onListFragmentInteraction(PurchaseService item) {

    }

    @Override
    public void onListFragmentInteraction(CashOnDelivery item) {

    }

    @Override
    public void onListFragmentInteraction(ContractAgreement item) {

    }

    @Override
    public void onListFragmentInteraction(GoodReceivedNote item) {

    }

    @Override
    public void onListFragmentInteraction(WorkHandover item) {

    }

    @Override
    public void onListFragmentInteraction(ServicesReceipt item) {

    }
}
