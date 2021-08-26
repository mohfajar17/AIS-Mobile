package com.asukacorp.aismobile.Project.MaterialRequest;

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
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.Data.Project.MaterialRequest;
import com.asukacorp.aismobile.Data.Project.MaterialRequestDetail;
import com.asukacorp.aismobile.Data.Project.MaterialRequestPickup;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailMaterialRequestActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private MaterialRequest materialRequest;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<MaterialRequestDetail> materialRequestDetails;
    private RecyclerView recyclerViewPickup;
    private MyPickupRecyclerViewAdapter adapterPickup;
    private List<MaterialRequestPickup> materialRequestPickups;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ProgressDialog progressDialog;

    private TextView textNumber;
    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuPickup;
    private TextView menuCatatan;
    private TextView menuHistory;
    private ScrollView layoutDetail;
    private LinearLayout layoutPickup;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutHistory;

    private TextView textJobOrder;
    private TextView textKeterangan;
    private TextView textSqNumber;
    private TextView textTglPermintaan;
    private TextView textTglPenggunaan;
    private TextView textApproval1;
    private TextView textApproval2;
    private TextView textApproval3;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

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
        setContentView(R.layout.activity_detail_material_request);

        Bundle bundle = getIntent().getExtras();
        materialRequest = bundle.getParcelable("detail");
        code = bundle.getInt("code");
        sharedPrefManager = new SharedPrefManager(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        materialRequestDetails = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDetail);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        materialRequestPickups = new ArrayList<>();
        recyclerViewPickup = (RecyclerView) findViewById(R.id.recyclerViewPickup);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerViewPickup.setLayoutManager(recylerViewLayoutManager);

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
                if (approval != 1 && materialRequest.getApproval1().matches("-")){
                    btnApprove1.setBackgroundResource(R.drawable.circle_red);
                    approval = 1;
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(materialRequestDetails, context);
                    recyclerView.setAdapter(adapter);
                } else approval = 0;
            }
        });
        btnApprove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                if (approval != 2 && materialRequest.getApproval2().matches("-")){
                    btnApprove2.setBackgroundResource(R.drawable.circle_red);
                    approval = 2;
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(materialRequestDetails, context);
                    recyclerView.setAdapter(adapter);
                } else approval = 0;
            }
        });
        btnApprove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                if (approval != 3 && materialRequest.getApproval3().matches("-")){
                    btnApprove3.setBackgroundResource(R.drawable.circle_red);
                    approval = 3;
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(materialRequestDetails, context);
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
                    materialRequest.setApproval1(sharedPrefManager.getUserDisplayName());
                    for (int i = 0; i<=materialRequestDetails.size(); i++) {
                        if (i == materialRequestDetails.size()) {
                            loadDetail();
                            progressDialog.dismiss();
                        } else updateApproval(String.valueOf(materialRequestDetails.get(i).getMaterial_request_detail_id()),
                                    ((Spinner) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.editApproval1)).getSelectedItem().toString(),
                                    ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval2)).getText().toString(),
                                    ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval3)).getText().toString());
                    }
                    updateApprovalId();
                    textApproval1.setText(sharedPrefManager.getUserDisplayName() + "\n" + dateFormater.format(dateObj) + "\n" + editCommand.getText().toString());
                } else if (approval == 2 && akses2 > 0 && approve2 > 0){
                    progressDialog.show();
                    materialRequest.setApproval2(sharedPrefManager.getUserDisplayName());
                    for (int i = 0; i<=materialRequestDetails.size(); i++) {
                        if (i==materialRequestDetails.size()){
                            loadDetail();
                            progressDialog.dismiss();
                        } else updateApproval(String.valueOf(materialRequestDetails.get(i).getMaterial_request_detail_id()),
                                    ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval1)).getText().toString(),
                                    ((Spinner) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.editApproval2)).getSelectedItem().toString(),
                                    ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval3)).getText().toString());

                    }
                    updateApprovalId();
                    textApproval2.setText(sharedPrefManager.getUserDisplayName() + "\n" + dateFormater.format(dateObj) + "\n" + editCommand.getText().toString());
                } else if (approval == 3 && akses3 > 0 && approve3 > 0){
                    progressDialog.show();
                    materialRequest.setApproval3(sharedPrefManager.getUserDisplayName());
                    for (int i = 0; i<=materialRequestDetails.size(); i++)
                        if (i==materialRequestDetails.size()){
                            loadDetail();
                            progressDialog.dismiss();
                        } else updateApproval(String.valueOf(materialRequestDetails.get(i).getMaterial_request_detail_id()),
                                ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval1)).getText().toString(),
                                ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval2)).getText().toString(),
                                ((Spinner) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.editApproval3)).getSelectedItem().toString());
                    updateApprovalId();
                    textApproval3.setText(sharedPrefManager.getUserDisplayName() + "\n" + dateFormater.format(dateObj) + "\n" + editCommand.getText().toString());
                } else Toast.makeText(DetailMaterialRequestActivity.this, "You don't have access to approve", Toast.LENGTH_LONG).show();
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

        textJobOrder = (TextView) findViewById(R.id.textJobOrder);
        textKeterangan = (TextView) findViewById(R.id.textKeterangan);
        textSqNumber = (TextView) findViewById(R.id.textSqNumber);
        textTglPermintaan = (TextView) findViewById(R.id.textTglPermintaan);
        textTglPenggunaan = (TextView) findViewById(R.id.textTglPenggunaan);
        textApproval1 = (TextView) findViewById(R.id.textApproval1);
        textApproval2 = (TextView) findViewById(R.id.textApproval2);
        textApproval3 = (TextView) findViewById(R.id.textApproval3);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuPickup = (TextView) findViewById(R.id.menuPickup);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutPickup = (LinearLayout) findViewById(R.id.layoutPickup);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);

        textNumber.setText(materialRequest.getMaterial_request_number());
        textJobOrder.setText(materialRequest.getJob_order_id());
        textKeterangan.setText(materialRequest.getJob_order_description());
        textSqNumber.setText(materialRequest.getSales_quotation_id());
        textTglPermintaan.setText(materialRequest.getRequisition_date());
        textTglPenggunaan.setText(materialRequest.getUsage_date());
        textApproval1.setText(materialRequest.getApproval1() + "\n" + materialRequest.getApproval_date1() + "\n" + materialRequest.getComment1());
        textApproval2.setText(materialRequest.getApproval2() + "\n" + materialRequest.getApproval_date2() + "\n" + materialRequest.getComment2());
        textApproval3.setText(materialRequest.getApproval3() + "\n" + materialRequest.getApproval_date3() + "\n" + materialRequest.getComment3());
        textCatatan.setText(materialRequest.getNotes());
        textCreatedBy.setText(materialRequest.getCreated_by());
        textCreatedDate.setText(materialRequest.getCreated_date());
        textModifiedBy.setText(materialRequest.getModified_by());
        textModifiedDate.setText(materialRequest.getModified_date());

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
        menuPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutPickup.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutPickup.setLayoutParams(params);
                menuPickup.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuPickup.setTextColor(getResources().getColor(R.color.colorBlack));
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
        loadPickup();
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
                Toast.makeText(DetailMaterialRequestActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user", sharedPrefManager.getUserId());
                param.put("id", "" + materialRequest.getMaterial_request_id());
                param.put("code", "1");
                return param;
            }
        };
        Volley.newRequestQueue(DetailMaterialRequestActivity.this).add(request);
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
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailMaterialRequestActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user", sharedPrefManager.getUserId());
                param.put("code", "1");
                return param;
            }
        };
        Volley.newRequestQueue(DetailMaterialRequestActivity.this).add(request);
    }

    public void changeColor(){
        if (materialRequest.getApproval1().matches("-")){
            btnApprove1.setBackgroundResource(R.drawable.circle_green);
            btnApprove2.setBackgroundResource(R.drawable.circle_green);
            btnApprove3.setBackgroundResource(R.drawable.circle_green);
        } else if (materialRequest.getApproval2().matches("-")){
            btnApprove1.setBackgroundResource(R.drawable.circle_blue_new);
            btnApprove2.setBackgroundResource(R.drawable.circle_green);
            btnApprove3.setBackgroundResource(R.drawable.circle_green);
        } else if (materialRequest.getApproval3().matches("-")){
            btnApprove1.setBackgroundResource(R.drawable.circle_blue_new);
            btnApprove2.setBackgroundResource(R.drawable.circle_blue_new);
            btnApprove3.setBackgroundResource(R.drawable.circle_green);
        } else if (!materialRequest.getApproval3().matches("-") &&
                !materialRequest.getApproval2().matches("-") &&
                !materialRequest.getApproval3().matches("-")){
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
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_APPROVAL_MATERIAL_REQUEST, new Response.Listener<String>() {
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
                param.put("approve3", approve3);
                return param;
            }
        };
        Volley.newRequestQueue(DetailMaterialRequestActivity.this).add(request);
    }

    public void updateApprovalId(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_ID_APPROVAL_MATERIAL_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Toast.makeText(DetailMaterialRequestActivity.this, "Success update data", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DetailMaterialRequestActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    Toast.makeText(DetailMaterialRequestActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailMaterialRequestActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("id", "" + materialRequest.getMaterial_request_id());
                param.put("user", sharedPrefManager.getUserId());
                param.put("command", editCommand.getText().toString() + " ");
                param.put("code", "" + approval);
                return param;
            }
        };
        Volley.newRequestQueue(DetailMaterialRequestActivity.this).add(request);
    }

    public void loadDetail(){
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_MATERIAL_REQUISITION_DETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        recyclerView.setAdapter(null);
                        materialRequestDetails.clear();

                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            materialRequestDetails.add(new MaterialRequestDetail(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(materialRequestDetails, context);
                        recyclerView.setAdapter(adapter);

                        changeColor();
                        approval = 0;
                    } else {
                        Toast.makeText(DetailMaterialRequestActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(DetailMaterialRequestActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailMaterialRequestActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("materialId", "" + materialRequest.getMaterial_request_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailMaterialRequestActivity.this).add(request);
    }

    private void hiddenLayout(){
        menuDetail.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuDetail.setTextColor(getResources().getColor(R.color.colorWhite));
        menuPickup.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuPickup.setTextColor(getResources().getColor(R.color.colorWhite));
        menuCatatan.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuCatatan.setTextColor(getResources().getColor(R.color.colorWhite));
        menuHistory.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuHistory.setTextColor(getResources().getColor(R.color.colorWhite));

        params = layoutDetail.getLayoutParams(); params.height = 0; layoutDetail.setLayoutParams(params);
        params = layoutPickup.getLayoutParams(); params.height = 0; layoutPickup.setLayoutParams(params);
        params = layoutCatatan.getLayoutParams(); params.height = 0; layoutCatatan.setLayoutParams(params);
        params = layoutHistory.getLayoutParams(); params.height = 0; layoutHistory.setLayoutParams(params);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<MaterialRequestDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<MaterialRequestDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_material_request_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textItem.setText(mValues.get(position).getItem_name() + " | " + mValues.get(position).getItem_specification());
            holder.textKeterangan.setText(mValues.get(position).getNotes());
            holder.textPO.setText(mValues.get(position).getPurchase_order_number());
            holder.textJumlah.setText(mValues.get(position).getQuantity()+" "+mValues.get(position).getUnit_abbr());
            holder.textStock.setText(mValues.get(position).getIs_stock_request());
            holder.textPembelian.setText(mValues.get(position).getQuantity_stock_request());
            holder.textApproval1.setText(mValues.get(position).getPo_app1());
            holder.textApproval2.setText(mValues.get(position).getPo_app2());
            holder.textApproval3.setText(mValues.get(position).getPo_app3());
            holder.textStatus.setText(mValues.get(position).getStatus());
            holder.textStockGudang.setText(mValues.get(position).getStock_charging());

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
            adapterApproval = new ArrayAdapter<String>(DetailMaterialRequestActivity.this, android.R.layout.simple_spinner_dropdown_item, approve);
            holder.editApproval1.setAdapter(adapterApproval);
            holder.editApproval2.setAdapter(adapterApproval);
            holder.editApproval3.setAdapter(adapterApproval);

            if (position%2==0)
                holder.layout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.layout.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView textNo;
            public final TextView textItem;
            public final TextView textKeterangan;
            public final TextView textPO;
            public final TextView textJumlah;
            public final TextView textUnitPrice;
            public final TextView textStock;
            public final TextView textPembelian;
            public final TextView textApproval1;
            public final TextView textApproval2;
            public final TextView textApproval3;
            public final TextView textStatus;
            public final TextView textStockGudang;
            public final Spinner editApproval1;
            public final Spinner editApproval2;
            public final Spinner editApproval3;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textItem = (TextView) itemView.findViewById(R.id.textItem);
                textKeterangan = (TextView) itemView.findViewById(R.id.textKeterangan);
                textPO = (TextView) itemView.findViewById(R.id.textPO);
                textJumlah = (TextView) itemView.findViewById(R.id.textJumlah);
                textUnitPrice = (TextView) itemView.findViewById(R.id.textUnitPrice);
                textStock = (TextView) itemView.findViewById(R.id.textStock);
                textPembelian = (TextView) itemView.findViewById(R.id.textPembelian);
                textApproval1 = (TextView) itemView.findViewById(R.id.textApproval1);
                textApproval2 = (TextView) itemView.findViewById(R.id.textApproval2);
                textApproval3 = (TextView) itemView.findViewById(R.id.textApproval3);
                textStatus = (TextView) itemView.findViewById(R.id.textStatus);
                textStockGudang = (TextView) itemView.findViewById(R.id.textStockGudang);
                editApproval1 = (Spinner) itemView.findViewById(R.id.editApproval1);
                editApproval2 = (Spinner) itemView.findViewById(R.id.editApproval2);
                editApproval3 = (Spinner) itemView.findViewById(R.id.editApproval3);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }

    public void loadPickup(){
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_MATERIAL_REQUISITION_PICKUP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            materialRequestPickups.add(new MaterialRequestPickup(jsonArray.getJSONObject(i)));
                        }
                        adapterPickup = new MyPickupRecyclerViewAdapter(materialRequestPickups, context);
                        recyclerViewPickup.setAdapter(adapterPickup);
                    } else {
                        Toast.makeText(DetailMaterialRequestActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
//                    Toast.makeText(DetailMaterialRequestActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailMaterialRequestActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("materialId", "" + materialRequest.getMaterial_request_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailMaterialRequestActivity.this).add(request);
    }

    private class MyPickupRecyclerViewAdapter extends RecyclerView.Adapter<MyPickupRecyclerViewAdapter.ViewHolder> {

        private final List<MaterialRequestPickup> mValues;
        private Context context;

        private MyPickupRecyclerViewAdapter(List<MaterialRequestPickup> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_material_request_pickup, parent, false);
            return new MyPickupRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyPickupRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.textNomorPengambilan.setText(mValues.get(position).getPickup_number());
            holder.textDiambilOleh.setText(mValues.get(position).getPickup_by());
            holder.textTanggalPengambilan.setText(mValues.get(position).getTaken_date());
            holder.textDiakui.setText(mValues.get(position).getRecognized());

            if (position%2==0)
                holder.layout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.layout.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView textNomorPengambilan;
            public final TextView textDiambilOleh;
            public final TextView textTanggalPengambilan;
            public final TextView textDiakui;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNomorPengambilan = (TextView) itemView.findViewById(R.id.textNomorPengambilan);
                textDiambilOleh = (TextView) itemView.findViewById(R.id.textDiambilOleh);
                textTanggalPengambilan = (TextView) itemView.findViewById(R.id.textTanggalPengambilan);
                textDiakui = (TextView) itemView.findViewById(R.id.textDiakui);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}