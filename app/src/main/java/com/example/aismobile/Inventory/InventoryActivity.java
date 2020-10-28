package com.example.aismobile.Inventory;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aismobile.Config;
import com.example.aismobile.Data.Inventory.Asset;
import com.example.aismobile.Data.Inventory.AssetRental;
import com.example.aismobile.Data.Inventory.Item;
import com.example.aismobile.Data.Inventory.ItemCategory;
import com.example.aismobile.Data.Inventory.ItemGroup;
import com.example.aismobile.Data.Inventory.ItemType;
import com.example.aismobile.Data.Inventory.MaterialReturn;
import com.example.aismobile.Data.Inventory.StockAdjustment;
import com.example.aismobile.Inventory.Aset.AsetFragment;
import com.example.aismobile.Inventory.AsetRental.AsetRentalFragment;
import com.example.aismobile.Inventory.Item.ItemFragment;
import com.example.aismobile.Inventory.Item.KategoriItemFragment;
import com.example.aismobile.Inventory.Item.KelompokItemFragment;
import com.example.aismobile.Inventory.Item.TypeItemFragment;
import com.example.aismobile.Inventory.Material.MaterialFragment;
import com.example.aismobile.Inventory.Stock.StockFragment;
import com.example.aismobile.LoginActivity;
import com.example.aismobile.R;
import com.example.aismobile.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InventoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ItemFragment.OnListFragmentInteractionListener,
        KelompokItemFragment.OnListFragmentInteractionListener,
        KategoriItemFragment.OnListFragmentInteractionListener,
        TypeItemFragment.OnListFragmentInteractionListener,
        AsetFragment.OnListFragmentInteractionListener,
        AsetRentalFragment.OnListFragmentInteractionListener,
        StockFragment.OnListFragmentInteractionListener,
        MaterialFragment.OnListFragmentInteractionListener {

    FragmentTransaction fragmentTransaction;
    String access = "";
    private Dialog myDialog;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        Toolbar toolbar = findViewById(R.id.toolbarInventory);
        setSupportActionBar(toolbar);

        myDialog = new Dialog(this);
        sharedPrefManager = new SharedPrefManager(this);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout_inventory);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_viewInventory);
        navigationView.setNavigationItemSelectedListener(this);

        access = getIntent().getStringExtra("access");
        int menu = Integer.valueOf(getIntent().getStringExtra("menu"));
        if (menu == 0)
            swapFragment(R.id.nav_item);
        else if (menu == 1)
            swapFragment(R.id.nav_aset);
        else if (menu == 2)
            swapFragment(R.id.nav_aset_rental);
        else if (menu == 3)
            swapFragment(R.id.nav_stock);
//        else if (menu == 4)
//            swapFragment(R.id.nav_harga);
        else if (menu == 4)
            swapFragment(R.id.nav_material_req);
        else swapFragment(R.id.nav_item);
    }

    private void swapFragment(int id) {
        getMobileIsActive(sharedPrefManager.getUserId());

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_item && access.toLowerCase().contains("item".toLowerCase())) {
            ItemFragment mainFragment = ItemFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_aset && access.toLowerCase().contains("asset".toLowerCase())) {
            AsetFragment mainFragment = AsetFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_aset_rental && access.toLowerCase().contains("aset_rental".toLowerCase())) {
            AsetRentalFragment mainFragment = AsetRentalFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_stock && access.toLowerCase().contains("stock_adjustment".toLowerCase())) {
            StockFragment mainFragment = StockFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
//        } else if (id == R.id.nav_harga && access.toLowerCase().contains("master_item_price".toLowerCase())) {
//            HargaFragment mainFragment = HargaFragment.newInstance();
//            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_material_req && access.toLowerCase().contains("material_return".toLowerCase())) {
            MaterialFragment mainFragment = MaterialFragment.newInstance();
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
                        Intent logout = new Intent(InventoryActivity.this, LoginActivity.class);
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
                Toast.makeText(InventoryActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", userId);
                return param;
            }
        };
        Volley.newRequestQueue(InventoryActivity.this).add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inventory, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        swapFragment(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_inventory);
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
    public void onListFragmentInteraction(Item item) {

    }

    @Override
    public void onListFragmentInteraction(ItemGroup item) {

    }

    @Override
    public void onListFragmentInteraction(ItemCategory item) {

    }

    @Override
    public void onListFragmentInteraction(ItemType item) {

    }

    @Override
    public void onListFragmentInteraction(Asset item) {

    }

    @Override
    public void onListFragmentInteraction(AssetRental item) {

    }

    @Override
    public void onListFragmentInteraction(StockAdjustment item) {

    }

    @Override
    public void onListFragmentInteraction(MaterialReturn item) {

    }
}
