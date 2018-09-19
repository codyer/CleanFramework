package com.cody.repository.business.bean;

/**
 * Created by cody.yi on 2017/5/18.
 * 图片
 */
public class ImageBean {
    /**
     * imgUrl : http://img1.dev.rs.com/g1/M00/00/47/wKh6y1kdKZCAOXbgAADNtgooW8M996.jpg
     */

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
