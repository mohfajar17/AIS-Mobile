package com.example.aismobile.Finance.CashAdvance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.aismobile.Data.FinanceAccounting.CashAdvance;
import com.example.aismobile.R;
import com.example.aismobile.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailCashAdvanceActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private CashAdvance cashAdvance;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private TextView textCaNumber;
    private TextView textCaDate;
    private TextView textNilai;
    private TextView textAdvancedFor;
    private TextView textReceivedBy;
    private TextView textApprovedStatus;
    private TextView textApprovedBy;
    private TextView textApprovedDate;
    private TextView textApprovedComment;
    private TextView textReconciledBy;
    private TextView textReconciledDate;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

    private int code = 0, approval = 0, akses1 = 0;
    private LinearLayout layoutApproval;
    private TextView btnApprove1;
    private TextView btnSaveApprove;
    private EditText editCommand;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cash_advance);

        Bundle bundle = getIntent().getExtras();
        cashAdvance = bundle.getParcelable("detail");
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
        }
        btnApprove1 = (TextView) findViewById(R.id.btnApprove1);
        btnSaveApprove = (TextView) findViewById(R.id.btnSaveApprove);
        editCommand = (EditText) findViewById(R.id.editCommand);
        btnApprove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnApprove1.setTextColor(getResources().getColor(R.color.colorWhite));
                if (approval != 1){
                    btnApprove1.setTextColor(getResources().getColor(R.color.colorBlack));
                    approval = 1;
                } else approval = 0;
            }
        });
        btnSaveApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (approval == 1 && akses1 > 0){
                    if (cashAdvance.getStatus().toLowerCase().contains("Paid".toLowerCase()) ||
                            cashAdvance.getStatus().toLowerCase().contains("Cleared".toLowerCase()))
                        Toast.makeText(DetailCashAdvanceActivity.this, "You are not able to approve because it has not been Checking", Toast.LENGTH_LONG).show();
                    else{
                        updateApprovalId();
                        btnApprove1.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                } else Toast.makeText(DetailCashAdvanceActivity.this, "You don't have access to approve", Toast.LENGTH_LONG).show();
            }
        });

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textCaNumber = (TextView) findViewById(R.id.textCaNumber);
        textCaDate = (TextView) findViewById(R.id.textCaDate);
        textNilai = (TextView) findViewById(R.id.textNilai);
        textAdvancedFor = (TextView) findViewById(R.id.textAdvancedFor);
        textReceivedBy = (TextView) findViewById(R.id.textReceivedBy);
        textApprovedStatus = (TextView) findViewById(R.id.textApprovedStatus);
        textApprovedBy = (TextView) findViewById(R.id.textApprovedBy);
        textApprovedDate = (TextView) findViewById(R.id.textApprovedDate);
        textApprovedComment = (TextView) findViewById(R.id.textApprovedComment);
        textReconciledBy = (TextView) findViewById(R.id.textReconciledBy);
        textReconciledDate = (TextView) findViewById(R.id.textReconciledDate);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textCaNumber.setText(cashAdvance.getAdvanced_number());
        textCaDate.setText(cashAdvance.getAdvanced_date());
        textNilai.setText(cashAdvance.getAmount());
        textAdvancedFor.setText(cashAdvance.getAdvanced_for());
        textReceivedBy.setText(cashAdvance.getReceived_by());
        textApprovedStatus.setText(cashAdvance.getApproved_status());
        textApprovedBy.setText(cashAdvance.getApproved_by());
        textApprovedDate.setText(cashAdvance.getApproved_date());
        textApprovedComment.setText(cashAdvance.getApproved_comment());
        textReconciledBy.setText(cashAdvance.getReconciled_by());
        textReconciledDate.setText(cashAdvance.getReconciled_date());
        textCatatan.setText(cashAdvance.getNotes());
        textCreatedBy.setText(cashAdvance.getCreated_by());
        textCreatedDate.setText(cashAdvance.getCreated_date());
        textModifiedBy.setText(cashAdvance.getModified_by());
        textModifiedDate.setText(cashAdvance.getModified_date());

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadAccess() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_APPROVAL_ALLOW, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    akses1 = jsonObject.getInt("access1");
                } catch (JSONException e) {
                    Toast.makeText(DetailCashAdvanceActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailCashAdvanceActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user", sharedPrefManager.getUserId());
                param.put("code", "17");
                return param;
            }
        };
        Volley.newRequestQueue(DetailCashAdvanceActivity.this).add(request);
    }

    public void updateApprovalId(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_APPROVAL_CASH_ADVANCE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Toast.makeText(DetailCashAdvanceActivity.this, "Success update data", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DetailCashAdvanceActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    Toast.makeText(DetailCashAdvanceActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailCashAdvanceActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("id", "" + cashAdvance.getAdvanced_id());
                param.put("user", sharedPrefManager.getUserId());
                param.put("command", editCommand.getText().toString() + " ");
                param.put("code", "" + approval);
                return param;
            }
        };
        Volley.newRequestQueue(DetailCashAdvanceActivity.this).add(request);
    }
}