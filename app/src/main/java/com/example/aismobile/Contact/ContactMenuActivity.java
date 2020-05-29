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
                Intent bukaInventoryActivity = new Intent(ContactMenuActivity.this, ContactActivity.class);
                bukaInventoryActivity.putExtra("menu", "0");
                startActivityForResult(bukaInventoryActivity,1);
                finish();
            }
        });

        textViewPerusahaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaInventoryActivity = new Intent(ContactMenuActivity.this, ContactActivity.class);
                bukaInventoryActivity.putExtra("menu", "1");
                startActivityForResult(bukaInventoryActivity,1);
                finish();
            }
        });

        textViewAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaInventoryActivity = new Intent(ContactMenuActivity.this, ContactActivity.class);
                bukaInventoryActivity.putExtra("menu", "2");
                startActivityForResult(bukaInventoryActivity,1);
                finish();
            }
        });
    }
}
