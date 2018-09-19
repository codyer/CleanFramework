package com.cody.repository.business.bean;

import java.util.List;

/**
 * Created by shijia on 2018/1/19.
 */

public class ImageListBean {

    private List<?> fail;
    private List<SucBean> suc;

    public List<?> getFail() {
        return fail;
    }

    public void setFail(List<?> fail) {
        this.fail = fail;
    }

    public List<SucBean> getSuc() {
        return suc;
    }

    public void setSuc(List<SucBean> suc) {
        this.suc = suc;
    }

    public static class SucBean {
        private long fileId;
        private String fileNo;
        private String fileUrl;
        private double height;
        private String originalName;
        private int size;
        private double width;

        public long getFileId() {
            return fileId;
        }

        public void setFileId(long fileId) {
            this.fileId = fileId;
        }

        public String getFileNo() {
            return fileNo;
        }

        public void setFileNo(String fileNo) {
            this.fileNo = fileNo;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public String getOriginalName() {
            return originalName;
        }

        public void setOriginalName(String originalName) {
            this.originalName = originalName;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }
    }
}
