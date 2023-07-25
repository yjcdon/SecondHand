package com.example.login.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.R;
import com.example.login.SQLite.MySQLite;
import com.example.login.Student;

import java.util.Locale;
import java.util.Objects;

public class forget_password extends AppCompatActivity {
    private EditText phoneNum, verificationCode, newPsw1, newPsw2;
    private TextView sendCode;
    private Button cancelBtn, resetBtn;
    private boolean isSendCode = false;  // 记录是否已经发送过验证码
    private CountDownTimer countDownTimer;
    private final long COUNTDOWN_TIME = 30000;  // 倒计时时间，单位为毫秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);

        phoneNum = findViewById(R.id.phoneNum);
        verificationCode = findViewById(R.id.verificationCode);
        newPsw1 = findViewById(R.id.newPsw1);
        newPsw2 = findViewById(R.id.newPsw2);
        sendCode = findViewById(R.id.sendCode);
        resetBtn = findViewById(R.id.resetPswBtn);
        cancelBtn = findViewById(R.id.cancelResetPswBtn);

        limitNumberLength();

        resetBtn.setOnClickListener(view -> {
            String psw1 = newPsw1.getText().toString().trim();
            String psw2 = newPsw2.getText().toString().trim();
            String phoneText = phoneNum.getText().toString().trim();
            String verCode = verificationCode.getText().toString().trim();

//            手机号输入要有其他限制
            if (phoneText.isEmpty()) {
                Toast.makeText(forget_password.this, "请输入手机号!", Toast.LENGTH_SHORT).show();
            } else if (Objects.equals(verCode, "")) {
                Toast.makeText(forget_password.this, "请输入验证码!", Toast.LENGTH_SHORT).show();
//                第三方API
            } else if (!Objects.equals(verCode, "123")) {
                Toast.makeText(forget_password.this, "验证码错误!", Toast.LENGTH_SHORT).show();
            } else if (!Objects.equals(psw1, psw2)) {
                Toast.makeText(forget_password.this, "请输入相同的密码!", Toast.LENGTH_SHORT).show();
            } else {
                MySQLite mySQLite = new MySQLite(this);
                Student student = new Student();
                student.setPhone(phoneText);
                student.setPassword(psw2);
                mySQLite.updateToPsw(student);

                Toast.makeText(forget_password.this, "重置密码成功!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(forget_password.this, sign_in.class);
                startActivity(intent);
            }
        });

        cancelBtn.setOnClickListener(view -> {
            Intent intent = new Intent(forget_password.this, sign_in.class);
            startActivity(intent);
        });

        sendCode.setOnClickListener(view -> {
            if (!isSendCode) {
                String pNum = phoneNum.getText().toString();
                if (pNum.isEmpty()) {
                    Toast.makeText(forget_password.this, "请输入手机号!", Toast.LENGTH_SHORT).show();
                } else {
                    sendVerificationCode();
                    startCountdown();
                }
            }
        });

    }

    private void limitNumberLength() {
        //        设置最多输入6位验证码
        verificationCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        //        11位手机号
        phoneNum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
    }

    private void sendVerificationCode() {
        // 模拟发送验证码的操作
        Toast.makeText(forget_password.this, "发送成功", Toast.LENGTH_SHORT).show();
        isSendCode = true;

    }

    private void startCountdown() {
        countDownTimer = new CountDownTimer(COUNTDOWN_TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                sendCode.setText(String.format(Locale.getDefault(), "%ds后重新发送", seconds));
                sendCode.setEnabled(false);  // 禁用发送按钮
            }

            @Override
            public void onFinish() {
                sendCode.setText("发送验证码");
                sendCode.setEnabled(true);  // 启用发送按钮
                isSendCode = false;
            }
        };
        countDownTimer.start();
    }
}