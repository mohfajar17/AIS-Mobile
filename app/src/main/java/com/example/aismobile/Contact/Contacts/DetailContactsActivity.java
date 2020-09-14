package com.example.aismobile.Contact.Contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aismobile.Data.Contact.Contact;
import com.example.aismobile.R;

public class DetailContactsActivity extends AppCompatActivity {

    private Contact contact;
    private ImageView buttonBack;

    private TextView textName;
    private TextView textKontakName;
    private TextView textPerusahaan;
    private TextView textPangkat;
    private TextView textAlamat;
    private TextView textKota;
    private TextView textPropinsi;
    private TextView textNegara;
    private TextView textKodepos;
    private TextView textTerminPembayaran;
    private TextView textKategoriKontak;
    private TextView textTelepon;
    private TextView textTelepon2;
    private TextView textFax;
    private TextView textEmail;
    private TextView textYahoo;
    private TextView textSkype;
    private TextView textAlamatPenagihan;
    private TextView textKotaPenagihan;
    private TextView textPropinsiPenagihan;
    private TextView textNegaraPenagihan;
    private TextView textKodeposPenagihan;
    private TextView textNpwp;
    private TextView textCatatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contacts);

        Bundle bundle = getIntent().getExtras();
        contact = bundle.getParcelable("detail");

        textName = (TextView) findViewById(R.id.textName);
        textKontakName = (TextView) findViewById(R.id.textKontakName);
        textPerusahaan = (TextView) findViewById(R.id.textPerusahaan);
        textPangkat = (TextView) findViewById(R.id.textPangkat);
        textAlamat = (TextView) findViewById(R.id.textAlamat);
        textKota = (TextView) findViewById(R.id.textKota);
        textPropinsi = (TextView) findViewById(R.id.textPropinsi);
        textNegara = (TextView) findViewById(R.id.textNegara);
        textKodepos = (TextView) findViewById(R.id.textKodepos);
        textTerminPembayaran = (TextView) findViewById(R.id.textTerminPembayaran);
        textKategoriKontak = (TextView) findViewById(R.id.textKategoriKontak);
        textTelepon = (TextView) findViewById(R.id.textTelepon);
        textTelepon2 = (TextView) findViewById(R.id.textTelepon2);
        textFax = (TextView) findViewById(R.id.textFax);
        textEmail = (TextView) findViewById(R.id.textEmail);
        textYahoo = (TextView) findViewById(R.id.textYahoo);
        textSkype = (TextView) findViewById(R.id.textSkype);
        textAlamatPenagihan = (TextView) findViewById(R.id.textAlamatPenagihan);
        textKotaPenagihan = (TextView) findViewById(R.id.textKotaPenagihan);
        textPropinsiPenagihan = (TextView) findViewById(R.id.textPropinsiPenagihan);
        textNegaraPenagihan = (TextView) findViewById(R.id.textNegaraPenagihan);
        textKodeposPenagihan = (TextView) findViewById(R.id.textKodeposPenagihan);
        textNpwp = (TextView) findViewById(R.id.textNpwp);
        textCatatan = (TextView) findViewById(R.id.textCatatan);

        textName.setText("#"+contact.getContact_name());
        if (Integer.valueOf(contact.getContact_salutation())==1)
            textKontakName.setText("Mr. "+contact.getContact_name());
        else textKontakName.setText("Mrs. "+contact.getContact_name());
        textPerusahaan.setText(contact.getCompany_name());
        textPangkat.setText(contact.getContact_jobtitle());
        textAlamat.setText(contact.getContact_address());
        textKota.setText(contact.getContact_city());
        textPropinsi.setText(contact.getContact_state());
        textNegara.setText(contact.getContact_country());
        textKodepos.setText(contact.getContact_zipcode());
        textTerminPembayaran.setText(contact.getPayment_term_id());
        textKategoriKontak.setText(contact.getContact_catid());
        textTelepon.setText(contact.getContact_phone1());
        textTelepon2.setText(contact.getContact_phone2());
        textFax.setText(contact.getContact_fax());
        textEmail.setText(contact.getContact_email());
        textYahoo.setText(contact.getContact_ym());
        textSkype.setText(contact.getContact_skype());
        textAlamatPenagihan.setText(contact.getContact_bill_address());
        textKotaPenagihan.setText(contact.getContact_bill_city());
        textPropinsiPenagihan.setText(contact.getContact_bill_state());
        textNegaraPenagihan.setText(contact.getContact_bill_country());
        textKodeposPenagihan.setText(contact.getContact_bill_zipcode());
        textNpwp.setText(contact.getNpwp());
        textCatatan.setText(contact.getContact_notes());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}