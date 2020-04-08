package com.example.android_tema3_vasu_andra;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_tema3_vasu_andra.models.User;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<User> users;
    private View.OnClickListener onClickListener;

    public UsersAdapter(List<User> users, View.OnClickListener onClickListener)
    {
        this.users = users;
        this.onClickListener = onClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_user, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {

        viewHolder.name.setText(users.get(i).getName());
        viewHolder.username.setText(users.get(i).getUsername());
        viewHolder.email.setText(users.get(i).getEmail());

        viewHolder.userLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((UsersFragment.myUserOnClickListener)onClickListener).setId(users.get(i).getId());
                onClickListener.onClick(view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void addItem(User user)
    {
        users.add(user);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name;
        public TextView username;
        public TextView email;
        public RelativeLayout userLayout;

        public ViewHolder(View view)
        {
            super(view);
            name = view.findViewById(R.id.name);
            username = view.findViewById(R.id.username);
            email = view.findViewById(R.id.email);
            userLayout=view.findViewById(R.id.userLayout);
        }
    }

}
