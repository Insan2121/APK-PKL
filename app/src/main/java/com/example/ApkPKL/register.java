package com.example.ApkPKL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class register extends AppCompatActivity {

    private final AppCompatActivity activity = register.this;

    private Button btn_reg;
    private EditText text_user;
    private EditText text_email;
    private EditText text_repassw;
    private EditText text_passw;
    private RadioGroup jenis_kelamin;
    private RadioButton gender;
    DatabaseHelper myDb;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        btn_reg =  findViewById(R.id.btn_register);
        text_user = findViewById(R.id.user);
        text_email = findViewById(R.id.email);
        jenis_kelamin = findViewById(R.id.radioButton);
        text_passw = findViewById(R.id.passw);
        text_repassw = findViewById(R.id.repassw);

        initObjects();
        addData();
    }

    public void addData(){
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String UserName = text_user.getText().toString();
                    String Email = text_email.getText().toString();
                    String Password = text_passw.getText().toString();
                    String Jenis_Kelamin = jenisKelamin();

                    if (!myDb.checkUser(Email)) {

                        user.setUsername(UserName);
                        user.setEmail(Email);
                        user.setPassword(Password);
                        user.setJenisKelamin(Jenis_Kelamin);

                        myDb.addUser(user);

                        Snackbar.make(btn_reg, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
                        emptyInputEditText();
                        goToLogin();

                    } else {
                        Snackbar.make(btn_reg, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void initObjects() {
        myDb = new DatabaseHelper(activity);
        user = new User();
    }

    public void goToLogin(){
        Intent i = new Intent(this,login.class);
        this.finish();
        startActivity(i);
    }

    public boolean validate() {
        boolean valid = false;

        String UserName = text_user.getText().toString();
        String Email = text_email.getText().toString();
        String Password = text_passw.getText().toString();
        String RePassword = text_repassw.getText().toString();
        String jenis_kelamin = jenisKelamin();

        if (UserName.isEmpty()) {
            valid = false;
            text_user.setError("Please enter valid username!");
        } else {
            if (UserName.length() > 0) {
                valid = true;
                text_user.setError(null);
            } else {
                valid = false;
                text_user.setError("Username is to short!");
            }
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            text_email.setError("Please enter valid email!");
        } else {
            valid = true;
            text_email.setError(null);
        }

        if (Password.isEmpty()) {
            valid = false;
            text_passw.setError("Please enter valid password!");
        } else {
            if (Password.length() >= 6) {
                valid = true;
                text_passw.setError(null);
            } else {
                valid = false;
                text_passw.setError("Password is to short!");
            }
        }

        if(RePassword.isEmpty()){
            valid = false;
            text_repassw.setError("Please enter re password!");
        } else {
            if (Password.toString().equals(RePassword)) {
                valid = true;
                text_repassw.setError(null);
            } else {
                valid = false;
                text_repassw.setError("Password not same!");
            }
        }

        if(jenis_kelamin.isEmpty()){
            gender.setError("Select Item");
            valid = false;
        }else {
            if (gender.isChecked()) {
                valid = true;
                gender.setError(null);
            } else {
                valid = false;
                gender.setError("Password not same!");
            }
        }

        return valid;
    }

    String jenisKelamin() {
        int selectedId = jenis_kelamin.getCheckedRadioButtonId();
        gender = (RadioButton) findViewById(selectedId);
        return gender.getText().toString();
    }

    private void emptyInputEditText() {
        text_user.setText(null);
        text_email.setText(null);
        text_passw.setText(null);
        text_repassw.setText(null);
    }
}
