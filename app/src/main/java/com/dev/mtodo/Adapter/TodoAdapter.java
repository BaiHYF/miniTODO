package com.dev.mtodo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.mtodo.MainActivity;
import com.dev.mtodo.R;
import com.dev.mtodo.Util.DbHelper;

public class Todo extends RecyclerView.Adapter<Todo.TodoViewHolder> {

    private DbHelper DB;
    private MainActivity activity;

    public Todo(DbHelper DB, MainActivity activity) {
        this.activity = activity;
        this.DB = DB;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TodoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        CheckBox todoCheckBox;
        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            todoCheckBox = itemView.findViewById(R.id.todocheckbox);
        }
    }
}
