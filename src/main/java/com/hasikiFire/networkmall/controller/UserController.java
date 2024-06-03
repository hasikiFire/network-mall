package com.hasikiFire.networkmall.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.hasikiFire.networkmall.core.common.req.UserLoginReqDto;
import com.hasikiFire.networkmall.core.common.resp.RestResp;
import com.hasikiFire.networkmall.core.common.resp.UserInfoRespDto;
import com.hasikiFire.networkmall.dto.req.UserRegisterReqDto;
import com.hasikiFire.networkmall.dto.resp.UserLoginRespDto;
import com.hasikiFire.networkmall.dto.resp.UserRegisterRespDto;
import com.hasikiFire.networkmall.service.UserService;

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
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  /**
   * 用户注册接口
   */
  @Operation(summary = "用户注册接口")
  @PostMapping("register")
  public RestResp<UserRegisterRespDto> register(@Valid @RequestBody UserRegisterReqDto dto) {
    return userService.register(dto);
  }

  /**
   * 用户登录接口
   */
  @Operation(summary = "用户登录接口")
  @PostMapping("login")
  public RestResp<UserLoginRespDto> login(@Valid @RequestBody UserLoginReqDto dto) {
    return userService.login(dto);
  }

  /**
   * 用户信息查询接口
   */
  @Operation(summary = "用户信息查询接口")
  @GetMapping("getUserInfo")
  public Map getUserInfo() {
    return userService.getUserInfo((long) 12312312);
  }
}
