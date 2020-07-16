package com.example.aismobile.Data.CRM;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Question implements Parcelable {

    private int question_id;
    private String question;
    private String question_year;

    public Question(int question_id, String question, String question_year){
        this.question_id = question_id;
        this.question = question;
        this.question_year = question_year;
    }

    protected Question(Parcel in) {
        question_id = in.readInt();
        question = in.readString();
        question_year = in.readString();
    }

    public Question(JSONObject jsonObject){
        try {
            this.question_id = jsonObject.getInt("question_id");
            this.question = jsonObject.getString("question");
            this.question_year = jsonObject.getString("question_year");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(question_id);
        dest.writeString(question);
        dest.writeString(question_year);
    }

    public int getQuestion_id() {
        return question_id;
    }

    public String getQuestion() {
        return question;
    }

    public String getQuestion_year() {
        return question_year;
    }
}
