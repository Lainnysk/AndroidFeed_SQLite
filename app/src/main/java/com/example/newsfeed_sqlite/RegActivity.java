package com.example.newsfeed_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    Spinner spnRoles;
    EditText txtLogin, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        txtLogin = findViewById(R.id.txtLogin);
        txtPassword = findViewById(R.id.txtPassword);
        spnRoles = findViewById(R.id.spnRoles);
        databaseHelper = new DatabaseHelper(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.roles));
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnRoles.setAdapter(adapter);
    }

    public void regClick(View view) {
        Boolean checkInsertData = databaseHelper.insertUser(txtLogin.getText().toString().trim(),
                txtPassword.getText().toString().trim(),
                spnRoles.getSelectedItem().toString());
        if (checkInsertData) {
            Cursor res = databaseHelper.getData(txtLogin.getText().toString().trim(), txtPassword.getText().toString().trim());
            while (res.moveToNext()) {
                if (res.getString(3).equals("Администратор")) {
                    Intent intent = new Intent(this, FeedForAdminActivity.class).putExtra("Id", res.getInt(0));
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "reg is ok", Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(this, AllNewsActivity.class));
                }
            }
        }
    }
}