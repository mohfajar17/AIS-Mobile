package com.asukacorp.aismobile.Data.Project.DetailJobOrder;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class JoMatRet implements Parcelable {

    private int material_return_id;
    private String material_return_number;

    protected JoMatRet(Parcel in) {
        material_return_id = in.readInt();
        material_return_number = in.readString();
    }

    public JoMatRet(JSONObject jsonObject){
        try {
            this.material_return_id = jsonObject.getInt("material_return_id");
            this.material_return_number = jsonObject.getString("material_return_number");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<JoMatRet> CREATOR = new Creator<JoMatRet>() {
        @Override
        public JoMatRet createFromParcel(Parcel in) {
            return new JoMatRet(in);
        }

        @Override
        public JoMatRet[] newArray(int size) {
            return new JoMatRet[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(material_return_id);
        dest.writeString(material_return_number);
    }

    public int getMaterial_return_id() {
        return material_return_id;
    }

    public String getMaterial_return_number() {
        return material_return_number;
    }
}
