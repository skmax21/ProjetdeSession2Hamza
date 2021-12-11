package com.example.gestiondesstages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {



    List<String> titres;
    List<Integer> images;
    LayoutInflater inflater;
    private ViewHolder.OnItemListener onItemListener;

    public Adapter(Context context, List<String> titres, List<Integer> images, ViewHolder.OnItemListener onItemListener) {

        this.titres=titres;
        this.images=images;
        this.inflater = LayoutInflater.from(context);
        this.onItemListener = onItemListener;



    }



    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_grid_layout,parent,false);

        return new ViewHolder(view,onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        holder.titre.setText(titres.get(position));
        holder.menuImage.setImageResource(images.get(position));

    }

    @Override
    public int getItemCount() {
        return titres.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

      TextView titre;
      ImageView menuImage;
      OnItemListener onItemListener;

        public ViewHolder(@NonNull View itemView,OnItemListener onItemListener) {

            super(itemView);
            menuImage = itemView.findViewById(R.id.imageMenu);
            titre= itemView.findViewById(R.id.textMenu);
            this.onItemListener=onItemListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            onItemListener.onItemClick(getAdapterPosition());
        }


        public interface OnItemListener{
            void onItemClick(int position);
        }
    }
}
