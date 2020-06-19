package com.example.aismobile.Data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class News implements Parcelable {

    private int news_id;
    private String news_title;
    private String image_name;

    public News (int news_id, String news_title, String image_name){
        this.news_id = news_id;
        this.news_title = news_title;
        this.image_name = image_name;
    }
    protected News(Parcel in) {
        news_id = in.readInt();
        news_title = in.readString();
        image_name = in.readString();
    }

    public News(JSONObject jsonObject){
        try {
            this.news_id = jsonObject.getInt("news_id");
            this.news_title = jsonObject.getString("news_title");
            this.image_name = jsonObject.getString("image_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(news_id);
        dest.writeString(news_title);
        dest.writeString(image_name);
    }

    public int getNews_id() {
        return news_id;
    }

    public String getNews_title() {
        return news_title;
    }

    public String getImage_name() {
        return image_name;
    }
}
