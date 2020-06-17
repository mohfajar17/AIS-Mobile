package com.example.aismobile.Project.JobOrder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aismobile.Config;
import com.example.aismobile.Data.JobOrder;
import com.example.aismobile.Project.ProjectActivity;
import com.example.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JobOrderFragment extends Fragment {

    public Button buatJobOrder;
    public RecyclerView recyclerView;
    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;

    public JobOrderFragment() {
    }

    public static JobOrderFragment newInstance() {
        JobOrderFragment fragment = new JobOrderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Silahkan tunggu...");
        progressDialog.setCancelable(false);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_order, container, false);

        buatJobOrder = (Button) view.findViewById(R.id.buatJobOrder);

        // Set the adapter
        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerJobOrder);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        buatJobOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new BuatJobOrderFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerFragment, fragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });

        loadJobOrder();

        return view;
    }

    private void loadJobOrder() {
        progressDialog.show();
        final List<JobOrder> jobOrders = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_JOB_ORDER_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status=jsonObject.getInt("status");
                            if(status==1){
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for(int i=0;i<jsonArray.length();i++){
                                    jobOrders.add(new JobOrder(jsonArray.getJSONObject(i)));
                                }
                                recyclerView.setAdapter(new JobOrderFragment.MyRecyclerViewAdapter(jobOrders, mListener));
                                progressDialog.dismiss();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Filed load data", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Filed load data", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Network is broken, please check your network", Toast.LENGTH_LONG).show();
                    }
                }){
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==10 && resultCode == getActivity().RESULT_OK ){
            loadJobOrder();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(JobOrder item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<JobOrder> mValues;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<JobOrder> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_job_order_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.JONomor.setText(""+mValues.get(position).getJob_order_number());
            holder.JODepartemen.setText(""+mValues.get(position).getDepartment_id());
            holder.JOPic.setText(""+mValues.get(position).getSupervisor());
            holder.JOTipeJob.setText(""+mValues.get(position).getJob_order_type());
            holder.JOKeterangan.setText(""+mValues.get(position).getJob_order_description());
            holder.JONilai.setText(""+mValues.get(position).getAmount());
            holder.JOStatus.setText(""+mValues.get(position).getJob_order_status());

            if (position%2==0)
                holder.layoutJobOrderColor.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.layoutJobOrderColor.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {}
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TextView JONomor;
            public final TextView JODepartemen;
            public final TextView JOPic;
            public final TextView JOTipeJob;
            public final TextView JOKeterangan;
            public final TextView JONilai;
            public final TextView JOStatus;
            public final LinearLayout layoutJobOrderColor;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                JONomor = (TextView) view.findViewById(R.id.JONomor);
                JODepartemen = (TextView) view.findViewById(R.id.JODepartemen);
                JOPic = (TextView) view.findViewById(R.id.JOPic);
                JOTipeJob = (TextView) view.findViewById(R.id.JOTipeJob);
                JOKeterangan = (TextView) view.findViewById(R.id.JOKeterangan);
                JONilai = (TextView) view.findViewById(R.id.JONilai);
                JOStatus = (TextView) view.findViewById(R.id.JOStatus);
                layoutJobOrderColor = (LinearLayout) view.findViewById(R.id.layoutJobOrderColor);
            }
        }
    }
}
