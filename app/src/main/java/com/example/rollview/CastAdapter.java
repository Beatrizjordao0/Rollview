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

    // 1. A LISTA AGORA É DE TMDBCast (Não mais de Actor)
    private List<TMDBCast> castList;

    // 2. O CONSTRUTOR AGORA RECEBE TMDBCast
    public CastAdapter(List<TMDBCast> castList) {
        this.castList = castList;
    }

    // 3. O MÉTODO DE ATUALIZAR AGORA RECEBE TMDBCast (Aqui estava o seu erro!)
    public void atualizarLista(List<TMDBCast> novaLista) {
        this.castList = novaLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Confirme se o nome do seu layout é item_cast mesmo
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        // Pega a pessoa da lista (Pode ser um ator ou um diretor)
        TMDBCast pessoa = castList.get(position);

        holder.tvName.setText(pessoa.getName());

        // Lembra do método inteligente que criamos na classe TMDBCast?
        // Ele vai decidir se escreve o nome do personagem ou a palavra "Diretor"
        holder.tvRole.setText(pessoa.getJob());

        // Lógica da Imagem: Monta o link da foto do TMDB
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
        TextView tvRole; // Substitui o tvCharacter

        public CastViewHolder(@NonNull View itemView) {
            super(itemView);
            // ⚠️ ATENÇÃO: Confirme se estes IDs são os mesmos que estão no seu item_cast.xml!
            imgProfile = itemView.findViewById(R.id.imgActor);
            tvName = itemView.findViewById(R.id.txtNameActor);
            tvRole = itemView.findViewById(R.id.txtRole);
        }
    }
}