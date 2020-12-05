package com.example.qunlsinhvin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername,etPassword;
    private CheckBox cbRemember;
    private Button btnLogin;
    private Boolean rememberMe=false;

    private SharedPreferences preferences;
    private static final String key="account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        initView();

        readAccount();

    }

    private void readAccount() {
        if(preferences != null){
            String username = preferences.getString("username",null);
            String password = preferences.getString("password",null);
            Boolean remember = preferences.getBoolean("remember",false);
            if(remember == true){
                etPassword.setText(password);
                etUsername.setText(username);
                cbRemember.setChecked(true);
            }
        }
    }

    private void login() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if(username.equals("admin") && password.equals("admin")){
            //save account in .xml
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("username",username);
            editor.putString("password",password);
            editor.putBoolean("remember",rememberMe);
            editor.commit();
            editor.apply();
            Intent intent= new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
        }

    }

    private void initView() {
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        
        cbRemember= (CheckBox) findViewById(R.id.cb_remember);
        btnLogin = (Button) findViewById(R.id.btn_login);

        preferences = getSharedPreferences(key,MODE_PRIVATE);

        cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rememberMe == false) rememberMe = true;
                else rememberMe = false;
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
}