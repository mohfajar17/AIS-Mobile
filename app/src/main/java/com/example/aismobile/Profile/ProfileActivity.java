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
    private ProgressDialog progressDialog;
    private String stringPilihFoto;

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

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Process");
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

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

        imageAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryConfig config = new GalleryConfig.Build()
                        .limitPickPhoto(1)
                        .singlePhoto(true)
                        .hintOfPick("Choose image")
                        .build();
                GalleryActivity.openActivity(ProfileActivity.this,10,config);
            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK && data!=null && requestCode == 10){
            String compressed = ((List<String>)data.getSerializableExtra(GalleryActivity.PHOTOS)).get(0);

            //imageBase64 = fileHandler.saveTempImage(compressed);
            Uri uri = Uri.fromFile(new File(compressed));
            Picasso.get()
                    .load(uri)
                    .resize(100, 100)
                    .centerCrop()
                    .into(imageAkun);
            Bitmap bm = BitmapFactory.decodeFile(compressed);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] byteArrayImage = baos.toByteArray();
            progressDialog.show();

            stringPilihFoto = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

            StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_UPDATE_PHOTO_PROFILE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int status=jsonObject.getInt("status");
                                if(status==1){
                                    //new DownloadImageTask(ProfileActivity.this).execute(Config.DATA_URL_PROFIL_PHOTO+"/"+sharedPrefManager.getPhoto());
                                    progressDialog.dismiss();
                                    Toast.makeText(ProfileActivity.this, "Success upload photo", Toast.LENGTH_LONG).show();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(ProfileActivity.this, "Failed upload foto", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                Toast.makeText(ProfileActivity.this, "Failed", Toast.LENGTH_LONG).show();
                                Log.v("TAG String Photo => ", stringPilihFoto);
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(ProfileActivity.this, "terjadi kesalahan jaringan, coba periksa jaringan internet ada", Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param=new HashMap<>();
                    param.put("employee_id", sharedPrefManager.getEmployeeId());
                    param.put("photoProfile", stringPilihFoto);
                    return param;
                }
            };
            Volley.newRequestQueue(ProfileActivity.this).add(request);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}