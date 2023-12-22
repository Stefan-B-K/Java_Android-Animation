package com.istef.triviaquiz.controller;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();
    private static AppController _instance;
    private RequestQueue _requestQueue;


    public static synchronized AppController getInstance() {
        return _instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
    }

    public RequestQueue getRequestQueue() {
        if (_requestQueue == null) {
            _requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return _requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set default tag if empty
        req.setTag(tag.isEmpty() ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(String tag) {
        if (_requestQueue != null) {
            _requestQueue.cancelAll(tag);
        }

    }
}
