package com.asukacorp.aismobile;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AppService extends Service {

    private Handler handler = new Handler();
    private Runnable refresh;
    private SharedPrefManager sharedPrefManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        refresh = new Runnable() {
//            public void run() {
//                load();
//                handler.postDelayed(refresh, 30000); //set 1000ms = 1s
//            }
//        };
//        handler.post(refresh);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void load(){
        sharedPrefManager = SharedPrefManager.getInstance(this);
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_USER_ISMOBILE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if(status==1){
                        JSONObject jsonData = jsonObject.getJSONObject("data");
                        if (jsonData.getString("is_mobile").toLowerCase().contains("2".toLowerCase())){
                            Intent logout = new Intent(AppService.this, LoginActivity.class);
                            startActivity(logout);
                            sharedPrefManager.logout();
                        }
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
                param.put("user_id", sharedPrefManager.getUserId());
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}
