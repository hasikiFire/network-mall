package com.hasikiFire.networkmall.service;

import com.hasikiFire.networkmall.core.common.resp.PageRespDto;
import com.hasikiFire.networkmall.core.common.resp.RestResp;
import com.hasikiFire.networkmall.dao.entity.Package;
import com.hasikiFire.networkmall.dto.req.PackageAddReqDto;
import com.hasikiFire.networkmall.dto.req.PackageBuyReqDto;
import com.hasikiFire.networkmall.dto.req.PackageEditReqDto;
import com.hasikiFire.networkmall.dto.req.PackageListReqDto;
import com.hasikiFire.networkmall.dto.resp.PackageListRespDto;
import com.hasikiFire.networkmall.dto.resp.PackageRespDto;

import jakarta.validation.Valid;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 套餐表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2024/06/03
 */
public interface PackageService extends IService<Package> {

  RestResp<PageRespDto<PackageListRespDto>> getPackageList(@Valid PackageListReqDto params);

  RestResp<PackageRespDto> addPackage(@Valid PackageAddReqDto params);

  RestResp<PackageRespDto> editPackage(@Valid PackageEditReqDto params);

  RestResp<PackageRespDto> purchasePackage(@Valid PackageBuyReqDto params);

}
