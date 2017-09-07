package com.wyyc.myview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wyyc.bean.ElemenntBean;
import com.wyyc.zqqworkproject.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/22.
 */
public class ElementaryAdapter extends RecyclerView.Adapter {


    private List<ElemenntBean> mElemenntBeen;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);

        return new DebounceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        DebounceViewHolder viewHolder = (DebounceViewHolder) holder;

        ElemenntBean bean = mElemenntBeen.get(position);
        Glide.with(holder.itemView.getContext()).load(bean.image_url).into(viewHolder.mImageIv);
        viewHolder.mDescriptionTv.setText(bean.description);
    }

    @Override
    public int getItemCount() {
        return mElemenntBeen == null ? 0 : mElemenntBeen.size();
    }

    public void setdata(List<ElemenntBean> been) {
        mElemenntBeen = been;
        notifyDataSetChanged();
    }

    class DebounceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageIv)
        ImageView mImageIv;
        @BindView(R.id.descriptionTv)
        TextView mDescriptionTv;

        public DebounceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
