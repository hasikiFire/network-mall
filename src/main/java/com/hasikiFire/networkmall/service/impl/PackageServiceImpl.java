package com.hasikiFire.networkmall.service.impl;

import com.hasikiFire.networkmall.core.common.resp.PageRespDto;
import com.hasikiFire.networkmall.core.common.resp.RestResp;
import com.hasikiFire.networkmall.dao.entity.Package;
import com.hasikiFire.networkmall.dao.mapper.PackageMapper;
import com.hasikiFire.networkmall.dto.req.PackageAddReqDto;
import com.hasikiFire.networkmall.dto.req.PackageBuyReqDto;
import com.hasikiFire.networkmall.dto.req.PackageEditReqDto;
import com.hasikiFire.networkmall.dto.req.PackageListReqDto;
import com.hasikiFire.networkmall.dto.resp.PackageListRespDto;
import com.hasikiFire.networkmall.dto.resp.PackageRespDto;
import com.hasikiFire.networkmall.service.PackageService;

import jakarta.validation.Valid;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class PackageServiceImpl extends ServiceImpl<PackageMapper, Package> implements PackageService {

  @Override
  public RestResp<PageRespDto<PackageListRespDto>> getPackageList(@Valid PackageListReqDto params) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getPackageList'");
  }

  @Override
  public RestResp<PackageRespDto> addPackage(@Valid PackageAddReqDto params) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'addPackage'");
  }

  @Override
  public RestResp<PackageRespDto> editPackage(@Valid PackageEditReqDto params) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'editPackage'");
  }

  @Override
  public RestResp<PackageRespDto> purchasePackage(@Valid PackageBuyReqDto params) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'purchasePackage'");
  }

}
