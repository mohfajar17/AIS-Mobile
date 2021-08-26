package com.asukacorp.aismobile.Inventory.Stock;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.Data.Inventory.StockAdjustment;
import com.asukacorp.aismobile.Data.Inventory.StockDetail;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.SharedPrefManager;
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

public class DetailStockActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private StockAdjustment stockAdjustment;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<StockDetail> stockDetails;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuHistory;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutHistory;

    private TextView textAdjustmentNumber;
    private TextView textPenjelasanSingkat;
    private TextView textTglAdjustment;
    private TextView textApprovalBy;
    private TextView textApprovalDate;
    private TextView textApprovalNotes;
    private TextView textTitle;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;
    private TextView textTotal;

    private int code = 0, approval = 0, akses1 = 0;
    private ArrayAdapter<String> adapterApproval;
    private LinearLayout layoutApproval;
    private TextView btnApprove1;
    private TextView btnApprove2;
    private EditText editCommand;
    private TextView btnSaveApprove;
    private FloatingActionButton fabRefresh;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_stock);

        Bundle bundle = getIntent().getExtras();
        stockAdjustment = bundle.getParcelable("detail");
        code = bundle.getInt("code");
        sharedPrefManager = new SharedPrefManager(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        stockDetails = new ArrayList<>();

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
        editCommand = (EditText) findViewById(R.id.editCommand);
        btnSaveApprove = (TextView) findViewById(R.id.btnSaveApprove);
        fabRefresh = (FloatingActionButton) findViewById(R.id.fabRefresh);

        btnApprove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (approval != 1 && textApprovalBy.getText().toString().matches("-")){
                    btnApprove1.setBackgroundResource(R.drawable.circle_red);
                    approval = 1;
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(stockDetails, context);
                    recyclerView.setAdapter(adapter);
                } else {
                    btnApprove1.setBackgroundResource(R.drawable.circle_green);
                    approval = 0;
                }
            }
        });
        btnApprove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (approval != 2){
                    btnApprove2.setBackgroundResource(R.drawable.circle_red);
                    approval = 2;
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(stockDetails, context);
                    recyclerView.setAdapter(adapter);
                } else {
                    btnApprove2.setBackgroundResource(R.drawable.circle_green);
                    approval = 0;
                }
            }
        });
        btnSaveApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date dateObj = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (approval == 1 && akses1 > 0){
//                    if (stockAdjustment.getApproval_by().toLowerCase().contains("-".toLowerCase()))
//                        Toast.makeText(DetailStockActivity.this, "You are not able to approve because it has not been Checking", Toast.LENGTH_LONG).show();
//                    else {
                        updateApprovalId();
                        textApprovalBy.setText(sharedPrefManager.getUserDisplayName());
                        textApprovalDate.setText(dateFormater.format(dateObj));
                        textApprovalNotes.setText(editCommand.getText().toString());
                        btnApprove1.setBackgroundResource(R.drawable.circle_blue_new);
//                    }
                } else if (approval == 2){
                    for (int i = 0; i<stockDetails.size(); i++)
                        updateApproval(String.valueOf(stockDetails.get(i).getStock_adjustment_detail_id()),
                                ((EditText) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.editApproval1)).getText().toString());
                    updateApprovalId();
                    btnApprove2.setBackgroundResource(R.drawable.circle_blue_new);
                } else Toast.makeText(DetailStockActivity.this, "You don't have access to approve", Toast.LENGTH_LONG).show();
            }
        });
        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approval = 0;
                loadDetail();
            }
        });

        textAdjustmentNumber = (TextView) findViewById(R.id.textAdjustmentNumber);
        textPenjelasanSingkat = (TextView) findViewById(R.id.textPenjelasanSingkat);
        textTglAdjustment = (TextView) findViewById(R.id.textTglAdjustment);
        textApprovalBy = (TextView) findViewById(R.id.textApprovalBy);
        textApprovalDate = (TextView) findViewById(R.id.textApprovalDate);
        textApprovalNotes = (TextView) findViewById(R.id.textApprovalNotes);
        textTitle = (TextView) findViewById(R.id.textTitle);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);
        textTotal = (TextView) findViewById(R.id.textTotal);

        textAdjustmentNumber.setText(stockAdjustment.getAdjustment_number());
        textPenjelasanSingkat.setText(stockAdjustment.getShort_description());
        textTglAdjustment.setText(stockAdjustment.getAdjustment_date());
        textApprovalBy.setText(stockAdjustment.getApproval_by());
        textApprovalDate.setText(stockAdjustment.getApproval_date());
        textApprovalNotes.setText(stockAdjustment.getApproval_notes());
        textTitle.setText("Stock Adjustment " + stockAdjustment.getShort_description());
        textCatatan.setText(stockAdjustment.getNotes());
        textCreatedBy.setText(stockAdjustment.getCreated_by());
        textCreatedDate.setText(stockAdjustment.getCreated_date());
        textModifiedBy.setText(stockAdjustment.getModified_by());
        textModifiedDate.setText(stockAdjustment.getModified_date());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);

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
                } catch (JSONException e) {
                    Toast.makeText(DetailStockActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailStockActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user", sharedPrefManager.getUserId());
                param.put("code", "12");
                return param;
            }
        };
        Volley.newRequestQueue(DetailStockActivity.this).add(request);
    }

    public void updateApproval(final String id, final String approve1){
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_APPROVAL_STOCK_ADJUSMENT, new Response.Listener<String>() {
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
                param.put("command", approve1);
                return param;
            }
        };
        Volley.newRequestQueue(DetailStockActivity.this).add(request);
    }

    public void updateApprovalId(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_ID_APPROVAL_STOCK_ADJUSMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        approval = 0;
                        loadDetail();
                        Toast.makeText(DetailStockActivity.this, "Success update data", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DetailStockActivity.this, "Filed update data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    Toast.makeText(DetailStockActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailStockActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("id", "" + stockAdjustment.getStock_adjustment_id());
                param.put("user", sharedPrefManager.getUserId());
                param.put("command", editCommand.getText().toString() + " ");
                return param;
            }
        };
        Volley.newRequestQueue(DetailStockActivity.this).add(request);
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

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_STOCK_ADJUSMENT_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        stockDetails.clear();
                        recyclerView.setAdapter(null);
                        double toDouble = 0;
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            stockDetails.add(new StockDetail(jsonArray.getJSONObject(i)));
                            toDouble += jsonArray.getJSONObject(i).getDouble("adjustment_value") * jsonArray.getJSONObject(i).getDouble("unit_price");
                        }
                        NumberFormat formatter = new DecimalFormat("#,###");
                        textTotal.setText("Rp. " + formatter.format((long) toDouble));

                        adapter = new MyRecyclerViewAdapter(stockDetails, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DetailStockActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DetailStockActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailStockActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("saId", "" + stockAdjustment.getStock_adjustment_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailStockActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<StockDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<StockDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_stock_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.textItem.setText(mValues.get(position).getItem_name());
            holder.textSpesifikasi.setText(mValues.get(position).getItem_specification());
            holder.textStockSekarang.setText(mValues.get(position).getCurrent_stock());
            holder.textStockAktual.setText(mValues.get(position).getActual_stock());
            holder.textPenyesuaian.setText(mValues.get(position).getAdjustment_value());
            holder.textCatatan.setText(mValues.get(position).getNotes());

            double toDouble;
            NumberFormat formatter = new DecimalFormat("#,###");
            toDouble = Double.valueOf(mValues.get(position).getUnit_price());
            holder.textHargaSatuan.setText("Rp. " + formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getUnit_price())*Double.valueOf(mValues.get(position).getAdjustment_value());
            holder.textSubTotal.setText("Rp. " + formatter.format((long) toDouble));

            if (position%2==0)
                holder.layout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.layout.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            if (approval == 2 && code == 1){
                params =  holder.editApproval1.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                holder.editApproval1.setLayoutParams(params);
                params =  holder.textCatatan.getLayoutParams();
                params.height = 0;
                holder.textCatatan.setLayoutParams(params);
            }
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView textItem;
            public final TextView textSpesifikasi;
            public final TextView textStockSekarang;
            public final TextView textStockAktual;
            public final TextView textPenyesuaian;
            public final TextView textHargaSatuan;
            public final TextView textSubTotal;
            public final TextView textCatatan;
            public final EditText editApproval1;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textItem = (TextView) itemView.findViewById(R.id.textItem);
                textSpesifikasi = (TextView) itemView.findViewById(R.id.textSpesifikasi);
                textStockSekarang = (TextView) itemView.findViewById(R.id.textStockSekarang);
                textStockAktual = (TextView) itemView.findViewById(R.id.textStockAktual);
                textPenyesuaian = (TextView) itemView.findViewById(R.id.textPenyesuaian);
                textHargaSatuan = (TextView) itemView.findViewById(R.id.textHargaSatuan);
                textSubTotal = (TextView) itemView.findViewById(R.id.textSubTotal);
                textCatatan = (TextView) itemView.findViewById(R.id.textCatatan);
                editApproval1 = (EditText) itemView.findViewById(R.id.editApproval1);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}