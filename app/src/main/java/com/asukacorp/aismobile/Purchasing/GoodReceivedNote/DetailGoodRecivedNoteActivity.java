package com.asukacorp.aismobile.Purchasing.GoodReceivedNote;

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
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.Data.Purchasing.GoodReceivedNote;
import com.asukacorp.aismobile.Data.Purchasing.GoodRecivedNoteDetail;
import com.asukacorp.aismobile.Data.Purchasing.GoodRecivedNoteSi;
import com.asukacorp.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailGoodRecivedNoteActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private GoodReceivedNote goodReceivedNote;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<GoodRecivedNoteDetail> goodRecivedNoteDetails;
    private RecyclerView recyclerViewSi;
    private MySiRecyclerViewAdapter adapterSi;
    private List<GoodRecivedNoteSi> goodRecivedNoteSis;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuHistory;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutHistory;

    private TextView textGrnNumber;
    private TextView textTglPenerimaan;
    private TextView textPurchaseOrders;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_good_recived_note);

        Bundle bundle = getIntent().getExtras();
        goodReceivedNote = bundle.getParcelable("detail");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();

        goodRecivedNoteDetails = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDetail);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        goodRecivedNoteSis = new ArrayList<>();
        recyclerViewSi = (RecyclerView) findViewById(R.id.recyclerViewSi);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerViewSi.setLayoutManager(recylerViewLayoutManager);

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);

        textGrnNumber = (TextView) findViewById(R.id.textGrnNumber);
        textTglPenerimaan = (TextView) findViewById(R.id.textTglPenerimaan);
        textPurchaseOrders = (TextView) findViewById(R.id.textPurchaseOrders);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textGrnNumber.setText(goodReceivedNote.getGrn_number());
        textTglPenerimaan.setText(goodReceivedNote.getReceipt_date());
        textPurchaseOrders.setText(goodReceivedNote.getPurchase_order_id());
        textCatatan.setText(goodReceivedNote.getNotes());
        textCreatedBy.setText(goodReceivedNote.getCreated_by());
        textCreatedDate.setText(goodReceivedNote.getCreated_date());
        textModifiedBy.setText(goodReceivedNote.getModified_by());
        textModifiedDate.setText(goodReceivedNote.getModified_date());

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
        loadSi();
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

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_GRN_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            goodRecivedNoteDetails.add(new GoodRecivedNoteDetail(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(goodRecivedNoteDetails, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DetailGoodRecivedNoteActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DetailGoodRecivedNoteActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailGoodRecivedNoteActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("grn_id", "" + goodReceivedNote.getGrn_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailGoodRecivedNoteActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<GoodRecivedNoteDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<GoodRecivedNoteDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_good_recived_note_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textItem.setText(mValues.get(position).getItem_name() + " | " + mValues.get(position).getItem_specification());
            holder.textlocation.setText(mValues.get(position).getWarehouse_name());
            holder.textJobOrder.setText(mValues.get(position).getJob_order_number());
            holder.textPurchaseRequest.setText(mValues.get(position).getQuantity_received() + " " + mValues.get(position).getUnit_abbr());
            holder.textReceived.setText(mValues.get(position).getQuantity_received() + " " + mValues.get(position).getUnit_abbr());
            holder.textReceiving.setText(mValues.get(position).getQuantity_received() + " " + mValues.get(position).getUnit_abbr());
            holder.textnotes.setText(mValues.get(position).getNotes());

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
            public final TextView textlocation;
            public final TextView textJobOrder;
            public final TextView textPurchaseRequest;
            public final TextView textReceived;
            public final TextView textReceiving;
            public final TextView textnotes;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textItem = (TextView) itemView.findViewById(R.id.textItem);
                textlocation = (TextView) itemView.findViewById(R.id.textlocation);
                textJobOrder = (TextView) itemView.findViewById(R.id.textJobOrder);
                textPurchaseRequest = (TextView) itemView.findViewById(R.id.textPurchaseRequest);
                textReceived = (TextView) itemView.findViewById(R.id.textReceived);
                textReceiving = (TextView) itemView.findViewById(R.id.textReceiving);
                textnotes = (TextView) itemView.findViewById(R.id.textnotes);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }

    public void loadSi(){
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_PURCHASING_DETAIL_SI_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            goodRecivedNoteSis.add(new GoodRecivedNoteSi(jsonArray.getJSONObject(i)));
                        }
                        adapterSi = new MySiRecyclerViewAdapter(goodRecivedNoteSis, context);
                        recyclerViewSi.setAdapter(adapterSi);
                    } else {
                        Toast.makeText(DetailGoodRecivedNoteActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(DetailGoodRecivedNoteActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailGoodRecivedNoteActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("tabel", "sid.grn_id");
                param.put("id", "" + goodReceivedNote.getGrn_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailGoodRecivedNoteActivity.this).add(request);
    }

    private class MySiRecyclerViewAdapter extends RecyclerView.Adapter<MySiRecyclerViewAdapter.ViewHolder> {

        private final List<GoodRecivedNoteSi> mValues;
        private Context context;

        private MySiRecyclerViewAdapter(List<GoodRecivedNoteSi> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_good_recived_note_si, parent, false);
            return new MySiRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MySiRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.textNomorSi.setText(mValues.get(position).getSupplier_invoice_number());
            holder.textSupplier.setText(mValues.get(position).getSupplier_name());
            holder.textPaymentDate.setText(mValues.get(position).getSupplier_invoice_date());
            holder.textStatus.setText(mValues.get(position).getSupplier_invoice_status());

            double toDouble;
            NumberFormat formatter = new DecimalFormat("#,###");
            toDouble = Double.valueOf(mValues.get(position).getAmount());
            holder.textNilai.setText("" + formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getDiscount());
            holder.textDiscount.setText("" + formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getPpn());
            holder.textPPN.setText("" + formatter.format((long) toDouble));

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
            public final TextView textNomorSi;
            public final TextView textSupplier;
            public final TextView textNilai;
            public final TextView textDiscount;
            public final TextView textPPN;
            public final TextView textPaymentDate;
            public final TextView textStatus;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNomorSi = (TextView) itemView.findViewById(R.id.textNomorSi);
                textSupplier = (TextView) itemView.findViewById(R.id.textSupplier);
                textNilai = (TextView) itemView.findViewById(R.id.textNilai);
                textDiscount = (TextView) itemView.findViewById(R.id.textDiscount);
                textPPN = (TextView) itemView.findViewById(R.id.textPPN);
                textPaymentDate = (TextView) itemView.findViewById(R.id.textPaymentDate);
                textStatus = (TextView) itemView.findViewById(R.id.textStatus);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}