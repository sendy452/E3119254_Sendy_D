package com.example.sharedpreferences;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Deklarasi variable nama dengan Label Nama dari Layout MainActivity */
        TextView nama = findViewById(R.id.tv_namaMain);

        /** Menngatur Label Nama dengan data User yang sedang login dari Preferences */
        nama.setText(Preferences.getLoggedInUser(getBaseContext()));

        /** Menngatur Status dan User yang sedang login menjadi default atau kosong di
         * Data Preferences. Kemudian diarahkan ke LoginActivity */
        findViewById(R.id.button_logoutMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Menghapus Status login saat ini dan kembali ke Login Activity */
                Preferences.clearLoggedInUser(getBaseContext());
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();
            }
        });
    }
}