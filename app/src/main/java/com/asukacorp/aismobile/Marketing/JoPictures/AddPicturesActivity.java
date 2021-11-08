package com.asukacorp.aismobile.Marketing.JoPictures;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.SharedPrefManager;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class AddPicturesActivity extends AppCompatActivity {

    private ImageView buttonBack;
    private ImageView editJopBrowse;
    private Spinner editJopJo;
    private EditText editJopDesc;
    private Button buttonSave;

    private ProgressDialog progressDialog;
    private ArrayAdapter<String> adapter;
    private String[] jobCodeId;
    private String filePhoto;
    private String fileType;
    private int filePic = 0;

    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pictures);

        progressDialog = new ProgressDialog(AddPicturesActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        sharedPrefManager = new SharedPrefManager(AddPicturesActivity.this);

        editJopJo = (Spinner) findViewById(R.id.editJopJo);
        editJopDesc = (EditText) findViewById(R.id.editJopDesc);

        editJopBrowse = (ImageView) findViewById(R.id.editJopBrowse);
        editJopBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(AddPicturesActivity.this)
                        .cropSquare()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });

        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editJopJo.getSelectedItemPosition() > 0 || filePic > 0){
                    BitmapDrawable drawable = (BitmapDrawable) editJopBrowse.getDrawable();
                    Bitmap bm = drawable.getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] byteArrayImage = baos.toByteArray();
                    filePhoto = Base64.encodeToString(byteArrayImage, 0);

                    addData();
                } else Toast.makeText(AddPicturesActivity.this, "Please complete your data", Toast.LENGTH_LONG).show();
            }
        });

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getData();
    }

    private void getData() {
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_LIST_JOB_ORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                String[] jobCode = new String[jsonArray.length()+1];
                                jobCodeId = new String[jsonArray.length()+1];
                                jobCode[0] = "-- Pilih Job Code --";
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jobCode[i + 1] = jsonArray.getJSONObject(i).getString("job_order_number") + " | " + jsonArray.getJSONObject(i).getString("job_order_description");
                                    jobCodeId[i + 1] = jsonArray.getJSONObject(i).getString("job_order_id");
                                }
                                adapter = new ArrayAdapter<String>(AddPicturesActivity.this, android.R.layout.simple_spinner_dropdown_item, jobCode);
                                editJopJo.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void addData() {
        progressDialog.show();
        final String jobCode = jobCodeId[editJopJo.getSelectedItemPosition()];
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CREATE_JOB_ORDER_PICTURE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status=jsonObject.getInt("status");
                            int data=jsonObject.getInt("data");
                            if(status==1){
                                if (data==1) {
                                    Toast.makeText(AddPicturesActivity.this, "Success added photo", Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                } else Toast.makeText(AddPicturesActivity.this, "You do not have access to add job order photos", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(AddPicturesActivity.this, "Filed added photo", Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            Toast.makeText(AddPicturesActivity.this, "Error added photo", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(AddPicturesActivity.this, "terjadi kesalahan jaringan, coba periksa jaringan internet ada", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("idJobOrder", jobCode);
                param.put("description", editJopDesc.getText().toString());
                param.put("fileType", fileType);
                param.put("photoCode", filePhoto);
                param.put("employeeId", sharedPrefManager.getEmployeeId());
                return param;
            }
        };
        Volley.newRequestQueue(AddPicturesActivity.this).add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && data!=null){
            Uri uri = data.getData();
            editJopBrowse.setImageURI(uri);
            fileType = String.valueOf(uri).substring(String.valueOf(uri).lastIndexOf(".") + 1);
            filePic = 1;
        } else Toast.makeText(AddPicturesActivity.this, "Filed load picture", Toast.LENGTH_LONG).show();
    }
}