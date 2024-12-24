package com.dev.mtodo;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.dev.mtodo.Interfaces.DialogCloseListener;
import com.dev.mtodo.Util.DbHelper;
import com.dev.mtodo.Model.Todo;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


/**
 * CreateTodo to handle the creation and update of new Todo items
 */
public class CreateTodo extends BottomSheetDialogFragment {
    public static final String TAG = "New";

    private EditText todoEditText;
    private Button todoSaveBtn;

    private DbHelper DB;

    public static CreateTodo newInstance() {
        return new CreateTodo();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_todo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        todoEditText = view.findViewById(R.id.edittext);
        todoSaveBtn = view.findViewById(R.id.button_save);

        DB = new DbHelper(getActivity());

        boolean update = false;

        final Bundle bundle = getArguments();
        if (bundle != null) {
            update = true;
            String content = bundle.getString("content");
            todoEditText.setText(content);
            assert content != null;
            if (!content.isEmpty()) {
                todoSaveBtn.setEnabled(false);
            }
        }

        todoEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Disable save button when nothing inputted
                if (s.toString().isEmpty()) {
                    todoSaveBtn.setEnabled(false);
                    todoSaveBtn.setBackgroundColor(Color.GRAY);
                } else {
                    todoSaveBtn.setEnabled(true);
                    todoSaveBtn.setBackgroundColor(R.color.colorPrimary);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        boolean finalUpdate = update;
        todoSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = todoEditText.getText().toString();

                if (finalUpdate) {
                    DB.updateTask(bundle.getInt("id"), content);
                } else {
                    Todo todoItem = new Todo();
                    todoItem.setContent(content);
                    todoItem.setStatus(0);
                    DB.insertTask(todoItem);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        FragmentActivity activity = getActivity();
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener)activity).onDialogClose(dialog);
        }
    }
}
