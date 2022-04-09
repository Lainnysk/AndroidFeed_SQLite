package com.example.newsfeed_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

public class editNews extends AppCompatActivity {

    Calendar date;
    DatabaseHelper databaseHelper;
    Bundle bundle;
    private int IdNews;
    private int IdUser;
    EditText txtTitle, txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_news);

        databaseHelper = new DatabaseHelper(this);

        bundle = getIntent().getExtras();
        txtTitle = findViewById(R.id.txtTitle);
        txtContent = findViewById(R.id.txtContent);
        date = Calendar.getInstance();
        IdNews = bundle.getInt("IdNews");
        IdUser = bundle.getInt("Id");

        Cursor res = databaseHelper.getNewsData(IdNews);
        while (res.moveToNext()){
            txtTitle.setText(res.getString(1));
            txtContent.setText(res.getString(2));
        }
    }

    public void editClick(View view)
    {
        databaseHelper.updateNews(IdNews, txtTitle.getText().toString().trim(),
                txtContent.getText().toString().trim(),
                DateUtils.formatDateTime(getApplicationContext(), date.getTimeInMillis(),
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME));
        Intent intent = new Intent(this, FeedForAdminActivity.class);
        intent.putExtra("Id", IdUser);
        startActivity(intent);
    }

    public void deleteClick(View view)
    {
        databaseHelper.deleteNews(IdNews);
        Intent intent = new Intent(this, FeedForAdminActivity.class);
        intent.putExtra("Id", IdUser);
        startActivity(intent);
    }
}