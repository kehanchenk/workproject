package com.wyyc.myview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wyyc.zqqworkproject.R;

import java.util.List;

/**
 * Created by zqq on 2017/3/8.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    private int mItemLayoutId;
    private List<Integer> mList;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;


    public GalleryAdapter(int itemLayoutId, List<Integer> list) {
        mItemLayoutId = itemLayoutId;
        mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mInflater = LayoutInflater.from(parent.getContext());

        View view = mInflater.inflate(mItemLayoutId, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mImageView.setImageResource(mList.get(position));
        holder.mTextView.setText("第"+position);

        if (mOnItemClickListener != null) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextView;
        private final ImageView mImageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            mTextView = (TextView) itemView.findViewById(R.id.tv_gallery_text);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_gallery_image);
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 回调接口
     */
    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }

}

