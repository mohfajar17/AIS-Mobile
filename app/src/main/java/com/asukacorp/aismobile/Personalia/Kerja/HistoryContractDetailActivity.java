package com.asukacorp.aismobile.Personalia.Kerja;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asukacorp.aismobile.Data.Personalia.HistoryContract;
import com.asukacorp.aismobile.R;

public class HistoryContractDetailActivity extends AppCompatActivity {

    private HistoryContract department;

    private ImageView buttonBack;
    private TextView textEmployee;
    private TextView textEmployeeGrade;
    private TextView textDescription;
    private TextView textStartDate;
    private TextView textEndDate;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_contract_detail);

        Bundle bundle = getIntent().getExtras();
        department = bundle.getParcelable("detail");

        textEmployee = (TextView) findViewById(R.id.textEmployee);
        textEmployeeGrade = (TextView) findViewById(R.id.textEmployeeGrade);
        textDescription = (TextView) findViewById(R.id.textDescription);
        textStartDate = (TextView) findViewById(R.id.textStartDate);
        textEndDate = (TextView) findViewById(R.id.textEndDate);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textEmployee.setText(department.getFullname());
        textEmployeeGrade.setText(department.getEmployee_grade_name());
        textDescription.setText(department.getDescription());
        textStartDate.setText(department.getStart_date());
        textEndDate.setText(department.getEnd_date());
        textCreatedBy.setText(department.getCreated_by());
        textCreatedDate.setText(department.getCreated_date());
        textModifiedBy.setText(department.getModified_by());
        textModifiedDate.setText(department.getModified_date());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}