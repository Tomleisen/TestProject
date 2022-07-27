package com.test.testproject.main.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.testproject.R;
import com.test.testproject.main.module.bean.ListBean;
import com.test.testproject.main.view.asynctask.DownLoadTaskUtils;
import com.test.testproject.main.view.utils.MainUtils;
import com.tom.commonframework.common.base.ICommonView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TomLeisen on 2022/7/23 4:42 下午
 * Email: xy162162a@163.com
 * Description:推荐
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.Holder> {

    private Context mContext;
    //Activity 的 ICommonView
    private ICommonView mICommonView;
    private List<ListBean> mDatas = new ArrayList<>();
    private OnItemClickListener mListener;

    public RecommendAdapter(Context context) {
        this.mContext = context;
    }

    public RecommendAdapter(Context context, ICommonView view) {
        this.mContext = context;
        this.mICommonView = view;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void addDatas(List<ListBean> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
        for (int i = 0; i < mDatas.size(); i++) {
            ListBean listBean = mDatas.get(i);
            //异步下载应用星级和评论数
            DownLoadTaskUtils.DownLoadTasks(this, mICommonView, listBean.getId(), listBean);
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gross_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendAdapter.Holder holder, int position) {
        ListBean entryBean = mDatas.get(position);
        if (entryBean != null) {
            //TODO 网络差的时候会出现问题。会出现方形变圆形，圆形变方形
//            ImageUtils.loadRoundedImage(mContext, entryBean.getImageUrl(), holder.ivImage, 10);
            MainUtils.loadRoundedImage(mContext,entryBean.getImageUrl(), holder.ivImage);
            //title
            holder.tvTitle.setText(entryBean.getTitle());
            //应用类别
            holder.tvCategory.setText(entryBean.getCategory());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onItemClick(position, entryBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvTitle, tvCategory;
        public Holder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCategory = itemView.findViewById(R.id.tvCategory);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int position, ListBean entryBean);
    }

} 

