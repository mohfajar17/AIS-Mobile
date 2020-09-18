package com.example.aismobile.Inventory.AsetRental;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aismobile.Data.Inventory.Asset;
import com.example.aismobile.Data.Inventory.AssetRental;
import com.example.aismobile.R;

public class DetailAsetRentalActivity extends AppCompatActivity {

    private AssetRental asset;

    private TextView textNumber;
    private ImageView buttonBack;

    private TextView textKodeAset;
    private TextView textNamaAset;
    private TextView textModelAset;
    private TextView textTipeAset;
    private TextView textNamaMerek;
    private TextView textSerialNumber;
    private TextView textLocation;
    private TextView textRack;
    private TextView textKeterangan;
    private TextView textTglAkuisisi;
    private TextView textTglPenggunaan;
    private TextView textMetodeDepresiasi;
    private TextView textCostOfSales;
    private TextView textTingkatPenyusutan;
    private TextView textJenisPenyusutan;
    private TextView textAkunAktiva;
    private TextView textAkunDepresiasi;
    private TextView textAkunPertukaran;
    private TextView textAktif;
    private TextView textKeteranganKondisi;
    private TextView textSupplier;
    private TextView textJumlah;
    private TextView textSatuanUnit;
    private TextView textMataUang;
    private TextView textHargaBeli;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_aset_rental);

        Bundle bundle = getIntent().getExtras();
        asset = bundle.getParcelable("detail");

        textKodeAset = (TextView) findViewById(R.id.textKodeAset);
        textNamaAset = (TextView) findViewById(R.id.textNamaAset);
        textModelAset = (TextView) findViewById(R.id.textModelAset);
        textTipeAset = (TextView) findViewById(R.id.textTipeAset);
        textNamaMerek = (TextView) findViewById(R.id.textNamaMerek);
        textSerialNumber = (TextView) findViewById(R.id.textSerialNumber);
        textLocation = (TextView) findViewById(R.id.textLocation);
        textRack = (TextView) findViewById(R.id.textRack);
        textKeterangan = (TextView) findViewById(R.id.textKeterangan);
        textTglAkuisisi = (TextView) findViewById(R.id.textTglAkuisisi);
        textTglPenggunaan = (TextView) findViewById(R.id.textTglPenggunaan);
        textMetodeDepresiasi = (TextView) findViewById(R.id.textMetodeDepresiasi);
        textCostOfSales = (TextView) findViewById(R.id.textCostOfSales);
        textTingkatPenyusutan = (TextView) findViewById(R.id.textTingkatPenyusutan);
        textJenisPenyusutan = (TextView) findViewById(R.id.textJenisPenyusutan);
        textAkunAktiva = (TextView) findViewById(R.id.textAkunAktiva);
        textAkunDepresiasi = (TextView) findViewById(R.id.textAkunDepresiasi);
        textAkunPertukaran = (TextView) findViewById(R.id.textAkunPertukaran);
        textAktif = (TextView) findViewById(R.id.textAktif);
        textKeteranganKondisi = (TextView) findViewById(R.id.textKeteranganKondisi);
        textSupplier = (TextView) findViewById(R.id.textSupplier);
        textJumlah = (TextView) findViewById(R.id.textJumlah);
        textSatuanUnit = (TextView) findViewById(R.id.textSatuanUnit);
        textMataUang = (TextView) findViewById(R.id.textMataUang);
        textHargaBeli = (TextView) findViewById(R.id.textHargaBeli);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textKodeAset.setText(asset.getAsset_rental_code());
        textNamaAset.setText(asset.getAsset_rental_name());
        textModelAset.setText(asset.getShort_description());
        textTipeAset.setText(asset.getAsset_type_id());
        textNamaMerek.setText(asset.getBrand_name());
        textSerialNumber.setText(asset.getSerial_number());
        textLocation.setText(asset.getWarehouse_name());
        textRack.setText(asset.getRack_code());
        textKeterangan.setText(asset.getDescription());
        textTglAkuisisi.setText(asset.getAcquisition_date());
        textTglPenggunaan.setText(asset.getUsage_date());
        textMetodeDepresiasi.setText(asset.getDepreciation_method());
        textCostOfSales.setText("0");
        textTingkatPenyusutan.setText(asset.getDepreciation_rate());
        textJenisPenyusutan.setText(asset.getAge_type());
        textAkunAktiva.setText(asset.getActiva_account());
        textAkunDepresiasi.setText(asset.getDepreciation_account());
        textAkunPertukaran.setText(asset.getExchange_depreciation_account());
        textAktif.setText(asset.getIs_active());
        textKeteranganKondisi.setText(asset.getRemark());
        textSupplier.setText(asset.getSupplier_name());
        textJumlah.setText(asset.getQuantity());
        textSatuanUnit.setText(asset.getUnit_abbr());
        textMataUang.setText(asset.getCurrency_symbol());
        textHargaBeli.setText(asset.getPrice_buy());
        textCatatan.setText(asset.getNotes());
        textCreatedBy.setText(asset.getCreated_by());
        textCreatedDate.setText(asset.getCreated_date());
        textModifiedBy.setText(asset.getModified_by());
        textModifiedDate.setText(asset.getModified_date());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);

        textNumber.setText("Aset #" + asset.getAsset_rental_id());
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}