package com.dev.mtodo.Util;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.mtodo.Adapter.TodoAdapter;
import com.dev.mtodo.R;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class RecyclerTouchHelper extends ItemTouchHelper.SimpleCallback {

    private TodoAdapter todoAdapter;

    public RecyclerTouchHelper(TodoAdapter todoAdapter) {
        // two actions for swiping left or right
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
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(todoAdapter.getActivity());
            dialogBuilder.setTitle("Delete Todo Item");
            dialogBuilder.setMessage("Are you absolutely sure? This action cannot be undo.");
            dialogBuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    todoAdapter.deleteTodo(position);
                }
            });
            dialogBuilder.setNegativeButton("Canel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    todoAdapter.notifyItemChanged(position);
                }
            });
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();
        } else {
            // Swipe left: edit item
            todoAdapter.update(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeLeftBackgroundColor(ContextCompat.getColor(todoAdapter.getActivity() , R.color.colorPrimaryDark))
                .addSwipeLeftActionIcon(R.drawable.baseline_mode_edit_24)
                .addSwipeRightBackgroundColor(ContextCompat.getColor(todoAdapter.getActivity() , R.color.colorPrimaryDark))
                .addSwipeRightActionIcon(R.drawable.baseline_playlist_remove_24)
                .create()
                .decorate();
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
