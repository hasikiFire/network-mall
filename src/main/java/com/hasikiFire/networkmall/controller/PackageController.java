package com.hasikiFire.networkmall.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hasikiFire.networkmall.core.common.resp.PageRespDto;
import com.hasikiFire.networkmall.core.common.resp.RestResp;
import com.hasikiFire.networkmall.dto.req.PackageAddReqDto;
import com.hasikiFire.networkmall.dto.req.PackageBuyReqDto;
import com.hasikiFire.networkmall.dto.req.PackageEditReqDto;
import com.hasikiFire.networkmall.dto.req.PackageListReqDto;
import com.hasikiFire.networkmall.dto.resp.PackageListRespDto;
import com.hasikiFire.networkmall.dto.resp.PackageRespDto;
import com.hasikiFire.networkmall.service.PackagePurchaseRecordService;
import com.hasikiFire.networkmall.service.PackageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 套餐表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2024/06/03
 */
@RestController
@RequestMapping("/package")
@RequiredArgsConstructor
public class PackageController {
  private final PackageService packageService;

  // 套餐列表
  @GetMapping("/list")
  public RestResp<PageRespDto<PackageListRespDto>> listPackages(@Valid @RequestParam PackageListReqDto params) {
    return packageService.getPackageList(params);
  }

  // 新增套餐
  @PostMapping("/add")
  public RestResp<PackageRespDto> addPackage(@Valid @RequestBody PackageAddReqDto params) {
    return packageService.addPackage(params);
  }

  // 编辑套餐
  @PutMapping("/edit")
  public RestResp<PackageRespDto> editPackage(@Valid @RequestBody PackageEditReqDto params) {
    return packageService.editPackage(params);
  }

  // 购买套餐
  @PostMapping("/purchase")
  public RestResp<PackageRespDto> purchasePackage(@Valid @RequestBody PackageBuyReqDto params) {
    return packageService.purchasePackage(params);
  }
}
