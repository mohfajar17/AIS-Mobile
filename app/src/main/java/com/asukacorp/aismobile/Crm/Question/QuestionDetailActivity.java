package com.asukacorp.aismobile.Crm.Question;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asukacorp.aismobile.Data.CRM.Question;
import com.asukacorp.aismobile.R;

public class QuestionDetailActivity extends AppCompatActivity {

    private Question lead;

    private ImageView buttonBack;
    private TextView textNumber;
    private TextView textQuestion;
    private TextView textQuestionYear;
    private TextView textType;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        Bundle bundle = getIntent().getExtras();
        lead = bundle.getParcelable("detail");

        textNumber = (TextView) findViewById(R.id.textNumber);
        textQuestion = (TextView) findViewById(R.id.textQuestion);
        textQuestionYear = (TextView) findViewById(R.id.textQuestionYear);
        textType = (TextView) findViewById(R.id.textType);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textNumber.setText("Question #" + lead.getQuestion_id());
        textQuestion.setText(lead.getQuestion());
        textQuestionYear.setText(lead.getQuestion_year());
        textType.setText(lead.getType());
        textCreatedBy.setText(lead.getCreated_by());
        textCreatedDate.setText(lead.getCreated_date());
        textModifiedBy.setText(lead.getModified_by());
        textModifiedDate.setText(lead.getModified_date());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}