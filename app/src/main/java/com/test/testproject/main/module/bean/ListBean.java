package com.test.testproject.main.module.bean;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by TomLeisen on 2022/7/25 11:52 下午
 * Email: xy162162a@163.com
 * Description:
 */
@Entity(tableName = "listbean")
public class ListBean {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "id")
    public String id;//应用ID
    public String imageUrl;//图标
    public String title;//应用名称
    public String category;//应用类别
    public String summary;//描述
    public String artist;//应用作者
    @ColumnInfo(name = "rating",typeAffinity = ColumnInfo.REAL)
    public float rating;//评分
    @ColumnInfo(name = "commentsNum",typeAffinity = ColumnInfo.INTEGER)
    public Integer commentsNum = null;//评论数

    public ListBean(@NonNull String id) {
        this.id = id;
    }

    @Ignore
    public ListBean(@NonNull String id, String imageUrl, String title, String category, String summary, String artist, float rating, int commentsNum) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.category = category;
        this.summary = summary;
        this.artist = artist;
        this.rating = rating;
        this.commentsNum = commentsNum;
    }


    public ListBean(RecommendBean.FeedBean.EntryBean entryBean) {
        this.id = entryBean.getId().getAttributesBean().getImId();
        if (entryBean.getImImageList() != null && entryBean.getImImageList().size() > 0) {
            String url = entryBean.getImImageList().get(0).getLabel();
            for (int i = 0; i < entryBean.getImImageList().size(); i++) {
                url = entryBean.getImImageList().get(i).getLabel();
            }
            this.imageUrl = url;
        }
        this.title = entryBean.getImName().getLabel();
        this.category = entryBean.getCategory().getAttributesBean().getLabel();
        this.summary = entryBean.getSummary().getLabel();
        this.artist = entryBean.getImArtist().getLabel();
        if (entryBean.getResultsBean() != null){
            this.rating = entryBean.getResultsBean().getRating();
            this.commentsNum = entryBean.getResultsBean().getCommentsNum();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Integer getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(Integer commentsNum) {
        this.commentsNum = commentsNum;
    }
}

