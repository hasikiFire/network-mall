package com.hasikiFire.networkmall.service.impl;

import com.hasikiFire.networkmall.core.common.req.UserLoginReqDto;
import com.hasikiFire.networkmall.core.common.resp.RestResp;
import com.hasikiFire.networkmall.core.common.resp.UserInfoRespDto;
import com.hasikiFire.networkmall.dao.entity.User;
import com.hasikiFire.networkmall.dao.mapper.UserMapper;
import com.hasikiFire.networkmall.dto.req.UserRegisterReqDto;
import com.hasikiFire.networkmall.dto.resp.UserLoginRespDto;
import com.hasikiFire.networkmall.dto.resp.UserRegisterRespDto;
import com.hasikiFire.networkmall.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2024/06/03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

  @Override
  public RestResp<UserRegisterRespDto> register(UserRegisterReqDto dto) {
    return null;
  }

  @Override
  public RestResp<UserLoginRespDto> login(UserLoginReqDto dto) {
    Map map = new HashMap<>();
    map.put("name", "fangxiao");
    map.put("age", "15");
    return (RestResp<UserLoginRespDto>) map;

  }

  @Override
  public Map getUserInfo(Long id) {
    Map map = new HashMap<>();
    map.put("name", "fangxiao");
    map.put("age", "15");
    System.out.println("id = " + id);
    return map;

  }
}
