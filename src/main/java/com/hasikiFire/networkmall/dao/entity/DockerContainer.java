package com.hasikiFire.networkmall.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * docker容器信息表
 * </p>
 *
 * @author ${author}
 * @since 2024/06/03
 */
@TableName("docker_container")
public class DockerContainer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 容器的ID
     */
    private Integer containerId;

    /**
     * 容器的名称
     */
    private String containerName;

    /**
     * 关联的服务器ID
     */
    private Integer serverId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 套餐计划主键。取速度限制，设备限制等信息
     */
    private Integer packageId;

    /**
     * 已购套餐主键。取到期时间等信息
     */
    private Integer purchaseId;

    /**
     * 与容器绑定的端口
     */
    private Integer boundPort;

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

    public Integer getContainerId() {
        return containerId;
    }

    public void setContainerId(Integer containerId) {
        this.containerId = containerId;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Integer getBoundPort() {
        return boundPort;
    }

    public void setBoundPort(Integer boundPort) {
        this.boundPort = boundPort;
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
        return "DockerContainer{" +
        "id=" + id +
        ", containerId=" + containerId +
        ", containerName=" + containerName +
        ", serverId=" + serverId +
        ", userId=" + userId +
        ", packageId=" + packageId +
        ", purchaseId=" + purchaseId +
        ", boundPort=" + boundPort +
        ", deleted=" + deleted +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
