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
import com.hasikiFire.networkmall.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 用户已购套餐记录表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2024/06/03
 */
@RestController
@RequestMapping("/packagePurchaseRecord")
@RequiredArgsConstructor
public class PackagePurchaseRecordController {
  private final PackagePurchaseRecordService packagePurchaseRecordService;

  // 获取使用纪录列表
  @GetMapping("/detail")
  public RestResp<PageRespDto<PackageListRespDto>> recordDetail(@Valid @RequestParam PackageListReqDto params) {
    return packagePurchaseRecordService.recordDetail(params);
  }

  // 编辑套餐使用纪录
  @PutMapping("/edit")
  public RestResp<PackageRespDto> editRecord(@Valid @RequestBody PackageEditReqDto params) {
    return packagePurchaseRecordService.editRecord(params);
  }

}
