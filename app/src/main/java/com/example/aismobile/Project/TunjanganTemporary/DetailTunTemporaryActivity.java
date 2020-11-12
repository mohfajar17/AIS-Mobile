package com.example.aismobile.Project.TunjanganTemporary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aismobile.Config;
import com.example.aismobile.Data.Project.TunjanganTemporary;
import com.example.aismobile.R;
import com.example.aismobile.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DetailTunTemporaryActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private TunjanganTemporary tunjanganTemporary;

    private TextView textNumber;
    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuHistory;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutHistory;
    private TextView textCatatan;
    private TextView textCheckedComment;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

    private TextView textJobOrder;
    private TextView textTglAwal;
    private TextView textTglAkhir;
    private TextView textRequestedBy;
    private TextView textVerifiedBy;
    private TextView textVerifiedDate;
    private TextView textCheckedBy;
    private TextView textCheckedDate;
    private TextView textApproval1;
    private TextView textApproval2;

    private TextView textListNama;
    private TextView textListJenjang;
    private TextView textListType;
    private TextView textListKabupaten;
    private TextView textListDays;
    private TextView textListApproval1;
    private TextView textListApproval2;
    private TextView textListPaid;

    private int code = 0, approval = 0, akses1 = 0, akses2 = 0, approve1 = 0, approve2 = 0;
    private ArrayAdapter<String> adapterApproval;
    private LinearLayout layoutApproval;
    private TextView btnApprove1;
    private TextView btnApprove2;
    private TextView btnApprove3;
    private TextView btnSaveApprove;
    private EditText editCommand;
    public Spinner editApproval1;
    public Spinner editApproval2;
    private FloatingActionButton fabRefresh;
    private SharedPrefManager sharedPrefManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tun_temporary);

        Bundle bundle = getIntent().getExtras();
        tunjanganTemporary = bundle.getParcelable("detail");
        code = bundle.getInt("code");
        sharedPrefManager = new SharedPrefManager(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        layoutApproval = (LinearLayout) findViewById(R.id.layoutApproval);
        if (code > 0){
            params = layoutApproval.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutApproval.setLayoutParams(params);
            loadAccess();
            loadAccessApproval();
        }
        btnApprove1 = (TextView) findViewById(R.id.btnApprove1);
        btnApprove2 = (TextView) findViewById(R.id.btnApprove2);
        btnApprove3 = (TextView) findViewById(R.id.btnApprove3);
        btnSaveApprove = (TextView) findViewById(R.id.btnSaveApprove);
        editCommand = (EditText) findViewById(R.id.editCommand);
        fabRefresh = (FloatingActionButton) findViewById(R.id.fabRefresh);
        editApproval1 = (Spinner) findViewById(R.id.editApproval1);
        editApproval2 = (Spinner) findViewById(R.id.editApproval2);

        btnApprove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                if (approval != 1 && tunjanganTemporary.getApproval1_by().matches("-")){
                    btnApprove1.setBackgroundResource(R.drawable.circle_red);
                    approval = 1;
                } else approval = 0;
                openApproval();
            }
        });
        btnApprove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                if (approval != 2 && tunjanganTemporary.getApproval2_by().matches("-")){
                    btnApprove2.setBackgroundResource(R.drawable.circle_red);
                    approval = 2;
                } else approval = 0;
                openApproval();
            }
        });
        btnApprove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                if (approval != 3 && tunjanganTemporary.getVerified_by().matches("-")){
                    btnApprove3.setBackgroundResource(R.drawable.circle_red);
                    approval = 3;
                } else approval = 0;
                openApproval();
            }
        });
        btnSaveApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date dateObj = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (approval == 1 && akses1 > 0 && approve1 > 0){
                    updateApproval(String.valueOf(tunjanganTemporary.getEmployee_allowance_id()),
                            editApproval1.getSelectedItem().toString(),
                            textListApproval2.getText().toString());
                    updateApprovalId(1);
                    textListApproval1.setText(editApproval1.getSelectedItem().toString());
                    textApproval1.setText(sharedPrefManager.getUserDisplayName() + "\n" + dateFormater.format(dateObj) + "\n" + editCommand.getText().toString());
                } else if (approval == 2 && akses2 > 0 && approve2 > 0){
                    updateApproval(String.valueOf(tunjanganTemporary.getEmployee_allowance_id()),
                            textListApproval1.getText().toString(),
                            editApproval2.getSelectedItem().toString());
                    updateApprovalId(2);
                    textListApproval2.setText(editApproval2.getSelectedItem().toString());
                    textApproval2.setText(sharedPrefManager.getUserDisplayName() + "\n" + dateFormater.format(dateObj) + "\n" + editCommand.getText().toString());
                } else if (approval == 3){
                    updateApprovalId(3);
                    textVerifiedBy.setText(sharedPrefManager.getUserDisplayName() + "\n" + dateFormater.format(dateObj) + "\n" + editCommand.getText().toString());
                } else Toast.makeText(DetailTunTemporaryActivity.this, "You don't have access to approve", Toast.LENGTH_LONG).show();
            }
        });
        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approval = 0;
                changeColor();
                openApproval();
            }
        });

        textListNama = (TextView) findViewById(R.id.textListNama);
        textListJenjang = (TextView) findViewById(R.id.textListJenjang);
        textListType = (TextView) findViewById(R.id.textListType);
        textListKabupaten = (TextView) findViewById(R.id.textListKabupaten);
        textListDays = (TextView) findViewById(R.id.textListDays);
        textListApproval1 = (TextView) findViewById(R.id.textListApproval1);
        textListApproval2 = (TextView) findViewById(R.id.textListApproval2);
        textListPaid = (TextView) findViewById(R.id.textListPaid);

        textListNama.setText(tunjanganTemporary.getEmployee_id());
        textListJenjang.setText(tunjanganTemporary.getEmployee_grade_id());
        textListType.setText(tunjanganTemporary.getAdditional_allowance_type());
        textListKabupaten.setText(tunjanganTemporary.getKab_id());
        textListDays.setText(tunjanganTemporary.getDays());
        textListApproval1.setText(tunjanganTemporary.getApproval1_status());
        textListApproval2.setText(tunjanganTemporary.getApproval2_status());
        textListPaid.setText(tunjanganTemporary.getPaid());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCheckedComment = (TextView) findViewById(R.id.textCheckedComment);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textJobOrder = (TextView) findViewById(R.id.textJobOrder);
        textTglAwal = (TextView) findViewById(R.id.textTglAwal);
        textTglAkhir = (TextView) findViewById(R.id.textTglAkhir);
        textRequestedBy = (TextView) findViewById(R.id.textRequestedBy);
        textVerifiedBy = (TextView) findViewById(R.id.textVerifiedBy);
        textVerifiedDate = (TextView) findViewById(R.id.textVerifiedDate);
        textCheckedBy = (TextView) findViewById(R.id.textCheckedBy);
        textCheckedDate = (TextView) findViewById(R.id.textCheckedDate);
        textApproval1 = (TextView) findViewById(R.id.textApproval1);
        textApproval2 = (TextView) findViewById(R.id.textApproval2);

        textJobOrder.setText(tunjanganTemporary.getJob_order_id() + " (" + tunjanganTemporary.getJob_order_location() +
                ") => " + tunjanganTemporary.getJob_order_description());
        textTglAwal.setText(tunjanganTemporary.getBegin_date());
        textTglAkhir.setText(tunjanganTemporary.getEnd_date());
        textRequestedBy.setText(tunjanganTemporary.getRequested_by());
        textVerifiedBy.setText(tunjanganTemporary.getVerified_by());
        textVerifiedDate.setText(tunjanganTemporary.getVerified_date());
        textCheckedBy.setText(tunjanganTemporary.getChecked_by());
        textCheckedDate.setText(tunjanganTemporary.getChecked_date());
        textApproval1.setText(tunjanganTemporary.getApproval1_by() + "\n" + tunjanganTemporary.getApproval_date1() + "\n" + tunjanganTemporary.getApproval_comment1());
        textApproval2.setText(tunjanganTemporary.getApproval2_by() + "\n" + tunjanganTemporary.getApproval_date2() + "\n" + tunjanganTemporary.getApproval_comment2());

        textNumber.setText(tunjanganTemporary.getEmployee_allowance_number());
        textCatatan.setText(tunjanganTemporary.getNotes());
        textCheckedComment.setText(tunjanganTemporary.getChecked_comment());
        textCreatedBy.setText(tunjanganTemporary.getCreated_by());
        textCreatedDate.setText(tunjanganTemporary.getCreated_date());
        textModifiedBy.setText(tunjanganTemporary.getModified_by());
        textModifiedDate.setText(tunjanganTemporary.getModified_date());
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

        changeColor();
    }

    private void loadAccessApproval() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_ALLOW, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    approve1 = jsonObject.getInt("access1");
                    approve2 = jsonObject.getInt("access2");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailTunTemporaryActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user", sharedPrefManager.getUserId());
                param.put("id", "" + tunjanganTemporary.getEmployee_allowance_id());
                param.put("code", "7");
                return param;
            }
        };
        Volley.newRequestQueue(DetailTunTemporaryActivity.this).add(request);
    }

    private void loadAccess() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_APPROVAL_ALLOW, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    akses1 = jsonObject.getInt("access1");
                    akses2 = jsonObject.getInt("access2");
                } catch (JSONException e) {
                    Toast.makeText(DetailTunTemporaryActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailTunTemporaryActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user", sharedPrefManager.getUserId());
                param.put("code", "7");
                return param;
            }
        };
        Volley.newRequestQueue(DetailTunTemporaryActivity.this).add(request);
    }

    private void openApproval(){
        if (approval == 1 && code == 1){
            params =  editApproval1.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            editApproval1.setLayoutParams(params);
            params =  textListApproval1.getLayoutParams();
            params.height = 0;
            textListApproval1.setLayoutParams(params);
        } else if (approval == 2 && code == 1) {
            params =  editApproval2.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            editApproval2.setLayoutParams(params);
            params =  textListApproval2.getLayoutParams();
            params.height = 0;
            textListApproval2.setLayoutParams(params);
        } else {
            params =  textListApproval1.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            textListApproval1.setLayoutParams(params);
            params =  editApproval1.getLayoutParams();
            params.height = 0;
            editApproval1.setLayoutParams(params);

            params =  textListApproval2.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            textListApproval2.setLayoutParams(params);
            params =  editApproval2.getLayoutParams();
            params.height = 0;
            editApproval2.setLayoutParams(params);
        }

        String[] approve = {"Approved", "Reject", "-"};
        adapterApproval = new ArrayAdapter<String>(DetailTunTemporaryActivity.this, android.R.layout.simple_spinner_dropdown_item, approve);
        editApproval1.setAdapter(adapterApproval);
        editApproval2.setAdapter(adapterApproval);
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

    public void changeColor(){
        if (tunjanganTemporary.getApproval1_by().matches("-")){
            btnApprove1.setBackgroundResource(R.drawable.circle_green);
            btnApprove2.setBackgroundResource(R.drawable.circle_green);
            btnApprove3.setBackgroundResource(R.drawable.circle_green);
        } else if (tunjanganTemporary.getApproval2_by().matches("-")){
            btnApprove1.setBackgroundResource(R.drawable.circle_blue_new);
            btnApprove2.setBackgroundResource(R.drawable.circle_green);
            btnApprove3.setBackgroundResource(R.drawable.circle_green);
        } else if (tunjanganTemporary.getVerified_by().matches("-")){
            btnApprove1.setBackgroundResource(R.drawable.circle_blue_new);
            btnApprove2.setBackgroundResource(R.drawable.circle_blue_new);
            btnApprove3.setBackgroundResource(R.drawable.circle_green);
        } else {
            btnApprove1.setBackgroundResource(R.drawable.circle_green);
            btnApprove2.setBackgroundResource(R.drawable.circle_green);
            btnApprove3.setBackgroundResource(R.drawable.circle_green);
        }
    }

    public void updateApproval(final String id, final String approve1, final String approve2){
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_APPROVAL_TUN_TEMPORARY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("id", id);
                param.put("approve1", approve1);
                param.put("approve2", approve2);
                return param;
            }
        };
        Volley.newRequestQueue(DetailTunTemporaryActivity.this).add(request);
    }

    public void updateApprovalId(final int code_update){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_ID_APPROVAL_TUN_TEMPORARY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        if (code_update == 1)
                            tunjanganTemporary.setApproval1_by(sharedPrefManager.getUserDisplayName());
                        else if (code_update == 2)
                            tunjanganTemporary.setApproval2_by(sharedPrefManager.getUserDisplayName());
                        else if (code_update == 3)
                            tunjanganTemporary.setVerified_by(sharedPrefManager.getUserDisplayName());
                        changeColor();
                        Toast.makeText(DetailTunTemporaryActivity.this, "Success update data", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DetailTunTemporaryActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    Toast.makeText(DetailTunTemporaryActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailTunTemporaryActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("id", "" + tunjanganTemporary.getEmployee_allowance_id());
                param.put("user", sharedPrefManager.getUserId());
                param.put("command", editCommand.getText().toString() + " ");
                param.put("code", "" + approval);
                return param;
            }
        };
        Volley.newRequestQueue(DetailTunTemporaryActivity.this).add(request);
    }
}