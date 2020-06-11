package com.example.aismobile.Safety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aismobile.R;

public class SafetyMenuActivity extends AppCompatActivity {

    private TextView textViewWorkAccidents;
    private TextView textViewGenbaSafety;
    private TextView textViewFileReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_menu);

        textViewWorkAccidents = (TextView) findViewById(R.id.textViewWorkAccidents);
        textViewGenbaSafety = (TextView) findViewById(R.id.textViewGenbaSafety);
        textViewFileReport = (TextView) findViewById(R.id.textViewFileReport);

        textViewWorkAccidents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("0");
            }
        });
        textViewGenbaSafety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("1");
            }
        });
        textViewFileReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaActivity("2");
            }
        });
    }

    private void bukaActivity(String menu){
        Intent bukaActivity = new Intent(SafetyMenuActivity.this, SafetyActivity.class);
        bukaActivity.putExtra("menu", menu);
        startActivityForResult(bukaActivity,1);
        finish();
    }
}
