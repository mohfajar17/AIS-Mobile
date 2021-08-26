package com.asukacorp.aismobile.Inventory.Aset;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.asukacorp.aismobile.Data.Inventory.Asset;
import com.asukacorp.aismobile.R;

public class DetailAsetActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private Asset asset;

    private TextView textNumber;
    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuSupplier;
    private TextView menuHistory;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutSupplier;
    private LinearLayout layoutHistory;

    private TextView textKodeAset;
    private TextView textNamaAset;
    private TextView textModelAset;
    private TextView textTipeAset;
    private TextView textNamaMerek;
    private TextView textSerialNumber;
    private TextView textLocation;
    private TextView textKeterangan;
    private TextView textTglAkuisisi;
    private TextView textTglPenggunaan;
    private TextView textMetodeDepresiasi;
    private TextView textTingkatPenyusutan;
    private TextView textJenisPenyusutan;
    private TextView textAkunAktiva;
    private TextView textJumlah;
    private TextView textSatuanUnit;
    private TextView textMataUang;
    private TextView textHargaBeli;
    private TextView textCurrentPrice;
    private TextView textCatatan;
    private TextView textDescription;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;
    private TextView textSupplier;
    private TextView textOfficePhone;
    private TextView textEmailAddress;
    private TextView textPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_aset);

        Bundle bundle = getIntent().getExtras();
        asset = bundle.getParcelable("detail");

        textKodeAset = (TextView) findViewById(R.id.textKodeAset);
        textNamaAset = (TextView) findViewById(R.id.textNamaAset);
        textModelAset = (TextView) findViewById(R.id.textModelAset);
        textTipeAset = (TextView) findViewById(R.id.textTipeAset);
        textNamaMerek = (TextView) findViewById(R.id.textNamaMerek);
        textSerialNumber = (TextView) findViewById(R.id.textSerialNumber);
        textLocation = (TextView) findViewById(R.id.textLocation);
        textKeterangan = (TextView) findViewById(R.id.textKeterangan);
        textTglAkuisisi = (TextView) findViewById(R.id.textTglAkuisisi);
        textTglPenggunaan = (TextView) findViewById(R.id.textTglPenggunaan);
        textMetodeDepresiasi = (TextView) findViewById(R.id.textMetodeDepresiasi);
        textTingkatPenyusutan = (TextView) findViewById(R.id.textTingkatPenyusutan);
        textJenisPenyusutan = (TextView) findViewById(R.id.textJenisPenyusutan);
        textAkunAktiva = (TextView) findViewById(R.id.textAkunAktiva);
        textJumlah = (TextView) findViewById(R.id.textJumlah);
        textSatuanUnit = (TextView) findViewById(R.id.textSatuanUnit);
        textMataUang = (TextView) findViewById(R.id.textMataUang);
        textHargaBeli = (TextView) findViewById(R.id.textHargaBeli);
        textCurrentPrice = (TextView) findViewById(R.id.textCurrentPrice);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textDescription = (TextView) findViewById(R.id.textDescription);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);
        textSupplier = (TextView) findViewById(R.id.textSupplier);
        textOfficePhone = (TextView) findViewById(R.id.textOfficePhone);
        textEmailAddress = (TextView) findViewById(R.id.textEmailAddress);
        textPhone = (TextView) findViewById(R.id.textPhone);

        textKodeAset.setText(asset.getAsset_code());
        textNamaAset.setText(asset.getAsset_name());
        textModelAset.setText(asset.getShort_description());
        textTipeAset.setText(asset.getAsset_type_id());
        textNamaMerek.setText(asset.getBrand_name());
        textSerialNumber.setText(asset.getSerial_number());
        textLocation.setText(asset.getLocation());
        textKeterangan.setText(asset.getRemark());
        textTglAkuisisi.setText(asset.getAcquisition_date());
        textTglPenggunaan.setText(asset.getUsage_date());
        textMetodeDepresiasi.setText(asset.getDepreciation_method_id());
        textTingkatPenyusutan.setText(asset.getDepreciation_rate());
        textJenisPenyusutan.setText(asset.getAge_type());
        textAkunAktiva.setText(asset.getActiva_account());
        textJumlah.setText(asset.getQuantity());
        textSatuanUnit.setText(asset.getUnit_abbr());
        textMataUang.setText(asset.getCurrency_symbol());
        textHargaBeli.setText(asset.getPrice_buy());
        textCurrentPrice.setText(asset.getPrice_buy());
        textCatatan.setText(asset.getNotes());
        textDescription.setText(asset.getDescription());
        textCreatedBy.setText(asset.getCreated_by());
        textCreatedDate.setText(asset.getCreated_date());
        textModifiedBy.setText(asset.getModified_by());
        textModifiedDate.setText(asset.getModified_date());
        textSupplier.setText(asset.getSupplier_name());
        textOfficePhone.setText(asset.getOffice_phone());
        textEmailAddress.setText(asset.getEmail_address());
        textPhone.setText(asset.getPhone());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuSupplier = (TextView) findViewById(R.id.menuSupplier);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutSupplier = (LinearLayout) findViewById(R.id.layoutSupplier);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);

        textNumber.setText("Aset #" + asset.getAsset_code());
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        menuDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutDetail.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutDetail.setLayoutParams(params);
                menuDetail.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuDetail.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        menuSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutSupplier.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutSupplier.setLayoutParams(params);
                menuSupplier.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuSupplier.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        menuCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutCatatan.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutCatatan.setLayoutParams(params);
                menuCatatan.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuCatatan.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        menuHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutHistory.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutHistory.setLayoutParams(params);
                menuHistory.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuHistory.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
    }

    private void hiddenLayout(){
        menuDetail.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuDetail.setTextColor(getResources().getColor(R.color.colorWhite));
        menuCatatan.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuCatatan.setTextColor(getResources().getColor(R.color.colorWhite));
        menuSupplier.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuSupplier.setTextColor(getResources().getColor(R.color.colorWhite));
        menuHistory.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuHistory.setTextColor(getResources().getColor(R.color.colorWhite));

        params = layoutDetail.getLayoutParams(); params.height = 0; layoutDetail.setLayoutParams(params);
        params = layoutCatatan.getLayoutParams(); params.height = 0; layoutCatatan.setLayoutParams(params);
        params = layoutSupplier.getLayoutParams(); params.height = 0; layoutSupplier.setLayoutParams(params);
        params = layoutHistory.getLayoutParams(); params.height = 0; layoutHistory.setLayoutParams(params);
    }
}