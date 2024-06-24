package com.hasikiFire.networkmall.service.impl;

import com.hasikiFire.networkmall.core.common.req.PageReqDto;
import com.hasikiFire.networkmall.core.common.resp.PageRespDto;
import com.hasikiFire.networkmall.core.common.resp.RestResp;
import com.hasikiFire.networkmall.dao.entity.PackageItem;
import com.hasikiFire.networkmall.dao.entity.User;
import com.hasikiFire.networkmall.dao.mapper.PackageMapper;
import com.hasikiFire.networkmall.dto.req.PackageAddReqDto;
import com.hasikiFire.networkmall.dto.req.PackageBuyReqDto;
import com.hasikiFire.networkmall.dto.req.PackageEditReqDto;
import com.hasikiFire.networkmall.dto.req.PackageListReqDto;
import com.hasikiFire.networkmall.dto.resp.PackageListRespDto;
import com.hasikiFire.networkmall.dto.resp.PackageRespDto;
import com.hasikiFire.networkmall.dto.resp.UserListRespDto;
import com.hasikiFire.networkmall.service.PackageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 套餐表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2024/06/03
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PackageServiceImpl extends ServiceImpl<PackageMapper, PackageItem> implements PackageService {

  private final PackageMapper packageMapper;

  @Override
  public RestResp<PageRespDto<PackageListRespDto>> getUserPackageList(PageReqDto reqDto) {

    IPage<PackageItem> page = new Page<>();
    page.setCurrent(reqDto.getPageNum());
    page.setSize(reqDto.getPageSize());

    LambdaQueryWrapper<PackageItem> queryWrapper = new LambdaQueryWrapper<PackageItem>();
    queryWrapper.eq(PackageItem::getPackageStatus, 1);

    IPage<PackageItem> pPage = packageMapper.selectPage(page, queryWrapper);
    List<PackageItem> packages = pPage.getRecords();
    log.info("getUserPackageList pPage: {}", pPage);
    List<PackageListRespDto> packageListRespDtos = packages.stream().map(p -> {
      return PackageListRespDto.builder().id(p.getId()).packageId(p.getPackageId()).packageName(p.getPackageName())
          .packageDesc(p.getPackageDesc()).packageStatus(p.getPackageStatus()).originalPrice(p.getOriginalPrice())
          .salePrice(p.getSalePrice()).discount(p.getDiscount()).discountStartDate(p.getDiscountStartDate())
          .discountEndDate(p.getDiscountEndDate()).dataAllowance(p.getDataAllowance()).deviceLimit(p.getDeviceLimit())
          .speedLimit(p.getSpeedLimit()).deleted(p.getDeleted()).createdAt(p.getCreatedAt()).updatedAt(p.getUpdatedAt())
          .build();
    }).collect(Collectors.toList());

    return RestResp.ok(
        PageRespDto.of(reqDto.getPageNum(), reqDto.getPageSize(), page.getTotal(), packageListRespDtos));

  }

  @Override
  public RestResp<PackageRespDto> addPackage(PackageAddReqDto reqDto) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'addPackage'");
  }

  @Override
  public RestResp<PackageRespDto> editPackage(@Valid PackageEditReqDto reqDto) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'editPackage'");
  }

  @Override
  public RestResp<PackageRespDto> buyPackage(@Valid PackageBuyReqDto reqDto) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'buyPackage'");
  }

  @Override
  public RestResp<PageRespDto<PackageListRespDto>> getPackageAllList(PackageListReqDto reqDto) {
    IPage<PackageItem> page = new Page<>();
    page.setCurrent(reqDto.getPageNum());
    page.setSize(reqDto.getPageSize());

    LambdaQueryWrapper<PackageItem> queryWrapper = new LambdaQueryWrapper<PackageItem>();

    if (reqDto.getPackageId() != null) {
      queryWrapper.eq(PackageItem::getPackageId, reqDto.getPackageId());
    }

    // 根据名字筛选
    if (reqDto.getPackageName() != null && !reqDto.getPackageName().isEmpty()) {
      queryWrapper.like(PackageItem::getPackageName, reqDto.getPackageName());
    }

    // 根据状态筛选
    if (reqDto.getPackageStatus() != null) {
      queryWrapper.eq(PackageItem::getPackageStatus, reqDto.getPackageStatus());
    }

    if (reqDto.isInDiscount()) {
      LocalDateTime now = LocalDateTime.now();
      queryWrapper
          .ge(PackageItem::getDiscountEndDate, now) // 折扣结束日期大于等于当前日期
          .le(PackageItem::getDiscountStartDate, now); // 折扣开始日期小于等于当前日期
    }

    IPage<PackageItem> pPage = packageMapper.selectPage(page, queryWrapper);
    List<PackageItem> packages = pPage.getRecords();
    log.info("getUserPackageList pPage: {}", pPage.getRecords());
    List<PackageListRespDto> packageListRespDtos = packages.stream().map(p -> {
      return PackageListRespDto.builder().id(p.getId()).packageId(p.getPackageId()).packageName(p.getPackageName())
          .packageDesc(p.getPackageDesc()).packageStatus(p.getPackageStatus()).originalPrice(p.getOriginalPrice())
          .salePrice(p.getSalePrice()).discount(p.getDiscount()).discountStartDate(p.getDiscountStartDate())
          .discountEndDate(p.getDiscountEndDate()).dataAllowance(p.getDataAllowance()).deviceLimit(p.getDeviceLimit())
          .speedLimit(p.getSpeedLimit()).deleted(p.getDeleted()).createdAt(p.getCreatedAt()).updatedAt(p.getUpdatedAt())
          .build();
    }).collect(Collectors.toList());

    return RestResp.ok(
        PageRespDto.of(reqDto.getPageNum(), reqDto.getPageSize(), page.getTotal(), packageListRespDtos));

  }

}
