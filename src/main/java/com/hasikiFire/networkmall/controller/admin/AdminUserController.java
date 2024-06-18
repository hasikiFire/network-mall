package com.hasikiFire.networkmall.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hasikiFire.networkmall.core.common.resp.PageRespDto;
import com.hasikiFire.networkmall.core.common.resp.RestResp;
import com.hasikiFire.networkmall.dao.entity.User;
import com.hasikiFire.networkmall.dto.req.UsedEditDto;
import com.hasikiFire.networkmall.dto.req.UserListReqDto;
import com.hasikiFire.networkmall.dto.resp.UserListRespDto;
import com.hasikiFire.networkmall.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminUserController {
  private final UserService userService;

  @Operation(summary = "查询用户列表")
  @GetMapping("/getUserList")
  public RestResp<PageRespDto<UserListRespDto>> getUserList(@RequestParam UserListReqDto params) {
    return userService.getUserList(params);
  }

  @Operation(summary = "新增/编辑用户")
  @PostMapping("/updateUser")
  public RestResp<User> updateUser(@RequestBody UsedEditDto user) {
    return userService.updateUser(user);
  }

  @Operation(summary = "禁用/删除用户")
  @PostMapping("/changeStatus")
  // `status` tinyint NOT NULL COMMENT '状态 1 正常 0 无效 2 已禁用（触发审计规则）',
  public RestResp<String> deleteUser(
      @Parameter(schema = @Schema(description = "`status` tinyint NOT NULL COMMENT '状态 1 正常 0 无效 2 已禁用（触发审计规则）'")) @RequestParam Integer status) {
    return userService.deleteUser(status);
  }

}
