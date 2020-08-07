package com.example.ApkPKL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class login extends AppCompatActivity {

    private final AppCompatActivity activity = login.this;

    private EditText username;
    private EditText pass;
    private Button btn_login;
    private TextView tv_regis;
    private DatabaseHelper myDb;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        username    = findViewById(R.id.username);
        pass        = findViewById(R.id.pass);
        btn_login   = findViewById(R.id.btn_login);
        tv_regis    = findViewById(R.id.register);

        initObjects();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {

                    String Usermame = username.getText().toString();
                    String Password = pass.getText().toString();

                    if (myDb.checkUser(Usermame, Password)) {
                        goToMainActivity();
                    } else {
                        Snackbar.make(btn_login, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    public void goToMainActivity(){
        Intent i = new Intent(activity,MainActivity.class);
        String Email = username.getText().toString();
        i.putExtra("USERNAME", Email);
        emptyInputEditText();
        activity.finish();
        startActivity(i);
    }

    private void initObjects() {
        myDb = new DatabaseHelper(activity);
        user = new User();
    }

    public boolean validate() {
        boolean valid = false;

        String user = username.getText().toString();
        String Password = pass.getText().toString();

        if (user.isEmpty()) {
            valid = false;
            username.setError("Please enter username!");
        } else {
            valid = true;
            username.setError(null);
        }

        if (Password.isEmpty()) {
            valid = false;
            pass.setError("Please enter valid password!");
        } else {
            if (Password.length() >= 6) {
                valid = true;
                pass.setError(null);
            } else {
                valid = false;
                pass.setError("Password is to short!");
            }
        }

        return valid;
    }

    private void emptyInputEditText() {
        username.setText(null);
        pass.setText(null);
    }
}