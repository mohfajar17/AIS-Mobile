package com.asukacorp.aismobile.Data.CRM;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class KuesionerDetail implements Parcelable {
    private String question;
    private String value;
    private String comment;

    protected KuesionerDetail(Parcel in) {
        question = in.readString();
        value = in.readString();
        comment = in.readString();
    }

    public KuesionerDetail(JSONObject jsonObject){
        try {
            this.question = jsonObject.getString("question");
            this.value = jsonObject.getString("value");
            this.comment = jsonObject.getString("comment");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<KuesionerDetail> CREATOR = new Creator<KuesionerDetail>() {
        @Override
        public KuesionerDetail createFromParcel(Parcel in) {
            return new KuesionerDetail(in);
        }

        @Override
        public KuesionerDetail[] newArray(int size) {
            return new KuesionerDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(value);
        dest.writeString(comment);
    }

    public String getQuestion() {
        return question;
    }

    public String getValue() {
        return value;
    }

    public String getComment() {
        return comment;
    }
}
