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
import com.example.aismobile.Data.Project.ProposedBudget;
import com.example.aismobile.Data.Project.ProposedBudgetDetail;
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

    private int totalTransfered;
    private int totalApproved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_proposed_budget);

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

        textNumber = (TextView) findViewById(R.id.textNumber);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);

        textNumber.setText(proposedBudget.getCash_advance_number());
        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);
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

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_PROPOSE_BUDGET_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            proposedBudgetDetails.add(new ProposedBudgetDetail(jsonArray.getJSONObject(i)));

                            double quantity = jsonArray.getJSONObject(i).getDouble("quantity");
                            double price = jsonArray.getJSONObject(i).getDouble("unit_price");

                            totalTransfered += (int) quantity * (int) price;
                        }
                        double restValue = Double.valueOf(proposedBudget.getRest_value());
                        totalApproved = totalTransfered + (int) restValue;
                        adapter = new MyRecyclerViewAdapter(proposedBudgetDetails, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DetailProposedBudgetActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DetailProposedBudgetActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
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

            NumberFormat formatter = new DecimalFormat("#,###");
            double toDouble = 0;
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

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}