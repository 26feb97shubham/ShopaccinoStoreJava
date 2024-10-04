package com.shopaccino.network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shopaccino.config.AppConfig;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NetworkClass {
    public static void createRequest(
            Context context,
            String endPoint,
            JSONObject jsonParams,
            final VolleyCallback onSuccess,
            final VolleyErrorCallback onError) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                AppConfig.BASE_URL + endPoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onSuccess.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onError.onError(error.toString());
                    }
                }) {

            // Override getHeaders to set custom headers
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer JBo5Fs9iA7KfYaQ7WYSCdvqXIZWLmkfXU4mBAzlS");
                headers.put("Accept-Charset", "utf-8");
                return headers;
            }

            // Override getBodyContentType to set content type as JSON
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            // Override getBody to send the JSON body
            @Override
            public byte[] getBody() {
                try {
                    String requestBody = jsonParams.toString();
                    return requestBody.getBytes("utf-8");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };

        // Set retry policy
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        // Disable caching
        stringRequest.setShouldCache(false);

        // Add the request to the RequestQueue
        requestQueue.add(stringRequest);
    }

    // Interface for success callback
    public interface VolleyCallback {
        void onSuccess(String response);
    }

    // Interface for error callback
    public interface VolleyErrorCallback {
        void onError(String error);
    }
}
