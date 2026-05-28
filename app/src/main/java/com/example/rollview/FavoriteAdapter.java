package com.example.rollview;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<TMDBMovieResponse> favoriteList;

    public FavoriteAdapter(List<TMDBMovieResponse> favoriteList) {
        this.favoriteList = favoriteList;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        TMDBMovieResponse movie = favoriteList.get(position);

        holder.txtFavTitle.setText(movie.getTitle());

        String ano = "";
        if (movie.getReleaseDate() != null && movie.getReleaseDate().length() >= 4) {
            ano = movie.getReleaseDate().substring(0, 4);
        }

        holder.txtFavInfo.setText(ano + "  |  Sua Nota: " + movie.getVoteAverage());

        if (movie.getPosterPath() != null) {
            String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .into(holder.imgFavPoster);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), FilmeActivity.class);
            intent.putExtra("movie_id", movie.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return favoriteList != null ? favoriteList.size() : 0;
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFavPoster;
        TextView txtFavTitle;
        TextView txtFavInfo;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFavPoster = itemView.findViewById(R.id.imgFavPoster);
            txtFavTitle = itemView.findViewById(R.id.txtFavTitle);
            txtFavInfo = itemView.findViewById(R.id.txtFavInfo);
        }
    }
}