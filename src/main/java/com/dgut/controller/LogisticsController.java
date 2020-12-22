package com.dgut.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.dto.LogisticsSearchDTO;
import com.dgut.dto.PageDTO;
import com.dgut.entity.LogisticsEntity;
import com.dgut.mapper.LogisticsMapper;
import com.dgut.service.LogisticsService;
import com.dgut.utils.ResultUtils;
import com.dgut.utils.SnowFlakeUtil;
import com.dgut.vo.PageResult;
import com.dgut.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "物流控制器", description = "物流控制器")
public class LogisticsController extends BaseController {

    @Autowired
    LogisticsMapper logisticsMapper;

    @Autowired
    LogisticsService logisticsService;

    @ApiOperation(value = "物流查询[列表]", httpMethod = "GET")
    @GetMapping("/logistics")
    public PageResult getlLogisticsList(LogisticsSearchDTO logisticsSearchDTO, PageDTO pageDTO) throws Exception {
        QueryWrapper<LogisticsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like("company_name", logisticsSearchDTO.getCompanyName()!=null?logisticsSearchDTO.getCompanyName():"")
                .like("sender_name", logisticsSearchDTO.getSenderName()!=null?logisticsSearchDTO.getSenderName():"")
                .like("sender_address", logisticsSearchDTO.getSenderAddress()!=null?logisticsSearchDTO.getSenderAddress():"")
                .like("sender_phone", logisticsSearchDTO.getSenderPhone()!=null?logisticsSearchDTO.getSenderPhone():"")
                .like("addressee_name", logisticsSearchDTO.getAddresseeName()!=null?logisticsSearchDTO.getAddresseeName():"")
                .like("addressee_address", logisticsSearchDTO.getAddresseeAddress()!=null?logisticsSearchDTO.getAddresseeAddress():"")
                .like("addressee_phone", logisticsSearchDTO.getAddresseePhone()!=null?logisticsSearchDTO.getAddresseePhone():"");
        IPage<LogisticsEntity> logisticsPage = new Page<>(pageDTO.getPage(), pageDTO.getLimit());
        logisticsPage = logisticsMapper.selectPage(logisticsPage, queryWrapper);
//        List<LogisticsEntity> list = logisticsPage.getRecords();
        return ResultUtils.pageResult(logisticsPage);
//        return ResultUtils.success();
    }
    @ApiOperation(value = "物流查询[单一数据]", httpMethod = "GET")
    @GetMapping("/logistics/{logisticsId}")
    public Result<?> getlLogistics(@PathVariable("logisticsId") Integer logisticsId) {
        return ResultUtils.success(logisticsMapper.selectById(logisticsId));
    }
    @ApiOperation(value = "物流信息新增")
    @PostMapping("/logistics")
    public Result<?> saveLogistics(LogisticsEntity logisticsEntity) throws Exception {
        if(logisticsService.saveLogistics(logisticsEntity)!=1) {
            throw new Exception("新增异常");
        }
        return ResultUtils.success(
                logisticsMapper.selectOne(new QueryWrapper<LogisticsEntity>().eq("logistics_no", logisticsEntity.getLogisticsNo()))
        );
    }
    @ApiOperation(value = "物流信息修改")
    @PutMapping("/logistics")
    public Result<?> updateLogistics(LogisticsEntity logisticsEntity) throws Exception {
        LogisticsEntity logisticsEntity1 = logisticsMapper.selectById(logisticsEntity.getLogisticsId());
        if(logisticsEntity1 == null) {
            throw new Exception("查无此物流信息");
        }
        logisticsEntity.setVersion(logisticsEntity1.getVersion()-1);
        return ResultUtils.success(logisticsMapper.updateById(logisticsEntity));
    }
    @ApiOperation(value = "物流信息删除")
    @PostMapping("/logistics/{logisticsId}")
    public Result<?> deleteLogistics(@PathVariable("logisticsId") Integer logisticsId) {
        return ResultUtils.success(logisticsMapper.deleteById(logisticsId));
    }
}
