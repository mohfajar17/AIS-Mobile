package com.asukacorp.aismobile.Safety.WorkAccident;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asukacorp.aismobile.Data.Safety.WorkAccident;
import com.asukacorp.aismobile.R;
import com.squareup.picasso.Picasso;

public class DetailWorkAccidentActivity extends AppCompatActivity {

    private WorkAccident workAccident;
    private Dialog myDialog;

    private ImageView buttonBack;
    private TextView textNamaKaryawan;
    private TextView textDay;
    private TextView textDate;
    private TextView textTime;
    private TextView textJobGrade;
    private TextView textWorkbase;
    private TextView textAccidentType;
    private TextView textDiLokasiKerja;
    private TextView textSaksiMata;
    private TextView textChronology;
    private TextView textAction;
    private TextView textCounterMeassure;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;
    private ImageView downloadAtachment1;
    private ImageView downloadAtachment2;
    private ImageView downloadAtachment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_work_accident);

        Bundle bundle = getIntent().getExtras();
        workAccident = bundle.getParcelable("detail");
        myDialog = new Dialog(this);

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNamaKaryawan = (TextView) findViewById(R.id.textNamaKaryawan);
        textDay = (TextView) findViewById(R.id.textDay);
        textDate = (TextView) findViewById(R.id.textDate);
        textTime = (TextView) findViewById(R.id.textTime);
        textJobGrade = (TextView) findViewById(R.id.textJobGrade);
        textWorkbase = (TextView) findViewById(R.id.textWorkbase);
        textAccidentType = (TextView) findViewById(R.id.textAccidentType);
        textDiLokasiKerja = (TextView) findViewById(R.id.textDiLokasiKerja);
        textSaksiMata = (TextView) findViewById(R.id.textSaksiMata);
        textChronology = (TextView) findViewById(R.id.textChronology);
        textAction = (TextView) findViewById(R.id.textAction);
        textCounterMeassure = (TextView) findViewById(R.id.textCounterMeassure);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);
        downloadAtachment1 = (ImageView) findViewById(R.id.downloadAtachment1);
        downloadAtachment2 = (ImageView) findViewById(R.id.downloadAtachment2);
        downloadAtachment3 = (ImageView) findViewById(R.id.downloadAtachment3);

        if (workAccident.getEmployee_id().matches(""))
            textNamaKaryawan.setText("#" + workAccident.getEmployee_name());
        else textNamaKaryawan.setText("#" + workAccident.getEmployee_id());
        textDay.setText(workAccident.getDay_accident());
        textDate.setText(workAccident.getDate_accident());
        textTime.setText(workAccident.getTime_accident());
        textJobGrade.setText(workAccident.getJob_grade_id());
        textWorkbase.setText(workAccident.getCompany_workbase_id());
        textAccidentType.setText(workAccident.getAccident_type());
        textDiLokasiKerja.setText(workAccident.getIs_work_location());
        textSaksiMata.setText(workAccident.getWitness());
        textChronology.setText(workAccident.getNotes());
        textAction.setText(workAccident.getAction());
        textCounterMeassure.setText(workAccident.getCounter_meassure());
        textCreatedBy.setText(workAccident.getCreated_by());
        textCreatedDate.setText(workAccident.getCreated_date());
        textModifiedBy.setText(workAccident.getModified_by());
        textModifiedDate.setText(workAccident.getModified_date());

        Picasso.get().load("https://ais.asukaindonesia.co.id/protected/attachments/workAccident/"+ workAccident.getAccident_photo_1()).into(downloadAtachment1);
        if (downloadAtachment1.getDrawable() == null)
            downloadAtachment1.setImageResource(R.drawable.no_image);
        downloadAtachment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup("https://ais.asukaindonesia.co.id/protected/attachments/workAccident/"+ workAccident.getAccident_photo_1());
            }
        });

        Picasso.get().load("https://ais.asukaindonesia.co.id/protected/attachments/workAccident/"+ workAccident.getAccident_photo_2()).into(downloadAtachment2);
        if (downloadAtachment2.getDrawable() == null)
            downloadAtachment2.setImageResource(R.drawable.no_image);
        downloadAtachment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup("https://ais.asukaindonesia.co.id/protected/attachments/workAccident/"+ workAccident.getAccident_photo_2());
            }
        });

        Picasso.get().load("https://ais.asukaindonesia.co.id/protected/attachments/workAccident/"+ workAccident.getAccident_photo_3()).into(downloadAtachment3);
        if (downloadAtachment3.getDrawable() == null)
            downloadAtachment3.setImageResource(R.drawable.no_image);
        downloadAtachment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup("https://ais.asukaindonesia.co.id/protected/attachments/workAccident/"+ workAccident.getAccident_photo_3());
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void showPopup(String url) {
        ImageView imageView;
        myDialog.setContentView(R.layout.custom_popup_image);
        imageView = (ImageView) myDialog.findViewById(R.id.imageNo);
        Picasso.get().load(url).into(imageView);
        if (imageView.getDrawable() == null)
            imageView.setImageResource(R.drawable.no_image);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}