package com.example.aismobile.Project.TunjanganKaryawan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.aismobile.Data.Project.TunjanganKaryawan;
import com.example.aismobile.R;

public class DetailTunjanganKaryawanActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private TunjanganKaryawan tunjanganKaryawan;

    private TextView textNumber;
    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuHistory;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutHistory;
    private TextView textCatatan;
    private TextView textCheckedComment;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

    private TextView textJobOrder;
    private TextView textTglAwal;
    private TextView textTglAkhir;
    private TextView textRequestedBy;
    private TextView textVerifiedBy;
    private TextView textVerifiedDate;
    private TextView textCheckedBy;
    private TextView textCheckedDate;
    private TextView textApproval1;
    private TextView textApproval2;

    private TextView textListNama;
    private TextView textListJenjang;
    private TextView textListType;
    private TextView textListKabupaten;
    private TextView textListDays;
    private TextView textListApproval1;
    private TextView textListApproval2;
    private TextView textListPaid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tunjangan_karyawan);

        Bundle bundle = getIntent().getExtras();
        tunjanganKaryawan = bundle.getParcelable("detail");

        textListNama = (TextView) findViewById(R.id.textListNama);
        textListJenjang = (TextView) findViewById(R.id.textListJenjang);
        textListType = (TextView) findViewById(R.id.textListType);
        textListKabupaten = (TextView) findViewById(R.id.textListKabupaten);
        textListDays = (TextView) findViewById(R.id.textListDays);
        textListApproval1 = (TextView) findViewById(R.id.textListApproval1);
        textListApproval2 = (TextView) findViewById(R.id.textListApproval2);
        textListPaid = (TextView) findViewById(R.id.textListPaid);

        textListNama.setText(tunjanganKaryawan.getEmployee_id());
        textListJenjang.setText(tunjanganKaryawan.getEmployee_grade_name());
        textListType.setText(tunjanganKaryawan.getAdditional_allowance_type());
        textListKabupaten.setText(tunjanganKaryawan.getKab_id());
        textListDays.setText(tunjanganKaryawan.getDays());
        textListApproval1.setText(tunjanganKaryawan.getApproval1_status());
        textListApproval2.setText(tunjanganKaryawan.getApproval2_status());
        textListPaid.setText(tunjanganKaryawan.getPaid());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCheckedComment = (TextView) findViewById(R.id.textCheckedComment);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textJobOrder = (TextView) findViewById(R.id.textJobOrder);
        textTglAwal = (TextView) findViewById(R.id.textTglAwal);
        textTglAkhir = (TextView) findViewById(R.id.textTglAkhir);
        textRequestedBy = (TextView) findViewById(R.id.textRequestedBy);
        textVerifiedBy = (TextView) findViewById(R.id.textVerifiedBy);
        textVerifiedDate = (TextView) findViewById(R.id.textVerifiedDate);
        textCheckedBy = (TextView) findViewById(R.id.textCheckedBy);
        textCheckedDate = (TextView) findViewById(R.id.textCheckedDate);
        textApproval1 = (TextView) findViewById(R.id.textApproval1);
        textApproval2 = (TextView) findViewById(R.id.textApproval2);

        textJobOrder.setText(tunjanganKaryawan.getJob_order_id() + " (" + tunjanganKaryawan.getJob_order_location() + ") => " +
                tunjanganKaryawan.getJob_order_description());
        textTglAwal.setText(tunjanganKaryawan.getBegin_date());
        textTglAkhir.setText(tunjanganKaryawan.getEnd_date());
        textRequestedBy.setText(tunjanganKaryawan.getRequested_by());
        textVerifiedBy.setText(tunjanganKaryawan.getVerified_by());
        textVerifiedDate.setText(tunjanganKaryawan.getVerified_date());
        textCheckedBy.setText(tunjanganKaryawan.getChecked_by());
        textCheckedDate.setText(tunjanganKaryawan.getChecked_date());
        textApproval1.setText(tunjanganKaryawan.getApproval1_by() + "\n" + tunjanganKaryawan.getApproval_date1() + "\n" + tunjanganKaryawan.getApproval_comment1());
        textApproval2.setText(tunjanganKaryawan.getApproval2_by() + "\n" + tunjanganKaryawan.getApproval_date2() + "\n" + tunjanganKaryawan.getApproval_comment2());

        textNumber.setText(tunjanganKaryawan.getEmployee_allowance_number());
        textCatatan.setText(tunjanganKaryawan.getNotes());
        textCheckedComment.setText(tunjanganKaryawan.getChecked_comment());
        textCreatedBy.setText(tunjanganKaryawan.getCreated_by());
        textCreatedDate.setText(tunjanganKaryawan.getCreated_date());
        textModifiedBy.setText(tunjanganKaryawan.getModified_by());
        textModifiedDate.setText(tunjanganKaryawan.getModified_date());
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        menuDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutDetail.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutDetail.setLayoutParams(params);
                menuDetail.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuDetail.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        menuCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutCatatan.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutCatatan.setLayoutParams(params);
                menuCatatan.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuCatatan.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        menuHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutHistory.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutHistory.setLayoutParams(params);
                menuHistory.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuHistory.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
    }

    private void hiddenLayout(){
        menuDetail.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuDetail.setTextColor(getResources().getColor(R.color.colorWhite));
        menuCatatan.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuCatatan.setTextColor(getResources().getColor(R.color.colorWhite));
        menuHistory.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuHistory.setTextColor(getResources().getColor(R.color.colorWhite));

        params = layoutDetail.getLayoutParams(); params.height = 0; layoutDetail.setLayoutParams(params);
        params = layoutCatatan.getLayoutParams(); params.height = 0; layoutCatatan.setLayoutParams(params);
        params = layoutHistory.getLayoutParams(); params.height = 0; layoutHistory.setLayoutParams(params);
    }
}