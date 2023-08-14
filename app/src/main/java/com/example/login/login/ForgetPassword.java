package com.example.login.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.MySQL.MySQL;
import com.example.login.R;
import com.example.login.Student;

import java.util.Objects;

public class ForgetPassword extends AppCompatActivity {
    private EditText phoneNum, stuNum, newPsw1, newPsw2;
    private Button cancelBtn, resetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);

        initView();
        limitNumberLength();

        resetBtn.setOnClickListener(view -> {
            String psw1 = newPsw1.getText().toString().trim();
            String psw2 = newPsw2.getText().toString().trim();
            String phoneText = phoneNum.getText().toString().trim();
            String stuNumText = stuNum.getText().toString().trim();

//            手机号输入要有其他限制
            if (Objects.equals(stuNumText, "")) {
                Toast.makeText(ForgetPassword.this, "请输入学号!", Toast.LENGTH_SHORT).show();
            } else if (phoneText.isEmpty()) {
                Toast.makeText(ForgetPassword.this, "请输入手机号!", Toast.LENGTH_SHORT).show();
            } else if (!Objects.equals(psw1, psw2)) {
                Toast.makeText(ForgetPassword.this, "请输入相同的密码!", Toast.LENGTH_SHORT).show();
            } else {
                Student student = new Student();
                student.setStuNum(stuNumText);
                student.setPhone(phoneText);
                student.setPassword(psw2);

                new Thread(() -> {
                    MySQL mySQL = new MySQL();
                    mySQL.updatePswByStuNumAndPhone(student);
                    runOnUiThread(() -> {
                        Toast.makeText(ForgetPassword.this, "重置密码成功!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgetPassword.this, SignIn.class);
                        startActivity(intent);
                    });
                }).start();
            }
        });

        cancelBtn.setOnClickListener(view -> {
            Intent intent = new Intent(ForgetPassword.this, SignIn.class);
            startActivity(intent);
        });
    }

    private void limitNumberLength() {
        //        设置最多输入6位验证码
        stuNum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        //        11位手机号
        phoneNum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
    }

    private void initView() {
        phoneNum = findViewById(R.id.phoneNum);
        stuNum = findViewById(R.id.stuNum);
        newPsw1 = findViewById(R.id.newPsw1);
        newPsw2 = findViewById(R.id.newPsw2);
        resetBtn = findViewById(R.id.resetPswBtn);
        cancelBtn = findViewById(R.id.cancelResetPswBtn);
    }

}