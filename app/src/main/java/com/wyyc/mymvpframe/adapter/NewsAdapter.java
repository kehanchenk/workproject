package com.wyyc.mymvpframe.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wyyc.mymvpframe.adapter.item.NewsInfo;
import com.wyyc.zqqworkproject.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zqq on 2017/6/20.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {



    private List<NewsInfo> mNewsInfos;


    public void setData(List<NewsInfo> infos) {
        mNewsInfos = infos;
        notifyDataSetChanged();
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

        NewsInfo newsInfo = mNewsInfos.get(position);
        holder.mDescriptionTv.setText(newsInfo.getDescription());
        Glide.with(holder.itemView.getContext()).load(newsInfo.getImage_url()).into(holder.mImageIv);
    }

    @Override
    public int getItemCount() {
        return mNewsInfos == null ? 0 : mNewsInfos.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageIv)
        ImageView mImageIv;
        @BindView(R.id.descriptionTv)
        TextView mDescriptionTv;


        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
