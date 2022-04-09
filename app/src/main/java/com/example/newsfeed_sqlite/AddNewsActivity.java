package com.example.newsfeed_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddNewsActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText txtContent, txtTitle;
    Bundle bundle;
    Calendar date;
    private int IdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        bundle = getIntent().getExtras();
        txtContent = findViewById(R.id.txtContent);
        txtTitle = findViewById(R.id.txtTitle);
        databaseHelper = new DatabaseHelper(this);
        date = Calendar.getInstance();
        IdUser = bundle.getInt("Id");
    }

    public void addNewsClick(View view) {
        if (TextUtils.isEmpty(txtContent.getText()) || TextUtils.isEmpty(txtTitle.getText())) {
            Toast.makeText(this, "Вы заполнили не все поля", Toast.LENGTH_SHORT).show();
            return;
        }
        databaseHelper
                .insertNews(txtTitle.getText().toString().trim(), txtContent.getText().toString().trim(),
                        DateUtils.formatDateTime(getApplicationContext(), date.getTimeInMillis(),
                                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME), IdUser);

        Intent intent = new Intent(this, FeedForAdminActivity.class);
        intent.putExtra("Id", IdUser);
        startActivity(intent);
        finish();
    }

    public void exitClick(View view) {
        Intent intent = new Intent(this, FeedForAdminActivity.class);
        intent.putExtra("Id", IdUser);
        startActivity(intent);
        finish();
    }
}