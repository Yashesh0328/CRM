package com.example.user.activites;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.models.DefaultResponse;
import com.example.user.R;
import com.example.user.api.RetrofitClient;
import com.example.user.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword, editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);

        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void userSignUp() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .createUser(email, password, name);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.code() == 201) {

                    DefaultResponse dr = response.body();
                    Toast.makeText(MainActivity.this, dr.getMsg(), Toast.LENGTH_LONG).show();

                } else if (response.code() == 422) {
                    Toast.makeText(MainActivity.this, "User already exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSignUp:
                userSignUp();
                break;
            case R.id.textViewLogin:

                startActivity(new Intent(this, LoginActivity.class));

                break;
        }
    }
}
