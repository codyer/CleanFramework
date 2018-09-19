package com.cody.repository.business.bean.order;

import java.util.List;

/**
 * Ta 的订单
 */
public class CustomerOrderListBean {
    private int totalPage ;
    private int currentPage;
    private int showCount;
    private int totalResult;
    private boolean hasNextPage;

    public List<OrderBean> getData() {
        return data;
    }

    public void setData(List<OrderBean> data) {
        this.data = data;
    }

    private List<OrderBean> data;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getShowCount() {
        return showCount;
    }

    public void setShowCount(int showCount) {
        this.showCount = showCount;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public class OrderBean {
        private int id;
        private String userName;
        private String serialNumber;
        private String orderType;
        private String orderTypeDesc;
        private String orderStatus;
        private String orderStatusDesc;
        private String createDate;
        private double payableAmount;
        private double paidAmount;
        private String mobile;
        private String purchaserId;
        private String purchaserName;
        private String provinceName;
        private String cityName;
        private String districtName;
        private String address;
        private List<OrderItemBean> orderItems;
        private double orderItemQuantity;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getOrderTypeDesc() {
            return orderTypeDesc;
        }

        public void setOrderTypeDesc(String orderTypeDesc) {
            this.orderTypeDesc = orderTypeDesc;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderStatusDesc() {
            return orderStatusDesc;
        }

        public void setOrderStatusDesc(String orderStatusDesc) {
            this.orderStatusDesc = orderStatusDesc;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public double getPayableAmount() {
            return payableAmount;
        }

        public void setPayableAmount(double payableAmount) {
            this.payableAmount = payableAmount;
        }

        public double getPaidAmount() {
            return paidAmount;
        }

        public void setPaidAmount(double paidAmount) {
            this.paidAmount = paidAmount;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPurchaserId() {
            return purchaserId;
        }

        public void setPurchaserId(String purchaserId) {
            this.purchaserId = purchaserId;
        }

        public String getPurchaserName() {
            return purchaserName;
        }

        public void setPurchaserName(String purchaserName) {
            this.purchaserName = purchaserName;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getDistrictName() {
            return districtName;
        }

        public void setDistrictName(String districtName) {
            this.districtName = districtName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public List<OrderItemBean> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(List<OrderItemBean> orderItems) {
            this.orderItems = orderItems;
        }

        public double getOrderItemQuantity() {
            return orderItemQuantity;
        }

        public void setOrderItemQuantity(double orderItemQuantity) {
            this.orderItemQuantity = orderItemQuantity;
        }

        public class OrderItemBean {
            private int id;
            private String productName;
            private String merchantName;
            private String brandName;
            private double quantity;
            private String imgUrl;
            private String signedCode;
            private String model;
            private String size;
            private String sizeUnit;
            private String serialCode;
            private String serialName;
            private String description;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getMerchantName() {
                return merchantName;
            }

            public void setMerchantName(String merchantName) {
                this.merchantName = merchantName;
            }

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }

            public double getQuantity() {
                return quantity;
            }

            public void setQuantity(double quantity) {
                this.quantity = quantity;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getSignedCode() {
                return signedCode;
            }

            public void setSignedCode(String signedCode) {
                this.signedCode = signedCode;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public String getSizeUnit() {
                return sizeUnit;
            }

            public void setSizeUnit(String sizeUnit) {
                this.sizeUnit = sizeUnit;
            }

            public String getSerialCode() {
                return serialCode;
            }

            public void setSerialCode(String serialCode) {
                this.serialCode = serialCode;
            }

            public String getSerialName() {
                return serialName;
            }

            public void setSerialName(String serialName) {
                this.serialName = serialName;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

        }
    }
}
