package com.example.androidchatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidchatapp.Entities.*;

public class LoginActivity extends AppCompatActivity {
    private AppDB db;
    private UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        userDao = db.userDao();

        Button toReg = findViewById(R.id.btnToRegister);

        toReg.setOnClickListener(view -> {
            Intent i = new Intent(this, RegisterActivity.class);

            startActivity(i);
        });


        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            EditText userName = findViewById(R.id.etUsername);
            EditText password = findViewById(R.id.etPassword);
            Log.i("uName", userName.getText().toString());

            User user = userDao.find(userName.getText().toString());
            if(user == null){
                return;
            }
            if(userName.getText().toString().equals(user.getName())){
                if(password.getText().toString().equals(user.getPassword())){
                    Intent i = new Intent(this, ContactsActivity.class);
                    i.putExtra("CurUsr", userName.getText().toString());
                    startActivity(i);
                }
            }

        });


    }
}