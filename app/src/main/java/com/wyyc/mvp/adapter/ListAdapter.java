package com.wyyc.mvp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wyyc.bean.MvpBean;
import com.wyyc.zqqworkproject.R;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private List<MvpBean> mList;

    public ListAdapter(List<MvpBean> list) {
        mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.mImageView.setImageResource(mList.get(position).getImage());
        holder.mTextView.setText(mList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView mImageView;

        public MyViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.tv_name);
            mImageView = (ImageView) view.findViewById(R.id.iv_image);
        }
    }
}
