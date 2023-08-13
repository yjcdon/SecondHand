package com.example.login.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.MySQL.MySQLTest;
import com.example.login.R;
import com.example.login.ButtonNavigation.Main;
import com.example.login.SQLite.MySQLite;
import com.example.login.Student;

import java.sql.Connection;

public class sign_in extends AppCompatActivity {
    private static final String TAG = "ConnectMySQLTask";
    private ImageView visibilityOff, visibility;
    private EditText stuNum, password;
    private Button loginBtn, signUpBtn, testBtn;
    private CheckBox rememberMe;
    private TextView forgetPsw;

    private static final String KEY_ACCOUNT = "account", KEY_PASSWORD = "password", PREF_NAME = "myPref";
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        intiView();
        // 检查SharedPreferences中是否存在已保存的账号和密码
        String savedAccount = sharedPreferences.getString(KEY_ACCOUNT, "");
        String savedPassword = sharedPreferences.getString(KEY_PASSWORD, "");

        // 如果存在已保存的账号和密码，自动填充到输入框中
        if (!TextUtils.isEmpty(savedAccount) && !TextUtils.isEmpty(savedPassword)) {
            stuNum.setText(savedAccount);
            password.setText(savedPassword);
            rememberMe.setChecked(true);
        }


        Bundle bundle = getIntent().getExtras();
//        注册成功后,把在注册页面填写的信息自动填入登录页面的框中
        if (bundle != null) {
            String account = bundle.getString("stuNum");
            String password = bundle.getString("password");
            stuNum.setText(account);
            this.password.setText(password);
        }

        visibilityOff.setOnClickListener(view -> {
            togglePasswordVisibility();
        });

        visibility.setOnClickListener(view -> {
            togglePasswordVisibility();
        });


        loginBtn.setOnClickListener(view -> {
            //用于查询
            String stuNumText = stuNum.getText().toString().trim();
            //查询后,用于对比
            String passwordText = password.getText().toString().trim();

            if (stuNumText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(sign_in.this, "请输入账号和密码!", Toast.LENGTH_SHORT).show();
                return;
            }

            MySQLite mySQLite = new MySQLite(this);
            Student student = new Student();
            student.setStuNum(stuNumText);

            Student student2 = mySQLite.searchByStuNum(student);

            if (student2 == null) {
                Toast.makeText(sign_in.this, "账号错误!", Toast.LENGTH_SHORT).show();
            } else if (!passwordText.equals(student2.getPassword())) {
                Toast.makeText(sign_in.this, "密码错误!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(sign_in.this, Main.class);
                startActivity(intent);
                Toast.makeText(sign_in.this, "登陆成功!", Toast.LENGTH_SHORT).show();
            }
        });


//        CheckBox先设置监听,再判断是否选中
        rememberMe.setOnCheckedChangeListener((compoundButton, checked) -> {
            if (checked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_ACCOUNT, stuNum.getText().toString().trim());
                editor.putString(KEY_PASSWORD, password.getText().toString().trim());
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

        testBtn.setOnClickListener(view -> {
            new Thread(() -> {
                new MySQLTest();
                try {
                    Connection connection = MySQLTest.getConnect();
                    if (connection != null) {
                        Log.d(TAG, "连接成功");
                    } else {
                        Log.d(TAG, "连接失败");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();

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

    private void intiView() {
        visibilityOff = findViewById(R.id.visibilityoff);
        visibility = findViewById(R.id.visibility);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        stuNum = findViewById(R.id.account);
        rememberMe = findViewById(R.id.rememberMe);
        forgetPsw = findViewById(R.id.forgetPsw);
        testBtn = findViewById(R.id.testBtn);
        // 初始化SharedPreferences实例
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
    }

}