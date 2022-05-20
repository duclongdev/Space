package com.example.space.MusicPlayer.MoreBottomSheet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.space.R;

import java.util.List;

public class More_Item_Adapter extends RecyclerView.Adapter<More_Item_Adapter.ItemViewHolder>{
    private List<More_Item> list;
    IClickItemMoreListener iClickItemMoreListener;

    public More_Item_Adapter(List<More_Item> list, IClickItemMoreListener iClickItemMoreListener) {
        this.list = list;
        this.iClickItemMoreListener = iClickItemMoreListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final More_Item item_object = list.get(position);
        if(item_object == null)
            return;
        holder.tv.setText(item_object.getTitle());
        holder.imageView.setImageResource(item_object.getImage());
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemMoreListener.Clickitem(item_object);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list != null)
            return list.size();
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tv;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_item);
            imageView = itemView.findViewById(R.id.icon_item);
        }
    }
}
