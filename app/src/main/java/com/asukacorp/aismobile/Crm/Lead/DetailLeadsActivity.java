package com.asukacorp.aismobile.Crm.Lead;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asukacorp.aismobile.Data.CRM.Lead;
import com.asukacorp.aismobile.R;

public class DetailLeadsActivity extends AppCompatActivity {

    private Lead lead;

    private ImageView buttonBack;
    private TextView textLeadName;
    private TextView textLeadPhone;
    private TextView textLeadEmail;
    private TextView textLeadAddress;
    private TextView textPerson;
    private TextView textPosition;
    private TextView textPersonalPhone;
    private TextView textStatus;
    private TextView textCatatan;
    private TextView textDiubahOleh;
    private TextView textDiubahTgl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_leads);

        Bundle bundle = getIntent().getExtras();
        lead = bundle.getParcelable("detail");

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textLeadName = (TextView) findViewById(R.id.textLeadName);
        textLeadPhone = (TextView) findViewById(R.id.textLeadPhone);
        textLeadEmail = (TextView) findViewById(R.id.textLeadEmail);
        textLeadAddress = (TextView) findViewById(R.id.textLeadAddress);
        textPerson = (TextView) findViewById(R.id.textPerson);
        textPosition = (TextView) findViewById(R.id.textPosition);
        textPersonalPhone = (TextView) findViewById(R.id.textPersonalPhone);
        textStatus = (TextView) findViewById(R.id.textStatus);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textDiubahOleh = (TextView) findViewById(R.id.textDiubahOleh);
        textDiubahTgl = (TextView) findViewById(R.id.textDiubahTgl);

        textLeadName.setText(lead.getLead_name());
        textLeadPhone.setText(lead.getLead_phone());
        textLeadEmail.setText(lead.getLead_email());
        textLeadAddress.setText(lead.getLead_address());
        textPerson.setText(lead.getPerson());
        textPosition.setText(lead.getPosition());
        textPersonalPhone.setText(lead.getPersonal_phone());
        textStatus.setText(lead.getStatus());
        textCatatan.setText(lead.getNotes());
        textDiubahOleh.setText(lead.getModified_by());
        textDiubahTgl.setText(lead.getModified_date());

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}