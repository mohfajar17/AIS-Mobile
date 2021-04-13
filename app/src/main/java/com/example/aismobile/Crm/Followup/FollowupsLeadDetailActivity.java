package com.example.aismobile.Crm.Followup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aismobile.Data.CRM.Followup;
import com.example.aismobile.R;

public class FollowupsLeadDetailActivity extends AppCompatActivity {
    private Followup followup;

    private ImageView downloadAtachment;
    private ImageView buttonBack;
    private TextView textNumber;
    private TextView textLead;
    private TextView textTglFollowup;
    private TextView textFollowupBy;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followups_lead_detail);

        Bundle bundle = getIntent().getExtras();
        followup = bundle.getParcelable("detail");

        textLead = (TextView) findViewById(R.id.textLead);
        textTglFollowup = (TextView) findViewById(R.id.textTglFollowup);
        textFollowupBy = (TextView) findViewById(R.id.textFollowupBy);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textLead.setText(followup.getLead_id());
        textTglFollowup.setText(followup.getFollowup_date());
        textFollowupBy.setText(followup.getFollowup_by());
        textCatatan.setText(followup.getNotes());
        textCreatedBy.setText(followup.getCreated_by());
        textCreatedDate.setText(followup.getCreated_date());
        textModifiedBy.setText(followup.getModified_by());
        textModifiedDate.setText(followup.getModified_date());

        downloadAtachment = (ImageView) findViewById(R.id.downloadAtachment);
        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);

        textNumber.setText("Followup Lead #" + followup.getLead_followup_id());
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (!followup.getFollowup_file_name().matches("null")){
            downloadAtachment.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorAsukaRed));
            downloadAtachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/followup/"+ followup.getFollowup_file_name());
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }
            });
        }
    }
}