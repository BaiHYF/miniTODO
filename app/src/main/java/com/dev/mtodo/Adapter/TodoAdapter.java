package com.dev.mtodo.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.mtodo.MainActivity;
import com.dev.mtodo.R;
import com.dev.mtodo.Util.DbHelper;
import com.dev.mtodo.Model.Todo;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private List<Todo> todoList;
    private DbHelper DB;
    private MainActivity activity;

    public TodoAdapter(DbHelper DB, MainActivity activity) {
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
        final Todo item = todoList.get(position);
        holder.todoCheckBox.setText(item.getContent());
        holder.todoCheckBox.setChecked(item.getStatus() != 0);
        holder.todoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DB.updateTodosStatus(item.getId(), isChecked ? 1 : 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.todoList.size();
    }


    public MainActivity getActivity() {
        return activity;
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public void deleteTodo(int position) {
        Todo todoItem = todoList.get(position);
        DB.DeleteTodo(todoItem.getId());
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public void update(int position) {
        Todo todoItem = todoList.get(position);
        Bundle bundle = new Bundle();

        // TODO: 2024/12/24 Complete update function. 
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        CheckBox todoCheckBox;
        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            todoCheckBox = itemView.findViewById(R.id.todocheckbox);
        }
    }
}
