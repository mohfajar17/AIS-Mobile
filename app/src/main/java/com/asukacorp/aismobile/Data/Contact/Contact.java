package com.asukacorp.aismobile.Data.Contact;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Contact implements Parcelable {

    private int contact_id;
    private String contact_name;
    private String company_name;
    private String contact_jobtitle;
    private String contact_email;
    private String contact_phone1;
    private String contact_address;
    private String contact_city;
    private String contact_state;
    private String contact_country;
    private String contact_zipcode;
    private String payment_term_id;
    private String contact_catid;
    private String contact_phone2;
    private String contact_fax;
    private String contact_ym;
    private String contact_skype;
    private String contact_bill_address;
    private String contact_bill_city;
    private String contact_bill_state;
    private String contact_bill_country;
    private String contact_bill_zipcode;
    private String npwp;
    private String contact_notes;
    private String contact_salutation;

    public Contact(int contact_id, String contact_name, String company_name, String contact_jobtitle,
                   String contact_email, String contact_phone1){
        this.contact_id = contact_id;
        this.contact_name = contact_name;
        this.company_name = company_name;
        this.contact_jobtitle = contact_jobtitle;
        this.contact_email = contact_email;
        this.contact_phone1 = contact_phone1;
    }

    protected Contact(Parcel in) {
        contact_id = in.readInt();
        contact_name = in.readString();
        company_name = in.readString();
        contact_jobtitle = in.readString();
        contact_email = in.readString();
        contact_phone1 = in.readString();
        contact_address = in.readString();
        contact_city = in.readString();
        contact_state = in.readString();
        contact_country = in.readString();
        contact_zipcode = in.readString();
        payment_term_id = in.readString();
        contact_catid = in.readString();
        contact_phone2 = in.readString();
        contact_fax = in.readString();
        contact_ym = in.readString();
        contact_skype = in.readString();
        contact_bill_address = in.readString();
        contact_bill_city = in.readString();
        contact_bill_state = in.readString();
        contact_bill_country = in.readString();
        contact_bill_zipcode = in.readString();
        npwp = in.readString();
        contact_notes = in.readString();
        contact_salutation = in.readString();
    }

    public Contact(JSONObject jsonObject){
        try {
            this.contact_id = jsonObject.getInt("contact_id");
            this.contact_name = jsonObject.getString("contact_name");
            this.company_name = jsonObject.getString("company_name");
            this.contact_jobtitle = jsonObject.getString("contact_jobtitle");
            this.contact_email = jsonObject.getString("contact_email");
            this.contact_phone1 = jsonObject.getString("contact_phone1");
            this.contact_address = jsonObject.getString("contact_address");
            this.contact_city = jsonObject.getString("contact_city");
            this.contact_state = jsonObject.getString("contact_state");
            this.contact_country = jsonObject.getString("contact_country");
            this.contact_zipcode = jsonObject.getString("contact_zipcode");
            this.payment_term_id = jsonObject.getString("payment_term_id");
            this.contact_catid = jsonObject.getString("contact_catid");
            this.contact_phone2 = jsonObject.getString("contact_phone2");
            this.contact_fax = jsonObject.getString("contact_fax");
            this.contact_ym = jsonObject.getString("contact_ym");
            this.contact_skype = jsonObject.getString("contact_skype");
            this.contact_bill_address = jsonObject.getString("contact_bill_address");
            this.contact_bill_city = jsonObject.getString("contact_bill_city");
            this.contact_bill_state = jsonObject.getString("contact_bill_state");
            this.contact_bill_country = jsonObject.getString("contact_bill_country");
            this.contact_bill_zipcode = jsonObject.getString("npwp");
            this.npwp = jsonObject.getString("contact_bill_zipcode");
            this.contact_notes = jsonObject.getString("contact_notes");
            this.contact_salutation = jsonObject.getString("contact_salutation");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(contact_id);
        dest.writeString(contact_name);
        dest.writeString(company_name);
        dest.writeString(contact_jobtitle);
        dest.writeString(contact_email);
        dest.writeString(contact_phone1);
        dest.writeString(contact_address);
        dest.writeString(contact_city);
        dest.writeString(contact_state);
        dest.writeString(contact_country);
        dest.writeString(contact_zipcode);
        dest.writeString(payment_term_id);
        dest.writeString(contact_catid);
        dest.writeString(contact_phone2);
        dest.writeString(contact_fax);
        dest.writeString(contact_ym);
        dest.writeString(contact_skype);
        dest.writeString(contact_bill_address);
        dest.writeString(contact_bill_city);
        dest.writeString(contact_bill_state);
        dest.writeString(contact_bill_country);
        dest.writeString(contact_bill_zipcode);
        dest.writeString(npwp);
        dest.writeString(contact_notes);
        dest.writeString(contact_salutation);
    }

    public int getContact_id() {
        return contact_id;
    }

    public String getContact_name() {
        return contact_name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getContact_jobtitle() {
        return contact_jobtitle;
    }

    public String getContact_email() {
        return contact_email;
    }

    public String getContact_phone1() {
        return contact_phone1;
    }

    public String getContact_address() {
        return contact_address;
    }

    public String getContact_city() {
        return contact_city;
    }

    public String getContact_state() {
        return contact_state;
    }

    public String getContact_zipcode() {
        return contact_zipcode;
    }

    public String getPayment_term_id() {
        return payment_term_id;
    }

    public String getContact_catid() {
        return contact_catid;
    }

    public String getContact_phone2() {
        return contact_phone2;
    }

    public String getContact_fax() {
        return contact_fax;
    }

    public String getContact_ym() {
        return contact_ym;
    }

    public String getContact_skype() {
        return contact_skype;
    }

    public String getContact_bill_address() {
        return contact_bill_address;
    }

    public String getContact_bill_city() {
        return contact_bill_city;
    }

    public String getContact_bill_state() {
        return contact_bill_state;
    }

    public String getContact_bill_country() {
        return contact_bill_country;
    }

    public String getContact_bill_zipcode() {
        return contact_bill_zipcode;
    }

    public String getNpwp() {
        return npwp;
    }

    public String getContact_notes() {
        return contact_notes;
    }

    public String getContact_salutation() {
        return contact_salutation;
    }

    public String getContact_country() {
        return contact_country;
    }
}
