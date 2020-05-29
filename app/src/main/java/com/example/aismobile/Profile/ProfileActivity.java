package com.example.aismobile.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aismobile.Config;
import com.example.aismobile.LoginActivity;
import com.example.aismobile.MainActivity;
import com.example.aismobile.R;
import com.example.aismobile.SharedPrefManager;
import com.squareup.picasso.Picasso;
import com.tangxiaolv.telegramgallery.GalleryActivity;
import com.tangxiaolv.telegramgallery.GalleryConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private SharedPrefManager sharedPrefManager;

    private CircleImageView imageAkun;
    private TextView textViewEmployeeId;
    private TextView textViewUsername;
    private TextView textViewEmployeeName;
    private TextView textViewEmployeeGroup;
    private TextView textViewBirthdayPlace;
    private TextView textViewBirthday;
    private TextView textViewAddress;
    private TextView textViewMobilePhone;
    private TextView textViewEmail;
    private LinearLayout textViewEditProfile;
    private LinearLayout textViewLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        imageAkun = (CircleImageView) findViewById(R.id.imageAkun);
        textViewEmployeeId = (TextView) findViewById(R.id.textViewEmployeeId);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewEmployeeName = (TextView) findViewById(R.id.textViewEmployeeName);
        textViewEmployeeGroup = (TextView) findViewById(R.id.textViewEmployeeGroup);
        textViewBirthdayPlace = (TextView) findViewById(R.id.textViewBirthdayPlace);
        textViewBirthday = (TextView) findViewById(R.id.textViewBirthday);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        textViewMobilePhone = (TextView) findViewById(R.id.textViewMobilePhone);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewEditProfile = (LinearLayout) findViewById(R.id.textViewEditProfile);
        textViewLogout = (LinearLayout) findViewById(R.id.textViewLogout);

        Picasso.get().load(Config.DATA_URL_PHOTO_PROFILE+sharedPrefManager.getEmployeeId()+".jpg").into(imageAkun);
        textViewEmployeeId.setText(sharedPrefManager.getEmployeeNumber());
        textViewUsername.setText(sharedPrefManager.getUserName());
        textViewEmployeeName.setText(sharedPrefManager.getUserDisplayName());
        textViewEmployeeGroup.setText(sharedPrefManager.getUserGroupName());
        textViewBirthdayPlace.setText(sharedPrefManager.getPlaceBirthday());
        textViewBirthday.setText(sharedPrefManager.getBirthday());
        textViewAddress.setText(sharedPrefManager.getAddress());
        textViewMobilePhone.setText(sharedPrefManager.getMobilePhone());
        textViewEmail.setText(sharedPrefManager.getEmail());

        textViewEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaActivity = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(bukaActivity);
                finish();
            }
        });

        textViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(logout);
                sharedPrefManager.logout();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent bukaActivity = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(bukaActivity);
        finish();
    }
}