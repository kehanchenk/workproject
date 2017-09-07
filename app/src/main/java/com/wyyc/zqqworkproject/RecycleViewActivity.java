package com.wyyc.zqqworkproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.wyyc.myview.MyRecycleView;
import com.wyyc.myview.adapter.GalleryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecycleViewActivity extends AppCompatActivity {

    @BindView(R.id.Rv_recycleview)
    MyRecycleView mRvRecycleview;
    @BindView(R.id.im_content)
    ImageView mImContent;
    private List<Integer> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        ButterKnife.bind(this);
        initData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvRecycleview.setLayoutManager(layoutManager);
        GalleryAdapter adapter = new GalleryAdapter(R.layout.item_gallery_layout, mList);
        adapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecycleViewActivity.this, position + "", Toast.LENGTH_SHORT)
                        .show();
                mImContent.setImageResource(mList.get(position));
            }
        });
        mRvRecycleview.setAdapter(adapter);

        mRvRecycleview.setScrollListener(new MyRecycleView.OnItemScrollListener() {
            @Override
            public void scroller(int position) {
                mImContent.setImageResource(mList.get(position));
            }
        });



    }

    private void initData() {

        mList = new ArrayList<>();
        mList.add(R.drawable.w01);
        mList.add(R.drawable.w02);
        mList.add(R.drawable.w03);
        mList.add(R.drawable.w04);
        mList.add(R.drawable.w05);
        mList.add(R.drawable.w01);
        mList.add(R.drawable.w02);
        mList.add(R.drawable.w03);
        mList.add(R.drawable.w04);
        mList.add(R.drawable.w05);

    }
}
