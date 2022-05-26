package com.example.lab3.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab3.R;
import com.example.lab3.data.model.TodoListItem;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {

    private MainViewModel mainViewModel;
    private List<TodoListItem> localDataSet;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView todoText;
        private final Switch doneSwitch;
        private TodoListItem boundItem;

        public ViewHolder(View view) {
            super(view);
            todoText = view.findViewById(R.id.todoText);
            doneSwitch = view.findViewById(R.id.doneSwitch);
            doneSwitch.setOnCheckedChangeListener(((compoundButton, b) -> {
                if (boundItem != null) {
                    boundItem.setDone(b);
                    mainViewModel.toggleTask(boundItem);
                }
            }));
        }

        public TextView getTodoText() {
            return todoText;
        }

        public Switch getDoneSwitch() {
            return doneSwitch;
        }

        public void setBoundItem(TodoListItem boundItem) {
            this.boundItem = boundItem;
        }
    }

    public TodoListAdapter(MainViewModel mainViewModel, List<TodoListItem> localDataSet) {
        this.mainViewModel = mainViewModel;
        this.localDataSet = localDataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTodoText().setText(localDataSet.get(position).getText());
        holder.getDoneSwitch().setChecked(localDataSet.get(position).isDone());
        holder.setBoundItem(localDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
