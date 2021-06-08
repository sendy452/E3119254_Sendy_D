package com.example.apivolley.Util;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class AppController extends Application {
    private static final String TAG = AppController.class.getSimpleName();
    private static AppController instance;
    RequestQueue mRequestQueue;

    public static synchronized AppController getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * Mencoba mengantri untuk mengambil data melalui API
     */
    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * Memproses untuk mengambil data
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * Menampilkan data yang telah diambil melalui API
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * Membatalkan semua proses pengambilan data
     */
    public void cancelAllRequest(Object req) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(req);
        }
    }


}