package com.test.testproject.main.view.interfaces;

import com.test.testproject.main.module.bean.ListBean;
import com.tom.commonframework.common.base.ICommonView;

import java.util.List;

/**
 * Created by TomLeisen on 2022/7/23 12:57 上午
 * Email: xy162162a@163.com
 * Description:
 */
public interface IMainView extends ICommonView {

    /**
     * 推荐APP回调
     */
    void callBackGross(List<ListBean> grossList);

    /**
     * APP列表回调
     */
    void callBackFree(List<ListBean> freeList);

    /**
     * 是否最后一页
     *
     * @param isLastPage true 为最后一页  false 非最后一页
     */
    void onLastPage(boolean isLastPage);

}
