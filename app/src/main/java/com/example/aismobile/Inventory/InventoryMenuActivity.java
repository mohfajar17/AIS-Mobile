package com.example.aismobile.Inventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aismobile.R;

public class InventoryMenuActivity extends AppCompatActivity {

    private TextView textViewItem;
    private TextView textViewAset;
    private TextView textViewAsetRental;
    private TextView textViewStock;
    private TextView textViewHarga;
    private TextView textViewMaterial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_menu);

        textViewItem = (TextView) findViewById(R.id.textViewItem);
        textViewAset = (TextView) findViewById(R.id.textViewAset);
        textViewAsetRental = (TextView) findViewById(R.id.textViewAsetRental);
        textViewStock = (TextView) findViewById(R.id.textViewStock);
        textViewHarga = (TextView) findViewById(R.id.textViewHarga);
        textViewMaterial = (TextView) findViewById(R.id.textViewMaterial);

        textViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaInventoryActivity = new Intent(InventoryMenuActivity.this, InventoryActivity.class);
                bukaInventoryActivity.putExtra("menu", "0");
                startActivityForResult(bukaInventoryActivity,1);
                finish();
            }
        });

        textViewAset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaInventoryActivity = new Intent(InventoryMenuActivity.this, InventoryActivity.class);
                bukaInventoryActivity.putExtra("menu", "1");
                startActivityForResult(bukaInventoryActivity,1);
                finish();
            }
        });

        textViewAsetRental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaInventoryActivity = new Intent(InventoryMenuActivity.this, InventoryActivity.class);
                bukaInventoryActivity.putExtra("menu", "2");
                startActivityForResult(bukaInventoryActivity,1);
                finish();
            }
        });

        textViewStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaInventoryActivity = new Intent(InventoryMenuActivity.this, InventoryActivity.class);
                bukaInventoryActivity.putExtra("menu", "3");
                startActivityForResult(bukaInventoryActivity,1);
                finish();
            }
        });

        textViewHarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaInventoryActivity = new Intent(InventoryMenuActivity.this, InventoryActivity.class);
                bukaInventoryActivity.putExtra("menu", "4");
                startActivityForResult(bukaInventoryActivity,1);
                finish();
            }
        });

        textViewMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaInventoryActivity = new Intent(InventoryMenuActivity.this, InventoryActivity.class);
                bukaInventoryActivity.putExtra("menu", "5");
                startActivityForResult(bukaInventoryActivity,1);
                finish();
            }
        });
    }
}
