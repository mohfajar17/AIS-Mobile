package com.example.aismobile.News;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aismobile.Config;
import com.example.aismobile.Data.News;
import com.example.aismobile.R;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private ImageView imageDetailInfo;
    private WebView webNewsContent;

    private News news;
    private Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        myDialog = new Dialog(this);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        imageDetailInfo = (ImageView) findViewById(R.id.imageDetailInfo);
        webNewsContent = (WebView) findViewById(R.id.webNews);

        Bundle bundle = getIntent().getExtras();
        news = bundle.getParcelable("detailNews");

        textViewTitle.setText(news.getNews_title());
        webNewsContent.loadData(news.getNews_contents(), "text/html", null);
        imageDetailInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup();
            }
        });
        Picasso.get().load(Config.DATA_URL_IMAGE+news.getImage_name()).into(imageDetailInfo);

        if (imageDetailInfo.getDrawable() == null)
            imageDetailInfo.setImageResource(R.drawable.no_image);
    }



    public void ShowPopup() {
        ImageView imageNo;
        myDialog.setContentView(R.layout.custom_popup_image);
        imageNo = (ImageView) myDialog.findViewById(R.id.imageNo);
        Picasso.get().load(Config.DATA_URL_IMAGE+news.getImage_name()).into(imageNo);
        if (imageNo.getDrawable() == null)
            imageNo.setImageResource(R.drawable.no_image);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}