package com.hasikiFire.networkmall.service.impl;

import com.hasikiFire.networkmall.core.common.constant.DatabaseConsts;
import com.hasikiFire.networkmall.core.common.constant.ErrorCodeEnum;
import com.hasikiFire.networkmall.core.common.exception.BusinessException;
import com.hasikiFire.networkmall.core.common.req.UserLoginReqDto;
import com.hasikiFire.networkmall.core.common.resp.RestResp;
import com.hasikiFire.networkmall.core.util.JwtUtils;
import com.hasikiFire.networkmall.core.util.PasswordUtils;
import com.hasikiFire.networkmall.dao.entity.User;
import com.hasikiFire.networkmall.dao.entity.UserId;
import com.hasikiFire.networkmall.dao.mapper.UserIdMapper;
import com.hasikiFire.networkmall.dao.mapper.UserMapper;
import com.hasikiFire.networkmall.dto.req.UserRegisterReqDto;
import com.hasikiFire.networkmall.dto.req.UsersendEmailCodeDto;
import com.hasikiFire.networkmall.dto.resp.UserLoginRespDto;
import com.hasikiFire.networkmall.dto.resp.UserRegisterRespDto;
import com.hasikiFire.networkmall.service.UserService;

import lombok.RequiredArgsConstructor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

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

  private final UserIdMapper userIDMapper;

  private final JwtUtils jwtUtils;

  @Autowired
  private JavaMailSender javaMailSender;

  @Override
  public RestResp<UserRegisterRespDto> register(UserRegisterReqDto dto) {

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
      throw new BusinessException("邮箱已被注册");
    }
    // 随机取一个未使用的用户ID
    QueryWrapper<UserId> queryWrapperUserID = new QueryWrapper<>();
    queryWrapperUserID.eq("status", 0).orderByAsc("RAND()").last("LIMIT 1");
    UserId userId = userIDMapper.selectOne(queryWrapperUserID);
    if (userId != null) {
      userId.setStatus(1);
      userIDMapper.updateById(userId);
    }

    // 注册成功，保存用户信息
    User user = new User();
    String salt = PasswordUtils.generateSalt();
    String passwordHash = DigestUtils.md5DigestAsHex(
        (dto.getPassword() + salt).getBytes(StandardCharsets.UTF_8));
    user.setPasswordHash(
        passwordHash);
    user.setName(dto.getName());
    user.setEmail(dto.getEmail());
    user.setUserId(userId.getUserId());
    user.setStatus(1);
    user.setSalt(salt);
    userMapper.insert(user);

    // 删除验证码
    // verifyCodeManager.removeImgVerifyCode(dto.getSessionId());

    // 生成JWT 并返回
    return RestResp.ok(
        UserRegisterRespDto.builder()
            .token(jwtUtils.generateToken(user.getUserId()))
            .uid(user.getUserId())
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

  @Override
  public RestResp<Void> sendEmailVerificationCode(UsersendEmailCodeDto email) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      String code = RandomStringUtils.randomNumeric(4);
      message.setTo(email.getEmail());
      message.setSubject("Verification Code");
      message.setText("Your verification code is " + code);
      javaMailSender.send(message);
      return RestResp.ok();
    } catch (MailException e) {
      throw new BusinessException(e.getMessage());
    }

  }
}
