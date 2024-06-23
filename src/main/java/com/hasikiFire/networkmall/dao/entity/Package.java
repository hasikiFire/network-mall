package com.hasikiFire.networkmall.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 套餐表
 * </p>
 *
 * @author ${author}
 * @since 2024/06/03
 */
public class Package implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 套餐主键
     */
    private Integer packageId;

    /**
     * 套餐名称
     */
    private String packageName;

    /**
     * 套餐描述
     */
    private String packageDesc;

    /**
     * 状态。 0: 未启用 1：活动，2：下架
     */
    private Integer packageStatus;

    /**
     * 商品原价
     */
    private BigDecimal originalPrice;

    /**
     * 商品销售价
     */
    private BigDecimal salePrice;

    /**
     * 折扣百分比
     */
    private BigDecimal discount;

    /**
     * 折扣开始日期
     */
    private LocalDateTime discountStartDate;

    /**
     * 折扣结束日期
     */
    private LocalDateTime discountEndDate;

    /**
     * 数据流量限额（单位：GB）。无值表示无限制
     */
    private Integer dataAllowance;

    /**
     * 设备数量限制。无值表示无限制
     */
    private Integer deviceLimit;

    /**
     * 速率限制（单位：Mbps）。无值表示无限制
     */
    private Integer speedLimit;

    /**
     * 是否已删除 1：已删除 0：未删除
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageDesc() {
        return packageDesc;
    }

    public void setPackageDesc(String packageDesc) {
        this.packageDesc = packageDesc;
    }

    public Integer getPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(Integer packageStatus) {
        this.packageStatus = packageStatus;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public LocalDateTime getDiscountStartDate() {
        return discountStartDate;
    }

    public void setDiscountStartDate(LocalDateTime discountStartDate) {
        this.discountStartDate = discountStartDate;
    }

    public LocalDateTime getDiscountEndDate() {
        return discountEndDate;
    }

    public void setDiscountEndDate(LocalDateTime discountEndDate) {
        this.discountEndDate = discountEndDate;
    }

    public Integer getDataAllowance() {
        return dataAllowance;
    }

    public void setDataAllowance(Integer dataAllowance) {
        this.dataAllowance = dataAllowance;
    }

    public Integer getDeviceLimit() {
        return deviceLimit;
    }

    public void setDeviceLimit(Integer deviceLimit) {
        this.deviceLimit = deviceLimit;
    }

    public Integer getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(Integer speedLimit) {
        this.speedLimit = speedLimit;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Package{" +
                "id=" + id +
                ", packageId=" + packageId +
                ", packageName=" + packageName +
                ", packageDesc=" + packageDesc +
                ", packageStatus=" + packageStatus +
                ", originalPrice=" + originalPrice +
                ", salePrice=" + salePrice +
                ", discount=" + discount +
                ", discountStartDate=" + discountStartDate +
                ", discountEndDate=" + discountEndDate +
                ", dataAllowance=" + dataAllowance +
                ", deviceLimit=" + deviceLimit +
                ", speedLimit=" + speedLimit +
                ", deleted=" + deleted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                "}";
    }
}
