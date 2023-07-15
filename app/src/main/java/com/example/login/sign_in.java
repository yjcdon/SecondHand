package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class sign_in extends AppCompatActivity {
    private ImageView visibilityOff;
    private ImageView visibility;
    private EditText accountText;
    private EditText passwordText;
    private Button loginBtn;
    private Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        visibilityOff = findViewById(R.id.visibilityoff);
        visibility = findViewById(R.id.visibility);
        passwordText = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        signupBtn = findViewById(R.id.signUpBtn);
        accountText = findViewById(R.id.account);
        Bundle bundle = getIntent().getExtras();

//        注册成功后,把在注册页面填写的信息自动填入登录页面的框中
        if (bundle != null) {
            String account = bundle.getString("account");
            String password = bundle.getString("password");
            accountText.setText(account);
            passwordText.setText(password);

        }

        visibilityOff.setOnClickListener(view -> {
            togglePasswordVisibility();
        });

        visibility.setOnClickListener(view -> {
            togglePasswordVisibility();
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(sign_in.this, "登陆成功", Toast.LENGTH_SHORT).show();
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sign_in.this, sign_up.class);
                startActivity(intent);
            }
        });
    }

    private void togglePasswordVisibility() {
        if (visibilityOff.getVisibility() == View.VISIBLE) {
            visibilityOff.setVisibility(View.GONE);
            visibility.setVisibility(View.VISIBLE);
            passwordText.setTransformationMethod(null);
        } else {
            visibility.setVisibility(View.GONE);
            visibilityOff.setVisibility(View.VISIBLE);
            passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        passwordText.setSelection(passwordText.getText().length());
    }

}