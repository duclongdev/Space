package com.example.space.home.advSlide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.space.R;
import com.example.space.myInterface.IClickAdvSlideShow;

import java.util.List;

public class AdvViewPageAdapter extends RecyclerView.Adapter<AdvViewPageAdapter.advViewHolder> {
    public AdvViewPageAdapter(List<Advertisement> advList, Fragment fragment, IClickAdvSlideShow iClickAdvSlideShow) {
        this.advList = advList;
        this.fragment = fragment;
        this.iClickAdvSlideShow = iClickAdvSlideShow;
    }

    private List<Advertisement> advList;
    private Fragment fragment;
    private IClickAdvSlideShow iClickAdvSlideShow;

    @NonNull
    @Override
    public advViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adv_item, parent, false);
        return new advViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull advViewHolder holder, int position) {
        Advertisement adv = advList.get(position);
        if (adv == null)
            return;
        Glide.with(fragment).load(adv.getLinkImage()).centerCrop().into(holder.advImage);
        holder.clickSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickAdvSlideShow.onClickAdvItem(1);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (advList == null)
            return 0;
        return advList.size();
    }

    public class advViewHolder extends RecyclerView.ViewHolder {
        private ImageView advImage;
        private CardView clickSlide;

        public advViewHolder(@NonNull View itemView) {
            super(itemView);
            advImage = itemView.findViewById(R.id.advImage);
            clickSlide = itemView.findViewById(R.id.clickSlide);
        }
    }

}
