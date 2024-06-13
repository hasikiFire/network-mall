/*
 * @Author: hasikiFire
 * @Date: 2024-06-04 11:13:28
 */
/*
 * @Author: hasikiFire
 * @Date: 2024-06-04 11:13:28
 */
package com.hasikiFire.networkmall.service.impl;

import com.hasikiFire.networkmall.core.common.constant.DatabaseConsts;
import com.hasikiFire.networkmall.core.common.constant.DatabaseConsts.RolesTable.RoleEnum;
import com.hasikiFire.networkmall.core.common.constant.SendCodeTypeEnum;
import com.hasikiFire.networkmall.core.common.exception.BusinessException;
import com.hasikiFire.networkmall.core.common.resp.PageRespDto;
import com.hasikiFire.networkmall.core.common.resp.RestResp;
import com.hasikiFire.networkmall.core.util.PasswordUtils;
import com.hasikiFire.networkmall.core.util.RedisUtil;
import com.hasikiFire.networkmall.dao.entity.Roles;
import com.hasikiFire.networkmall.dao.entity.User;
import com.hasikiFire.networkmall.dao.entity.UserId;
import com.hasikiFire.networkmall.dao.mapper.PackagePurchaseRecordMapper;
import com.hasikiFire.networkmall.dao.mapper.RolesMapper;
import com.hasikiFire.networkmall.dao.mapper.UserIdMapper;
import com.hasikiFire.networkmall.dao.mapper.UserMapper;
import com.hasikiFire.networkmall.dao.mapper.WalletMapper;
import com.hasikiFire.networkmall.dto.req.UserListReqDto;
import com.hasikiFire.networkmall.dto.req.UserLoginReqDto;
import com.hasikiFire.networkmall.dto.req.UserRegisterReqDto;
import com.hasikiFire.networkmall.dto.req.UsersendEmailCodeDto;
import com.hasikiFire.networkmall.dto.resp.UserInfoRespDto;
import com.hasikiFire.networkmall.dto.resp.UserListRespDto;
import com.hasikiFire.networkmall.dto.resp.UserLoginRespDto;
import com.hasikiFire.networkmall.dto.resp.UserRegisterRespDto;
import com.hasikiFire.networkmall.service.UserService;

