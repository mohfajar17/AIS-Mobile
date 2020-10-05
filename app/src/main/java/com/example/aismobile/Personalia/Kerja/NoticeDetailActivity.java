package com.example.aismobile.Personalia.Kerja;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aismobile.Data.Personalia.EmployeeNotice;
import com.example.aismobile.R;

public class NoticeDetailActivity extends AppCompatActivity {

    private EmployeeNotice department;

    private ImageView buttonBack;

    private TextView textEmployee;
    private TextView textNoticeDate;
    private TextView textSubject;
    private TextView textExpiredDate;
    private TextView textJenjangKaryawan;
    private TextView textJabatanKaryawan;
    private TextView textGajiPokok;
    private TextView textTunjanganMakan;
    private TextView textTunjanganTransport;
    private TextView textTunjanganSkill;
    private TextView textLembur;
    private TextView textTunjanganKesejahteraan;
    private TextView textTunjanganLokasi;
    private TextView textOtherAllowance;
    private TextView textCatatan;
    private TextView textPreparedBy;
    private TextView textCommentedBy;
    private TextView textApprovalBy;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        Bundle bundle = getIntent().getExtras();
        department = bundle.getParcelable("detail");

        textEmployee = (TextView) findViewById(R.id.textEmployee);
        textNoticeDate = (TextView) findViewById(R.id.textNoticeDate);
        textSubject = (TextView) findViewById(R.id.textSubject);
        textExpiredDate = (TextView) findViewById(R.id.textExpiredDate);
        textJenjangKaryawan = (TextView) findViewById(R.id.textJenjangKaryawan);
        textJabatanKaryawan = (TextView) findViewById(R.id.textJabatanKaryawan);
        textGajiPokok = (TextView) findViewById(R.id.textGajiPokok);
        textTunjanganMakan = (TextView) findViewById(R.id.textTunjanganMakan);
        textTunjanganTransport = (TextView) findViewById(R.id.textTunjanganTransport);
        textTunjanganSkill = (TextView) findViewById(R.id.textTunjanganSkill);
        textLembur = (TextView) findViewById(R.id.textLembur);
        textTunjanganKesejahteraan = (TextView) findViewById(R.id.textTunjanganKesejahteraan);
        textTunjanganLokasi = (TextView) findViewById(R.id.textTunjanganLokasi);
        textOtherAllowance = (TextView) findViewById(R.id.textOtherAllowance);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textPreparedBy = (TextView) findViewById(R.id.textPreparedBy);
        textCommentedBy = (TextView) findViewById(R.id.textCommentedBy);
        textApprovalBy = (TextView) findViewById(R.id.textApprovalBy);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textEmployee.setText(department.getFullname());
        textNoticeDate.setText(department.getNotice_date());
        textSubject.setText(department.getSubject());
        textExpiredDate.setText(department.getExpired_date());
        textJenjangKaryawan.setText(department.getEmployee_grade_name());
        textJabatanKaryawan.setText(department.getJob_grade_name());
        textGajiPokok.setText(department.getBasic_salary());
        textTunjanganMakan.setText(department.getMeal_allowance());
        textTunjanganTransport.setText(department.getTransport_allowance());
        textTunjanganSkill.setText(department.getProfesional_allowance());
        textLembur.setText(department.getOvertime());
        textTunjanganKesejahteraan.setText(department.getWelfare_allowance());
        textTunjanganLokasi.setText(department.getLocation_project_allowance());
        textOtherAllowance.setText(department.getOther_allowance());
        textCatatan.setText(department.getNotes());
        textPreparedBy.setText(department.getPrepared_by());
        textCommentedBy.setText(department.getCommented_by());
        textApprovalBy.setText(department.getProcessed_by());
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