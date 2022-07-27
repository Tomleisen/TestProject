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
import com.test.testproject.main.view.asynctask.DownLoadTaskUtils;
import com.test.testproject.main.view.utils.MainUtils;
import com.tom.commonframework.common.base.ICommonView;
import com.tom.commonframework.widget.NiceImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TomLeisen on 2022/7/23 3:28 下午
 * Email: xy162162a@163.com
 * Description:免费
 */
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    //Activity 的 ICommonView
    private ICommonView mICommonView;
    public static final int TYPE_HEADER = 0;
    private List<ListBean> mDatas = new ArrayList<>();
    //Header
    private View mHeaderView;
    //点击事件回调接口
    private OnItemClickListener mListener;
    //预加载回调接口
    public OnPreLoadListener mOnPreLoadListener;
    //所有数据加载完成后要设置为true,避免向上滑动时走预加载回调
    private boolean noMoreData;

    public MainAdapter(Context context) {
        this.mContext = context;
    }

    public MainAdapter(Context context, ICommonView view) {
        this.mContext = context;
        this.mICommonView = view;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public void addDatas(List<ListBean> datas) {
        if (datas != null) {
            mDatas.addAll(datas);
            notifyDataSetChanged();
            for (int i = 0; i < datas.size(); i++) {
                //异步下载应用星级和评论数
                DownLoadTaskUtils.DownLoadTasks(this, mICommonView, datas.get(i).getId(), datas.get(i));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) return new Holder(mHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_free_layout, parent, false);
        return new Holder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        int pos = getRealPosition(viewHolder);

        if (viewHolder instanceof Holder) {
            ListBean entryBean = mDatas.get(pos);
            if (entryBean != null) {
                ((Holder) viewHolder).tvNum.setText(String.valueOf(pos + 1));
                if (pos % 2 != 0) {
                    ((Holder) viewHolder).ivImage.setCornerRadius(50);
                }
                MainUtils.loadRoundedImage(mContext, entryBean.getImageUrl(), ((Holder) viewHolder).ivImage);
                //title
                ((Holder) viewHolder).tvTitle.setText(entryBean.getTitle());
                //应用类别
                ((Holder) viewHolder).tvCategory.setText(entryBean.getCategory());
                //评分
                if (entryBean.getRating() > 0) {
                    ((Holder) viewHolder).ratingBar.setRating(entryBean.getRating());
                }
                if (entryBean.getCommentsNum() != null) {
                    //评论数量
                    ((Holder) viewHolder).tvComments.setText("(" + entryBean.getCommentsNum() + ")");
                }
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null)
                        mListener.onItemClick(pos, entryBean);
                }
            });
            //数据预加载
            checkPreload(pos);
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }


    @Override
    public int getItemCount() {
        return mHeaderView == null ? mDatas.size() : mDatas.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder {
        NiceImageView ivImage;
        TextView tvNum, tvTitle, tvCategory, tvComments;
        AppCompatRatingBar ratingBar;

        public Holder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) return;
            ivImage = itemView.findViewById(R.id.ivImage);
            tvNum = itemView.findViewById(R.id.tvNum);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvComments = itemView.findViewById(R.id.tvComments);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }

    //判断是否进行预加载
    private void checkPreload(int position) {
        //倒数第4个就自动加载
        int preLoad = 4;
        if (mDatas != null && mDatas.size() - preLoad == position) {
            if (mOnPreLoadListener != null && !noMoreData) {
                mOnPreLoadListener.onPreload();
            }
        }
    }

    public void setNoMoreData(boolean noMoreData) {
        this.noMoreData = noMoreData;
    }

    public void setOnPreLoadListener(OnPreLoadListener onPreLoadListener) {
        mOnPreLoadListener = onPreLoadListener;
    }

    public interface OnPreLoadListener {
        void onPreload();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, ListBean entryBean);
    }

}

