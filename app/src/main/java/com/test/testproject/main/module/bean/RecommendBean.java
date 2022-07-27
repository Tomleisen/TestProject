package com.test.testproject.main.module.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TomLeisen on 2022/7/23 1:09 上午
 * Email: xy162162a@163.com
 * Description:
 */
public class RecommendBean {

    private FeedBean feed;

    public FeedBean getFeed() {
        return feed;
    }

    public void setFeed(FeedBean feed) {
        this.feed = feed;
    }



    public static class FeedBean {
        @SerializedName("entry")
        private List<FeedBean.EntryBean> entryList;

        public List<EntryBean> getEntryList() {
            return entryList;
        }

        public void setEntryList(List<EntryBean> entryList) {
            this.entryList = entryList;
        }

        public static class EntryBean {
            @SerializedName("im:name")
            private LabelBean imName;//title
            @SerializedName("im:image")
            private List<LabelBean> imImageList;//图标
            private LabelBean summary;//描述
            @SerializedName("im:price")
            private LabelBean imPrice;//价格
            @SerializedName("im:contentType")
            private LabelBean imContentType;//内容类型
            private LabelBean rights;
            private LabelBean title;
            private LabelBean id;
            @SerializedName("im:artist")
            private LabelBean imArtist;//作者
            private LabelBean category;//类别
            @SerializedName("im:releaseDate")
            private LabelBean imReleaseDate;

            //评分数据
            private ResultsBean resultsBean;

            public ResultsBean getResultsBean() {
                return resultsBean;
            }

            public void setResultsBean(ResultsBean resultsBean) {
                this.resultsBean = resultsBean;
            }

            public LabelBean getImName() {
                return imName;
            }

            public void setImName(LabelBean imName) {
                this.imName = imName;
            }

            public List<LabelBean> getImImageList() {
                return imImageList;
            }

            public void setImImageList(List<LabelBean> imImageList) {
                this.imImageList = imImageList;
            }

            public LabelBean getSummary() {
                return summary;
            }

            public void setSummary(LabelBean summary) {
                this.summary = summary;
            }

            public LabelBean getImPrice() {
                return imPrice;
            }

            public void setImPrice(LabelBean imPrice) {
                this.imPrice = imPrice;
            }

            public LabelBean getImContentType() {
                return imContentType;
            }

            public void setImContentType(LabelBean imContentType) {
                this.imContentType = imContentType;
            }

            public LabelBean getRights() {
                return rights;
            }

            public void setRights(LabelBean rights) {
                this.rights = rights;
            }

            public LabelBean getTitle() {
                return title;
            }

            public void setTitle(LabelBean title) {
                this.title = title;
            }

            public LabelBean getId() {
                return id;
            }

            public void setId(LabelBean id) {
                this.id = id;
            }

            public LabelBean getImArtist() {
                return imArtist;
            }

            public void setImArtist(LabelBean imArtist) {
                this.imArtist = imArtist;
            }

            public LabelBean getCategory() {
                return category;
            }

            public void setCategory(LabelBean category) {
                this.category = category;
            }

            public LabelBean getImReleaseDate() {
                return imReleaseDate;
            }

            public void setImReleaseDate(LabelBean imReleaseDate) {
                this.imReleaseDate = imReleaseDate;
            }
        }
    }
    
} 

