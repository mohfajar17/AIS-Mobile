package com.asukacorp.aismobile.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.LoginActivity;
import com.asukacorp.aismobile.MainActivity;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.SharedPrefManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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

        if (sharedPrefManager.getFileName().equals("")){
            if (sharedPrefManager.getGender().equals("1"))
                imageAkun.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.akun_male));
            else imageAkun.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.akun_female));
        } else Picasso.get().load(Config.DATA_URL_PHOTO_PROFILE+sharedPrefManager.getFileName()).into(imageAkun); //get photo profile

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

        getMobileIsActive(sharedPrefManager.getUserId());
    }

    @Override
    public void onBackPressed() {
        Intent bukaActivity = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(bukaActivity);
        finish();
    }

    private void getMobileIsActive(final String userId) {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_MOBILE_IS_ACTIVE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int akses = jsonObject.getInt("is_mobile");
                    if (akses > 1){
                        Intent logout = new Intent(ProfileActivity.this, LoginActivity.class);
                        startActivity(logout);
                        sharedPrefManager.logout();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(ProfileActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", userId);
                return param;
            }
        };
        Volley.newRequestQueue(ProfileActivity.this).add(request);
    }
}