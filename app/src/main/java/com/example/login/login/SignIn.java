package com.example.login.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.login.MySQL.Account;
import com.example.login.R;
import com.example.login.ButtonNavigation.Main;
import com.example.login.StudentInfo;

public class SignIn extends AppCompatActivity {
    private ImageView visibilityOff, visibility;
    private EditText stuNum, password;
    private Button loginBtn, signUpBtn, forgetPswBtn;
    private CheckBox rememberMe;

    private static final String KEY_ACCOUNT = "account", KEY_PASSWORD = "password", PREF_NAME = "myPref";
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        initView();
        loadSavedSP();

//        注册成功后,把在注册页面填写的信息自动填入登录页面的框中
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String account = bundle.getString("stuNum");
            String password = bundle.getString("password");
            stuNum.setText(account);
            this.password.setText(password);
        }

//        如果重置密码成功,那就重新输入账号密码
        boolean resetPsw = getIntent().getBooleanExtra("resetPsw", false);
        if (resetPsw) {
            deleteSP();
            stuNum.setText("");
            password.setText("");
            rememberMe.setChecked(false);
        }

        visibilityOff.setOnClickListener(view -> {
            togglePasswordVisibility();
        });

        visibility.setOnClickListener(view -> {
            togglePasswordVisibility();
        });


        loginBtn.setOnClickListener(view -> {
            String stuNumText = stuNum.getText().toString().trim();
            String passwordText = password.getText().toString().trim();
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (stuNumText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(SignIn.this, "请输入账号和密码!", Toast.LENGTH_SHORT).show();
                return;
            }

            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setStuNum(stuNumText);

            new Thread(() -> {
                Account account = new Account();
                StudentInfo studentInfo2 = account.searchByStuNum(studentInfo.getStuNum());
                runOnUiThread(() -> {
                    if (networkInfo == null || !networkInfo.isConnected()) {
                        Toast.makeText(SignIn.this, "请连接网络", Toast.LENGTH_SHORT).show();
                    } else {
                        if (studentInfo2 == null) {
                            Toast.makeText(SignIn.this, "学号错误!", Toast.LENGTH_SHORT).show();
                        } else if (!passwordText.equals(studentInfo2.getPassword())) {
                            Toast.makeText(SignIn.this, "密码错误!", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(SignIn.this, Main.class);
                            startActivity(intent);
                            Toast.makeText(SignIn.this, "登陆成功!", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

            }).start();
        });

//        CheckBox先设置监听,再判断是否选中
        rememberMe.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                createSP();
                loadSavedSP();
            } else {
                deleteSP();
            }
        });

        signUpBtn.setOnClickListener(view -> {
            Intent intent = new Intent(SignIn.this, SignUp.class);
            startActivity(intent);
        });

        forgetPswBtn.setOnClickListener(view -> {
            Intent intent = new Intent(SignIn.this, ForgetPassword.class);
            startActivity(intent);
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

    private void initView() {
        visibilityOff = findViewById(R.id.visibilityoff);
        visibility = findViewById(R.id.visibility);
        password = findViewById(R.id.signIn_password);
        loginBtn = findViewById(R.id.loginBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        stuNum = findViewById(R.id.signIn_stuNum);
        rememberMe = findViewById(R.id.rememberMe);
        forgetPswBtn = findViewById(R.id.forgetPswBtn);
        // 初始化SharedPreferences实例
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
    }

    public void deleteSP() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_ACCOUNT);
        editor.remove(KEY_PASSWORD);
        editor.apply();
    }

    public void createSP() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ACCOUNT, stuNum.getText().toString().trim());
        editor.putString(KEY_PASSWORD, password.getText().toString().trim());
        editor.apply();
    }

    private void loadSavedSP() {
        // 检查SharedPreferences中是否存在已保存的账号和密码
        String savedAccount = sharedPreferences.getString(KEY_ACCOUNT, "");
        String savedPassword = sharedPreferences.getString(KEY_PASSWORD, "");

        // 如果存在已保存的账号和密码，自动填充到输入框中
        if (!savedAccount.isEmpty() && !savedPassword.isEmpty()) {
            stuNum.setText(savedAccount);
            password.setText(savedPassword);
            rememberMe.setChecked(true);
        }
    }

}