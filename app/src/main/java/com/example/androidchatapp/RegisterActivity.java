package com.example.androidchatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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


            if(userName.getText().toString().isEmpty() || nick.getText().toString().isEmpty()
                    || password.getText().toString().isEmpty() || passwordValid.getText().toString().isEmpty()){
                Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show();
                return;
            }
            else if (userDao.find(userName.getText().toString()) != null){
                Toast.makeText(this,"username already in use",Toast.LENGTH_SHORT).show();
                return;
            }
            else if( password.getText().toString().length() < 4 ){
                Toast.makeText(this,"Password length should be at least 4 characters",Toast.LENGTH_SHORT).show();
                return;
            }

            else if (!password.getText().toString().equals(passwordValid.getText().toString())){
                Toast.makeText(this,"Password doesn't match",Toast.LENGTH_SHORT).show();
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