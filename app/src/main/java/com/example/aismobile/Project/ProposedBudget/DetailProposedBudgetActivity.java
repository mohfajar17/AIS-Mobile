package com.example.aismobile.Project.ProposedBudget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.example.aismobile.Data.Project.ProposedBudget;
import com.example.aismobile.Data.Project.ProposedBudgetDetail;
import com.example.aismobile.R;
import com.example.aismobile.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailProposedBudgetActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private ProposedBudget proposedBudget;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<ProposedBudgetDetail> proposedBudgetDetails;
    private ProgressDialog progressDialog;

    private TextView textNumber;
    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuHistory;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutHistory;

    private TextView textJobCode;
    private TextView textKeteranganJobCode;
    private TextView textPenanggungJwb;
    private TextView textRequestDate;
    private TextView textPaymentDate;
    private TextView textDueDate;
    private TextView textAccount;
    private TextView textCategory;
    private TextView textDone;
    private TextView textCheckedBy;
    private TextView textCheckedDate;
    private TextView textPenerima;
    private TextView textApproval1;
    private TextView textApproval2;
    private TextView textApproval3;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;
    private TextView textGrandTotal;

    private TextView textRestItem;
    private TextView textRestPrice;
    private TextView textRestSubTotal;
    private TextView textTotal;
    private TextView textTotalApproved;
    private TextView textTotalTransfered;

    private double total;
    private double totalTransfered;
    private double totalApproved;

    private NumberFormat formatter;
    private double toDouble;

    private int code = 0, approval = 0, akses1 = 0, akses2 = 0, akses3 = 0, approve1 = 0, approve2 = 0, approve3 = 0;
    private ArrayAdapter<String> adapterApproval;
    private LinearLayout layoutApproval;
    private TextView btnApprove1;
    private TextView btnApprove2;
    private TextView btnApprove3;
    private TextView btnSaveApprove;
    private EditText editCommand;
    private FloatingActionButton fabRefresh;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_proposed_budget);

        Bundle bundle = getIntent().getExtras();
        proposedBudget = bundle.getParcelable("detail");
        code = bundle.getInt("code");
        sharedPrefManager = new SharedPrefManager(this);

        formatter = new DecimalFormat("#,###");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        proposedBudgetDetails = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDetail);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

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

        btnApprove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                if (approval != 1 && proposedBudget.getApproval1().matches("-")){
                    btnApprove1.setBackgroundResource(R.drawable.circle_red);
                    approval = 1;
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(proposedBudgetDetails, context);
                    recyclerView.setAdapter(adapter);
                } else approval = 0;
            }
        });
        btnApprove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                if (approval != 2 && proposedBudget.getApproval2().matches("-")){
                    btnApprove2.setBackgroundResource(R.drawable.circle_red);
                    approval = 2;
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(proposedBudgetDetails, context);
                    recyclerView.setAdapter(adapter);
                } else approval = 0;
            }
        });
        btnApprove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                if (approval != 3 && proposedBudget.getApproval3().matches("-")){
                    btnApprove3.setBackgroundResource(R.drawable.circle_red);
                    approval = 3;
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(proposedBudgetDetails, context);
                    recyclerView.setAdapter(adapter);
                } else approval = 0;
            }
        });
        btnSaveApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date dateObj = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (approval == 1 && akses1 > 0 && approve1 > 0){
                    progressDialog.show();
                    proposedBudget.setApproval1(sharedPrefManager.getUserDisplayName());
                    for (int i = 0; i<=proposedBudgetDetails.size(); i++) {
                        if (i==proposedBudgetDetails.size()){
                            loadDetail();
                            progressDialog.dismiss();
                        } else updateApproval(String.valueOf(proposedBudgetDetails.get(i).getCash_advance_detail_id()),
                                ((Spinner) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.editApproval1)).getSelectedItem().toString(),
                                ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval2)).getText().toString(),
                                ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval3)).getText().toString());
                    }
                    updateApprovalId();
                    textApproval1.setText(sharedPrefManager.getUserDisplayName() + "\n" + dateFormater.format(dateObj) + "\n" + editCommand.getText().toString());
                } else if (approval == 2 && akses2 > 0 && approve2 > 0){
                    progressDialog.show();
                    proposedBudget.setApproval2(sharedPrefManager.getUserDisplayName());
                    for (int i = 0; i<=proposedBudgetDetails.size(); i++) {
                        if (i==proposedBudgetDetails.size()){
                            loadDetail();
                            progressDialog.dismiss();
                        } else updateApproval(String.valueOf(proposedBudgetDetails.get(i).getCash_advance_detail_id()),
                                ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval1)).getText().toString(),
                                ((Spinner) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.editApproval2)).getSelectedItem().toString(),
                                ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval3)).getText().toString());
                    }
                    updateApprovalId();
                    textApproval2.setText(sharedPrefManager.getUserDisplayName() + "\n" + dateFormater.format(dateObj) + "\n" + editCommand.getText().toString());
                } else if (approval == 3 && akses3 > 0 && approve3 > 0){
                    progressDialog.show();
                    proposedBudget.setApproval3(sharedPrefManager.getUserDisplayName());
                    for (int i = 0; i<=proposedBudgetDetails.size(); i++) {
                        if (i==proposedBudgetDetails.size()){
                            loadDetail();
                            progressDialog.dismiss();
                        } else updateApproval(String.valueOf(proposedBudgetDetails.get(i).getCash_advance_detail_id()),
                                ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval1)).getText().toString(),
                                ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval2)).getText().toString(),
                                ((Spinner) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.editApproval3)).getSelectedItem().toString());
                    }
                    updateApprovalId();
                    textApproval3.setText(sharedPrefManager.getUserDisplayName() + "\n" + dateFormater.format(dateObj) + "\n" + editCommand.getText().toString());
                } else Toast.makeText(DetailProposedBudgetActivity.this, "You don't have access to approve", Toast.LENGTH_LONG).show();
            }
        });
        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDetail();
                changeColor();
                approval = 0;
            }
        });

        textJobCode = (TextView) findViewById(R.id.textJobCode);
        textKeteranganJobCode = (TextView) findViewById(R.id.textKeteranganJobCode);
        textPenanggungJwb = (TextView) findViewById(R.id.textPenanggungJwb);
        textRequestDate = (TextView) findViewById(R.id.textRequestDate);
        textPaymentDate = (TextView) findViewById(R.id.textPaymentDate);
        textDueDate = (TextView) findViewById(R.id.textDueDate);
        textAccount = (TextView) findViewById(R.id.textAccount);
        textCategory = (TextView) findViewById(R.id.textCategory);
        textDone = (TextView) findViewById(R.id.textDone);
        textCheckedBy = (TextView) findViewById(R.id.textCheckedBy);
        textCheckedDate = (TextView) findViewById(R.id.textCheckedDate);
        textPenerima = (TextView) findViewById(R.id.textPenerima);
        textApproval1 = (TextView) findViewById(R.id.textApproval1);
        textApproval2 = (TextView) findViewById(R.id.textApproval2);
        textApproval3 = (TextView) findViewById(R.id.textApproval3);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);
        textGrandTotal = (TextView) findViewById(R.id.textGrandTotal);

        textRestItem = (TextView) findViewById(R.id.textRestItem);
        textRestPrice = (TextView) findViewById(R.id.textRestPrice);
        textRestSubTotal = (TextView) findViewById(R.id.textRestSubTotal);
        textTotal = (TextView) findViewById(R.id.textTotal);
        textTotalApproved = (TextView) findViewById(R.id.textTotalApproved);
        textTotalTransfered = (TextView) findViewById(R.id.textTotalTransfered);

        NumberFormat formatter = new DecimalFormat("#,###");
        double toDouble = Double.valueOf(proposedBudget.getRest_value());
        textRestPrice.setText("Rp. " + formatter.format((long) toDouble));
        textRestSubTotal.setText("Rp. " + formatter.format((long) toDouble));
        textRestItem.setText(proposedBudget.getRest_from());

        textJobCode.setText(proposedBudget.getJob_order_id());
        textKeteranganJobCode.setText(proposedBudget.getJob_order_description());
        textPenanggungJwb.setText(proposedBudget.getPerson_in_charge());
        textRequestDate.setText(proposedBudget.getRequisition_date());
        textPaymentDate.setText(proposedBudget.getPayment_date());
        textDueDate.setText(proposedBudget.getDue_date());
        textAccount.setText(proposedBudget.getCategory());
        textCategory.setText(proposedBudget.getBank_transaction_type_id());
        if (Integer.valueOf(proposedBudget.getDone()) == 2)
            textDone.setText("Tidak");
        else textDone.setText("Ya");
        textCheckedBy.setText(proposedBudget.getChecked_by());
        textCheckedDate.setText(proposedBudget.getChecked_date());
        textPenerima.setText(proposedBudget.getRecipient_by());
        textApproval1.setText(proposedBudget.getApproval1() + "\n" + proposedBudget.getApproval_date1() + "\n" + proposedBudget.getApproval_comment1());
        textApproval2.setText(proposedBudget.getApproval2() + "\n" + proposedBudget.getApproval_date2() + "\n" + proposedBudget.getApproval_comment2());
        textApproval3.setText(proposedBudget.getApproval3() + "\n" + proposedBudget.getApproval_date3() + "\n" + proposedBudget.getApproval_comment3());
        textCatatan.setText(proposedBudget.getNotes());
        textCreatedBy.setText(proposedBudget.getCreated_by());
        textCreatedDate.setText(proposedBudget.getCreated_date());
        textModifiedBy.setText(proposedBudget.getModified_by());
        textModifiedDate.setText(proposedBudget.getModified_date());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);

        textNumber.setText(proposedBudget.getCash_advance_number());
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

        loadDetail();
    }

    private void loadAccessApproval() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_ALLOW, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    approve1 = jsonObject.getInt("access1");
                    approve2 = jsonObject.getInt("access2");
                    approve3 = jsonObject.getInt("access3");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailProposedBudgetActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user", sharedPrefManager.getUserId());
                param.put("id", "" + proposedBudget.getCash_advance_id());
                param.put("code", "4");
                return param;
            }
        };
        Volley.newRequestQueue(DetailProposedBudgetActivity.this).add(request);
    }

    private void loadAccess() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_APPROVAL_ALLOW, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    akses1 = jsonObject.getInt("access1");
                    akses2 = jsonObject.getInt("access2");
                    akses3 = jsonObject.getInt("access3");
                } catch (JSONException e) {
                    Toast.makeText(DetailProposedBudgetActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailProposedBudgetActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user", sharedPrefManager.getUserId());
                param.put("code", "4");
                return param;
            }
        };
        Volley.newRequestQueue(DetailProposedBudgetActivity.this).add(request);
    }

    public void changeColor(){
        if (proposedBudget.getApproval1().matches("-")){
            btnApprove1.setBackgroundResource(R.drawable.circle_green);
            btnApprove2.setBackgroundResource(R.drawable.circle_green);
            btnApprove3.setBackgroundResource(R.drawable.circle_green);
        } else if (proposedBudget.getApproval2().matches("-")){
            btnApprove1.setBackgroundResource(R.drawable.circle_blue_new);
            btnApprove2.setBackgroundResource(R.drawable.circle_green);
            btnApprove3.setBackgroundResource(R.drawable.circle_green);
        } else if (proposedBudget.getApproval3().matches("-")){
            btnApprove1.setBackgroundResource(R.drawable.circle_blue_new);
            btnApprove2.setBackgroundResource(R.drawable.circle_blue_new);
            btnApprove3.setBackgroundResource(R.drawable.circle_green);
        } else if (!proposedBudget.getApproval3().matches("-") &&
                !proposedBudget.getApproval2().matches("-") &&
                !proposedBudget.getApproval3().matches("-")){
            btnApprove1.setBackgroundResource(R.drawable.circle_blue_new);
            btnApprove2.setBackgroundResource(R.drawable.circle_blue_new);
            btnApprove3.setBackgroundResource(R.drawable.circle_blue_new);
        } else {
            btnApprove1.setBackgroundResource(R.drawable.circle_green);
            btnApprove2.setBackgroundResource(R.drawable.circle_green);
            btnApprove3.setBackgroundResource(R.drawable.circle_green);
        }
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

    public void updateApproval(final String id, final String approve1, final String approve2, final String approve3){
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_APPROVAL_PROPOSED_BUDGET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        loadDetail();
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
                param.put("approve3", approve3);
                return param;
            }
        };
        Volley.newRequestQueue(DetailProposedBudgetActivity.this).add(request);
    }

    public void updateApprovalId(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_ID_APPROVAL_PROPOSED_BUDGET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Toast.makeText(DetailProposedBudgetActivity.this, "Success update data", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DetailProposedBudgetActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    Toast.makeText(DetailProposedBudgetActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailProposedBudgetActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("id", "" + proposedBudget.getCash_advance_id());
                param.put("user", sharedPrefManager.getUserId());
                param.put("command", editCommand.getText().toString() + " ");
                param.put("code", "" + approval);
                return param;
            }
        };
        Volley.newRequestQueue(DetailProposedBudgetActivity.this).add(request);
    }

    public void loadDetail(){
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_PROPOSE_BUDGET_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        recyclerView.setAdapter(null);
                        proposedBudgetDetails.clear();
                        total = 0;
                        totalApproved = 0;
                        totalTransfered = 0;

                        total += Double.valueOf(proposedBudget.getRest_value());
                        totalApproved += Double.valueOf(proposedBudget.getRest_value());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            proposedBudgetDetails.add(new ProposedBudgetDetail(jsonArray.getJSONObject(i)));

                            total += jsonArray.getJSONObject(i).getDouble("quantity") * jsonArray.getJSONObject(i).getDouble("unit_price");
                            if (jsonArray.getJSONObject(i).getString("advance_app3").toLowerCase().contains("Approved".toLowerCase()))
                                totalApproved += jsonArray.getJSONObject(i).getDouble("quantity") * jsonArray.getJSONObject(i).getDouble("unit_price");
                        }

                        totalTransfered = totalApproved - Double.valueOf(proposedBudget.getRest_value());

                        textTotal.setText("Rp. " + formatter.format((long) total));
                        textTotalApproved.setText("Rp. " + formatter.format((long) totalApproved));
                        textTotalTransfered.setText("Rp. " + formatter.format((long) totalTransfered));

                        adapter = new MyRecyclerViewAdapter(proposedBudgetDetails, context);
                        recyclerView.setAdapter(adapter);

                        changeColor();
                        approval = 0;
                    } else {
                        Toast.makeText(DetailProposedBudgetActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(DetailProposedBudgetActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailProposedBudgetActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("id", "" + proposedBudget.getCash_advance_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailProposedBudgetActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<ProposedBudgetDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<ProposedBudgetDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_proposed_budget_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+2;
            holder.textNo.setText("" + nomor);
            holder.textItem.setText(mValues.get(position).getItem_name());
            holder.textQty.setText(mValues.get(position).getQuantity() + " " + mValues.get(position).getUnit_abbr());
            holder.textApproval1.setText(mValues.get(position).getAdvance_app1());
            holder.textApproval2.setText(mValues.get(position).getAdvance_app2());
            holder.textApproval3.setText(mValues.get(position).getAdvance_app3());

            toDouble = Double.valueOf(mValues.get(position).getUnit_price());
            holder.textPrice.setText("Rp. " + formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getUnit_price())*Double.valueOf(mValues.get(position).getQuantity());
            holder.textSubTotal.setText("Rp. " + formatter.format((long) toDouble));

            if (position%2==0)
                holder.layout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.layout.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            if (mValues.size() == position+1){
//                textTotalTransfered.setText("Rp. " + formatter.format(totalTransfered));
//                textTotal.setText("Rp. " + formatter.format(totalTransfered));
            }

            if (approval == 1 && code == 1){
                params =  holder.editApproval1.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                holder.editApproval1.setLayoutParams(params);
                params =  holder.textApproval1.getLayoutParams();
                params.height = 0;
                holder.textApproval1.setLayoutParams(params);
            } else if (approval == 2 && code == 1) {
                params =  holder.editApproval2.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                holder.editApproval2.setLayoutParams(params);
                params =  holder.textApproval2.getLayoutParams();
                params.height = 0;
                holder.textApproval2.setLayoutParams(params);
            } else if (approval == 3 && code == 1){
                params =  holder.editApproval3.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                holder.editApproval3.setLayoutParams(params);
                params =  holder.textApproval3.getLayoutParams();
                params.height = 0;
                holder.textApproval3.setLayoutParams(params);
            }

            String[] approve = {"Approved", "Reject", "-"};
            adapterApproval = new ArrayAdapter<String>(DetailProposedBudgetActivity.this, android.R.layout.simple_spinner_dropdown_item, approve);
            holder.editApproval1.setAdapter(adapterApproval);
            holder.editApproval2.setAdapter(adapterApproval);
            holder.editApproval3.setAdapter(adapterApproval);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView textNo;
            public final TextView textItem;
            public final TextView textQty;
            public final TextView textPrice;
            public final TextView textSubTotal;
            public final TextView textApproval1;
            public final TextView textApproval2;
            public final TextView textApproval3;
            public final Spinner editApproval1;
            public final Spinner editApproval2;
            public final Spinner editApproval3;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textItem = (TextView) itemView.findViewById(R.id.textItem);
                textQty = (TextView) itemView.findViewById(R.id.textQty);
                textPrice = (TextView) itemView.findViewById(R.id.textPrice);
                textSubTotal = (TextView) itemView.findViewById(R.id.textSubTotal);
                textApproval1 = (TextView) itemView.findViewById(R.id.textApproval1);
                textApproval2 = (TextView) itemView.findViewById(R.id.textApproval2);
                textApproval3 = (TextView) itemView.findViewById(R.id.textApproval3);
                editApproval1 = (Spinner) itemView.findViewById(R.id.editApproval1);
                editApproval2 = (Spinner) itemView.findViewById(R.id.editApproval2);
                editApproval3 = (Spinner) itemView.findViewById(R.id.editApproval3);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}