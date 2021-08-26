package com.asukacorp.aismobile.Data.CRM;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Question implements Parcelable {

    private int question_id;
    private String question;
    private String question_year;
    private String type;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;

    public Question(int question_id, String question, String question_year){
        this.question_id = question_id;
        this.question = question;
        this.question_year = question_year;
    }

    protected Question(Parcel in) {
        question_id = in.readInt();
        question = in.readString();
        question_year = in.readString();
        type = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
    }

    public Question(JSONObject jsonObject){
        try {
            this.question_id = jsonObject.getInt("question_id");
            this.question = jsonObject.getString("question");
            this.question_year = jsonObject.getString("question_year");
            this.type = jsonObject.getString("type");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
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
        dest.writeString(type);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
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

    public String getType() {
        return type;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getCreated_date() {
        return created_date;
    }

    public String getModified_by() {
        return modified_by;
    }

    public String getModified_date() {
        return modified_date;
    }
}
