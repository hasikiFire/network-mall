package com.hasikiFire.networkmall.service.impl;

import com.hasikiFire.networkmall.core.common.resp.PageRespDto;
import com.hasikiFire.networkmall.core.common.resp.RestResp;
import com.hasikiFire.networkmall.dao.entity.PackagePurchaseRecord;
import com.hasikiFire.networkmall.dao.mapper.PackagePurchaseRecordMapper;
import com.hasikiFire.networkmall.dto.req.PackageAddReqDto;
import com.hasikiFire.networkmall.dto.req.PackageBuyReqDto;
import com.hasikiFire.networkmall.dto.req.PackageEditReqDto;
import com.hasikiFire.networkmall.dto.req.PackageListReqDto;
import com.hasikiFire.networkmall.dto.resp.PackageListRespDto;
import com.hasikiFire.networkmall.dto.resp.PackageRespDto;
import com.hasikiFire.networkmall.service.PackagePurchaseRecordService;

import jakarta.validation.Valid;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户已购套餐记录表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2024/06/03
 */
@Service
public class PackagePurchaseRecordServiceImpl extends ServiceImpl<PackagePurchaseRecordMapper, PackagePurchaseRecord>
        implements PackagePurchaseRecordService {

    @Override
    public RestResp<PageRespDto<PackageListRespDto>> recordDetail(@Valid PackageListReqDto params) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'recordDetail'");
    }

    @Override
    public RestResp<PackageRespDto> editRecord(@Valid PackageEditReqDto params) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editRecord'");
    }

    @Override
    public RestResp<PackageRespDto> addRecord(@Valid PackageEditReqDto params) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addRecord'");
    }

}
