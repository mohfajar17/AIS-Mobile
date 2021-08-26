package com.asukacorp.aismobile.Data.Personalia.Employee;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeDetFile implements Parcelable {
    private String category;
    private String file_description;
    private String file_name;
    private String file_location;

    protected EmployeeDetFile (Parcel in) {
        category = in.readString();
        file_description = in.readString();
        file_name = in.readString();
        file_location = in.readString();
    }

    public EmployeeDetFile (JSONObject jsonObject){
        try {
            this.category = jsonObject.getString("category");
            this.file_description = jsonObject.getString("file_description");
            this.file_name = jsonObject.getString("file_name");
            this.file_location = jsonObject.getString("file_location");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<EmployeeDetFile> CREATOR = new Creator<EmployeeDetFile>() {
        @Override
        public EmployeeDetFile createFromParcel(Parcel in) {
            return new EmployeeDetFile(in);
        }

        @Override
        public EmployeeDetFile[] newArray(int size) {
            return new EmployeeDetFile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(file_description);
        dest.writeString(file_name);
        dest.writeString(file_location);
    }

    public String getCategory() {
        return category;
    }

    public String getFile_description() {
        return file_description;
    }

    public String getFile_name() {
        return file_name;
    }

    public String getFile_location() {
        return file_location;
    }
}
