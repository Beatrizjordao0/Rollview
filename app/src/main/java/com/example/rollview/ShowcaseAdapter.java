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

public class ShowcaseAdapter extends RecyclerView.Adapter<ShowcaseAdapter.ShowcaseViewHolder> {

    private List<Movie> movies;

    public ShowcaseAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public ShowcaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showcase, parent, false);
        return new ShowcaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowcaseViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.tvTitle.setText(movie.getTitle());

        // Baixa a imagem horizontal larga
        Glide.with(holder.itemView.getContext())
                .load(movie.getBackdrop_path())
                .into(holder.imgBackdrop);
    }

    @Override
    public int getItemCount() {
        // Pega apenas os 5 primeiros filmes para o carrossel, para não ficar gigante
        return Math.min(movies.size(), 5);
    }

    public static class ShowcaseViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBackdrop;
        TextView tvTitle;

        public ShowcaseViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBackdrop = itemView.findViewById(R.id.imgShowcaseBackdrop);
            tvTitle = itemView.findViewById(R.id.tvShowcaseTitle);
        }
    }
}