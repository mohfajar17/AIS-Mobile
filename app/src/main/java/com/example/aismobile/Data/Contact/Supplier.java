package com.example.aismobile.Data.Contact;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Supplier implements Parcelable {

    private int supplier_id;
    private String supplier_name;
    private String supplier_number;
    private String supplier_type;
    private String office_phone;
    private String supplier_contact;
    private String phone;
    private String email_address;
    private String bank_name;
    private String bank_account;
    private String is_active;
    private String website;
    private String supplier_lastlogin;
    private String office_address1;
    private String office_address2;
    private String office_fax;
    private String npwp;
    private String sppkp_number;
    private String name_of_bank_account;
    private String description;
    private String supplier_file_name;

    public Supplier(int supplier_id, String supplier_name, String supplier_number, String supplier_type,
                   String office_phone, String supplier_contact, String phone, String email_address, String bank_name,
                    String bank_account, String is_active){
        this.supplier_id = supplier_id;
        this.supplier_name = supplier_name;
        this.supplier_number = supplier_number;
        this.supplier_type = supplier_type;
        this.office_phone = office_phone;
        this.supplier_contact = supplier_contact;
        this.phone = phone;
        this.email_address = email_address;
        this.bank_name = bank_name;
        this.bank_account = bank_account;
        this.is_active = is_active;
    }

    protected Supplier(Parcel in) {
        supplier_id = in.readInt();
        supplier_name = in.readString();
        supplier_number = in.readString();
        supplier_type = in.readString();
        office_phone = in.readString();
        supplier_contact = in.readString();
        phone = in.readString();
        email_address = in.readString();
        bank_name = in.readString();
        bank_account = in.readString();
        is_active = in.readString();
        website = in.readString();
        supplier_lastlogin = in.readString();
        office_address1 = in.readString();
        office_address2 = in.readString();
        office_fax = in.readString();
        npwp = in.readString();
        sppkp_number = in.readString();
        name_of_bank_account = in.readString();
        description = in.readString();
        supplier_file_name = in.readString();
    }

    public Supplier(JSONObject jsonObject){
        try {
            this.supplier_id = jsonObject.getInt("supplier_id");
            this.supplier_name = jsonObject.getString("supplier_name");
            this.supplier_number = jsonObject.getString("supplier_number");
            this.supplier_type = jsonObject.getString("supplier_type");
            this.office_phone = jsonObject.getString("office_phone");
            this.supplier_contact = jsonObject.getString("supplier_contact");
            this.phone = jsonObject.getString("supplier_contact");
            this.email_address = jsonObject.getString("email_address");
            this.bank_name = jsonObject.getString("bank_name");
            this.bank_account = jsonObject.getString("bank_account");
            this.is_active = jsonObject.getString("is_active");
            this.website = jsonObject.getString("website");
            this.supplier_lastlogin = jsonObject.getString("supplier_lastlogin");
            this.office_address1 = jsonObject.getString("office_address1");
            this.office_address2 = jsonObject.getString("office_address2");
            this.office_fax = jsonObject.getString("office_fax");
            this.npwp = jsonObject.getString("npwp");
            this.sppkp_number = jsonObject.getString("sppkp_number");
            this.name_of_bank_account = jsonObject.getString("name_of_bank_account");
            this.description = jsonObject.getString("description");
            this.supplier_file_name = jsonObject.getString("supplier_file_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Supplier> CREATOR = new Creator<Supplier>() {
        @Override
        public Supplier createFromParcel(Parcel in) {
            return new Supplier(in);
        }

        @Override
        public Supplier[] newArray(int size) {
            return new Supplier[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(supplier_id);
        dest.writeString(supplier_name);
        dest.writeString(supplier_number);
        dest.writeString(supplier_type);
        dest.writeString(office_phone);
        dest.writeString(supplier_contact);
        dest.writeString(phone);
        dest.writeString(email_address);
        dest.writeString(bank_name);
        dest.writeString(bank_account);
        dest.writeString(is_active);
        dest.writeString(website);
        dest.writeString(supplier_lastlogin);
        dest.writeString(office_address1);
        dest.writeString(office_address2);
        dest.writeString(office_fax);
        dest.writeString(npwp);
        dest.writeString(sppkp_number);
        dest.writeString(name_of_bank_account);
        dest.writeString(description);
        dest.writeString(supplier_file_name);
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public String getSupplier_number() {
        return supplier_number;
    }

    public String getSupplier_type() {
        return supplier_type;
    }

    public String getOffice_phone() {
        return office_phone;
    }

    public String getSupplier_contact() {
        return supplier_contact;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail_address() {
        return email_address;
    }

    public String getBank_name() {
        return bank_name;
    }

    public String getBank_account() {
        return bank_account;
    }

    public String getIs_active() {
        return is_active;
    }

    public String getWebsite() {
        return website;
    }

    public String getSupplier_lastlogin() {
        return supplier_lastlogin;
    }

    public String getOffice_address1() {
        return office_address1;
    }

    public String getOffice_address2() {
        return office_address2;
    }

    public String getOffice_fax() {
        return office_fax;
    }

    public String getNpwp() {
        return npwp;
    }

    public String getSppkp_number() {
        return sppkp_number;
    }

    public String getName_of_bank_account() {
        return name_of_bank_account;
    }

    public String getDescription() {
        return description;
    }

    public String getSupplier_file_name() {
        return supplier_file_name;
    }
}
