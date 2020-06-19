package com.example.aismobile.News;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aismobile.R;

public class NewsDetailActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private TextView imageDetailInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        imageDetailInfo = (ImageView) findViewById(R.id.imageDetailInfo);
    }
}
