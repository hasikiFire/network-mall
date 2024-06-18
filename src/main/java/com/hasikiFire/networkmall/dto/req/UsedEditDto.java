package com.hasikiFire.networkmall.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UsedEditDto {

  @Schema(description = "用户ID")
  private Integer userId;

  @Schema(description = "名字", required = true)
  @NotBlank(message = "名字不能为空！")
  private String name;

  @Schema(description = "邮箱", required = true)
  @NotBlank(message = "邮箱不能为空！")
  @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+).com$", message = "邮箱格式不正确！")
  private String email;

  @Schema(description = "密码", required = true)
  @NotBlank(message = "邮箱不能为空！")
  private String password;

  @Schema(description = "邀请代码")
  private String inviteCode;
}