package com.asukacorp.aismobile.Safety.SafetyFileReport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asukacorp.aismobile.Data.Safety.JobOrderSafety;
import com.asukacorp.aismobile.R;

public class SafetyFileReportDetailActivity extends AppCompatActivity {
    private JobOrderSafety jobOrderSafety;

    private ImageView buttonBack;
    private TextView textNumber;
    private TextView textJobOrder;
    private TextView textDescription;
    private TextView textJenisCategory;
    private ImageView downloadAtachment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_file_report_detail);

        Bundle bundle = getIntent().getExtras();
        jobOrderSafety = bundle.getParcelable("detail");

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);
        textJobOrder = (TextView) findViewById(R.id.textJobOrder);
        textDescription = (TextView) findViewById(R.id.textDescription);
        textJenisCategory = (TextView) findViewById(R.id.textJenisCategory);
        downloadAtachment = (ImageView) findViewById(R.id.downloadAtachment);

        textJobOrder.setText(jobOrderSafety.getJob_order_id());
        textDescription.setText(jobOrderSafety.getDescription());
        textJenisCategory.setText(jobOrderSafety.getCategory_id());
        textNumber.setText("Job Order Safety #"+ jobOrderSafety.getJob_order_safety_id());
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (!jobOrderSafety.getJob_safety_file_name().matches("null")){
            downloadAtachment.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorAsukaRed));
            downloadAtachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/jo_safety/safety/"+ jobOrderSafety.getJob_safety_file_name());
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }
            });
        }
    }
}