package com.example.rollview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ActorViewHolder> {

    private List<Actor> listActor;

    public CastAdapter(List<Actor> listActor) {
        this.listActor = listActor;
    }

    @NonNull
    @Override
    public ActorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast, parent, false);
        return new ActorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorViewHolder holder, int position) {
        Actor actor = listActor.get(position);
        holder.txtName.setText(actor.getName());
        holder.txtRole.setText(actor.getRole());


        Glide.with(holder.itemView.getContext())
                .load(actor.getImageUrl())
                .placeholder(R.drawable.user_default) //
                .error(R.drawable.user_default)
                .into(holder.imgActor);
    }

    @Override
    public int getItemCount() {
        return listActor.size();
    }

    public static class ActorViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtRole;
        ImageView imgActor;

        public ActorViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtNameActor);
            txtRole = itemView.findViewById(R.id.txtRole);
            imgActor = itemView.findViewById(R.id.imgActor);
        }
    }
}