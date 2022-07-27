package com.test.testproject.main.view.ui;

import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.testproject.R;
import com.test.testproject.main.module.bean.ListBean;
import com.test.testproject.main.presenter.MainPresenter;
import com.test.testproject.main.view.adapter.MainAdapter;
import com.test.testproject.main.view.adapter.RecommendAdapter;
import com.test.testproject.main.view.adapter.SearchAdapter;
import com.test.testproject.main.view.interfaces.IMainView;
import com.test.testproject.main.view.utils.MainUtils;
import com.test.testproject.room.asynctask.QueryAsyncTask;
import com.test.testproject.room.utils.RoomUtils;
import com.tom.commonframework.common.base.BaseActivity;
import com.tom.commonframework.common.base.utils.ExitUtil;
import com.tom.commonframework.common.base.view.MyToast;
import com.tom.commonframework.widget.CustomEdittext;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView {

    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private RecommendAdapter recommendAdapter;

    private CustomEdittext editText;
    private TextView tvSearch;
    private RecyclerView searchRecyclerView;
    private SearchAdapter searchAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        hideToolbar();
        recyclerView = findViewById(R.id.recyclerView);
        searchRecyclerView = findViewById(R.id.searchRecyclerView);
        editText = findViewById(R.id.editText);
        tvSearch = findViewById(R.id.tvSearch);
    }

    @Override
    protected void addEvents() {
        editText.setOnEditorActionListener(editorActionListener);
        editText.addEdittextTextWatcher(textWatcher);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //首页列表
        adapter = new MainAdapter(this, presenter.getView());
        adapter.setOnPreLoadListener(presenter);
        setHeaderView();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, ListBean entryBean) {
                ToastLog(entryBean);
            }
        });

        //搜索列表
        searchAdapter = new SearchAdapter(this);
        searchRecyclerView.setAdapter(searchAdapter);
        searchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, ListBean entryBean) {
                ToastLog(entryBean);
            }
        });
    }

    /**
     * 添加Header
     */
    private void setHeaderView() {
        View header = LayoutInflater.from(this).inflate(R.layout.layout_gross, recyclerView, false);
        RecyclerView recyclerView = header.findViewById(R.id.grossRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recommendAdapter = new RecommendAdapter(this, presenter.getView());
        recyclerView.setAdapter(recommendAdapter);
        recommendAdapter.setOnItemClickListener(new RecommendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, ListBean entryBean) {
                ToastLog(entryBean);
            }
        });
        adapter.setHeaderView(header);

    }

    @Override
    public void callBackGross(List<ListBean> grossList) {
        if (recommendAdapter != null) {
            recommendAdapter.addDatas(grossList);

        }
    }

    @Override
    public void callBackFree(List<ListBean> freeList) {
        if (adapter != null) {
            adapter.addDatas(freeList);
        }
    }

    @Override
    public void onLastPage(boolean isLastPage) {
        if (adapter != null) {
            adapter.setNoMoreData(isLastPage);
        }
    }

    TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                clickSearch();
                return true;
            }
            return false;
        }
    };

    CustomEdittext.CustomEdittextTextWatcher textWatcher = new CustomEdittext.CustomEdittextTextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0) {
                searchRecyclerView.setVisibility(View.GONE);
            } else {
                //实时搜索
                search(s.toString());
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };


    /**
     * 数据库实时搜索
     *
     * @param text 搜索关键词
     */
    private void search(String text) {
        RoomUtils.queryData(this, text, new QueryAsyncTask.QueryCallback() {
            @Override
            public void getQueryCallback(List<ListBean> listBeanList) {
                if (listBeanList != null && listBeanList.size() > 0) {
                    searchRecyclerView.setVisibility(View.VISIBLE);
                    searchAdapter.addDatas(listBeanList);
                } else {
                    searchAdapter.addDatas(new ArrayList<>());
                }
            }
        });
    }

    /**
     * 点击搜索按钮
     */
    private void clickSearch(){
        if (!TextUtils.isEmpty(editText.getText().toString())) {
            //关闭软键盘
            MainUtils.hide(MainActivity.this);
            search(editText.getText().toString());
        } else {
            MyToast.show(MainActivity.this, "请输入搜索内容");
        }
    }

    /**
     * 列表滑动监听-控制键盘开启关闭
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (MainUtils.isShouldHideInput(v, ev)) {
                //关闭软键盘
                MainUtils.hide(this);
                if (searchRecyclerView.getVisibility() == View.GONE) {
                    editText.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void ToastLog(ListBean listBean) {
        if (listBean.getId() != null) {
            String id = listBean.getId();
            String appName = listBean.getTitle();
            int num = listBean.getCommentsNum();
            showToast("应用名称：" + appName + " \n应用ID:" + id + " \n评论数：" + num);
            Log.e("评论数：", "应用名称：" + appName + " \n应用ID:" + id + " \n评论数：" + num);
        }
    }

    @Override
    public void onBackPressed() {
        if (searchRecyclerView.getVisibility() == View.VISIBLE) {
            editText.setText("");
            editText.clearFocus();
            searchRecyclerView.setVisibility(View.GONE);
        } else {
            ExitUtil.exitApp(this);
        }
    }

}