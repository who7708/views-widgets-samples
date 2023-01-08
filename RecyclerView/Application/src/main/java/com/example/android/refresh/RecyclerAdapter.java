package com.example.android.refresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.recyclerview.R;

import java.util.List;

/**
 * RecycleView的Adapter
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        View.OnClickListener {
    private static final int ITEM_FOOTER = 0x1;

    private static final int ITEM_DATA = 0x2;

    private Context mContext;

    private RecyclerView recyclerView;

    private List<String> dataList;

    public RecyclerAdapter() {
    }

    public RecyclerAdapter(Context mContext, List<String> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    /**
     * 用于创建ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder vh = null;
        switch (viewType) {
            case ITEM_DATA:
                view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item, null);
                view.setOnClickListener(this);
                vh = new DataViewHolder(view);
                //使用代码设置宽高（xml布局设置无效时）
                view.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                break;
            case ITEM_FOOTER:
                view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_footer, null);
                //使用代码设置宽高（xml布局设置无效时）
                vh = new FooterViewHolder(view);
                view.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                break;
        }
        return vh;
    }

    /**
     * 获取Item的View类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        //根据 Item 的 position 返回不同的 Viewtype
        if (position == (getItemCount()) - 1) {
            return ITEM_FOOTER;
        } else {
            return ITEM_DATA;
        }
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DataViewHolder) {
            DataViewHolder dataViewHolder = (DataViewHolder) holder;
            dataViewHolder.tv_data.setText(dataList.get(position));
        } else if (holder instanceof FooterViewHolder) {

        }
    }

    /**
     * 选项总数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    @Override
    public void onClick(View view) {
        //根据RecyclerView获得当前View的位置
        int position = recyclerView.getChildAdapterPosition(view);
        //程序执行到此，会去执行具体实现的onItemClick()方法
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(recyclerView, view, position, dataList.get(position));
        }
    }

    /**
     * 创建ViewHolder
     */
    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView tv_data;

        public DataViewHolder(View itemView) {
            super(itemView);
            tv_data = (TextView) itemView.findViewById(R.id.tv_recycle);
        }
    }

    /**
     * 创建footer的ViewHolder
     */
    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 定义RecyclerView选项单击事件的回调接口
     */
    public interface OnItemClickListener {
        //参数（父组件，当前单击的View,单击的View的位置，数据）
        void onItemClick(RecyclerView parent, View view, int position, String data);
    }

    /**
     * 将RecycleView附加到Adapter上
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    /**
     * 将RecycleView从Adapter解除
     */
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }
}
