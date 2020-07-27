package com.example.aismobile.Data.Personalia;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class PersonNews implements Parcelable {

    private int news_id;
    private String news_title;
    private String news_date;
    private String start_publish_date;
    private String finish_publish_date;
    private String created_by;

    public PersonNews (int news_id, String news_title, String news_date, String start_publish_date, String finish_publish_date,
                       String created_by){
        this.news_id = news_id;
        this.news_title = news_title;
        this.news_date = news_date;
        this.start_publish_date = start_publish_date;
        this.finish_publish_date = finish_publish_date;
        this.created_by = created_by;
    }
    protected PersonNews (Parcel in) {
        news_id = in.readInt();
        news_title = in.readString();
        news_date = in.readString();
        start_publish_date = in.readString();
        finish_publish_date = in.readString();
        created_by = in.readString();
    }

    public PersonNews (JSONObject jsonObject){
        try {
            this.news_id = jsonObject.getInt("news_id");
            this.news_title = jsonObject.getString("news_title");
            this.news_date = jsonObject.getString("news_date");
            this.start_publish_date = jsonObject.getString("start_publish_date");
            this.finish_publish_date = jsonObject.getString("finish_publish_date");
            this.created_by = jsonObject.getString("created_by");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<PersonNews> CREATOR = new Creator<PersonNews>() {
        @Override
        public PersonNews createFromParcel(Parcel in) {
            return new PersonNews(in);
        }

        @Override
        public PersonNews[] newArray(int size) {
            return new PersonNews[size];
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
        dest.writeString(news_date);
        dest.writeString(start_publish_date);
        dest.writeString(finish_publish_date);
        dest.writeString(created_by);
    }

    public int getNews_id() {
        return news_id;
    }

    public String getNews_title() {
        return news_title;
    }

    public String getNews_date() {
        return news_date;
    }

    public String getStart_publish_date() {
        return start_publish_date;
    }

    public String getFinish_publish_date() {
        return finish_publish_date;
    }

    public String getCreated_by() {
        return created_by;
    }
}
