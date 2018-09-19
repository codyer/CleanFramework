package com.cody.handler.business.mapper;


import com.cody.handler.business.viewmodel.ItemBusinessOrderListViewModel;
import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.repository.business.bean.order.BusinessOrderListBean;
import com.cody.xf.utils.NumberUtil;

/**
 * Created by liuliwei on 2018-07-12.
 * 商户订单列表
 UNPAID(1, "待付款"),
 USED(2, "已核销"),
 SUCCESS(3, "交易成功"),
 UNDELIVERED(4, "待发货"),
 DELIVERED(5, "已发货"),
 PART_UNDELIVERED(6, "部分发货"),
 CLOSED(7, "交易关闭"),
 NOT_RECEIVING(8, "待收货"),
 UN_REVIEWED(9, "待评价"),
 AFTER_SALE(10, "售后中"),
 REFOUNDED(12, "已退款"),
 UNCHECKED(13, "待审核"),
 PART_PAID(14, "部分付款"),
 PAID(15, "已付款"),
 LOCKED(16, "已锁定"),
 PART_REFOUNDED(17, "部分退款"),
 REFOUNDING(18, "退款处理中"),
 CANCELED(19, "已撤销"),
 UNREFOUNDED(23, "待退款"),
 REFUND_SUCCESS(24, "退款成功"),
 REFUSED(27, "已拒绝"),;
 */
public class BusinessOrderListMapper extends ModelMapper<ItemBusinessOrderListViewModel, BusinessOrderListBean> {
    @Override
    public ItemBusinessOrderListViewModel mapper(BusinessOrderListBean dataModel, int position) {
        ItemBusinessOrderListViewModel viewModel = new ItemBusinessOrderListViewModel();
        return mapper(viewModel, dataModel);
    }

    @Override
    public ItemBusinessOrderListViewModel mapper(ItemBusinessOrderListViewModel viewModel, BusinessOrderListBean dataModel) {
        viewModel.setOrderId(dataModel.getId());//订单号
        viewModel.setSerialNumber(dataModel.getSerialNumber());
        viewModel.setUserName(dataModel.getPurchaserName());//商户名,默认为第一个
        viewModel.setUserMobile(dataModel.getMobile());
        viewModel.setOrderType(dataModel.getOrderType());
        viewModel.setOrderStatus(dataModel.getOrderStatus());
        viewModel.setOriginalSerialNumber(dataModel.getOriginalSerialNumber());
        viewModel.setLastPaymentDate(dataModel.getLastPaymentDate());

        viewModel.setMobileStr(dataModel.getMobile().replaceAll("(?<=[\\d]{3})\\d(?=[\\d]{4})", "*"));


        viewModel.setPayableAmount("¥"+ NumberUtil.getPrice(dataModel.getPayableAmount())); //订单金额
        viewModel.setPaidAmount("¥"+NumberUtil.getPrice(dataModel.getPaidAmount()));//已付金额
        viewModel.setOrderStatusDesc(dataModel.getOrderStatusDesc());//支付状态
        viewModel.setCreateDate(dataModel.getCreateDate());

        viewModel.setShowPay(false);
        viewModel.setShowAmount(true);
        viewModel.setSaleOrder(true);
        viewModel.setShowTime(true);


        //待付款和交易关闭不显示金额 和 付款时间
        if(dataModel.getOrderStatusValue() == 1 || dataModel.getOrderStatusValue() == 19
                || dataModel.getOrderStatusValue() == 7){
            viewModel.setShowAmount(false);
            viewModel.setShowTime(false);
        }

        //交易成功不显示实付金额
        if(dataModel.getOrderStatusValue()==3){
            viewModel.setShowAmount(false);
        }

        //待付款
        if (dataModel.getOrderStatusValue()==1||dataModel.getOrderStatusValue()==14) {
            viewModel.setShowPay(true);
        }

        //2 5 是退单
        if(dataModel.getOrderType()==2||dataModel.getOrderType()==5){
            viewModel.setSaleOrder(false);
        }

        return viewModel;
    }

    private String getTime(long time1, long time2) {
        StringBuffer sb=new StringBuffer();
        try {
            long diff = time1 - time2;//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);

            sb.append(days+"天");
            if(hours<10){
                sb.append("0"+hours);
            }else{
                sb.append(hours);
            }
            sb.append(":");
            if(minutes<10){
                sb.append("0"+minutes);
            }else{
                sb.append(minutes);
            }

            return sb.toString();
        } catch (Exception e) {
        }
        return "0天00:00";
    }
}
