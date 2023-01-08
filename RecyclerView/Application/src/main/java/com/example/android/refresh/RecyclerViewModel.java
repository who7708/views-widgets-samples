package com.example.android.refresh;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Chris
 * @version 1.0
 * @since 2023-01-08
 */
public class RecyclerViewModel extends ViewModel {
    private final MutableLiveData<List<String>> mDataList;

    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    private static final List<String> dataList = new ArrayList<>();

    public RecyclerViewModel() {
        mDataList = new MutableLiveData<>();
        initData();
    }

    public LiveData<List<String>> getDataList() {
        return mDataList;
    }

    /**
     * 初始化数据
     *
     * @return
     */
    public void initData() {
        for (int i = 0; i < 20; i++) {
            dataList.add("第" + COUNTER.incrementAndGet() + "条数据");
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mDataList.postValue(dataList);
    }
}
