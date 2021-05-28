package com.example.sharedpreferences;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText mViewUser, mViewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /** Deklarasi variable dengan Form User dan Form Password dari Layout LoginActivity */
        mViewUser = findViewById(R.id.et_emailSignin);
        mViewPassword = findViewById(R.id.et_passwordSignin);

        /** Menjalankan Method razia() Jika tombol SignIn diklik dan tidak
         * ada perubahan atau kesalahan pada saat pengisian Password */
        mViewPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    razia();
                    return true;
                }
                return false;
            }
        });

        /** Menjalankan Method razia() jika tombol SignIn diklik */
        findViewById(R.id.button_signinSignin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                razia();
            }
        });

        /** Menuju ke RegisterActivity jika tombol SignUp diklik */
        findViewById(R.id.button_signupSignin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), RegisterActivity.class));
            }
        });
    }

    /**
     * Menuju MainActivity jika data Status Login saat ini dari Data Preferences bernilai true
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (Preferences.getLoggedInStatus(getBaseContext())) {
            startActivity(new Intent(getBaseContext(), MainActivity.class));
            finish();
        }
    }

    /**
     * Menngecek inputan User dan Password dan Memberikan akses ke MainActivity
     */
    private void razia() {
        /** Mereset semua Error dan fokus menjadi default */
        mViewUser.setError(null);
        mViewPassword.setError(null);
        View fokus = null;
        boolean cancel = false;

        /** Mengambil text dari form Username dan form Password dengan variable baru bertipe String */
        String user = mViewUser.getText().toString();
        String password = mViewPassword.getText().toString();

        /** Jika form Username kosong maka akan menampilkan kondisi yang pertama,
         * apabila form Username terisi namun belum terdaftar maka akan menampilkan kondisi pada else */
        if (TextUtils.isEmpty(user)) {
            mViewUser.setError("This field is required");
            fokus = mViewUser;
            cancel = true;
        } else if (!cekUser(user)) {
            mViewUser.setError("This Username is not found");
            fokus = mViewUser;
            cancel = true;
        }

        /** Jika form Password kosong maka akan menampilkan kondisi yang pertama,
         * apabila form Password terisi namun belum terdaftar maka akan menampilkan kondisi pada else */
        if (TextUtils.isEmpty(password)) {
            mViewPassword.setError("This field is required");
            fokus = mViewPassword;
            cancel = true;
        } else if (!cekPassword(password)) {
            mViewPassword.setError("This password is incorrect");
            fokus = mViewPassword;
            cancel = true;
        }

        /** Jika kondisi cancel true, maka variable fokus akan menampilkan pesan error */
        if (cancel) fokus.requestFocus();
        else masuk();
    }

    /**
     * Menuju ke MainActivity dan Set User dan Status yang sedang login saat ini, di Preferences
     */
    private void masuk() {
        Preferences.setLoggedInUser(getBaseContext(), Preferences.getRegisteredUser(getBaseContext()));
        Preferences.setLoggedInStatus(getBaseContext(), true);
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();
    }

    /**
     * True jika parameter Password sama dengan data Password yang terdaftar dari Preferences
     */
    private boolean cekPassword(String password) {
        return password.equals(Preferences.getRegisteredPass(getBaseContext()));
    }

    /**
     * True jika parameter Username sama dengan data Username yang terdaftar dari Preferences
     */
    private boolean cekUser(String user) {
        return user.equals(Preferences.getRegisteredUser(getBaseContext()));
    }
}