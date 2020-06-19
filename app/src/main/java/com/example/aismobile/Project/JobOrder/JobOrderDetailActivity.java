package com.example.aismobile.Project.JobOrder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.aismobile.Data.JobOrder;
import com.example.aismobile.R;

public class JobOrderDetailActivity extends AppCompatActivity {

    private TextView textViewNamaJO;
    private TextView textViewDepartemen;
    private TextView textViewPIC;
    private TextView textViewTipe;
    private TextView textViewKeteranganJO;
    private TextView textViewNilai;
    private TextView textViewStatus;

    private JobOrder jobOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_order_detail);

        textViewNamaJO = (TextView) findViewById(R.id.textViewNamaJO);
        textViewDepartemen = (TextView) findViewById(R.id.textViewDepartemen);
        textViewPIC = (TextView) findViewById(R.id.textViewPIC);
        textViewTipe = (TextView) findViewById(R.id.textViewTipe);
        textViewKeteranganJO = (TextView) findViewById(R.id.textViewKeteranganJO);
        textViewNilai = (TextView) findViewById(R.id.textViewNilai);
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);

        Bundle bundle = getIntent().getExtras();
        jobOrder = bundle.getParcelable("detailJO");

        textViewNamaJO.setText(jobOrder.getJob_order_number());
        textViewDepartemen.setText(jobOrder.getDepartment_id());
        textViewPIC.setText(jobOrder.getSupervisor());
        textViewTipe.setText(jobOrder.getJob_order_type());
        textViewKeteranganJO.setText(jobOrder.getJob_order_description());
        textViewNilai.setText(jobOrder.getAmount());
        textViewStatus.setText(jobOrder.getJob_order_status());
    }
}
