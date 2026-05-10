package com.example.rollview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Importação da biblioteca Glide
import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies;

    // Construtor que recebe a lista
    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    // Cria a visualização puxando o XML
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    // Preenche os dados do objeto na tela
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.tvTitle.setText(movie.getTitle());

        // Usando o Glide para baixar a imagem da URL e colocar no ImageView
        Glide.with(holder.itemView.getContext())
                .load(movie.getPosterUrl())
                .into(holder.imgPoster);
    }

    // Diz quantos itens existem na lista
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // Classe interna que mapeia os componentes do XML
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgMoviePoster);
            tvTitle = itemView.findViewById(R.id.tvMovieTitle);
        }
    }
}