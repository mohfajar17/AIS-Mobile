package com.example.aismobile.Crm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aismobile.R;

public class CrmMenuActivity extends AppCompatActivity {

    private TextView textViewMonitoring;
    private TextView textViewLeads;
    private TextView textViewEvents;
    private TextView textViewSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crm_menu);

        textViewMonitoring = (TextView) findViewById(R.id.textViewMonitoring);
        textViewLeads = (TextView) findViewById(R.id.textViewLeads);
        textViewEvents = (TextView) findViewById(R.id.textViewEvents);
        textViewSchedule = (TextView) findViewById(R.id.textViewSchedule);

        textViewMonitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaInventoryActivity = new Intent(CrmMenuActivity.this, CrmActivity.class);
                bukaInventoryActivity.putExtra("menu", "0");
                startActivityForResult(bukaInventoryActivity,1);
                finish();
            }
        });

        textViewLeads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaInventoryActivity = new Intent(CrmMenuActivity.this, CrmActivity.class);
                bukaInventoryActivity.putExtra("menu", "1");
                startActivityForResult(bukaInventoryActivity,1);
                finish();
            }
        });

        textViewEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaInventoryActivity = new Intent(CrmMenuActivity.this, CrmActivity.class);
                bukaInventoryActivity.putExtra("menu", "2");
                startActivityForResult(bukaInventoryActivity,1);
                finish();
            }
        });

        textViewSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaInventoryActivity = new Intent(CrmMenuActivity.this, CrmActivity.class);
                bukaInventoryActivity.putExtra("menu", "3");
                startActivityForResult(bukaInventoryActivity,1);
                finish();
            }
        });
    }
}
