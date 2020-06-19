package com.example.aismobile.News;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aismobile.Config;
import com.example.aismobile.Data.News;
import com.example.aismobile.R;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private ImageView imageDetailInfo;

    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        imageDetailInfo = (ImageView) findViewById(R.id.imageDetailInfo);

        Bundle bundle = getIntent().getExtras();
        news = bundle.getParcelable("detailNews");

        textViewTitle.setText(news.getNews_title());
        Picasso.get().load(Config.DATA_URL_IMAGE+news.getImage_name()).into(imageDetailInfo);

        if (imageDetailInfo.getDrawable() == null)
            imageDetailInfo.setImageResource(R.drawable.no_image);
    }
}
