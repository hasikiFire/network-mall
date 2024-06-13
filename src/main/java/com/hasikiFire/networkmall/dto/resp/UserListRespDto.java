package com.hasikiFire.networkmall.dto.resp;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserListRespDto {

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "名字")
    private String name;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "状态 1 正常 0 无效 2 已禁用（触发审计规则）")
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
