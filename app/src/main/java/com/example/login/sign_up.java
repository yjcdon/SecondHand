package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class sign_up extends AppCompatActivity {
    private ImageView visibility;
    private ImageView visibilityOff;
    private Button signupBtn;
    private EditText password;
    private EditText account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        visibility = findViewById(R.id.visibilityImageView);
        visibilityOff = findViewById(R.id.visibilityOffImageView);
        signupBtn = findViewById(R.id.sign_up_btn);
        password = findViewById(R.id.editTextTextPassword);
        account = findViewById(R.id.editTextText);

        visibilityOff.setOnClickListener(view -> {
            togglePasswordVisibility();
        });

        visibility.setOnClickListener(view -> {
            togglePasswordVisibility();
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accountText = account.getText().toString();
                String passwordText = password.getText().toString();

                if (TextUtils.isEmpty(accountText) && TextUtils.isEmpty(passwordText)) {
                    Toast.makeText(sign_up.this, "请输入账号和密码!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(passwordText)) {
                    Toast.makeText(sign_up.this, "密码不能为空!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(accountText)) {
                    Toast.makeText(sign_up.this, "账号不能为空!", Toast.LENGTH_SHORT).show();
                } else {
                    Bundle bundle = new Bundle();
                    // 将账号和密码添加到 Bundle 中
                    bundle.putString("account", accountText);
                    bundle.putString("password", passwordText);

                    // 创建一个新的 Intent 对象，并将 Bundle 作为参数传递进去
                    Intent intent = new Intent(sign_up.this, sign_in.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    Toast.makeText(sign_up.this, "注册成功!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void togglePasswordVisibility() {
        if (visibilityOff.getVisibility() == View.VISIBLE) {
            visibilityOff.setVisibility(View.GONE);
            visibility.setVisibility(View.VISIBLE);
            password.setTransformationMethod(null);
        } else {
            visibility.setVisibility(View.GONE);
            visibilityOff.setVisibility(View.VISIBLE);
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        password.setSelection(password.getText().length());
    }
}