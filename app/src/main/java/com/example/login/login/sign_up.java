package com.example.login.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.login.R;

public class sign_up extends AppCompatActivity {
    private ImageView visibility;
    private ImageView visibilityOff;
    private Button signUpBtn;
    private EditText password1, password2;
    private EditText account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        visibility = findViewById(R.id.visibilityImageView);
        visibilityOff = findViewById(R.id.visibilityOffImageView);
        signUpBtn = findViewById(R.id.sign_up_btn);
        password1 = findViewById(R.id.editTextTextPassword);
        password2 = findViewById(R.id.editTextTextPassword2);
        account = findViewById(R.id.editTextText);

        account.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        visibilityOff.setOnClickListener(view -> {
            togglePasswordVisibility();
        });

        visibility.setOnClickListener(view -> {
            togglePasswordVisibility();
        });

        signUpBtn.setOnClickListener(view -> {
            String accountText = account.getText().toString();
            String psw1 = password1.getText().toString();
            String psw2 = password2.getText().toString();

            if (accountText.isEmpty() && psw1.isEmpty() && psw2.isEmpty()) {
                Toast.makeText(sign_up.this, "请输入账号和密码!", Toast.LENGTH_SHORT).show();
            } else if (psw1.isEmpty()) {
                Toast.makeText(sign_up.this, "密码不能为空!", Toast.LENGTH_SHORT).show();
            } else if (accountText.isEmpty()) {
                Toast.makeText(sign_up.this, "账号不能为空!", Toast.LENGTH_SHORT).show();
            } else if (!psw1.equals(psw2)) {
                Toast.makeText(sign_up.this, "密码不一致!", Toast.LENGTH_SHORT).show();
            } else {
                Bundle bundle = new Bundle();
                // 将账号和密码添加到 Bundle 中,key,value
                bundle.putString("account", accountText);
                bundle.putString("password", psw1);

                // 创建一个新的 Intent 对象，并将 Bundle 作为参数传递进去
                Intent intent = new Intent(sign_up.this, sign_in.class);
                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(sign_up.this, "注册成功!", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void togglePasswordVisibility() {
        if (visibilityOff.getVisibility() == View.VISIBLE) {
            visibilityOff.setVisibility(View.GONE);
            visibility.setVisibility(View.VISIBLE);
            password1.setTransformationMethod(null);
        } else {
            visibility.setVisibility(View.GONE);
            visibilityOff.setVisibility(View.VISIBLE);
            password1.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        password1.setSelection(password1.getText().length());
    }
}