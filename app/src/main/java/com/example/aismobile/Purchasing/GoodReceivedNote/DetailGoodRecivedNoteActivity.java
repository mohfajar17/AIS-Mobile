package com.example.aismobile.Purchasing.GoodReceivedNote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.aismobile.Data.Purchasing.GoodReceivedNote;
import com.example.aismobile.Data.Purchasing.GoodRecivedNoteDetail;
import com.example.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailGoodRecivedNoteActivity extends AppCompatActivity {

    private GoodReceivedNote goodReceivedNote;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<GoodRecivedNoteDetail> goodRecivedNoteDetails;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
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

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
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

        loadDetail();
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
            holder.textItem.setText(mValues.get(position).getItem_name());
            holder.textlocation.setText("-");
            holder.textJobOrder.setText("-");
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
}