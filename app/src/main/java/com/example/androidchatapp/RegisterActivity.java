package com.example.androidchatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidchatapp.Daos.UserDao;
import com.example.androidchatapp.Entities.User;

public class RegisterActivity extends AppCompatActivity {
    private AppDB db;
    private UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                .allowMainThreadQueries().build();
        Button toLogin = findViewById(R.id.btnToLogin);

        userDao = db.userDao();
        toLogin.setOnClickListener(view -> {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        });

        Button Reg = findViewById(R.id.btnRegister);

        Reg.setOnClickListener(view -> {
            EditText userName = findViewById(R.id.etUsername);
            EditText password = findViewById(R.id.etPassword);
            EditText passwordValid = findViewById(R.id.etPasswordValid);
            EditText nick = findViewById(R.id.etNickname);
            Log.i("reg", "pressed");
            if(!password.getText().toString().equals(passwordValid.getText().toString())){
                return;
            }
            User u = new User(userName.getText().toString(), password.getText().toString());
           // userDao.insert(u);
            UsersApi usersApi = new UsersApi();
            usersApi.register(u);
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);

        });
    }
}