package com.example.aismobile.Crm.ScheduleVisit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aismobile.Data.CRM.ScheduleVisit;
import com.example.aismobile.R;

public class DetailScheduleActivity extends AppCompatActivity {

    private ScheduleVisit scheduleVisit;

    private ImageView buttonBack;
    private ImageView downloadAtachment;
    private TextView textNumber;
    private TextView textVisitsDate;
    private TextView textLead;
    private TextView textCompany;
    private TextView textPropose;
    private TextView textDone;
    private TextView textNotes;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_schedule);

        Bundle bundle = getIntent().getExtras();
        scheduleVisit = bundle.getParcelable("detail");

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        downloadAtachment = (ImageView) findViewById(R.id.downloadAtachment);
        textNumber = (TextView) findViewById(R.id.textNumber);
        textVisitsDate = (TextView) findViewById(R.id.textVisitsDate);
        textLead = (TextView) findViewById(R.id.textLead);
        textCompany = (TextView) findViewById(R.id.textCompany);
        textPropose = (TextView) findViewById(R.id.textPropose);
        textDone = (TextView) findViewById(R.id.textDone);
        textNotes = (TextView) findViewById(R.id.textNotes);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textNumber.setText(scheduleVisit.getVisits_number());
        textVisitsDate.setText(scheduleVisit.getVisits_date());
        textLead.setText(scheduleVisit.getLead_id());
        textCompany.setText(scheduleVisit.getCompany_id());
        textPropose.setText(scheduleVisit.getPropose());
        textDone.setText(scheduleVisit.getDone());
        textNotes.setText(scheduleVisit.getNotes());
        textCreatedBy.setText(scheduleVisit.getCreated_by());
        textCreatedDate.setText(scheduleVisit.getCreated_date());
        textModifiedBy.setText(scheduleVisit.getModified_by());
        textModifiedDate.setText(scheduleVisit.getModified_date());

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        downloadAtachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/scheduleVisits/visits_attachment_"+ scheduleVisit.getSchedule_visits_id()+".pdf");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });
    }
}