package com.test.testproject.main.module.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TomLeisen on 2022/7/24 12:46 上午
 * Email: xy162162a@163.com
 * Description:
 */
public class ResultsBean {

    @SerializedName("userRatingCountForCurrentVersion")
    private int commentsNum;//评论数
    @SerializedName("averageUserRating")
    private float rating;//评分
    @SerializedName("averageUserRatingForCurrentVersion")
    private float ratingForCurrentVersion;//评分
    private String trackName;//应用名称

    public int getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(int commentsNum) {
        this.commentsNum = commentsNum;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getRatingForCurrentVersion() {
        return ratingForCurrentVersion;
    }

    public void setRatingForCurrentVersion(float ratingForCurrentVersion) {
        this.ratingForCurrentVersion = ratingForCurrentVersion;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }
}

