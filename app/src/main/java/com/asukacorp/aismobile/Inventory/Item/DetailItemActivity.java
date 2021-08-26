package com.asukacorp.aismobile.Inventory.Item;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.asukacorp.aismobile.Data.Inventory.Item;
import com.asukacorp.aismobile.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DetailItemActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private Dialog myDialog;
    private Item item;
    private double toDouble;

    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuHistory;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutHistory;

    private TextView textKodeItem;
    private TextView textNamaItem;
    private TextView textNamaMerek;
    private TextView textSerialNumber;
    private TextView textModelNumber;
    private TextView textTipeItem;
    private TextView textKategoriItem;
    private TextView textKelompokItem;
    private TextView textItemSection;
    private TextView textSupplierItem;
    private TextView textSatuanUnit;
    private TextView textSpecification;
    private TextView textDescription;
    private TextView textPrice;
    private TextView textStokSekarang;
    private TextView textStokTerendah;
    private TextView textStokTertinggi;
    private TextView textSupplier;
    private TextView textSupplierAddress;
    private TextView textOfficePhone;
    private TextView textOfficeFax;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;
    private ImageView downloadAtachment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);

        Bundle bundle = getIntent().getExtras();
        item = bundle.getParcelable("detail");
        myDialog = new Dialog(this);

        textKodeItem = (TextView) findViewById(R.id.textKodeItem);
        textNamaItem = (TextView) findViewById(R.id.textNamaItem);
        textNamaMerek = (TextView) findViewById(R.id.textNamaMerek);
        textSerialNumber = (TextView) findViewById(R.id.textSerialNumber);
        textModelNumber = (TextView) findViewById(R.id.textModelNumber);
        textTipeItem = (TextView) findViewById(R.id.textTipeItem);
        textKategoriItem = (TextView) findViewById(R.id.textKategoriItem);
        textKelompokItem = (TextView) findViewById(R.id.textKelompokItem);
        textItemSection = (TextView) findViewById(R.id.textItemSection);
        textSupplierItem = (TextView) findViewById(R.id.textSupplierItem);
        textSatuanUnit = (TextView) findViewById(R.id.textSatuanUnit);
        textSpecification = (TextView) findViewById(R.id.textSpecification);
        textDescription = (TextView) findViewById(R.id.textDescription);
        textPrice = (TextView) findViewById(R.id.textPrice);
        textStokSekarang = (TextView) findViewById(R.id.textStokSekarang);
        textStokTerendah = (TextView) findViewById(R.id.textStokTerendah);
        textStokTertinggi = (TextView) findViewById(R.id.textStokTertinggi);
        textSupplier = (TextView) findViewById(R.id.textSupplier);
        textSupplierAddress = (TextView) findViewById(R.id.textSupplierAddress);
        textOfficePhone = (TextView) findViewById(R.id.textOfficePhone);
        textOfficeFax = (TextView) findViewById(R.id.textOfficeFax);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);
        downloadAtachment = (ImageView) findViewById(R.id.downloadAtachment);

        textKodeItem.setText(item.getItem_code());
        textNamaItem.setText(item.getItem_name());
        textNamaMerek.setText(item.getBrand_name());
        textSerialNumber.setText(item.getSerial_number());
        textModelNumber.setText(item.getModel_number());
        textTipeItem.setText(item.getItem_type_name());
        textKategoriItem.setText(item.getItem_category_name());
        textKelompokItem.setText(item.getItem_group_code());
        textItemSection.setText(item.getItem_section());
        textSupplierItem.setText(item.getSupplier_name());
        textSatuanUnit.setText(item.getUnit_abbr());
        textSpecification.setText(item.getItem_specification());
        textDescription.setText(item.getDescription());
        NumberFormat formatter = new DecimalFormat("#,###");
        toDouble = Double.valueOf(item.getPrice_buy());
        textPrice.setText("Rp. " + formatter.format((long) toDouble) + " /" + item.getUnit_abbr());
        textStokSekarang.setText(item.getCurrent_stock());
        textStokTerendah.setText(item.getLow_level_stock());
        textStokTertinggi.setText(item.getReorder_level_stock());
        textSupplier.setText(item.getSupplier_name());
        textSupplierAddress.setText(item.getOffice_address1());
        textOfficePhone.setText(item.getOffice_phone());
        textOfficeFax.setText(item.getOffice_fax());
        textCreatedBy.setText(item.getCreated_by());
        textCreatedDate.setText(item.getCreated_date());
        textModifiedBy.setText(item.getModified_by());
        textModifiedDate.setText(item.getModified_date());

        Picasso.get().load("https://ais.asukaindonesia.co.id/protected/attachments/itemAndService/"+item.getItem_file_name()).into(downloadAtachment);
        if (downloadAtachment.getDrawable() == null)
            downloadAtachment.setImageResource(R.drawable.no_image);
        downloadAtachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup("https://ais.asukaindonesia.co.id/protected/attachments/genbaSafety/"+item.getItem_file_name());
            }
        });

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);

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
        menuHistory.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuHistory.setTextColor(getResources().getColor(R.color.colorWhite));

        params = layoutDetail.getLayoutParams(); params.height = 0; layoutDetail.setLayoutParams(params);
        params = layoutCatatan.getLayoutParams(); params.height = 0; layoutCatatan.setLayoutParams(params);
        params = layoutHistory.getLayoutParams(); params.height = 0; layoutHistory.setLayoutParams(params);
    }

    public void showPopup(String url) {
        ImageView imageView;
        myDialog.setContentView(R.layout.custom_popup_image);
        imageView = (ImageView) myDialog.findViewById(R.id.imageNo);
        Picasso.get().load(url).into(imageView);
        if (imageView.getDrawable() == null)
            imageView.setImageResource(R.drawable.no_image);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}