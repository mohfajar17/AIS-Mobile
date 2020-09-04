package com.example.aismobile.Project.WorkRequests;

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
import com.example.aismobile.Data.Project.WorkOrder;
import com.example.aismobile.Data.Project.WorkOrderDetail;
import com.example.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailWorkReqActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private WorkOrder workOrder;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<WorkOrderDetail> workOrderDetails;
    private ProgressDialog progressDialog;
    private int totalNilai = 0;

    private TextView textNumber;
    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuHistory;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutHistory;

    private TextView textWoDescript;
    private TextView textJobOrder;
    private TextView textKeterangan;
    private TextView textTglAwal;
    private TextView textTglAkhir;
    private TextView textCheckedBy;
    private TextView textCheckedDate;
    private TextView textCheckedComment;
    private TextView textApproval1;
    private TextView textApproval2;
    private TextView textApproval3;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;
    private TextView textGrandTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_work_req);

        Bundle bundle = getIntent().getExtras();
        workOrder = bundle.getParcelable("detail");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        workOrderDetails = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        textWoDescript = (TextView) findViewById(R.id.textWoDescript);
        textJobOrder = (TextView) findViewById(R.id.textJobOrder);
        textKeterangan = (TextView) findViewById(R.id.textKeterangan);
        textTglAwal = (TextView) findViewById(R.id.textTglAwal);
        textTglAkhir = (TextView) findViewById(R.id.textTglAkhir);
        textCheckedBy = (TextView) findViewById(R.id.textCheckedBy);
        textCheckedDate = (TextView) findViewById(R.id.textCheckedDate);
        textCheckedComment = (TextView) findViewById(R.id.textCheckedComment);
        textApproval1 = (TextView) findViewById(R.id.textApproval1);
        textApproval2 = (TextView) findViewById(R.id.textApproval2);
        textApproval3 = (TextView) findViewById(R.id.textApproval3);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);
        textGrandTotal = (TextView) findViewById(R.id.textGrandTotal);

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);

        textNumber.setText(workOrder.getWork_order_number());
        textWoDescript.setText(workOrder.getWork_order_description());
        textJobOrder.setText(workOrder.getJob_order_id());
        textKeterangan.setText(workOrder.getJob_order_description());
        textTglAwal.setText(workOrder.getBegin_date());
        textTglAkhir.setText(workOrder.getEnd_date());
        textCheckedBy.setText(workOrder.getChecked_by());
        textCheckedDate.setText(workOrder.getChecked_date());
        textCheckedComment.setText(workOrder.getChecked_comment());
        textApproval1.setText(workOrder.getApproval1() + "\n" + workOrder.getApproval_date1() + "\n" + workOrder.getApproval_comment1());
        textApproval2.setText(workOrder.getApproval2() + "\n" + workOrder.getApproval_date2() + "\n" + workOrder.getApproval_comment2());
        textApproval3.setText(workOrder.getApproval3() + "\n" + workOrder.getApproval_date3() + "\n" + workOrder.getApproval_comment3());
        textCatatan.setText(workOrder.getNotes());
        textCreatedBy.setText(workOrder.getCreated_by());
        textCreatedDate.setText(workOrder.getCreated_date());
        textModifiedBy.setText(workOrder.getModified_by());
        textModifiedDate.setText(workOrder.getModified_date());

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

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_WORK_ORDER_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            workOrderDetails.add(new WorkOrderDetail(jsonArray.getJSONObject(i)));

                            double toDouble = (jsonArray.getJSONObject(i).getDouble("quantity")*jsonArray.getJSONObject(i).getDouble("unit_price"))-jsonArray.getJSONObject(i).getDouble("discount");
                            totalNilai += (long) toDouble;
                        }
                        adapter = new MyRecyclerViewAdapter(workOrderDetails, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DetailWorkReqActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DetailWorkReqActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailWorkReqActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("workOrderId", "" + workOrder.getWork_order_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailWorkReqActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<WorkOrderDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<WorkOrderDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_work_req_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textItem.setText(mValues.get(position).getItem_name());
            holder.textKeterangan.setText(mValues.get(position).getWo_notes());
            holder.textJumlah.setText(mValues.get(position).getQuantity()+" "+mValues.get(position).getUnit_abbr());
            holder.textSubTotal.setText(mValues.get(position).getUnit_price());
            holder.textApproval1.setText(mValues.get(position).getWo_app1());
            holder.textApproval2.setText(mValues.get(position).getWo_app2());
            holder.textApproval3.setText(mValues.get(position).getWo_app3());

            NumberFormat formatter = new DecimalFormat("#,###");
            double toDouble = 0;
            toDouble = Double.valueOf(mValues.get(position).getUnit_price());
            holder.textUnitPrice.setText("Rp. " + formatter.format((long) toDouble));
            toDouble = (Double.valueOf(mValues.get(position).getUnit_price())*Double.valueOf(mValues.get(position).getQuantity()))-Double.valueOf(mValues.get(position).getDiscount());
            holder.textSubTotal.setText("Rp. " + formatter.format((long) toDouble));

            if (position%2==0)
                holder.layout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.layout.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            if (position+1 == mValues.size())
                textGrandTotal.setText("Rp. " + formatter.format((long) totalNilai));
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
            public final TextView textJumlah;
            public final TextView textUnitPrice;
            public final TextView textSubTotal;
            public final TextView textApproval1;
            public final TextView textApproval2;
            public final TextView textApproval3;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textItem = (TextView) itemView.findViewById(R.id.textItem);
                textKeterangan = (TextView) itemView.findViewById(R.id.textKeterangan);
                textJumlah = (TextView) itemView.findViewById(R.id.textJumlah);
                textUnitPrice = (TextView) itemView.findViewById(R.id.textUnitPrice);
                textSubTotal = (TextView) itemView.findViewById(R.id.textSubTotal);
                textApproval1 = (TextView) itemView.findViewById(R.id.textApproval1);
                textApproval2 = (TextView) itemView.findViewById(R.id.textApproval2);
                textApproval3 = (TextView) itemView.findViewById(R.id.textApproval3);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}