import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
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

  @Value("${spring.mail.username}")
  private String username;

  private final UserMapper userMapper;

  private final UserIdMapper userIDMapper;

  private final WalletMapper walletMapper;
  private final PackagePurchaseRecordMapper packagePurchaseRecordMapper;

  private final RolesMapper roleMapper;

  @Autowired
  private RedisUtil redisUtil;

  @Autowired
  private JavaMailSender javaMailSender;

  @Override
  public RestResp<UserRegisterRespDto> register(UserRegisterReqDto dto) {
    String redisKey = SendCodeTypeEnum.REGISTER.getType() + ":" + dto.getEmail();
    String emailCode = (String) redisUtil.get(redisKey);

    // 校验验证码是否正确
    if (emailCode == null || !emailCode.equals(dto.getVelCode())) {
      // 验证码校验失败
      throw new BusinessException("验证码错误");
    }

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
    UserId userIdDto = userIDMapper.selectOne(queryWrapperUserID);
    if (userIdDto == null) {
      throw new BusinessException("用户ID已用完");
    }
    userIdDto.setStatus(1);
    userIDMapper.updateById(userIdDto);
    Integer userID = userIdDto.getUserId();
    // 注册成功，保存用户信息
    User user = new User();
    String salt = PasswordUtils.generateSalt();
    String passwordHash = DigestUtils.md5DigestAsHex(
        (dto.getPassword() + salt).getBytes(StandardCharsets.UTF_8));
    user.setPasswordHash(
        passwordHash);
    user.setName(dto.getName());
    user.setEmail(dto.getEmail());
    user.setUserId(userID);
    user.setStatus(1);
    user.setSalt(salt);
    userMapper.insert(user);

    // 删除验证码
    redisUtil.delete(redisKey);
    Roles role = new Roles();
    role.setUserId(user.getUserId());
    role.setRoleName(RoleEnum.USER.getRoleName());
    roleMapper.insert(role);

    StpUtil.login(user.getUserId());
    String token = StpUtil.getTokenValue();
    // 生成JWT 并返回
    return RestResp.ok(
        UserRegisterRespDto.builder()
            .token(token)
            .uid(user.getUserId())
            .build());
  }

  @Override
  public RestResp<UserLoginRespDto> login(UserLoginReqDto dto) {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq(DatabaseConsts.UserInfoTable.COLUMN_EMAIL,
        dto.getEmail())
        .last(DatabaseConsts.SqlEnum.LIMIT_1.getSql());
    User user = userMapper.selectOne(queryWrapper);
    if (user == null) {
      throw new BusinessException("邮箱或者密码错误");
    }
    String passwordHash = DigestUtils.md5DigestAsHex(
        (dto.getPassword() + user.getSalt()).getBytes(StandardCharsets.UTF_8));

    if (!passwordHash.equals(user.getPasswordHash())) {
      throw new BusinessException("邮箱或者密码错误");
    }

    StpUtil.login(user.getUserId());
    String token = StpUtil.getTokenValue();

    return RestResp.ok(
        UserLoginRespDto.builder()
            .token(token)
            .uid(user.getUserId())
            .build());

  }

  @Override
  public RestResp<UserInfoRespDto> getUserInfo(Integer id) {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq(DatabaseConsts.UserInfoTable.COLUMN_USERID,
        id)
        .last(DatabaseConsts.SqlEnum.LIMIT_1.getSql());
    User user = userMapper.selectOne(queryWrapper);
    if (user == null) {
      throw new BusinessException("用户不存在");
    }
    return RestResp.ok(
        UserInfoRespDto.builder()
            .userId(user.getUserId())
            .name(user.getName())
            .email(user.getEmail())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build());
  }

  @Override
  public RestResp<Void> sendEmailVerificationCode(UsersendEmailCodeDto emailDto) {
    try {
      String redisKey = emailDto.getType().getType() + ":" + emailDto.getEmail();
      String code = RandomStringUtils.randomNumeric(4);
      redisUtil.set(redisKey, code, 10, TimeUnit.MINUTES);
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom(username);
      message.setTo(emailDto.getEmail());
      message.setSubject("验证码");
      message.setText("本次" + emailDto.getType().getDesc() + "为: " + code + " ，有效期为 10 分钟");
      javaMailSender.send(message);
      return RestResp.ok();
    } catch (MailException e) {
      throw new BusinessException(e.getMessage());
    }
  }

  @Override
  public RestResp<PageRespDto<UserListRespDto>> getUserList(UserListReqDto params) {
    IPage<User> page = new Page<>();
    page.setCurrent(params.getPageNum());
    page.setSize(params.getPageSize());

    LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
    queryWrapper.eq(User::getDeleted, 0);
    if (params.getName() != null) {
      queryWrapper.like(User::getName, params.getName());
    }
    if (params.getEmail() != null) {
      queryWrapper.like(User::getEmail, params.getEmail());
    }

    queryWrapper.orderByDesc(User::getUpdatedAt);
    queryWrapper.last("LIMIT " + params.getPageSize());
    IPage<User> usersPage = userMapper.selectPage(page, queryWrapper);
    List<User> users = usersPage.getRecords();

    // TODO 聚合查询

    // walletMapper.selectList(new QueryWrapper<>());
    // packagePurchaseRecordMapper.selectList(new QueryWrapper<>());

    return RestResp.ok(
        PageRespDto.of(params.getPageNum(), params.getPageSize(), page.getTotal(),
            users.stream().map(user -> UserListRespDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build()).collect(Collectors.toList())));

  }

}
