package com.asukacorp.aismobile.Crm.CustomerFeedback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asukacorp.aismobile.Data.CRM.CustomerFeedback;
import com.asukacorp.aismobile.R;

public class DetailCustomerFeedbackActivity extends AppCompatActivity {

    private CustomerFeedback customerFeedback;

    private TextView textFeedbackNumber;
    private TextView textFeedbackSubject;
    private TextView textDestination;
    private TextView textCategory;
    private TextView textMarketingAspect;
    private TextView textFeedbackMedia;
    private TextView textFeedbackDate;
    private TextView textClosedUser;
    private TextView textClosedTime;
    private TextView textContact;
    private TextView textContactPerson;
    private TextView textFeedbackStatus;
    private TextView textPriority;
    private TextView textFeedbackDesc;
    private TextView textAnalyzedDesc;
    private TextView textAnswerDesc;
    private TextView textPreventiveAct;

    private ImageView downloadAtachment1;
    private ImageView downloadAtachment2;
    private ImageView downloadAtachment3;
    private ImageView buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_customer_feedback);

        Bundle bundle = getIntent().getExtras();
        customerFeedback = bundle.getParcelable("detail");

        textFeedbackNumber = (TextView) findViewById(R.id.textFeedbackNumber);
        textFeedbackSubject = (TextView) findViewById(R.id.textFeedbackSubject);
        textDestination = (TextView) findViewById(R.id.textDestination);
        textCategory = (TextView) findViewById(R.id.textCategory);
        textMarketingAspect = (TextView) findViewById(R.id.textMarketingAspect);
        textFeedbackMedia = (TextView) findViewById(R.id.textFeedbackMedia);
        textFeedbackDate = (TextView) findViewById(R.id.textFeedbackDate);
        textClosedUser = (TextView) findViewById(R.id.textClosedUser);
        textClosedTime = (TextView) findViewById(R.id.textClosedTime);
        textContact = (TextView) findViewById(R.id.textContact);
        textContactPerson = (TextView) findViewById(R.id.textContactPerson);
        textFeedbackStatus = (TextView) findViewById(R.id.textFeedbackStatus);
        textPriority = (TextView) findViewById(R.id.textPriority);
        textFeedbackDesc = (TextView) findViewById(R.id.textFeedbackDesc);
        textAnalyzedDesc = (TextView) findViewById(R.id.textAnalyzedDesc);
        textAnswerDesc = (TextView) findViewById(R.id.textAnswerDesc);
        textPreventiveAct = (TextView) findViewById(R.id.textPreventiveAct);

        downloadAtachment1 = (ImageView) findViewById(R.id.downloadAtachment1);
        downloadAtachment2 = (ImageView) findViewById(R.id.downloadAtachment2);
        downloadAtachment3 = (ImageView) findViewById(R.id.downloadAtachment3);
        buttonBack = (ImageView) findViewById(R.id.buttonBack);

        textFeedbackNumber.setText(customerFeedback.getFeedback_number());
        textFeedbackSubject.setText(customerFeedback.getFeedback_subject());
        textDestination.setText(customerFeedback.getDepartment_name());
        textCategory.setText(customerFeedback.getFeedback_category_id());
        textMarketingAspect.setText(customerFeedback.getMarketing_aspect_id());
        textFeedbackMedia.setText(customerFeedback.getMedia_name());
        textFeedbackDate.setText(customerFeedback.getFeedback_date());
        textClosedUser.setText(customerFeedback.getClosed_user_id());
        textClosedTime.setText(customerFeedback.getClosed_time());
        textContact.setText(customerFeedback.getContact_id());
        textContactPerson.setText(customerFeedback.getContact_personal());
        textFeedbackStatus.setText(customerFeedback.getFeedback_status());
        textPriority.setText(customerFeedback.getPriority());
        textFeedbackDesc.setText(customerFeedback.getFeedback_description()+"\n\n"+customerFeedback.getUser_displayname()+"\n"+customerFeedback.getFeedback_entry_time());
        textAnalyzedDesc.setText(customerFeedback.getAnalyze_description());
        textAnswerDesc.setText(customerFeedback.getAnswer_description());
        textPreventiveAct.setText(customerFeedback.getPreventive_description());

        if (!customerFeedback.getArchive1().matches("null")){
            downloadAtachment1.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorAsukaRed));
            downloadAtachment1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/customerComplaint/"+customerFeedback.getArchive1());
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }
            });
        }
        if (!customerFeedback.getArchive2().matches("null")){
            downloadAtachment2.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorAsukaRed));
            downloadAtachment2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/customerComplaint/"+customerFeedback.getArchive2());
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }
            });
        }
        if (!customerFeedback.getArchive3().matches("null")){
            downloadAtachment3.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorAsukaRed));
            downloadAtachment3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/customerComplaint/"+customerFeedback.getArchive3());
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }
            });
        }
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}