package com.example.client_in_time;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activirt_login);

        EditText loginInput = findViewById(R.id.edit_login);
        EditText passwordInput = findViewById(R.id.edit_password);
        Button loginBtn = findViewById(R.id.btn_login);

        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goToMain();
            }
        });
    }

    protected void goToMain() {
        Intent sendIntent = new Intent(this, MainActivity.class);
        startActivity(sendIntent);
    }

}
