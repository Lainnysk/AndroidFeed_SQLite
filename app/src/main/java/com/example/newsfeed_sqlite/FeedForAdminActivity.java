package com.example.newsfeed_sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FeedForAdminActivity extends AppCompatActivity {

    Button btnAddNews;
    DatabaseHelper databaseHelper;
    RV_adapter adapter;
    RecyclerView recyclerNews;
    Bundle bundle;
    private int IdUser;
    public static Boolean admin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_for_admin);

        btnAddNews = findViewById(R.id.btnAddNews);
        recyclerNews = findViewById(R.id.recyclerNews);
        bundle = getIntent().getExtras();
        IdUser = bundle.getInt("Id");
        databaseHelper = new DatabaseHelper(this);

        Cursor res = databaseHelper.getData(IdUser);
        while (res.moveToNext())
            if(!res.getString(3).equals("Администратор"))
                admin = false;
            else
                admin = true;

        if (!admin) {
            btnAddNews.setVisibility(View.GONE);
            recyclerNews.setClickable(false);
        }
        else
        {
            btnAddNews.setVisibility(View.VISIBLE);
            recyclerNews.setClickable(true);
        }
        setRecyclerView();
    }

    private void setRecyclerView() {
        recyclerNews.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RV_adapter(this, databaseHelper.getNewsData());
        recyclerNews.setAdapter(adapter);
    }

    public void addNewClick(View view) {
        Intent intent = new Intent(this, AddNewsActivity.class);
        intent.putExtra("Id", IdUser);
        startActivity(intent);
        finish();
    }
}