package com.example.android.refresh;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.recyclerview.R;

import java.util.ArrayList;

public class RecyclerActivity extends AppCompatActivity {
    private static final int UPDATE_DATA = 0x3;

    private RecyclerView rv;

    RecyclerAdapter adapter;

    private ArrayList<String> arrayList = new ArrayList<>();

    //加载更多数据时最后一项的索引
    private int lastLoadDataItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        rv = (RecyclerView) findViewById(R.id.rv_main2);

        //设置布局管理器
        rv.setLayoutManager(new LinearLayoutManager(this));//线性
        // rv.setLayoutManager(new GridLayoutManager(this, 4));//线性
        // rv.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));//线性
        initData();
        adapter = new RecyclerAdapter(this, arrayList);
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, String data) {
                Toast.makeText(RecyclerActivity.this, data, Toast.LENGTH_SHORT).show();
            }
        });

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE &&
                        lastLoadDataItemPosition == adapter.getItemCount()) {
                    new LoadDataThread().start();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                    int firstVisibleItem = manager.findFirstVisibleItemPosition();
                    int l = manager.findLastCompletelyVisibleItemPosition();
                    lastLoadDataItemPosition = firstVisibleItem + (l - firstVisibleItem) + 1;
                }
            }
        });

        rv.setAdapter(adapter);
    }

    /**
     * 初始化数据
     *
     * @return
     */
    public void initData() {
        for (int i = 0; i < 25; i++) {
            arrayList.add("第" + i + "条数据");
        }
    }

    /**
     * 线程模拟加载数据
     */
    class LoadDataThread extends Thread {
        @Override
        public void run() {
            initData();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = handler.obtainMessage();
            message.what = UPDATE_DATA;
            message.obj = arrayList;
            handler.sendMessage(message);
        }
    }

    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_DATA:
                    arrayList = (ArrayList<String>) msg.obj;
                    adapter.setDataList(arrayList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}
