package com.test.testproject.main.module.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TomLeisen on 2022/7/23 1:26 下午
 * Email: xy162162a@163.com
 * Description:
 */
public class AttributesBean {

    private String amount;
    private String currency;
    private String term;
    private String label;
    private String rel;
    private String type;
    private String href;
    private String height;
    private String title;
    private String scheme;
    @SerializedName("im:assetType")
    private String imAssetType;
    @SerializedName("im:id")
    private String imId;
    @SerializedName("im:bundleId")
    private String imBundleId;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getImAssetType() {
        return imAssetType;
    }

    public void setImAssetType(String imAssetType) {
        this.imAssetType = imAssetType;
    }

    public String getImId() {
        return imId;
    }

    public void setImId(String imId) {
        this.imId = imId;
    }

    public String getImBundleId() {
        return imBundleId;
    }

    public void setImBundleId(String imBundleId) {
        this.imBundleId = imBundleId;
    }
}

