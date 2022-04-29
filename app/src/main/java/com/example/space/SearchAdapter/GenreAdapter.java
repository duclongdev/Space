package com.example.space.SearchAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.space.R;
import com.example.space.model.Genre;
import com.example.space.model.Theme;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder>{
    private Context mContext;
    private List<Genre> list_genre;
    public GenreAdapter(Context mContext, List<Genre> list_genre) {
        this.mContext = mContext;
        this.list_genre = list_genre;
    }
    @NonNull
    @Override
    public GenreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_theme, parent, false);
        GenreAdapter.ViewHolder viewHolder = new GenreAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext).load(list_genre.get(position).getLinkImage()).centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list_genre.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img_theme);
        }
    }

}
