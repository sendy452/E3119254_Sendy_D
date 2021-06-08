package com.example.apivolley;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.apivolley.Adapter.AdapterData;
import com.example.apivolley.Model.ModelData;
import com.example.apivolley.Util.AppController;
import com.example.apivolley.Util.ServerAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerview;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    List<ModelData> mItems;
    Button btnInsert, btnDelete;
    ProgressDialog pd;

    /**
     * Deklarasi widget dan layout pada activity_main
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerview = findViewById(R.id.recyclerviewTemp);
        btnInsert = findViewById(R.id.btn_insert);
        btnDelete = findViewById(R.id.btn_delete);
        pd = new ProgressDialog(MainActivity.this);
        mItems = new ArrayList<>();

        loadJson();

        mManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter = new AdapterData(MainActivity.this, mItems);
        mRecyclerview.setAdapter(mAdapter);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            /** Apabila tombol Insert di klik maka akan mengarah ke kelas InsertData */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InsertData.class);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            /** Apabila tombol Delete di klik maka akan mengarah ke kelas Delete */
            @Override
            public void onClick(View view) {
                Intent hapus = new Intent(MainActivity.this, Delete.class);
                startActivity(hapus);
            }
        });
    }

    /**
     * Memuat kumpulan data Json dari database melalui API
     */
    private void loadJson() {
        pd.setMessage("Mengambil Data");
        pd.setCancelable(false);
        pd.show();

        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.POST, ServerAPI.URL_DATA, null, new Response.Listener<JSONArray>() {
            /** Apabila sukses memuat maka akan menjalankan function onResponse dan menampilkan keseluruhan data */
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("Length: " + response.length());
                pd.cancel();
                Log.d("volley", "response : " + response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject data = response.getJSONObject(i);
                        ModelData md = new ModelData();
                        md.setUsername(data.getString("username"));
                        md.setGrup(data.getString("grup"));
                        md.setNama(data.getString("nama"));
                        md.setPassword(data.getString("password"));
                        mItems.add(md);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Length: " + response.length());
                mAdapter.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    /** Apabila gagal memuat maka akan menjalankan onErrorResponse */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Log.d("volley", "error : " + error.getMessage());
                    }
                });
        AppController.getInstance().addToRequestQueue(reqData);
    }
}