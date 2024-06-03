package com.hasikiFire.networkmall.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 支付订单交易记录表
 * </p>
 *
 * @author ${author}
 * @since 2024/06/03
 */
@TableName("pay_order_tradelog")
public class PayOrderTradelog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户请求代理信息
     */
    private String userAgent;

    /**
     * 业务订单号
     */
    private String orderCode;

    /**
     * 商户号
     */
    private String supplierId;

    /**
     * 交易方式:wxpay(微信交易), alipay(支付宝交易), USTD(加密货币交易)
     */
    private String tradeWay;

    /**
     * 交易类型:facePage(当面付), h5Pay(H5支付)、appPay(APP支付)、nativePay(扫码支付)、unifedPay(公众号支付)、refund(退款)、calback(回调)、cancel(撤单)
     */
    private String tradeType;

    /**
     * 交易请求数据
     */
    private String tradeReqData;

    /**
     * 交易请求时间
     */
    private LocalDateTime tradeReqTime;

    /**
     * 交易返回数据
     */
    private String tradeRespData;

    /**
     * 交易返回时间
     */
    private LocalDateTime tradeRespTime;

    /**
     * 数据创建时间
     */
    private LocalDateTime createTime;

    /**
     * 数据修改时间
     */
    private LocalDateTime updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getTradeWay() {
        return tradeWay;
    }

    public void setTradeWay(String tradeWay) {
        this.tradeWay = tradeWay;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeReqData() {
        return tradeReqData;
    }

    public void setTradeReqData(String tradeReqData) {
        this.tradeReqData = tradeReqData;
    }

    public LocalDateTime getTradeReqTime() {
        return tradeReqTime;
    }

    public void setTradeReqTime(LocalDateTime tradeReqTime) {
        this.tradeReqTime = tradeReqTime;
    }

    public String getTradeRespData() {
        return tradeRespData;
    }

    public void setTradeRespData(String tradeRespData) {
        this.tradeRespData = tradeRespData;
    }

    public LocalDateTime getTradeRespTime() {
        return tradeRespTime;
    }

    public void setTradeRespTime(LocalDateTime tradeRespTime) {
        this.tradeRespTime = tradeRespTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "PayOrderTradelog{" +
        "id=" + id +
        ", userId=" + userId +
        ", userAgent=" + userAgent +
        ", orderCode=" + orderCode +
        ", supplierId=" + supplierId +
        ", tradeWay=" + tradeWay +
        ", tradeType=" + tradeType +
        ", tradeReqData=" + tradeReqData +
        ", tradeReqTime=" + tradeReqTime +
        ", tradeRespData=" + tradeRespData +
        ", tradeRespTime=" + tradeRespTime +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
