package com.example.aismobile.Purchasing.PurchaseOrder;

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
import com.example.aismobile.Data.Purchasing.PurchaseOrder;
import com.example.aismobile.Data.Purchasing.PurchaseOrderDetail;
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

public class DetailPurchaseOrderActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private PurchaseOrder proposedBudget;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<PurchaseOrderDetail> proposedBudgetDetails;
    private ProgressDialog progressDialog;

    private TextView textNumber;
    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuHistory;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutHistory;

    private TextView textJenisPembelian;
    private TextView textContract;
    private TextView textSupplier;
    private TextView textNomorPenawaran;
    private TextView textTglPenawaran;
    private TextView textTglAwal;
    private TextView textTglKedatangan;
    private TextView textTerminPembayaran;
    private TextView textPaymentDesc;
    private TextView textPersetujuan;
    private TextView textJenisPajak;
    private TextView textCheckedBy;
    private TextView textCheckedDate;
    private TextView textApproval1;
    private TextView textApproval1Date;
    private TextView textPoComment;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

    private TextView textTotal;
    private TextView textBudget;
    private TextView textDiscount;
    private TextView textEfisiensi;
    private TextView textDPP;
    private TextView textPajak;
    private TextView textGrandTotal;

    private double total;
    private double budget;
    private double discount;
    private double efisiensi;
    private double dpp;
    private double pajak;
    private double grandTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_purchase_order);

        Bundle bundle = getIntent().getExtras();
        proposedBudget = bundle.getParcelable("detail");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        proposedBudgetDetails = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        textTotal = (TextView) findViewById(R.id.textTotal);
        textBudget = (TextView) findViewById(R.id.textBudget);
        textDiscount = (TextView) findViewById(R.id.textDiscount);
        textEfisiensi = (TextView) findViewById(R.id.textEfisiensi);
        textDPP = (TextView) findViewById(R.id.textDPP);
        textPajak = (TextView) findViewById(R.id.textPajak);
        textGrandTotal = (TextView) findViewById(R.id.textGrandTotal);

        textJenisPembelian = (TextView) findViewById(R.id.textJenisPembelian);
        textContract = (TextView) findViewById(R.id.textContract);
        textSupplier = (TextView) findViewById(R.id.textSupplier);
        textNomorPenawaran = (TextView) findViewById(R.id.textNomorPenawaran);
        textTglPenawaran = (TextView) findViewById(R.id.textTglPenawaran);
        textTglAwal = (TextView) findViewById(R.id.textTglAwal);
        textTglKedatangan = (TextView) findViewById(R.id.textTglKedatangan);
        textTerminPembayaran = (TextView) findViewById(R.id.textTerminPembayaran);
        textPaymentDesc = (TextView) findViewById(R.id.textPaymentDesc);
        textPersetujuan = (TextView) findViewById(R.id.textPersetujuan);
        textJenisPajak = (TextView) findViewById(R.id.textJenisPajak);
        textCheckedBy = (TextView) findViewById(R.id.textCheckedBy);
        textCheckedDate = (TextView) findViewById(R.id.textCheckedDate);
        textApproval1 = (TextView) findViewById(R.id.textApproval1);
        textApproval1Date = (TextView) findViewById(R.id.textApproval1Date);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textPoComment = (TextView) findViewById(R.id.textPoComment);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textJenisPembelian.setText(proposedBudget.getPurchase_order_type_id());
        textContract.setText(proposedBudget.getContract_agreement_id());
        textSupplier.setText(proposedBudget.getSupplier_id());
        textNomorPenawaran.setText(proposedBudget.getPurchase_quotation_number());
        textTglPenawaran.setText(proposedBudget.getPurchase_quotation_date());
        textTglAwal.setText(proposedBudget.getBegin_date());
        textTglKedatangan.setText(proposedBudget.getEnd_date());
        textTerminPembayaran.setText(proposedBudget.getPayment_term_id());
        textPaymentDesc.setText(proposedBudget.getPayment_desc());
        textPersetujuan.setText(proposedBudget.getApproval_assign_id());
        textJenisPajak.setText(proposedBudget.getTax_type_id());
        textCheckedBy.setText(proposedBudget.getChecked_by());
        textCheckedDate.setText(proposedBudget.getChecked_date());
        textApproval1.setText(proposedBudget.getPo_approval1());
        textApproval1Date.setText(proposedBudget.getPo_approval_date1());
        textCatatan.setText(proposedBudget.getNotes());
        textPoComment.setText(proposedBudget.getPo_comment1());
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

        textNumber.setText(proposedBudget.getPurchase_order_number());
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

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_PURCHASE_ORDER_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            proposedBudgetDetails.add(new PurchaseOrderDetail(jsonArray.getJSONObject(i)));

                            total += (jsonArray.getJSONObject(i).getDouble("quantity")*jsonArray.getJSONObject(i).getDouble("unit_price_buy"))-jsonArray.getJSONObject(i).getDouble("discount");
                            budget += jsonArray.getJSONObject(i).getDouble("quantity")*jsonArray.getJSONObject(i).getDouble("max_budget");
                            discount += jsonArray.getJSONObject(i).getDouble("discount");
                        }
                        efisiensi = budget - total;
                        dpp = total;
                        pajak = total*10/100;
                        grandTotal = dpp+pajak;

                        adapter = new MyRecyclerViewAdapter(proposedBudgetDetails, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DetailPurchaseOrderActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DetailPurchaseOrderActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailPurchaseOrderActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("po_id", "" + proposedBudget.getPurchase_order_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailPurchaseOrderActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<PurchaseOrderDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<PurchaseOrderDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_purchase_order_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textItem.setText(mValues.get(position).getItem_name());
            holder.textSpecification.setText(mValues.get(position).getItem_specification());
            holder.textQty.setText(mValues.get(position).getQuantity() + " " + mValues.get(position).getUnit_abbr());
            holder.textApproval1.setText(mValues.get(position).getPo_app1());

            NumberFormat formatter = new DecimalFormat("#,###");
            holder.textBudget.setText("Rp. " + formatter.format(Integer.valueOf(mValues.get(position).getMax_budget())));
            holder.textDiscount.setText("Rp. " + formatter.format(Integer.valueOf(mValues.get(position).getDiscount())));
            holder.textPrice.setText("Rp. " + formatter.format(Integer.valueOf(mValues.get(position).getUnit_price_buy())));
            int subTotal = (Integer.valueOf(mValues.get(position).getUnit_price_buy())*Integer.valueOf(mValues.get(position).getQuantity()))-Integer.valueOf(mValues.get(position).getDiscount());
            holder.textSubTotal.setText("Rp. " + formatter.format(subTotal));

            if (position%2==0)
                holder.layout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.layout.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            if (mValues.size() == position+1){
                textTotal.setText("Rp. " + formatter.format((long) total));
                textBudget.setText("Rp. " + formatter.format((long) budget));
                textDiscount.setText("Rp. " + formatter.format((long) discount));
                textEfisiensi.setText("Rp. " + formatter.format((long) efisiensi));
                textDPP.setText("Rp. " + formatter.format((long) dpp));
                textPajak.setText("Rp. " + formatter.format((long) pajak));
                textGrandTotal.setText("Rp. " + formatter.format((long) grandTotal));
            }
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView textNo;
            public final TextView textItem;
            public final TextView textSpecification;
            public final TextView textQty;
            public final TextView textPrice;
            public final TextView textDiscount;
            public final TextView textBudget;
            public final TextView textSubTotal;
            public final TextView textApproval1;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textItem = (TextView) itemView.findViewById(R.id.textItem);
                textSpecification = (TextView) itemView.findViewById(R.id.textSpecification);
                textQty = (TextView) itemView.findViewById(R.id.textQty);
                textPrice = (TextView) itemView.findViewById(R.id.textPrice);
                textDiscount = (TextView) itemView.findViewById(R.id.textDiscount);
                textBudget = (TextView) itemView.findViewById(R.id.textBudget);
                textSubTotal = (TextView) itemView.findViewById(R.id.textSubTotal);
                textApproval1 = (TextView) itemView.findViewById(R.id.textApproval1);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}