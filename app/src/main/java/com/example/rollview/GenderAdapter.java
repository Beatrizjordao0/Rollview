package com.example.rollview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GenderAdapter extends RecyclerView.Adapter<GenderAdapter.GenderViewHolder> {
    private List<TMDBGender> genderList;

    public GenderAdapter(List<TMDBGender> genderList){
        this.genderList = genderList;
    }

    public void atualizarLista(List<TMDBGender> novaLista){
        this.genderList = novaLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public GenderAdapter.GenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gender, parent, false);
        return new GenderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenderAdapter.GenderViewHolder holder, int position) {
        holder.tvGenderName.setText(genderList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return genderList != null ? genderList.size() : 0;
    }

    public static class GenderViewHolder extends RecyclerView.ViewHolder {
        TextView tvGenderName;
        public GenderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGenderName = itemView.findViewById(R.id.txtGenderName);
        }
    }
}
