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

import com.example.login.MySQL.MySQL;
import com.example.login.R;
import com.example.login.Student;

public class SignUp extends AppCompatActivity {
    private ImageView visibility, visibilityOff;
    private Button signUpBtn;
    private EditText password1, password2, stuNum, phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        initView();


        stuNum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        visibilityOff.setOnClickListener(view -> {
            togglePasswordVisibility();
        });

        visibility.setOnClickListener(view -> {
            togglePasswordVisibility();
        });

        signUpBtn.setOnClickListener(view -> {
            judgeAndExec();
        });


    }

    private void initView() {
        visibility = findViewById(R.id.visibilityImageView);
        visibilityOff = findViewById(R.id.visibilityOffImageView);
        signUpBtn = findViewById(R.id.sign_up_btn);
        password1 = findViewById(R.id.signUp_psw);
        password2 = findViewById(R.id.signUp_psw2);
        stuNum = findViewById(R.id.signUp_stuNum);
        phoneNum = findViewById(R.id.signUp_phoneNum);
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

    private void judgeAndExec() {
        String stuNumText = stuNum.getText().toString().trim();
        String phoneNumText = phoneNum.getText().toString().trim();
        String psw1 = password1.getText().toString().trim();
        String psw2 = password2.getText().toString().trim();

        if (stuNumText.isEmpty() && psw1.isEmpty() && psw2.isEmpty()) {
            Toast.makeText(SignUp.this, "请输入账号和密码!", Toast.LENGTH_SHORT).show();
        } else if (psw1.isEmpty()) {
            Toast.makeText(SignUp.this, "密码不能为空!", Toast.LENGTH_SHORT).show();
        } else if (stuNumText.isEmpty()) {
            Toast.makeText(SignUp.this, "账号不能为空!", Toast.LENGTH_SHORT).show();
        } else if (!psw1.equals(psw2)) {
            Toast.makeText(SignUp.this, "密码不一致!", Toast.LENGTH_SHORT).show();
        } else {
            Student student = new Student();
            student.setStuNum(stuNumText);
            student.setPhone(phoneNumText);
            student.setPassword(psw1);
            new Thread(() -> {
                MySQL mySQL = new MySQL();
                mySQL.insertData(student);

                runOnUiThread(() -> {
                    Bundle bundle = new Bundle();
                    // 将账号和密码添加到 Bundle 中,key,value
                    bundle.putString("stuNum", stuNumText);
                    bundle.putString("password", psw1);

                    // 创建一个新的 Intent 对象，并将 Bundle 作为参数传递进去
                    Intent intent = new Intent(SignUp.this, SignIn.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    Toast.makeText(SignUp.this, "注册成功!", Toast.LENGTH_LONG).show();
                });
            }).start();


        }
    }

}