package com.asukacorp.aismobile.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.MainActivity;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    private SharedPrefManager sharedPrefManager;
    private ProgressDialog progressDialog;

    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextKonfirmasiPassword;
    private EditText editTextEmployeeId;
    private EditText editTextEmployeeName;
    private EditText editTextEmployeeGroup;
    private EditText editTextBirthdayPlace;
    private EditText editTextBirthday;
    private EditText editTextAddress;
    private EditText editTextMobilePhone;
    private EditText editTextEmail;
    private Button buttonSimpanAkun;
    private Button buttonSimpanPersonal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Refresh Data");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextKonfirmasiPassword = (EditText) findViewById(R.id.editTextKonfPassword);
        editTextEmployeeId = (EditText) findViewById(R.id.editTextEmployeeId);
        editTextEmployeeName = (EditText) findViewById(R.id.editTextEmployeeName);
        editTextEmployeeGroup = (EditText) findViewById(R.id.editTextEmployeeGroup);
        editTextBirthdayPlace = (EditText) findViewById(R.id.editTextBirthdayPlace);
        editTextBirthday = (EditText) findViewById(R.id.editTextBirthday);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextMobilePhone = (EditText) findViewById(R.id.editTextMobilePhone);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        buttonSimpanAkun = (Button) findViewById(R.id.buttonSimpanAkun);
        buttonSimpanPersonal = (Button) findViewById(R.id.buttonSimpanPersonal);

        editTextUsername.setText(sharedPrefManager.getUserName());
        editTextEmployeeId.setText(sharedPrefManager.getEmployeeNumber());
        editTextEmployeeName.setText(sharedPrefManager.getUserDisplayName());
        editTextEmployeeGroup.setText(sharedPrefManager.getUserGroupName());
        editTextBirthdayPlace.setText(sharedPrefManager.getPlaceBirthday());
        editTextBirthday.setText(sharedPrefManager.getBirthday());
        editTextAddress.setText(sharedPrefManager.getAddress());
        editTextMobilePhone.setText(sharedPrefManager.getMobilePhone());
        editTextEmail.setText(sharedPrefManager.getEmail());

        buttonSimpanAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataAkun();
            }
        });
        buttonSimpanPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataPersonal();
            }
        });
    }

    private void saveDataPersonal() {
        if (String.valueOf(editTextAddress.getText()).equals("") || String.valueOf(editTextMobilePhone.getText()).equals("") || String.valueOf(editTextEmail.getText()).equals("")){
            Toast.makeText(EditProfileActivity.this, "Please enter your data", Toast.LENGTH_LONG).show();
        } else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_EDIT_PERSONAL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        if(status==1){
                            JSONObject jsonData = jsonObject.getJSONObject("data");
                            sharedPrefManager.setAddress(jsonData.getString("address"));
                            sharedPrefManager.setMobilePhone(jsonData.getString("mobile_phone"));
                            sharedPrefManager.setEmail(jsonData.getString("email1"));
                            Toast.makeText(EditProfileActivity.this, "Data has been changed", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            Intent saveData = new Intent(EditProfileActivity.this, MainActivity.class);
                            startActivity(saveData);
                            finish();
                        } else {
                            Toast.makeText(EditProfileActivity.this, "Failed to change data", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(EditProfileActivity.this, "Failed to change data", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(EditProfileActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param=new HashMap<>();
                    param.put("employee_id", sharedPrefManager.getEmployeeId());
                    param.put("address", String.valueOf(editTextAddress.getText()));
                    param.put("mobile_phone", String.valueOf(editTextMobilePhone.getText()));
                    param.put("email", String.valueOf(editTextEmail.getText()));
                    return param;
                }
            };
            Volley.newRequestQueue(this).add(request);
        }
    }

    private void saveDataAkun() {
        if (String.valueOf(editTextPassword.getText()).equals("") || String.valueOf(editTextKonfirmasiPassword.getText()).equals("")){
            Toast.makeText(EditProfileActivity.this, "Please enter new password and last password", Toast.LENGTH_LONG).show();
        } else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_EDIT_ACCOUNT, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        if(status==1){
                            JSONObject jsonData = jsonObject.getJSONObject("data");
                            sharedPrefManager.setUserPwd(jsonData.getString("user_pwd"));
                            Toast.makeText(EditProfileActivity.this, "Password has been changed", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            Intent saveData = new Intent(EditProfileActivity.this, MainActivity.class);
                            startActivity(saveData);
                            finish();
                        } else {
                            Toast.makeText(EditProfileActivity.this, "Failed to change password", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(EditProfileActivity.this, "Failed to change password", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(EditProfileActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param=new HashMap<>();
                    param.put("user_name", sharedPrefManager.getUserName());
                    param.put("new_password", String.valueOf(editTextPassword.getText()));
                    param.put("old_password", String.valueOf(editTextKonfirmasiPassword.getText()));
                    return param;
                }
            };
            Volley.newRequestQueue(this).add(request);
        }
    }

    @Override
    public void onBackPressed() {
        Intent bukaActivity = new Intent(EditProfileActivity.this, ProfileActivity.class);
        startActivity(bukaActivity);
        finish();
    }
}
