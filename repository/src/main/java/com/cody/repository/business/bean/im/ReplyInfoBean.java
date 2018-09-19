package com.cody.repository.business.bean.im;

import java.io.Serializable;
import java.util.List;

/**
 * Created by  qiaoping.xiao on 2017/9/28.
 */

public class ReplyInfoBean {


    private List<GreetingReplyVoListBean> greetingReplyVoList;
    private List<QuickReplyVoListBean> quickReplyVoList;

    public List<GreetingReplyVoListBean> getGreetingReplyVoList() {
        return greetingReplyVoList;
    }

    public void setGreetingReplyVoList(List<GreetingReplyVoListBean> greetingReplyVoList) {
        this.greetingReplyVoList = greetingReplyVoList;
    }

    public List<QuickReplyVoListBean> getQuickReplyVoList() {
        return quickReplyVoList;
    }

    public void setQuickReplyVoList(List<QuickReplyVoListBean> quickReplyVoList) {
        this.quickReplyVoList = quickReplyVoList;
    }

    public boolean empty(){
        return greetingReplyVoList == null
                || greetingReplyVoList.size() == 0
                || quickReplyVoList == null
                || quickReplyVoList.size() == 0;
    }

    public static class GreetingReplyVoListBean implements Serializable{
        /**
         * ownerId : 2_c1f6148b-b68d-448b-941c-69185a7e0a68
         * id : 388c4bcc-dd5a-43fd-b729-3d83e2c2f43d
         * replyContent : 最近这些商品有优惠活动
         * recommendatoryMerchandise : [{"merchandiseId":"f2476e55-d5ea-45c0-ad36-e0ba10b0233b","imageUrl":"1","merchandiseName":"2","merchandisePrice":3,"merchandiseSku":"4"},{"merchandiseId":"8c0add50-9de4-427a-96ca-1b548e0818e1","imageUrl":"1","merchandiseName":"2","merchandisePrice":3,"merchandiseSku":"5"}]
         * enable : 1
         * createdAt : 1506061528926
         */

        private String ownerId;
        private String id;
        private String replyContent;
        private int enable = 1;
        private long createdAt;
        private List<RecommendatoryMerchandiseBean> recommendatoryMerchandise;

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getReplyContent() {
            return replyContent;
        }

        public void setReplyContent(String replyContent) {
            this.replyContent = replyContent;
        }

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public long getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(long createdAt) {
            this.createdAt = createdAt;
        }

        public List<RecommendatoryMerchandiseBean> getRecommendatoryMerchandise() {
            return recommendatoryMerchandise;
        }

        public void setRecommendatoryMerchandise(List<RecommendatoryMerchandiseBean> recommendatoryMerchandise) {
            this.recommendatoryMerchandise = recommendatoryMerchandise;
        }

        public static class RecommendatoryMerchandiseBean implements Serializable{
            /**
             * merchandiseId : f2476e55-d5ea-45c0-ad36-e0ba10b0233b
             * imageUrl : 1
             * merchandiseName : 2
             * merchandisePrice : 3
             * merchandiseSku : 4
             */

            private String merchandiseId;
            private String imageUrl;
            private String merchandiseName;
            private String merchandisePrice;
            private String merchandiseSku;

            public String getMerchandiseId() {
                return merchandiseId;
            }

            public void setMerchandiseId(String merchandiseId) {
                this.merchandiseId = merchandiseId;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getMerchandiseName() {
                return merchandiseName;
            }

            public void setMerchandiseName(String merchandiseName) {
                this.merchandiseName = merchandiseName;
            }

            public String getMerchandisePrice() {
                return merchandisePrice;
            }

            public void setMerchandisePrice(String merchandisePrice) {
                this.merchandisePrice = merchandisePrice;
            }

            public String getMerchandiseSku() {
                return merchandiseSku;
            }

            public void setMerchandiseSku(String merchandiseSku) {
                this.merchandiseSku = merchandiseSku;
            }
        }
    }

    public static class QuickReplyVoListBean implements Serializable{
        /**
         * ownerId : 2_c1f6148b-b68d-448b-941c-69185a7e0a68
         * id : 0246632f-19dd-4f3f-877e-8c116ab335ed
         * replyContent : quickReply
         * enable : 1
         * createdAt : 1506049388579
         */

        private String ownerId;
        private String id;
        private String replyContent;
        private int enable;
        private long createdAt;
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getReplyContent() {
            return replyContent;
        }

        public void setReplyContent(String replyContent) {
            this.replyContent = replyContent;
        }

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public long getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(long createdAt) {
            this.createdAt = createdAt;
        }
    }
}
