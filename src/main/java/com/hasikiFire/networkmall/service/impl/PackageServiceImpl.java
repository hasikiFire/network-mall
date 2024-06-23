package com.hasikiFire.networkmall.service.impl;

import com.hasikiFire.networkmall.core.common.req.PageReqDto;
import com.hasikiFire.networkmall.core.common.resp.PageRespDto;
import com.hasikiFire.networkmall.core.common.resp.RestResp;
import com.hasikiFire.networkmall.dao.entity.Package;
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
public class PackageServiceImpl extends ServiceImpl<PackageMapper, Package> implements PackageService {

  private final PackageMapper packageMapper;

  @Override
  public RestResp<PageRespDto<PackageListRespDto>> getUserPackageList(PageReqDto params) {

    IPage<Package> page = new Page<>();
    page.setCurrent(params.getPageNum());
    page.setSize(params.getPageSize());

    LambdaQueryWrapper<Package> queryWrapper = new LambdaQueryWrapper<Package>();
    queryWrapper.eq(Package::getPackageStatus, 1);
    queryWrapper.last("LIMIT " + params.getPageSize());

    IPage<Package> pPage = packageMapper.selectPage(page, queryWrapper);
    List<Package> packages = pPage.getRecords();
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
        PageRespDto.of(params.getPageNum(), params.getPageSize(), page.getTotal(), packageListRespDtos));

  }

  @Override
  public RestResp<PackageRespDto> addPackage(PackageAddReqDto params) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'addPackage'");
  }

  @Override
  public RestResp<PackageRespDto> editPackage(@Valid PackageEditReqDto params) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'editPackage'");
  }

  @Override
  public RestResp<PackageRespDto> buyPackage(@Valid PackageBuyReqDto params) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'buyPackage'");
  }

  @Override
  public RestResp<PageRespDto<PackageListRespDto>> getPackageAllList(@Valid PackageListReqDto params) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getPackageAllList'");
  }

}
