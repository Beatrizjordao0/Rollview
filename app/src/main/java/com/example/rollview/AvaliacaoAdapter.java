package com.example.rollview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AvaliacaoAdapter extends RecyclerView.Adapter<AvaliacaoAdapter.ViewHolder> {

    private List<Avaliacao> lista;
    private Usuario usuario;

    public AvaliacaoAdapter(List<Avaliacao> lista, Usuario usuario) {
        this.lista = lista;
        this.usuario = usuario;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_avaliacao, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Avaliacao avaliacao = lista.get(position);

        holder.txtUserReview.setText(usuario.getUsername());
        holder.txtData.setText(avaliacao.getData());
        holder.txtAvaliacao.setText(avaliacao.getTexto());

        holder.ratingBar.setRating(avaliacao.getNota());

        if(usuario.getFotoPerfil() != null){
            holder.imgPerfilReview.setImageURI(usuario.getFotoPerfil());
        }
        if(avaliacao.getPosterFilme() != null){
            Glide.with(holder.itemView.getContext())
                    .load(avaliacao.getPosterFilme().toString())
                    .into(holder.imgPoster);
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPerfilReview;
        ImageView imgPoster;

        TextView txtUserReview;
        TextView txtData;
        TextView txtAvaliacao;

        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPerfilReview = itemView.findViewById(R.id.imgPerfilReview);
            imgPoster = itemView.findViewById(R.id.imgPoster);

            txtUserReview = itemView.findViewById(R.id.txtUserReview);
            txtData = itemView.findViewById(R.id.txtData);
            txtAvaliacao = itemView.findViewById(R.id.txtAvaliacao);

            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}