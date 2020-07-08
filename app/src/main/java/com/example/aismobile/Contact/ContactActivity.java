package com.example.aismobile.Contact;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.aismobile.Contact.Access.AccessFragment;
import com.example.aismobile.Contact.Contacts.ContactsFragment;
import com.example.aismobile.Contact.Perusahaan.PerusahaanFragment;
import com.example.aismobile.Contact.Supplier.SupplierFragment;
import com.example.aismobile.Data.Contact.Company;
import com.example.aismobile.Data.Contact.Contact;
import com.example.aismobile.Data.Contact.Supplier;
import com.example.aismobile.R;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ContactActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ContactsFragment.OnListFragmentInteractionListener,
        SupplierFragment.OnListFragmentInteractionListener,
        PerusahaanFragment.OnListFragmentInteractionListener {

    FragmentTransaction fragmentTransaction;
    String access = "";
    private Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Toolbar toolbar = findViewById(R.id.toolbar_contact);
        setSupportActionBar(toolbar);

        myDialog = new Dialog(this);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout_contact);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_contact);
        navigationView.setNavigationItemSelectedListener(this);

        access = getIntent().getStringExtra("access");
        int menu = Integer.valueOf(getIntent().getStringExtra("menu"));
        if (menu == 0)
            swapFragment(R.id.nav_contact);
        else if (menu == 1)
            swapFragment(R.id.nav_supplier);
        else if (menu == 2)
            swapFragment(R.id.nav_perusahaan);
        else if (menu == 3)
            swapFragment(R.id.nav_access);
        else swapFragment(R.id.nav_supplier);
    }

    private void swapFragment(int id) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_contact && access.toLowerCase().contains("contact".toLowerCase())) {
            ContactsFragment mainFragment = ContactsFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_supplier && access.toLowerCase().contains("supplier".toLowerCase())) {
            SupplierFragment mainFragment = SupplierFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_perusahaan && access.toLowerCase().contains("company".toLowerCase())) {
            PerusahaanFragment mainFragment = PerusahaanFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else if (id == R.id.nav_access) {
            AccessFragment mainFragment = AccessFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, mainFragment);
        } else {
            ShowPopup();
        }
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        swapFragment(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_contact);
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
    public void onListFragmentInteraction(Contact item) {

    }

    @Override
    public void onListFragmentInteraction(Supplier item) {

    }

    @Override
    public void onListFragmentInteraction(Company item) {

    }
}
