package com.example.aismobile.Data.FinanceAccounting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomerInvoice implements Parcelable {

    private int sales_order_invoice_id;
    private String sales_order_invoice_number;
    private String sales_order_invoice_description;
    private String job_order_id;
    private String sales_quotation_id;
    private String job_progress_report_id;
    private String due_date;
    private String client_po_number;
    private String sales_order_invoice_status;
    private String payment_late;
    private String grand_total;
    private String company_name;
    private String approval_assign;
    private String category;
    private String bank_transaction_type_name;
    private String tax_number;
    private String number_pieces_of_evidence;
    private String date_pieces_of_evidence;
    private String payment_date;
    private String invoice_date;
    private String customer_receive_date;
    private String material_description;
    private String amount;
    private String ppn;
    private String discount;
    private String service_description;
    private String service_amount;
    private String service_discount;
    private String service_ppn;
    private String tax_type;
    private String tax_type_rate;
    private String adjustment_desc;
    private String adjustment_value;
    private String bank_transaction_number;
    private String customer_receive_number;
    private String notes;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;
    private String material_income_type;
    private String service_income_type;

    protected CustomerInvoice(Parcel in) {
        sales_order_invoice_id = in.readInt();
        sales_order_invoice_number = in.readString();
        sales_order_invoice_description = in.readString();
        job_order_id = in.readString();
        sales_quotation_id = in.readString();
        job_progress_report_id = in.readString();
        due_date = in.readString();
        client_po_number = in.readString();
        sales_order_invoice_status = in.readString();
        payment_late = in.readString();
        grand_total = in.readString();
        company_name = in.readString();
        approval_assign = in.readString();
        category = in.readString();
        bank_transaction_type_name = in.readString();
        tax_number = in.readString();
        number_pieces_of_evidence = in.readString();
        date_pieces_of_evidence = in.readString();
        payment_date = in.readString();
        invoice_date = in.readString();
        customer_receive_date = in.readString();
        material_description = in.readString();
        amount = in.readString();
        ppn = in.readString();
        discount = in.readString();
        service_description = in.readString();
        service_amount = in.readString();
        service_discount = in.readString();
        service_ppn = in.readString();
        tax_type = in.readString();
        tax_type_rate = in.readString();
        adjustment_desc = in.readString();
        adjustment_value = in.readString();
        bank_transaction_number = in.readString();
        customer_receive_number = in.readString();
        notes = in.readString();
        created_by = in.readString();
        created_date = in.readString();
        modified_by = in.readString();
        modified_date = in.readString();
        material_income_type = in.readString();
        service_income_type = in.readString();
    }

    public CustomerInvoice(JSONObject jsonObject){
        try {
            this.sales_order_invoice_id = jsonObject.getInt("sales_order_invoice_id");
            this.sales_order_invoice_number = jsonObject.getString("sales_order_invoice_number");
            this.sales_order_invoice_description = jsonObject.getString("sales_order_invoice_description");
            this.job_order_id = jsonObject.getString("job_order_id");
            this.sales_quotation_id = jsonObject.getString("sales_quotation_id");
            this.job_progress_report_id = jsonObject.getString("job_progress_report_id");
            this.due_date = jsonObject.getString("due_date");
            this.client_po_number = jsonObject.getString("client_po_number");
            this.sales_order_invoice_status = jsonObject.getString("sales_order_invoice_status");
            this.payment_late = jsonObject.getString("payment_late");
            this.grand_total = jsonObject.getString("grand_total");
            this.company_name = jsonObject.getString("company_name");
            this.approval_assign = jsonObject.getString("approval_assign");
            this.category = jsonObject.getString("category");
            this.bank_transaction_type_name = jsonObject.getString("bank_transaction_type_name");
            this.tax_number = jsonObject.getString("tax_number");
            this.number_pieces_of_evidence = jsonObject.getString("number_pieces_of_evidence");
            this.date_pieces_of_evidence = jsonObject.getString("date_pieces_of_evidence");
            this.payment_date = jsonObject.getString("payment_date");
            this.invoice_date = jsonObject.getString("invoice_date");
            this.customer_receive_date = jsonObject.getString("customer_receive_date");
            this.material_description = jsonObject.getString("material_description");
            this.amount = jsonObject.getString("amount");
            this.ppn = jsonObject.getString("ppn");
            this.discount = jsonObject.getString("discount");
            this.service_description = jsonObject.getString("service_description");
            this.service_amount = jsonObject.getString("service_amount");
            this.service_discount = jsonObject.getString("service_discount");
            this.service_ppn = jsonObject.getString("service_ppn");
            this.tax_type = jsonObject.getString("tax_type");
            this.tax_type_rate = jsonObject.getString("tax_type_rate");
            this.adjustment_desc = jsonObject.getString("adjustment_desc");
            this.adjustment_value = jsonObject.getString("adjustment_value");
            this.bank_transaction_number = jsonObject.getString("bank_transaction_number");
            this.customer_receive_number = jsonObject.getString("customer_receive_number");
            this.notes = jsonObject.getString("notes");
            this.created_by = jsonObject.getString("created_by");
            this.created_date = jsonObject.getString("created_date");
            this.modified_by = jsonObject.getString("modified_by");
            this.modified_date = jsonObject.getString("modified_date");
            this.material_income_type = jsonObject.getString("material_income_type");
            this.service_income_type = jsonObject.getString("service_income_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<CustomerInvoice> CREATOR = new Creator<CustomerInvoice>() {
        @Override
        public CustomerInvoice createFromParcel(Parcel in) {
            return new CustomerInvoice(in);
        }

        @Override
        public CustomerInvoice[] newArray(int size) {
            return new CustomerInvoice[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sales_order_invoice_id);
        dest.writeString(sales_order_invoice_number);
        dest.writeString(sales_order_invoice_description);
        dest.writeString(job_order_id);
        dest.writeString(sales_quotation_id);
        dest.writeString(job_progress_report_id);
        dest.writeString(due_date);
        dest.writeString(client_po_number);
        dest.writeString(sales_order_invoice_status);
        dest.writeString(payment_late);
        dest.writeString(grand_total);
        dest.writeString(company_name);
        dest.writeString(approval_assign);
        dest.writeString(category);
        dest.writeString(bank_transaction_type_name);
        dest.writeString(tax_number);
        dest.writeString(number_pieces_of_evidence);
        dest.writeString(date_pieces_of_evidence);
        dest.writeString(payment_date);
        dest.writeString(invoice_date);
        dest.writeString(customer_receive_date);
        dest.writeString(material_description);
        dest.writeString(amount);
        dest.writeString(ppn);
        dest.writeString(discount);
        dest.writeString(service_description);
        dest.writeString(service_amount);
        dest.writeString(service_discount);
        dest.writeString(service_ppn);
        dest.writeString(tax_type);
        dest.writeString(tax_type_rate);
        dest.writeString(adjustment_desc);
        dest.writeString(adjustment_value);
        dest.writeString(bank_transaction_number);
        dest.writeString(customer_receive_number);
        dest.writeString(notes);
        dest.writeString(created_by);
        dest.writeString(created_date);
        dest.writeString(modified_by);
        dest.writeString(modified_date);
        dest.writeString(material_income_type);
        dest.writeString(service_income_type);
    }

    public CustomerInvoice(int sales_order_invoice_id, String sales_order_invoice_number, String sales_order_invoice_description,
                           String job_order_id, String sales_quotation_id, String job_progress_report_id,
                           String due_date, String client_po_number, String sales_order_invoice_status,
                           String payment_late, String grand_total){
        this.sales_order_invoice_id = sales_order_invoice_id;
        this.sales_order_invoice_number = sales_order_invoice_number;
        this.sales_order_invoice_description = sales_order_invoice_description;
        this.job_order_id = job_order_id;
        this.sales_quotation_id = sales_quotation_id;
        this.job_progress_report_id = job_progress_report_id;
        this.due_date = due_date;
        this.client_po_number = client_po_number;
        this.sales_order_invoice_status = sales_order_invoice_status;
        this.payment_late = payment_late;
        this.grand_total = grand_total;
    }

    public int getSales_order_invoice_id() {
        return sales_order_invoice_id;
    }

    public String getSales_order_invoice_number() {
        return sales_order_invoice_number;
    }

    public String getSales_order_invoice_description() {
        return sales_order_invoice_description;
    }

    public String getJob_order_id() {
        return job_order_id;
    }

    public String getSales_quotation_id() {
        return sales_quotation_id;
    }

    public String getJob_progress_report_id() {
        return job_progress_report_id;
    }

    public String getDue_date() {
        return due_date;
    }

    public String getClient_po_number() {
        return client_po_number;
    }

    public String getSales_order_invoice_status() {
        return sales_order_invoice_status;
    }

    public String getPayment_late() {
        return payment_late;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getApproval_assign() {
        return approval_assign;
    }

    public String getCategory() {
        return category;
    }

    public String getBank_transaction_type_name() {
        return bank_transaction_type_name;
    }

    public String getTax_number() {
        return tax_number;
    }

    public String getNumber_pieces_of_evidence() {
        return number_pieces_of_evidence;
    }

    public String getDate_pieces_of_evidence() {
        return date_pieces_of_evidence;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public String getCustomer_receive_date() {
        return customer_receive_date;
    }

    public String getMaterial_description() {
        return material_description;
    }

    public String getAmount() {
        return amount;
    }

    public String getPpn() {
        return ppn;
    }

    public String getDiscount() {
        return discount;
    }

    public String getService_description() {
        return service_description;
    }

    public String getService_amount() {
        return service_amount;
    }

    public String getService_discount() {
        return service_discount;
    }

    public String getService_ppn() {
        return service_ppn;
    }

    public String getTax_type() {
        return tax_type;
    }

    public String getTax_type_rate() {
        return tax_type_rate;
    }

    public String getAdjustment_desc() {
        return adjustment_desc;
    }

    public String getAdjustment_value() {
        return adjustment_value;
    }

    public String getBank_transaction_number() {
        return bank_transaction_number;
    }

    public String getCustomer_receive_number() {
        return customer_receive_number;
    }

    public String getNotes() {
        return notes;
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

    public String getMaterial_income_type() {
        return material_income_type;
    }

    public String getService_income_type() {
        return service_income_type;
    }
}
