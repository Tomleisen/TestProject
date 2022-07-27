package com.test.testproject.main.module.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TomLeisen on 2022/7/23 1:25 下午
 * Email: xy162162a@163.com
 * Description:
 */
public class LabelBean {

    private String label;
    @SerializedName("attributes")
    private AttributesBean attributesBean;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public AttributesBean getAttributesBean() {
        return attributesBean;
    }

    public void setAttributesBean(AttributesBean attributesBean) {
        this.attributesBean = attributesBean;
    }
}

