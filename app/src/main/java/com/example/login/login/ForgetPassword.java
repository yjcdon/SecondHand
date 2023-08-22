package com.example.login.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.MySQL.Account;
import com.example.login.R;
import com.example.login.StudentInfo;

import java.util.Objects;

public class ForgetPassword extends AppCompatActivity {
    private EditText editTextPhone, editTextStuNum, editTextNewPsw, editTextNewPsw2;
    private Button btnResetPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initView();
        limitNumberLength();

        btnResetPsw.setOnClickListener(view -> {
            String psw1 = editTextNewPsw.getText().toString().trim();
            String psw2 = editTextNewPsw2.getText().toString().trim();
            String phoneText = editTextPhone.getText().toString().trim();
            int stuNumText = Integer.parseInt(editTextStuNum.getText().toString().trim());

//            手机号输入要有其他限制
            if (Objects.equals(stuNumText, "")) {
                Toast.makeText(ForgetPassword.this, "请输入学号!", Toast.LENGTH_SHORT).show();
            } else if (phoneText.isEmpty()) {
                Toast.makeText(ForgetPassword.this, "请输入手机号!", Toast.LENGTH_SHORT).show();
            } else if (!Objects.equals(psw1, psw2)) {
                Toast.makeText(ForgetPassword.this, "请输入相同的密码!", Toast.LENGTH_SHORT).show();
            } else {
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setStuNum(stuNumText);
                studentInfo.setPhone(phoneText);
                studentInfo.setPassword(psw2);

                new Thread(() -> {
                    Account account = new Account();
                    account.updatePswByStuNumAndPhone(studentInfo);
                    runOnUiThread(() -> {
                        Toast.makeText(ForgetPassword.this, "重置密码成功!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgetPassword.this, LogIn.class);
                        intent.putExtra("resetPsw", true);
                        startActivity(intent);
                    });
                }).start();
            }
        });

    }

    private void limitNumberLength() {
        //        设置最多输入6位验证码
        editTextStuNum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        //        11位手机号
        editTextPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
    }

    private void initView() {
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextStuNum = findViewById(R.id.editTextStuNum);
        editTextNewPsw = findViewById(R.id.editTextNewPsw);
        editTextNewPsw2 = findViewById(R.id.editTextNewPsw2);
        btnResetPsw = findViewById(R.id.btnResetPsw);
    }

}