package com.cody.repository.business.bean.im;

/**
 * Created by  dong.wang
 * Date: 2017/2/13
 * Time: 14:36
 * Description:
 */
public class ChatLogBean {
    /**
     * msgId : null
     * from : 1_9b875c3a-4cd4-4da4-bd1d-9139f8155cf7
     * to : 2_fc5d5ef6-1825-48a2-9ff7-d26192fb6870
     * type : txt
     * timestamp : 1501054751720
     * chat_type : chat
     * msg : 哦哦哦嘻嘻
     * file_length : null
     * filename : null
     * secret : null
     * url : null
     * fastDFSUrl : null
     * fastDFSThumbUrl : null
     * addr : null
     * lat : null
     * lng : null
     * length : null
     * thumb : null
     * thumb_secret : null
     * sourceFileUrl : null
     * sourceThumb : null
     * extra : null
     */

    private String msgId;
    private String from;
    private String to;
    private String type;
    private long timestamp;
    private String chat_type;
    private String msg;
    private float file_length;
    private String filename;
    private Object secret;
    private String url;
    private String fastDFSUrl;
    private String fastDFSThumbUrl;
    private String addr;
    private double lat;
    private double lng;
    private int length;
    private String thumb;
    private String thumb_secret;
    private String sourceFileUrl;
    private String sourceThumb;
    private String extra;

    private String fromOpenId;
    private String fromName;
    private String fromNickName;
    private String fromAvatar;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getChat_type() {
        return chat_type;
    }

    public void setChat_type(String chat_type) {
        this.chat_type = chat_type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public float getFile_length() {
        return file_length;
    }

    public void setFile_length(float file_length) {
        this.file_length = file_length;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Object getSecret() {
        return secret;
    }

    public void setSecret(Object secret) {
        this.secret = secret;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFastDFSUrl() {
        return fastDFSUrl;
    }

    public void setFastDFSUrl(String fastDFSUrl) {
        this.fastDFSUrl = fastDFSUrl;
    }

    public String getFastDFSThumbUrl() {
        return fastDFSThumbUrl;
    }

    public void setFastDFSThumbUrl(String fastDFSThumbUrl) {
        this.fastDFSThumbUrl = fastDFSThumbUrl;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getThumb_secret() {
        return thumb_secret;
    }

    public void setThumb_secret(String thumb_secret) {
        this.thumb_secret = thumb_secret;
    }

    public String getSourceFileUrl() {
        return sourceFileUrl;
    }

    public void setSourceFileUrl(String sourceFileUrl) {
        this.sourceFileUrl = sourceFileUrl;
    }

    public String getSourceThumb() {
        return sourceThumb;
    }

    public void setSourceThumb(String sourceThumb) {
        this.sourceThumb = sourceThumb;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getFromOpenId() {
        return fromOpenId;
    }

    public void setFromOpenId(String fromOpenId) {
        this.fromOpenId = fromOpenId;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromNickName() {
        return fromNickName;
    }

    public void setFromNickName(String fromNickName) {
        this.fromNickName = fromNickName;
    }

    public String getFromAvatar() {
        return fromAvatar;
    }

    public void setFromAvatar(String fromAvatar) {
        this.fromAvatar = fromAvatar;
    }
}
