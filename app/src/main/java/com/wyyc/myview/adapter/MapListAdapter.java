package com.wyyc.myview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wyyc.bean.Item;
import com.wyyc.zqqworkproject.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zqq on 2017/3/22.
 */
public class MapListAdapter extends RecyclerView.Adapter<MapListAdapter.ItemViewHolder> {

    private List<Item> mItems;

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        Item item = mItems.get(position);
        holder.descriptionTv.setText(item.description);
        Glide.with(holder.itemView.getContext()).load(item.imageUrl).into(holder.imageIv);

    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageIv)
        ImageView imageIv;
        @BindView(R.id.descriptionTv)
        TextView descriptionTv;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void setData(List<Item> items) {
        mItems = items;
        notifyDataSetChanged();
    }
}
