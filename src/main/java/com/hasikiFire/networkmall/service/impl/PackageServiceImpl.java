package com.hasikiFire.networkmall.service.impl;

import com.hasikiFire.networkmall.core.common.exception.BusinessException;
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
    if (reqDto.getDiscountStartDate() != null) {
      if (reqDto.getDiscountEndDate() == null) {
        throw new BusinessException("折扣开始日期不为空时，折扣结束日期不能为空");
      }
      if (reqDto.getDiscountStartDate().isAfter(reqDto.getDiscountEndDate())) {
        throw new BusinessException("折扣开始日期必须在结束日期之前");
      }
    }

    // 检查套餐名称是否已存在
    if (packageMapper.existsByPackageName(reqDto.getPackageName())) {
      throw new BusinessException("套餐名称已存在");
    }
    PackageItem packageItem = new PackageItem();
    if (reqDto.getStatus() != null) {
      packageItem.setPackageStatus(reqDto.getStatus());
    } else {
      packageItem.setPackageStatus(0);
    }
    try {
      packageItem.setPackageName(reqDto.getPackageName());
      packageItem.setPackageDesc(reqDto.getPackageDesc());
      packageItem.setOriginalPrice(reqDto.getOriginalPrice());
      packageItem.setSalePrice(reqDto.getSalePrice());
      packageItem.setDiscount(reqDto.getDiscount());
      packageItem.setDiscountStartDate(reqDto.getDiscountStartDate());
      packageItem.setDiscountEndDate(reqDto.getDiscountEndDate());
      packageItem.setDataAllowance(reqDto.getDataAllowance());
      packageItem.setDeviceLimit(reqDto.getDeviceLimit());
      packageItem.setSpeedLimit(reqDto.getSpeedLimit());

      packageMapper.insert(packageItem);
    } catch (Exception e) {
      // 异常处理
      throw new BusinessException("添加套餐失败：" + e.getMessage());
    }
    return RestResp.ok(PackageRespDto.builder().item(packageItem).build());
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
