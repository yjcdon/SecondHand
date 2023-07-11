package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//jaiosdjaidjlajdla
        ImageView visibilityOff = findViewById(R.id.visibilityoff);
        ImageView visibility = findViewById(R.id.visibility);
        EditText password = findViewById(R.id.password);

        visibilityOff.setOnClickListener(view -> {
            visibilityOff.setVisibility(View.GONE);
            visibility.setVisibility(View.VISIBLE);
            password.setTransformationMethod(null);
            password.setSelection(password.getText().length());
        });

        visibility.setOnClickListener(view -> {
            visibility.setVisibility(View.GONE);
            visibilityOff.setVisibility(View.VISIBLE);
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            password.setSelection(password.getText().length());
        });
    }
}