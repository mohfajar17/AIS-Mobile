package com.example.aismobile.Finance.Budgeting;

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
import com.example.aismobile.Data.FinanceAccounting.Budgeting;
import com.example.aismobile.Data.FinanceAccounting.BudgetingDetail;
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

public class DetailBudgetingActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private Budgeting budgeting;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<BudgetingDetail> budgetingDetails;
    private ProgressDialog progressDialog;

    private TextView textNumber;
    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuHistory;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutHistory;

    private TextView textBudgetNumber;
    private TextView textStartDate;
    private TextView textCheckedBy;
    private TextView textCheckedDate;
    private TextView textApproval1;
    private TextView textApproval1Date;
    private TextView textComment1;
    private TextView textApproval2;
    private TextView textApproval2Date;
    private TextView textComment2;
    private TextView textApproval3;
    private TextView textApproval3Date;
    private TextView textComment3;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;
    private TextView textGrandTotal;

    private double toDouble;
    private double grandTotal;
    private NumberFormat formatter;

    private int code = 0, approval = 0, akses1 = 0, akses2 = 0, akses3 = 0, loadApproval = 0;
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
        setContentView(R.layout.activity_detail_budgeting);

        Bundle bundle = getIntent().getExtras();
        budgeting = bundle.getParcelable("detail");
        code = bundle.getInt("code");
        sharedPrefManager = new SharedPrefManager(this);

        formatter = new DecimalFormat("#,###");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        budgetingDetails = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDetail);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        layoutApproval = (LinearLayout) findViewById(R.id.layoutApproval);
        if (code > 0){
            params = layoutApproval.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutApproval.setLayoutParams(params);
            loadAccess();
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
                if (approval != 1 && textApproval1.getText().toString().matches("-")){
                    btnApprove1.setBackgroundResource(R.drawable.circle_red);
                    approval = 1;
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(budgetingDetails, context);
                    recyclerView.setAdapter(adapter);
                } else approval = 0;
            }
        });
        btnApprove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                if (approval != 2 && textApproval2.getText().toString().matches("-")){
                    btnApprove2.setBackgroundResource(R.drawable.circle_red);
                    approval = 2;
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(budgetingDetails, context);
                    recyclerView.setAdapter(adapter);
                } else approval = 0;
            }
        });
        btnApprove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                if (approval != 3 && textApproval3.getText().toString().matches("-")){
                    btnApprove3.setBackgroundResource(R.drawable.circle_red);
                    approval = 3;
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(budgetingDetails, context);
                    recyclerView.setAdapter(adapter);
                } else approval = 0;
            }
        });
        btnSaveApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date dateObj = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (approval == 1 && akses1 > 0){
                    if (budgeting.getChecked_by().matches("-") ||
                            budgeting.getDone().toLowerCase().contains("Ya".toLowerCase()))
                        Toast.makeText(DetailBudgetingActivity.this, "You are not able to approve because it has not been Checking", Toast.LENGTH_LONG).show();
                    else {
                        for (int i = 0; i<budgetingDetails.size(); i++) {
                            if (i == budgetingDetails.size()-1)
                                loadApproval = 1;
                            updateApproval(String.valueOf(budgetingDetails.get(i).getBudget_detail_id()),
                                    ((Spinner) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.editApproval1)).getSelectedItem().toString(),
                                    ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval2)).getText().toString(),
                                    ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval3)).getText().toString());
                        }
                        updateApprovalId();
                        textApproval1.setText(sharedPrefManager.getUserDisplayName());
                        textApproval1Date.setText(dateFormater.format(dateObj));
                        textComment1.setText(editCommand.getText().toString());
                    }
                } else if (approval == 2 && akses2 > 0){
                    if (budgeting.getChecked_by().matches("-") ||
                            budgeting.getDone().toLowerCase().contains("Ya".toLowerCase()) ||
                            budgeting.getApproval1().matches("-"))
                        Toast.makeText(DetailBudgetingActivity.this, "You are not able to approve because it has not been Checking", Toast.LENGTH_LONG).show();
                    else {
                        for (int i = 0; i<budgetingDetails.size(); i++) {
                            if (i == budgetingDetails.size()-1)
                                loadApproval = 1;
                            updateApproval(String.valueOf(budgetingDetails.get(i).getBudget_detail_id()),
                                    ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval1)).getText().toString(),
                                    ((Spinner) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.editApproval2)).getSelectedItem().toString(),
                                    ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval3)).getText().toString());
                        }
                        updateApprovalId();
                        textApproval2.setText(sharedPrefManager.getUserDisplayName());
                        textApproval2Date.setText(dateFormater.format(dateObj));
                        textComment2.setText(editCommand.getText().toString());
                    }
                } else if (approval == 3 && akses3 > 0){
                    if (budgeting.getChecked_by().matches("-") ||
                            budgeting.getDone().toLowerCase().contains("Ya".toLowerCase()) ||
                            budgeting.getApproval1().matches("-") ||
                            budgeting.getApproval2().matches("-"))
                        Toast.makeText(DetailBudgetingActivity.this, "You are not able to approve because it has not been Checking", Toast.LENGTH_LONG).show();
                    else {
                        for (int i = 0; i<budgetingDetails.size(); i++) {
                            if (i == budgetingDetails.size()-1)
                                loadApproval = 1;
                            updateApproval(String.valueOf(budgetingDetails.get(i).getBudget_detail_id()),
                                    ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval1)).getText().toString(),
                                    ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval2)).getText().toString(),
                                    ((Spinner) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.editApproval3)).getSelectedItem().toString());
                        }
                        updateApprovalId();
                        textApproval3.setText(sharedPrefManager.getUserDisplayName());
                        textApproval3Date.setText(dateFormater.format(dateObj));
                        textComment3.setText(editCommand.getText().toString());
                    }
                } else Toast.makeText(DetailBudgetingActivity.this, "You don't have access to approve", Toast.LENGTH_LONG).show();
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

        textBudgetNumber = (TextView) findViewById(R.id.textBudgetNumber);
        textStartDate = (TextView) findViewById(R.id.textStartDate);
        textCheckedBy = (TextView) findViewById(R.id.textCheckedBy);
        textCheckedDate = (TextView) findViewById(R.id.textCheckedDate);
        textApproval1 = (TextView) findViewById(R.id.textApproval1);
        textApproval1Date = (TextView) findViewById(R.id.textApproval1Date);
        textComment1 = (TextView) findViewById(R.id.textComment1);
        textApproval2 = (TextView) findViewById(R.id.textApproval2);
        textApproval2Date = (TextView) findViewById(R.id.textApproval2Date);
        textComment2 = (TextView) findViewById(R.id.textComment2);
        textApproval3 = (TextView) findViewById(R.id.textApproval3);
        textApproval3Date = (TextView) findViewById(R.id.textApproval3Date);
        textComment3 = (TextView) findViewById(R.id.textComment3);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);
        textGrandTotal = (TextView) findViewById(R.id.textGrandTotal);

        textBudgetNumber.setText(budgeting.getBudget_number());
        textStartDate.setText(budgeting.getStart_date());
        textCheckedBy.setText(budgeting.getChecked_by());
        textCheckedDate.setText(budgeting.getChecked_date());
        textApproval1.setText(budgeting.getApproval1());
        textApproval1Date.setText(budgeting.getApproval_date1());
        textComment1.setText(budgeting.getApproval_comment1());
        textApproval2.setText(budgeting.getApproval2());
        textApproval2Date.setText(budgeting.getApproval_date2());
        textComment2.setText(budgeting.getApproval_comment2());
        textApproval3.setText(budgeting.getApproval3());
        textApproval3Date.setText(budgeting.getApproval_date3());
        textComment3.setText(budgeting.getApproval_comment3());
        textCatatan.setText(budgeting.getNotes());
        textCreatedBy.setText(budgeting.getCreated_by());
        textCreatedDate.setText(budgeting.getCreated_date());
        textModifiedBy.setText(budgeting.getModified_by());
        textModifiedDate.setText(budgeting.getModified_date());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);

        textNumber.setText("Budgeting #" + budgeting.getBudget_number());
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
                    Toast.makeText(DetailBudgetingActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailBudgetingActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user", sharedPrefManager.getUserId());
                param.put("code", "13");
                return param;
            }
        };
        Volley.newRequestQueue(DetailBudgetingActivity.this).add(request);
    }

    public void changeColor(){
        if (textApproval1.getText().toString().matches("-")){
            btnApprove1.setBackgroundResource(R.drawable.circle_green);
            btnApprove2.setBackgroundResource(R.drawable.circle_green);
            btnApprove3.setBackgroundResource(R.drawable.circle_green);
        } else if (textApproval2.getText().toString().matches("-")){
            btnApprove1.setBackgroundResource(R.drawable.circle_blue_new);
            btnApprove2.setBackgroundResource(R.drawable.circle_green);
            btnApprove3.setBackgroundResource(R.drawable.circle_green);
        } else if (textApproval3.getText().toString().matches("-")){
            btnApprove1.setBackgroundResource(R.drawable.circle_blue_new);
            btnApprove2.setBackgroundResource(R.drawable.circle_blue_new);
            btnApprove3.setBackgroundResource(R.drawable.circle_green);
        } else if (!textApproval1.getText().toString().matches("-") &&
                !textApproval2.getText().toString().matches("-") &&
                !textApproval3.getText().toString().matches("-")){
            btnApprove1.setBackgroundResource(R.drawable.circle_blue_new);
            btnApprove2.setBackgroundResource(R.drawable.circle_blue_new);
            btnApprove3.setBackgroundResource(R.drawable.circle_blue_new);
        } else {
            btnApprove1.setBackgroundResource(R.drawable.circle_green);
            btnApprove2.setBackgroundResource(R.drawable.circle_green);
            btnApprove3.setBackgroundResource(R.drawable.circle_green);
        }
    }

    public void updateApproval(final String id, final String approve1, final String approve2, final String approve3){
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_APPROVAL_BUDGETING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        if (loadApproval == 1) {
                            loadDetail();
                            loadApproval = 0;
                        }
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
        Volley.newRequestQueue(DetailBudgetingActivity.this).add(request);
    }

    public void updateApprovalId(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_ID_APPROVAL_BUDGETING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        if (approval == 1)
                            budgeting.setApproval1(sharedPrefManager.getUserDisplayName());
                        else if (approval == 2)
                            budgeting.setApproval2(sharedPrefManager.getUserDisplayName());
                        else if (approval == 3)
                            budgeting.setApproval3(sharedPrefManager.getUserDisplayName());
                        Toast.makeText(DetailBudgetingActivity.this, "Success update data", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DetailBudgetingActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    Toast.makeText(DetailBudgetingActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailBudgetingActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("id", "" + budgeting.getBudget_id());
                param.put("user", sharedPrefManager.getUserId());
                param.put("command", editCommand.getText().toString() + " ");
                param.put("code", "" + approval);
                return param;
            }
        };
        Volley.newRequestQueue(DetailBudgetingActivity.this).add(request);
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

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_BUDGETING_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        recyclerView.setAdapter(null);
                        budgetingDetails.clear();
                        grandTotal = 0;
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            budgetingDetails.add(new BudgetingDetail(jsonArray.getJSONObject(i)));
                            grandTotal += jsonArray.getJSONObject(i).getDouble("amount");
                        }
                        textGrandTotal.setText("Rp. " + formatter.format((long) grandTotal));

                        adapter = new MyRecyclerViewAdapter(budgetingDetails, context);
                        recyclerView.setAdapter(adapter);

                        changeColor();
                    } else {
                        Toast.makeText(DetailBudgetingActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DetailBudgetingActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailBudgetingActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("budgetId", "" + budgeting.getBudget_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailBudgetingActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<BudgetingDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<BudgetingDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_budgeting_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textKeterangan.setText(mValues.get(position).getDescription());
            holder.textDepartment.setText(mValues.get(position).getDepartment_id());
            holder.textProjectLocation.setText(mValues.get(position).getCompany_workbase_id());
            holder.textToDepartment.setText(mValues.get(position).getTo_department());
            holder.textApproval1.setText(mValues.get(position).getBudget_app1());
            holder.textApproval2.setText(mValues.get(position).getBudget_app2());
            holder.textApproval3.setText(mValues.get(position).getBudget_app3());

            toDouble = Double.valueOf(mValues.get(position).getAmount());
            holder.textNilai.setText("Rp. " + formatter.format((long) toDouble));

            if (position%2==0)
                holder.layout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.layout.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

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
            adapterApproval = new ArrayAdapter<String>(DetailBudgetingActivity.this, android.R.layout.simple_spinner_dropdown_item, approve);
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
            public final TextView textKeterangan;
            public final TextView textDepartment;
            public final TextView textProjectLocation;
            public final TextView textToDepartment;
            public final TextView textNilai;
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
                textKeterangan = (TextView) itemView.findViewById(R.id.textKeterangan);
                textDepartment = (TextView) itemView.findViewById(R.id.textDepartment);
                textProjectLocation = (TextView) itemView.findViewById(R.id.textProjectLocation);
                textToDepartment = (TextView) itemView.findViewById(R.id.textToDepartment);
                textNilai = (TextView) itemView.findViewById(R.id.textNilai);
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