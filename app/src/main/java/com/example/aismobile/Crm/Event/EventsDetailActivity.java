package com.example.aismobile.Crm.Event;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aismobile.Data.CRM.Event;
import com.example.aismobile.R;
import com.squareup.picasso.Picasso;

public class EventsDetailActivity extends AppCompatActivity {

    private Event event;
    private Dialog myDialog;

    private ImageView buttonBack;
    private TextView textNamaEvent;
    private TextView textEventLocation;
    private TextView textTglAwal;
    private TextView textTglAkhir;
    private ImageView downloadAtachment1;
    private ImageView downloadAtachment2;
    private ImageView downloadAtachment3;
    private ImageView downloadAtachment4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_detail);

        Bundle bundle = getIntent().getExtras();
        event = bundle.getParcelable("detail");
        myDialog = new Dialog(this);

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNamaEvent = (TextView) findViewById(R.id.textNamaEvent);
        textEventLocation = (TextView) findViewById(R.id.textEventLocation);
        textTglAwal = (TextView) findViewById(R.id.textTglAwal);
        textTglAkhir = (TextView) findViewById(R.id.textTglAkhir);
        downloadAtachment1 = (ImageView) findViewById(R.id.downloadAtachment1);
        downloadAtachment2 = (ImageView) findViewById(R.id.downloadAtachment2);
        downloadAtachment3 = (ImageView) findViewById(R.id.downloadAtachment3);
        downloadAtachment4 = (ImageView) findViewById(R.id.downloadAtachment4);

        textNamaEvent.setText(event.getEvent_name());
        textEventLocation.setText(event.getEvent_location());
        textTglAwal.setText(event.getStart_date());
        textTglAkhir.setText(event.getEnd_date());

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Picasso.get().load("https://ais.asukaindonesia.co.id/protected/attachments/event/"+ event.getEvent_photo_1()).into(downloadAtachment1);
        if (downloadAtachment1.getDrawable() == null)
            downloadAtachment1.setImageResource(R.drawable.no_image);
        downloadAtachment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup("https://ais.asukaindonesia.co.id/protected/attachments/event/"+ event.getEvent_photo_1());
            }
        });

        Picasso.get().load("https://ais.asukaindonesia.co.id/protected/attachments/event/"+ event.getEvent_photo_2()).into(downloadAtachment2);
        if (downloadAtachment2.getDrawable() == null)
            downloadAtachment2.setImageResource(R.drawable.no_image);
        downloadAtachment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup("https://ais.asukaindonesia.co.id/protected/attachments/event/"+ event.getEvent_photo_2());
            }
        });

        Picasso.get().load("https://ais.asukaindonesia.co.id/protected/attachments/event/"+ event.getEvent_photo_3()).into(downloadAtachment3);
        if (downloadAtachment3.getDrawable() == null)
            downloadAtachment3.setImageResource(R.drawable.no_image);
        downloadAtachment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup("https://ais.asukaindonesia.co.id/protected/attachments/event/"+ event.getEvent_photo_3());
            }
        });

        Picasso.get().load("https://ais.asukaindonesia.co.id/protected/attachments/event/"+ event.getEvent_photo_4()).into(downloadAtachment4);
        if (downloadAtachment4.getDrawable() == null)
            downloadAtachment4.setImageResource(R.drawable.no_image);
        downloadAtachment4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup("https://ais.asukaindonesia.co.id/protected/attachments/event/"+ event.getEvent_photo_4());
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