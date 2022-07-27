package com.test.testproject.main.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.test.testproject.R;
import com.test.testproject.main.module.bean.ListBean;
import com.test.testproject.main.view.utils.MainUtils;
import com.tom.commonframework.widget.NiceImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TomLeisen on 2022/7/23 3:28 下午
 * Email: xy162162a@163.com
 * Description:搜索
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Holder> {

    private Context mContext;
    private List<ListBean> mDatas = new ArrayList<>();
    //点击事件回调接口
    private OnItemClickListener mListener;

    public SearchAdapter(Context context) {
        this.mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void addDatas(List<ListBean> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public SearchAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_free_layout, parent, false);
        return new Holder(layout);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.Holder viewHolder, int position) {
        ListBean entryBean = mDatas.get(position);
        if (entryBean != null) {
            viewHolder.tvNum.setText(String.valueOf(position + 1));
            if (position % 2 != 0) {
                viewHolder.ivImage.setCornerRadius(50);
            }
            MainUtils.loadRoundedImage(mContext, entryBean.getImageUrl(), viewHolder.ivImage);
            //title
            viewHolder.tvTitle.setText(entryBean.getTitle());
            //应用类别
            viewHolder.tvCategory.setText(entryBean.getCategory());
            //评分
            if (entryBean.getRating() > 0) {
                viewHolder.ratingBar.setRating(entryBean.getRating());
            }
            if (entryBean.getCommentsNum() != null) {
                //评论数量
                viewHolder.tvComments.setText("(" + entryBean.getCommentsNum() + ")");
            }
        }
        if (mListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(position, entryBean);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        NiceImageView ivImage;
        TextView tvNum, tvTitle, tvCategory, tvComments;
        AppCompatRatingBar ratingBar;

        public Holder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvNum = itemView.findViewById(R.id.tvNum);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvComments = itemView.findViewById(R.id.tvComments);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, ListBean entryBean);
    }
}

