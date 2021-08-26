package com.asukacorp.aismobile.Contact;

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
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.Contact.Access.AccessFragment;
import com.asukacorp.aismobile.Contact.Contacts.ContactsFragment;
import com.asukacorp.aismobile.Contact.Perusahaan.PerusahaanFragment;
import com.asukacorp.aismobile.Contact.Supplier.SupplierFragment;
import com.asukacorp.aismobile.Data.Contact.AccessRequest;
import com.asukacorp.aismobile.Data.Contact.Company;
import com.asukacorp.aismobile.Data.Contact.Contact;
import com.asukacorp.aismobile.Data.Contact.Supplier;
import com.asukacorp.aismobile.LoginActivity;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.SharedPrefManager;
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

public class ContactActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ContactsFragment.OnListFragmentInteractionListener,
        SupplierFragment.OnListFragmentInteractionListener,
        PerusahaanFragment.OnListFragmentInteractionListener,
        AccessFragment.OnListFragmentInteractionListener {

    FragmentTransaction fragmentTransaction;
    String access = "";
    private Dialog myDialog;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Toolbar toolbar = findViewById(R.id.toolbar_contact);
        setSupportActionBar(toolbar);

        myDialog = new Dialog(this);
        sharedPrefManager = new SharedPrefManager(this);

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
        getMobileIsActive(sharedPrefManager.getUserId());

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

    private void getMobileIsActive(final String userId) {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_MOBILE_IS_ACTIVE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int akses = jsonObject.getInt("is_mobile");
                    if (akses > 1){
                        Intent logout = new Intent(ContactActivity.this, LoginActivity.class);
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
                Toast.makeText(ContactActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", userId);
                return param;
            }
        };
        Volley.newRequestQueue(ContactActivity.this).add(request);
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

    @Override
    public void onListFragmentInteraction(AccessRequest item) {

    }
}
