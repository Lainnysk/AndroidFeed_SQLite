package com.example.newsfeed_sqlite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

public class RV_adapter extends RecyclerView.Adapter<RV_adapter.NewsViewHolder> {

    private Context context;
    private Cursor cursor;
    private int IdNews;
    private int IdUser;

    public RV_adapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }
        holder.txtTitle.setText(cursor.getString(1));
        holder.txtContent.setText(cursor.getString(2));
        holder.txtDateOfPublication.setText(cursor.getString(3));
        IdNews = cursor.getInt(0);
        holder.itemView.setTag(IdNews);
        IdUser = cursor.getInt(4);
        Cursor res = holder.databaseHelper.getData(IdUser);
        while (res.moveToNext()) {
            holder.txtAuthor.setText(res.getString(1));
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle, txtDateOfPublication, txtAuthor, txtContent;
        public DatabaseHelper databaseHelper;

        public NewsViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtContent = itemView.findViewById(R.id.txtContent);
            txtDateOfPublication = itemView.findViewById(R.id.txtDateOfPublication);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            databaseHelper = new DatabaseHelper(itemView.getContext());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (FeedForAdminActivity.admin) {
                        Context context = itemView.getContext();
                        Intent intent = new Intent(context, editNews.class);
                        intent.putExtra("IdNews", (int) itemView.getTag());
                        intent.putExtra("Id", IdUser);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
