package com.hasikiFire.networkmall.service.impl;

import com.hasikiFire.networkmall.core.common.constant.DatabaseConsts;
import com.hasikiFire.networkmall.core.common.constant.ErrorCodeEnum;
import com.hasikiFire.networkmall.core.common.exception.BusinessException;
import com.hasikiFire.networkmall.core.common.req.UserLoginReqDto;
import com.hasikiFire.networkmall.core.common.resp.RestResp;
import com.hasikiFire.networkmall.core.util.JwtUtils;
import com.hasikiFire.networkmall.dao.entity.User;
import com.hasikiFire.networkmall.dao.mapper.UserMapper;
import com.hasikiFire.networkmall.dto.req.UserRegisterReqDto;
import com.hasikiFire.networkmall.dto.resp.UserLoginRespDto;
import com.hasikiFire.networkmall.dto.resp.UserRegisterRespDto;
import com.hasikiFire.networkmall.service.UserService;

import lombok.RequiredArgsConstructor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2024/06/03
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

  private final UserMapper userMapper;

  private final JwtUtils jwtUtils;

  @Override
  public RestResp<UserRegisterRespDto> register(UserRegisterReqDto dto) {

    System.out.println("dto: " + dto);
    // 校验图形验证码是否正确
    // if (!verifyCodeManager.imgVerifyCodeOk(dto.getSessionId(), dto.getVelCode()))
    // {
    // // 图形验证码校验失败
    // throw new BusinessException(ErrorCodeEnum.USER_VERIFY_CODE_ERROR);
    // }

    // 校验邮箱是否已注册
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq(DatabaseConsts.UserInfoTable.COLUMN_EMAIL,
        dto.getEmail())
        .last(DatabaseConsts.SqlEnum.LIMIT_1.getSql());
    if (userMapper.selectCount(queryWrapper) > 0) {
      throw new BusinessException(ErrorCodeEnum.EMAIL_EXIST);
    }

    // 注册成功，保存用户信息
    User user = new User();
    user.setPasswordHash(
        DigestUtils.md5DigestAsHex(dto.getPassword().getBytes(StandardCharsets.UTF_8)));
    user.setName(dto.getName());
    user.setEmail(dto.getEmail());
    user.setUserId(12345);
    user.setStatus(0);
    user.setSalt("0");
    userMapper.insert(user);

    // 删除验证码
    // verifyCodeManager.removeImgVerifyCode(dto.getSessionId());

    // 生成JWT 并返回
    return RestResp.ok(
        UserRegisterRespDto.builder()
            .token(jwtUtils.generateToken(user.getId()))
            .uid(user.getId())
            .build());
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
