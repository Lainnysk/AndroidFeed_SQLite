package com.example.newsfeed_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText txtLogin, txtPassword;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        databaseHelper = new DatabaseHelper(this);
        txtLogin = findViewById(R.id.txtLogin);
        txtPassword = findViewById(R.id.txtPassword);
    }

    public void enterClick(View view) {
        Cursor res = databaseHelper.getData(txtLogin.getText().toString().trim(), txtPassword.getText().toString().trim());

        //intent.putExtra("Id", res.getInt(0));
        if (res.getCount() == 0) {
            Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            while (res.moveToNext()) {
                if (res.getString(3).equals("Администратор")) {
                    Intent intent = new Intent(this, FeedForAdminActivity.class).putExtra("Id", res.getInt(0));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, FeedForAdminActivity.class).putExtra("Id", res.getInt(0));
                    startActivity(intent);
                }
            }
        }
        finish();
    }

    public void regClick(View view) {
        startActivity(new Intent(this, RegActivity.class));
    }
}