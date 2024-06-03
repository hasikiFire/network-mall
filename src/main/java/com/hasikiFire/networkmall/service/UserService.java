package com.hasikiFire.networkmall.service;

import com.hasikiFire.networkmall.core.common.req.UserLoginReqDto;
import com.hasikiFire.networkmall.core.common.resp.RestResp;
import com.hasikiFire.networkmall.core.common.resp.UserInfoRespDto;
import com.hasikiFire.networkmall.dao.entity.User;
import com.hasikiFire.networkmall.dto.req.UserRegisterReqDto;
import com.hasikiFire.networkmall.dto.resp.UserLoginRespDto;
import com.hasikiFire.networkmall.dto.resp.UserRegisterRespDto;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2024/06/03
 */
public interface UserService extends IService<User> {
  /**
   * 用户注册
   *
   * @param dto 注册参数
   * @return JWT
   */
  RestResp<UserRegisterRespDto> register(UserRegisterReqDto dto);

  /**
   * 用户登录
   *
   * @param dto 登录参数
   * @return JWT + 昵称
   */
  RestResp<UserLoginRespDto> login(UserLoginReqDto dto);

  /**
   * 用户登录
   *
   * @param dto 登录参数
   * @return JWT + 昵称
   */
  Map getUserInfo(Long userId);

}
