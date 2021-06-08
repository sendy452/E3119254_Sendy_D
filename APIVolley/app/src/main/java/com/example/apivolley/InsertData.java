package com.example.apivolley;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.apivolley.Util.AppController;
import com.example.apivolley.Util.ServerAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class InsertData extends AppCompatActivity {
    EditText username, grup, nama, password;
    Button btnbatal, btnsimpan;
    ProgressDialog pd;

    /**
     * Deklarasi widget dan layout pada activity_insert_data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);

        /** Mengambil data dari MainActivity */
        Intent data = getIntent();
        final int update = data.getIntExtra("update", 0);
        String intent_username = data.getStringExtra("username");
        String intent_grup = data.getStringExtra("grup");
        String intent_nama = data.getStringExtra("nama");
        String intent_password = data.getStringExtra("password");

        username = findViewById(R.id.inp_username);
        grup = findViewById(R.id.inp_grup);
        nama = findViewById(R.id.inp_nama);
        password = findViewById(R.id.inp_password);
        btnbatal = findViewById(R.id.btn_cancel);
        btnsimpan = findViewById(R.id.btn_simpan);
        pd = new ProgressDialog(InsertData.this);

        /** Apabila kondisi update terpenuhi atau =1 maka akan menampilkan detail data*/
        if (update == 1) {
            btnsimpan.setText("Update Data");
            username.setText(intent_username);
            username.setVisibility(View.GONE);
            grup.setText(intent_grup);
            nama.setText(intent_nama);
            password.setText(intent_password);

        }
        btnsimpan.setOnClickListener(new View.OnClickListener() {
            /** Tombol simpan akan menjalankan function Update_data apabila kondisi update == 1
             *  jika tidak maka menjalankan function simpanData */
            @Override
            public void onClick(View view) {
                if (update == 1) {
                    Update_data();
                } else {
                    simpanData();
                }
            }
        });

        btnbatal.setOnClickListener(new View.OnClickListener() {
            /** Jika memilih tombol batal maka akan kembali menuju kelas MainActivity*/
            @Override
            public void onClick(View view) {
                Intent main = new Intent(InsertData.this, MainActivity.class);
                startActivity(main);
            }
        });
    }

    /**
     * Membuat function untuk menyimpan data yang telah dirubah, sesuai dengan kondisi username yang dipilih
     */
    private void Update_data() {
        pd.setMessage("Update Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest updateReq = new StringRequest(Request.Method.POST, ServerAPI.URL_UPDATE, new Response.Listener<String>() {
            /** Menjalankan function onResponse apabila berhasil merubah data*/
            @Override
            public void onResponse(String response) {
                pd.cancel();
                try {
                    JSONObject res = new JSONObject(response);
                    Toast.makeText(InsertData.this, "pesan : " + res.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                startActivity(new Intent(InsertData.this, MainActivity.class));
            }
        },
                new Response.ErrorListener() {
                    /** Menjalankan function onErrorResponse apabila gagal atau error saat merubah data*/
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(InsertData.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }) {
            /** Mengirim data sesuai dengan inputan */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("username", username.getText().toString());
                map.put("grup", grup.getText().toString());
                map.put("nama", nama.getText().toString());
                map.put("password", password.getText().toString());

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(updateReq);
    }

    /**
     * Function untuk menyimpan data baru yang telah dibuat
     */
    private void simpanData() {
        pd.setMessage("Menyimpan Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest sendData = new StringRequest(Request.Method.POST, ServerAPI.URL_INSERT, new Response.Listener<String>() {
            /** Menjalankan function onResponse apabila berhasil menyimpan data*/
            @Override
            public void onResponse(String response) {
                pd.cancel();
                try {
                    JSONObject res = new JSONObject(response);
                    Toast.makeText(InsertData.this, "pesan : " + res.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                startActivity(new Intent(InsertData.this, MainActivity.class));
            }
        },
                new Response.ErrorListener() {
                    /** Menjalankan function onResponse apabila gagal menyimpan data*/
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(InsertData.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }) {
            /** Mengirim data sesuai dengan inputan */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("username", username.getText().toString());
                map.put("grup", grup.getText().toString());
                map.put("nama", nama.getText().toString());
                map.put("password", password.getText().toString());

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sendData);
    }
}