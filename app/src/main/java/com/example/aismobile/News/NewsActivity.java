package com.example.aismobile.News;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aismobile.Config;
import com.example.aismobile.Data.News;
import com.example.aismobile.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<News> news;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        news = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerNews);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        
        setDataNews();
    }

    private void setDataNews() {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_NEWS_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status=jsonObject.getInt("status");
                            if(status==1){
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for(int i=0;i<jsonArray.length();i++){
                                    news.add(new News(jsonArray.getJSONObject(i)));
                                }
                                adapter = new MyRecyclerViewAdapter(news, context);
                                recyclerView.setAdapter(adapter);
                            } else {
                                Toast.makeText(NewsActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            Toast.makeText(NewsActivity.this, "Emboh karepmu", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(NewsActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }){
        };
        Volley.newRequestQueue(this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<News> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<News> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_news_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.newsTitle.setText(mValues.get(position).getNews_title());
            holder.newsContents.setText(mValues.get(position).getNews_contents());
            Picasso.get().load(Config.DATA_URL_IMAGE+mValues.get(position).getImage_name()).into(holder.imageNews);

            if (holder.imageNews.getDrawable() == null) {
                holder.imageNews.setImageResource(R.drawable.no_image);
            }

            if (position%2==0)
                holder.layoutNews.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
            else holder.layoutNews.setBackgroundColor(getResources().getColor(R.color.colorWhite));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NewsActivity.this, NewsDetailActivity.class);
                    intent.putExtra("detailNews", mValues.get(position));
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView newsTitle;
            public final TextView newsContents;
            public final ImageView imageNews;
            public final LinearLayout layoutNews;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                newsTitle = (TextView) itemView.findViewById(R.id.newsTitle);
                newsContents = (TextView) itemView.findViewById(R.id.newsContents);
                imageNews = (ImageView) itemView.findViewById(R.id.imageNews);
                layoutNews = (LinearLayout) itemView.findViewById(R.id.layuotNews);
            }
        }
    }
}
