package com.example.apivolley;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class Delete extends AppCompatActivity {
    EditText deleteID;
    Button btnDelete;
    ProgressDialog pd;

    /**
     * Deklarasi widget yang ada pada layout activity_delete
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        deleteID = findViewById(R.id.username_param);
        btnDelete = findViewById(R.id.btn_delete);
        pd = new ProgressDialog(Delete.this);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            /** Apabila di klik akan menjalankan function deleteData()*/
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });
    }

    /**
     * Membuat function untuk menghapus data
     */
    private void deleteData() {
        pd.setMessage("Delete Data ...");
        pd.setCancelable(false);
        pd.show();

        StringRequest delReq = new StringRequest(Request.Method.POST, ServerAPI.URL_DELETE,
                new Response.Listener<String>() {
                    /** Apabila data berhasil dihapus akan menjalankan method onResponse*/
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        Log.d("volley", "response : " + response);
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(Delete.this, "pesan : " + res.getString("message"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        startActivity(new Intent(Delete.this, MainActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    /** Apabila gagal menghapus data akan menjalakan function onErrorResponse */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Log.d("volley", "error : " + error.getMessage());
                        Toast.makeText(Delete.this, "pesan : gagal menghapus data", Toast.LENGTH_SHORT).show();
                    }
                }) {
            /** Menghapus data sesuai dengan value yang diinputkan yaitu username*/
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("username", deleteID.getText().toString());
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(delReq);
    }
}