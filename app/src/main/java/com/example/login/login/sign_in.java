package com.example.login.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.R;
import com.example.login.Main;

public class sign_in extends AppCompatActivity {
    private ImageView visibilityOff, visibility;
    private EditText accountText, passwordText;
    private Button loginBtn, signUpBtn;
    private CheckBox rememberMe;
    private TextView forgetPsw;

    private static final String KEY_ACCOUNT = "account", KEY_PASSWORD = "password", PREF_NAME = "myPref";
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        visibilityOff = findViewById(R.id.visibilityoff);
        visibility = findViewById(R.id.visibility);
        passwordText = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        accountText = findViewById(R.id.account);
        rememberMe = findViewById(R.id.rememberMe);
        forgetPsw = findViewById(R.id.forgetPsw);
        Bundle bundle = getIntent().getExtras();
        // 初始化SharedPreferences实例
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // 检查SharedPreferences中是否存在已保存的账号和密码
        String savedAccount = sharedPreferences.getString(KEY_ACCOUNT, "");
        String savedPassword = sharedPreferences.getString(KEY_PASSWORD, "");

        // 如果存在已保存的账号和密码，自动填充到输入框中
        if (!TextUtils.isEmpty(savedAccount) && !TextUtils.isEmpty(savedPassword)) {
            accountText.setText(savedAccount);
            passwordText.setText(savedPassword);
            rememberMe.setChecked(true);
        }


//        注册成功后,把在注册页面填写的信息自动填入登录页面的框中
        if (bundle != null) {
            String account = bundle.getString("stuNum");
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

        loginBtn.setOnClickListener(view -> {
            String account = accountText.getText().toString();
            String password = passwordText.getText().toString();

//            需要多加判断条件
            if (TextUtils.isEmpty(account) && TextUtils.isEmpty(password)) {
                Toast.makeText(sign_in.this, "请输入账号和密码!", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(sign_in.this, "密码不能为空!", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(account)) {
                Toast.makeText(sign_in.this, "账号不能为空!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(sign_in.this, Main.class);
                startActivity(intent);
                Toast.makeText(sign_in.this, "登陆成功", Toast.LENGTH_SHORT).show();
            }
        });

//        CheckBox先设置监听,再判断是否选中
        rememberMe.setOnCheckedChangeListener((compoundButton, checked) -> {
            if (checked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_ACCOUNT, accountText.getText().toString());
                editor.putString(KEY_PASSWORD, passwordText.getText().toString());
                editor.apply();
            }
        });

        signUpBtn.setOnClickListener(view -> {
            Intent intent = new Intent(sign_in.this, sign_up.class);
            startActivity(intent);
        });

        forgetPsw.setOnClickListener(view -> {
            Intent intent = new Intent(sign_in.this, forget_password.class);
            startActivity(intent);
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