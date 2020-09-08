package com.example.aismobile.Project.MaterialRequest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aismobile.Config;
import com.example.aismobile.Data.Project.MaterialRequest;
import com.example.aismobile.Data.Project.MaterialRequestDetail;
import com.example.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailMaterialRequestActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private MaterialRequest materialRequest;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<MaterialRequestDetail> materialRequestDetails;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_material_request);

        Bundle bundle = getIntent().getExtras();
        materialRequest = bundle.getParcelable("detail");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        materialRequestDetails = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

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
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_MATERIAL_REQUISITION_DETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            materialRequestDetails.add(new MaterialRequestDetail(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(materialRequestDetails, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DetailMaterialRequestActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DetailMaterialRequestActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
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

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}