package com.example.aismobile.Contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aismobile.R;

public class ContactMenuActivity extends AppCompatActivity {

    private TextView textViewSupplier;
    private TextView textViewPerusahaan;
    private TextView textViewAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_menu);

        textViewSupplier = (TextView) findViewById(R.id.textViewSupplier);
        textViewPerusahaan = (TextView) findViewById(R.id.textViewPerusahaan);
        textViewAccess = (TextView) findViewById(R.id.textViewAccess);

        textViewSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("0");
            }
        });

        textViewPerusahaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("1");
            }
        });

        textViewAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("2");
            }
        });
    }

    private void bukaActivity(String menu){
        Intent bukaInventoryActivity = new Intent(ContactMenuActivity.this, ContactActivity.class);
        bukaInventoryActivity.putExtra("menu", menu);
        startActivityForResult(bukaInventoryActivity,1);
        finish();
    }
}
