package com.example.login.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.login.MySQL.Account;
import com.example.login.R;
import com.example.login.StudentInfo;

public class SignUp extends AppCompatActivity {
    private ImageView visibility, visibilityOff;
    private Button btnSignUp;
    private EditText editTextPassword, editTextPassword2, editTextStuNum, editTextPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();


        editTextStuNum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        visibilityOff.setOnClickListener(view -> {
            togglePasswordVisibility();
        });

        visibility.setOnClickListener(view -> {
            togglePasswordVisibility();
        });

        btnSignUp.setOnClickListener(view -> {
            judgeAndExec();
        });


    }

    private void initView() {
        visibility = findViewById(R.id.visibilityImageView);
        visibilityOff = findViewById(R.id.visibilityOffImageView);
        btnSignUp = findViewById(R.id.btnSignUp);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPassword2 = findViewById(R.id.editTextPassword2);
        editTextStuNum = findViewById(R.id.editTextStuNum);
        editTextPhone = findViewById(R.id.editTextPhone);
    }

    private void togglePasswordVisibility() {
        if (visibilityOff.getVisibility() == View.VISIBLE) {
            visibilityOff.setVisibility(View.GONE);
            visibility.setVisibility(View.VISIBLE);
            editTextPassword.setTransformationMethod(null);
        } else {
            visibility.setVisibility(View.GONE);
            visibilityOff.setVisibility(View.VISIBLE);
            editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        editTextPassword.setSelection(editTextPassword.getText().length());
    }

    private void judgeAndExec() {
        int stuNumText = Integer.parseInt(editTextStuNum.getText().toString().trim());
        String phoneNumText = editTextPhone.getText().toString().trim();
        String psw1 = editTextPassword.getText().toString().trim();
        String psw2 = editTextPassword2.getText().toString().trim();

        if (String.valueOf(stuNumText).isEmpty() && psw1.isEmpty() && psw2.isEmpty()) {
            Toast.makeText(SignUp.this, "请输入账号和密码!", Toast.LENGTH_SHORT).show();
        } else if (psw1.isEmpty()) {
            Toast.makeText(SignUp.this, "密码不能为空!", Toast.LENGTH_SHORT).show();
        } else if (String.valueOf(stuNumText).isEmpty()) {
            Toast.makeText(SignUp.this, "账号不能为空!", Toast.LENGTH_SHORT).show();
        } else if (!psw1.equals(psw2)) {
            Toast.makeText(SignUp.this, "密码不一致!", Toast.LENGTH_SHORT).show();
        } else {
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setStuNum(stuNumText);
            studentInfo.setPhone(phoneNumText);
            studentInfo.setPassword(psw1);
            new Thread(() -> {
                Account account = new Account();
                account.insertData(studentInfo);

                runOnUiThread(() -> {
                    Bundle bundle = new Bundle();
                    // 将账号和密码添加到 Bundle 中,key,value
                    bundle.putInt("stuNum", stuNumText);
                    bundle.putString("password", psw1);

                    // 创建一个新的 Intent 对象，并将 Bundle 作为参数传递进去
                    Intent intent = new Intent(SignUp.this, LogIn.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    Toast.makeText(SignUp.this, "注册成功!", Toast.LENGTH_LONG).show();
                });
            }).start();


        }
    }

}