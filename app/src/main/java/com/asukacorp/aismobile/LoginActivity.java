package com.asukacorp.aismobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnSystemUiVisibilityChangeListener {

    private SharedPrefManager sharedPrefManager;
    private boolean doubleBackToExitPressedOnce = false;
    private ProgressDialog progressDialog;

    private Button buttonLogin;
    private EditText editTextUserName;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        if(sharedPrefManager.isLogin()){
            Intent bukaMainActivity = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(bukaMainActivity);
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Refresh Data");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        editTextUserName = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    private void getData() {
        if (String.valueOf(editTextUserName.getText()).equals("") || String.valueOf(editTextPassword.getText()).equals("")){
            Toast.makeText(LoginActivity.this, "Please enter username and password", Toast.LENGTH_LONG).show();
        } else {
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            final String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_LOGIN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        if(status==1){
                            JSONObject jsonData = jsonObject.getJSONObject("data");

                            sharedPrefManager.setUserId(jsonData.getString("user_id"));
                            sharedPrefManager.setEmployeeId(jsonData.getString("employee_id"));
                            sharedPrefManager.setUserName(jsonData.getString("user_name"));
                            sharedPrefManager.setUserDisplayName(jsonData.getString("user_displayname"));
                            sharedPrefManager.setUserGroupId(jsonData.getString("usergroup_id"));
                            sharedPrefManager.setUserGroupName(jsonData.getString("usergroup_name"));
                            sharedPrefManager.setUserPwd(jsonData.getString("user_pwd"));
                            sharedPrefManager.setToken(jsonData.getString("token"));

                            getDataEmployee(jsonData.getString("employee_id"));
                        } else {
                            Toast.makeText(LoginActivity.this, "Username and password incorrect", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Failed load data", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(LoginActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param=new HashMap<>();
                    param.put("user_name", String.valueOf(editTextUserName.getText()));
                    param.put("password", String.valueOf(editTextPassword.getText()));
                    param.put("last_ip", ipAddress);
                    return param;
                }
            };
            Volley.newRequestQueue(this).add(request);
        }
    }

    private void getDataEmployee(final String employeeId){
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_EMPLOYEE_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if(status==1){
                        JSONObject jsonData = jsonObject.getJSONObject("data");

                        sharedPrefManager.setEmployeeNumber(jsonData.getString("employee_number"));
                        sharedPrefManager.setGender(jsonData.getString("gender"));
                        sharedPrefManager.setPlaceBirthday(jsonData.getString("place_birthday"));
                        sharedPrefManager.setBirthday(jsonData.getString("birthday"));
                        sharedPrefManager.setAddress(jsonData.getString("address"));
                        sharedPrefManager.setMobilePhone(jsonData.getString("mobile_phone"));
                        sharedPrefManager.setEmail(jsonData.getString("email1"));
                        sharedPrefManager.setFileName(jsonData.getString("employee_file_name"));
                    } else {
                        sharedPrefManager.setEmployeeNumber("");
                        sharedPrefManager.setGender("");
                        sharedPrefManager.setPlaceBirthday("");
                        sharedPrefManager.setBirthday("");
                        sharedPrefManager.setAddress("");
                        sharedPrefManager.setMobilePhone("");
                        sharedPrefManager.setEmail("");
                        sharedPrefManager.setFileName("");
                    }
                    getAccessModul();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("employee_id", employeeId);
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void getAccessModul() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_USER_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if(status==1){
                        JSONObject jsonData = jsonObject.getJSONObject("data");
                        if (Integer.valueOf(jsonData.getString("modul_contact")) == 1)
                            sharedPrefManager.setAcessModul(sharedPrefManager.getAccessModul()+"contact, ");
                        if (Integer.valueOf(jsonData.getString("modul_crm")) == 1)
                            sharedPrefManager.setAcessModul(sharedPrefManager.getAccessModul()+"crm, ");
                        if (Integer.valueOf(jsonData.getString("modul_fa")) == 1)
                            sharedPrefManager.setAcessModul(sharedPrefManager.getAccessModul()+"finance, ");
                        if (Integer.valueOf(jsonData.getString("modul_hr")) == 1)
                            sharedPrefManager.setAcessModul(sharedPrefManager.getAccessModul()+"hrga, ");
                        if (Integer.valueOf(jsonData.getString("modul_hse")) == 1)
                            sharedPrefManager.setAcessModul(sharedPrefManager.getAccessModul()+"hse, ");
                        if (Integer.valueOf(jsonData.getString("modul_inventory")) == 1)
                            sharedPrefManager.setAcessModul(sharedPrefManager.getAccessModul()+"inventory, ");
                        if (Integer.valueOf(jsonData.getString("modul_marketing")) == 1)
                            sharedPrefManager.setAcessModul(sharedPrefManager.getAccessModul()+"marketing, ");
                        if (Integer.valueOf(jsonData.getString("modul_project")) == 1)
                            sharedPrefManager.setAcessModul(sharedPrefManager.getAccessModul()+"project, ");
                        if (Integer.valueOf(jsonData.getString("modul_purchasing")) == 1)
                            sharedPrefManager.setAcessModul(sharedPrefManager.getAccessModul()+"purchasing, ");
                        if (Integer.valueOf(jsonData.getString("modul_dashboard")) == 1)
                            sharedPrefManager.setAcessModul(sharedPrefManager.getAccessModul()+"dashboard, ");
                    } else {
                        sharedPrefManager.setAcessModul("");
                    }
                    sharedPrefManager.login();
                    Intent bukaMainActivity = new Intent(LoginActivity.this, MainActivity.class);
                    startActivityForResult(bukaMainActivity,1);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", sharedPrefManager.getUserId());
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    public void onSystemUiVisibilityChange(int visibility) {

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 3000);
    }
}
