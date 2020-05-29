package com.example.aismobile.Inventory;

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

public class InventoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        Toolbar toolbar = findViewById(R.id.toolbarInventory);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout_inventory);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_viewInventory);
        navigationView.setNavigationItemSelectedListener(this);

        int menu = Integer.valueOf(getIntent().getStringExtra("menu"));
        if (menu == 0)
            swapFragment(R.id.nav_item);
        else if (menu == 1)
            swapFragment(R.id.nav_aset);
        else if (menu == 2)
            swapFragment(R.id.nav_aset_rental);
        else if (menu == 3)
            swapFragment(R.id.nav_stock);
        else if (menu == 4)
            swapFragment(R.id.nav_harga);
        else if (menu == 5)
            swapFragment(R.id.nav_material_req);
        else swapFragment(R.id.nav_item);
    }

    private void swapFragment(int id) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_item) {
            ItemFragment mainFragment = ItemFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_aset) {
            AsetFragment mainFragment = AsetFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_aset_rental) {
            AsetRentalFragment mainFragment = AsetRentalFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_stock) {
            StockFragment mainFragment = StockFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_harga) {
            HargaFragment mainFragment = HargaFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_material_req) {
            MaterialFragment mainFragment = MaterialFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else {
            ItemFragment mainFragment = ItemFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment,mainFragment);
        }
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
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
}
