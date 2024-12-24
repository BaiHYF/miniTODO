package com.dev.mtodo.Interfaces;

import android.content.ClipData;
import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.mtodo.Adapter.TodoAdapter;

public class RecyclerTouchHelper extends ItemTouchHelper.SimpleCallback {

    private TodoAdapter todoAdapter;

    public RecyclerTouchHelper(TodoAdapter todoAdapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.todoAdapter = todoAdapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // get position
        int position = viewHolder.getAdapterPosition();

        // on swiped right
        if (direction == ItemTouchHelper.RIGHT) {
            // Invoke an alert dialog for delete todoItem
            // TODO: 2024/12/24  
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
