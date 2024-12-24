package com.dev.mtodo;

import android.content.DialogInterface;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.mtodo.Interfaces.DialogCloseListener;
import com.dev.mtodo.Util.RecyclerTouchHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.dev.mtodo.Util.DbHelper;
import com.dev.mtodo.Model.Todo;
import com.dev.mtodo.Adapter.TodoAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {
    private RecyclerView todoRecyclerView;
    private FloatingActionButton fab;
    private DbHelper DB;
    private TodoAdapter todoAdapter;
    private List<Todo> todoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoRecyclerView = findViewById(R.id.recyclerview);
        fab = findViewById(R.id.fab);
        DB = new DbHelper(MainActivity.this);
        todoAdapter = new TodoAdapter(DB, MainActivity.this);

        todoList = new ArrayList<>();
        todoList = DB.getAllTodos();
        Collections.reverse(todoList); // Reverse todolist, shown most currently edited on above
        todoAdapter.setTodoList(todoList);

        // Initialize recycler view
        todoRecyclerView.setHasFixedSize(true);
        todoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoRecyclerView.setAdapter(todoAdapter);

        fab.setOnClickListener(v -> CreateTodo.newInstance().show(getSupportFragmentManager(), CreateTodo.TAG));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerTouchHelper(todoAdapter ));
        itemTouchHelper.attachToRecyclerView(todoRecyclerView);
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        todoList = DB.getAllTodos();
        // Reverse todolist, shown most currently edited on above
        Collections.reverse(todoList);
        todoAdapter.setTodoList(todoList);
        todoAdapter.notifyDataSetChanged();
    }
}