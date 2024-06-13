package com.hasikiFire.networkmall.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.hasikiFire.networkmall.core.common.resp.RestResp;
import com.hasikiFire.networkmall.dto.req.UserLoginReqDto;
import com.hasikiFire.networkmall.dto.req.UserRegisterReqDto;
import com.hasikiFire.networkmall.dto.req.UsersendEmailCodeDto;
import com.hasikiFire.networkmall.dto.resp.UserInfoRespDto;
import com.hasikiFire.networkmall.dto.resp.UserLoginRespDto;
import com.hasikiFire.networkmall.dto.resp.UserRegisterRespDto;
import com.hasikiFire.networkmall.service.UserService;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2024/06/03
 */
@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  /**
   * 用户注册接口
   */
  @Operation(summary = "用户注册接口")
  @PostMapping("/user/register")
  public RestResp<UserRegisterRespDto> register(@Valid @RequestBody UserRegisterReqDto dto) {
    return userService.register(dto);
  }

  /**
   * 用户登录接口
   */
  @Operation(summary = "用户登录接口")
  @PostMapping("/user/login")
  public RestResp<UserLoginRespDto> login(@Valid @RequestBody UserLoginReqDto dto) {
    return userService.login(dto);
  }

  /**
   * 用户信息查询接口
   */
  @Operation(summary = "用户信息查询接口")
  @GetMapping("/user/getUserInfo")
  public RestResp<UserInfoRespDto> getUserInfo(Integer userId) {
    return userService.getUserInfo(userId);
  }

  /**
   * Send email verification code interface
   * 
   * @return
   */
  @Operation(summary = "Send email verification code interface")
  @PostMapping("/user/sendEmailVerificationCode")
  public RestResp<Void> sendEmailVerificationCode(@Valid @RequestBody UsersendEmailCodeDto email) {
    return userService.sendEmailVerificationCode(email);
  }

  // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
  @Operation(summary = "查询登录状态")
  @GetMapping("/user/isLogin")
  public String isLogin() {
    return "当前会话是否登录：" + StpUtil.isLogin();
  }

  // TODO 需要思考要放啥数据
  @Operation(summary = "查询用户列表")
  @GetMapping("/admin/getUserList")
  public RestResp<Void> getUserList() {
    return userService.getUserList();
  }

  // @Operation(summary = "重置密码接口") TODO
  // @PostMapping("resetPassword")
  // public RestResp<UserLoginRespDto> resetPassword(String email) {
  // return userService.resetPassword(email);
  // // https://juejin.cn/post/6844903777246560263
  // }
}
