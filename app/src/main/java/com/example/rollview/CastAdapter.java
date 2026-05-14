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

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {

    private List<TMDBCast> castList;

    public CastAdapter(List<TMDBCast> castList) {
        this.castList = castList;
    }

    public void atualizarLista(List<TMDBCast> novaLista) {
        this.castList = novaLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        TMDBCast pessoa = castList.get(position);

        holder.tvName.setText(pessoa.getName());

        holder.tvRole.setText(pessoa.getJob());

        if (pessoa.getProfilePath() != null) {
            String imageUrl = "https://image.tmdb.org/t/p/w500" + pessoa.getProfilePath();

            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .into(holder.imgProfile);
        } else {
            // Se a pessoa não tiver foto no TMDB, deixa a imagem vazia ou coloca um ícone padrão
            holder.imgProfile.setImageDrawable(null);
        }
    }

    @Override
    public int getItemCount() {
        return castList != null ? castList.size() : 0;
    }

    public static class CastViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProfile;
        TextView tvName;
        TextView tvRole;

        public CastViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProfile = itemView.findViewById(R.id.imgActor);
            tvName = itemView.findViewById(R.id.txtNameActor);
            tvRole = itemView.findViewById(R.id.txtRole);
        }
    }
}