package com.example.sharedpreferences;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText mViewUser, mViewPassword, mViewRepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /** Deklarasi variable dengan form inputan Username, Password, dan Confirm Password
         * dari Layout RegisterActivity */
        mViewUser = findViewById(R.id.et_emailSignup);
        mViewPassword = findViewById(R.id.et_passwordSignup);
        mViewRepassword = findViewById(R.id.et_passwordSignup2);

        /** Menjalankan Method razia() jika tombol SignUp diklik dan tidak
         * ada perubahan atau kesalahan pada saat pengisian Password */
        mViewRepassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    razia();
                    return true;
                }
                return false;
            }
        });

        /** Menjalankan Method razia() jika tombol SignUp diklik */
        findViewById(R.id.button_signupSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                razia();
            }
        });
    }

    /**
     * Mengecek inputan Username dan Password yang kemudian memberikan akses ke MainActivity
     */
    private void razia() {
        /** Mereset semua Error dan fokus menjadi default */
        mViewUser.setError(null);
        mViewPassword.setError(null);
        mViewRepassword.setError(null);
        View fokus = null;
        boolean cancel = false;

        /** Mengambil text dari Form Username, Password, Confirm Ppassword dengan variable bertipe String */
        String repassword = mViewRepassword.getText().toString();
        String user = mViewUser.getText().toString();
        String password = mViewPassword.getText().toString();

        /** Jika form Username kosong maka akan menampilkan kondisi yang pertama,
         * apabila form Username terisi namun sudah ada sebelumnya maka akan menampilkan kondisi pada else */
        if (TextUtils.isEmpty(user)) {
            mViewUser.setError("This field is required");
            fokus = mViewUser;
            cancel = true;
        } else if (cekUser(user)) {
            mViewUser.setError("This Username is already exist");
            fokus = mViewUser;
            cancel = true;
        }

        /** Jika form Password kosong maka akan menampilkan kondisi yang pertama,
         * apabila form Confirm Password terisi dan tidak sesuai pada form Password maka akan menampilkan kondisi pada else */
        if (TextUtils.isEmpty(password)) {
            mViewPassword.setError("This field is required");
            fokus = mViewPassword;
            cancel = true;
        } else if (!cekPassword(password, repassword)) {
            mViewRepassword.setError("This password is incorrect");
            fokus = mViewRepassword;
            cancel = true;
        }

        /** Jika kondisi cancel true, maka variable fokus akan menampilkan pesan error. Jika false, maka
         * Kembali ke LoginActivity dan Set Username dan Password untuk data yang terdaftar */
        if (cancel) {
            fokus.requestFocus();
        } else {
            Preferences.setRegisteredUser(getBaseContext(), user);
            Preferences.setRegisteredPass(getBaseContext(), password);
            finish();
        }
    }

    /**
     * True jika parameter Password sama dengan parameter Confirm Password
     */
    private boolean cekPassword(String password, String repassword) {
        return password.equals(repassword);
    }

    /**
     * True jika parameter Username sama dengan data Username yang terdaftar pada Preferences
     */
    private boolean cekUser(String user) {
        return user.equals(Preferences.getRegisteredUser(getBaseContext()));
    }
}