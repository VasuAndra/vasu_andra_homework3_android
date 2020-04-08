package com.example.android_tema3_vasu_andra;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_tema3_vasu_andra.models.ToDo;

import java.util.List;

public class ToDosAdapter extends RecyclerView.Adapter<ToDosAdapter.ViewHolder> {

    private List<ToDo> toDos;
    private View.OnClickListener onClickListener;

    public ToDosAdapter(List<ToDo> toDos, View.OnClickListener clickListener)
    {
        onClickListener = clickListener;
        this.toDos = toDos;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_todo, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.title.setText(toDos.get(i).getTitle());

        Boolean completed = toDos.get(i).isCompleted();
        if (completed) {
            viewHolder.completed.setTextColor(Color.GREEN);
            viewHolder.completed.setText("Completed");
        }
        else {
            viewHolder.completed.setTextColor(Color.RED);
            viewHolder.completed.setText("Not completed");
        }



        viewHolder.toDoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ToDosFragment.myToDoClickListener)onClickListener).setTitle(toDos.get(i).getTitle());
                onClickListener.onClick(view);
            }
        });

    }

    @Override
    public int getItemCount() {
        return toDos.size();
    }

    public void addItem(ToDo toDo)
    {
        toDos.add(toDo);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView title;
        public TextView completed;
        public RelativeLayout toDoLayout;

        public ViewHolder(View view)
        {
            super(view);
            title = view.findViewById(R.id.title);
            completed = view.findViewById(R.id.completed);
            toDoLayout= view.findViewById(R.id.toDoLayout);

        }
    }

}
