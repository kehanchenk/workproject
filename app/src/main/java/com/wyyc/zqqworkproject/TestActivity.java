package com.wyyc.zqqworkproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestActivity.this, "click success", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestActivity.this,"click success",Toast.LENGTH_SHORT).show();
            }
        });
    }

//    RecyclerView mView;
//    List<String> mStringList;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test);
//
//        mStringList = new ArrayList<>();
//
//        mStringList.add("1");
//        mStringList.add("2");
//        mStringList.add("3");
//
//        mView = (RecyclerView) findViewById(R.id.rv_recycle);
////        mView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        mView.setLayoutManager(new GridLayoutManager(this, 4));
//        mView.setAdapter(new RecycleViewAdapter(this, mStringList));
//    }
//
//
//    public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.Holder> {
//
//        private LayoutInflater mLayoutInflater;
//
//        private Context mContext;
//
//        private List<String> mInts;
//
//
//        public RecycleViewAdapter(Context context, List<String> list) {
//
//
//            mContext = context;
//            mInts = list;
//            mLayoutInflater = LayoutInflater.from(context);
//        }
//
//        @Override
//        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
//            return new Holder(mLayoutInflater.inflate(R.layout.item_layout_test, parent, false));
//        }
//
//        @Override
//        public void onBindViewHolder(Holder holder, int position) {
//
//
//            holder.mTextView.setText(mInts.get(position));
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return mInts == null ? 0 : mInts.size();
//        }
//
//
//        class Holder extends RecyclerView.ViewHolder {
//
//            TextView mTextView;
//
//            Holder(View itemView) {
//                super(itemView);
//                mTextView = (TextView) itemView.findViewById(R.id.id_num);
//            }
//        }
//
//    }
}
