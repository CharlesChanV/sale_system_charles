package com.dgut.controller;

import com.dgut.dto.StatisticsSearchDTO;
import com.dgut.service.StatisticsService;
import com.dgut.utils.ResultUtils;
import com.dgut.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "数据统计控制器", description = "数据统计控制器")
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @ApiOperation(value = "获取数据统计信息")
    @GetMapping("/statistics")
    public Result<?> getStatisticsBaseInfo() {
        return ResultUtils.success(statisticsService.getStatisticsBaseInfo());
    }

    @ApiOperation(value = "搜索数据统计信息")
    @GetMapping("/statistics/search")
    public Result<?> searchStatisticsInfoByCustomerIdOrSalespersonId(StatisticsSearchDTO searchDTO) {
        return ResultUtils.success(statisticsService.searchStatisticsInfo(searchDTO));
    }

    @ApiOperation(value = "搜索数据统计信息[商品分析]")
    @GetMapping("/statistics/goods/{goodsId}")
    public Result<?> searchStatisticsInfoByGoodsId(@PathVariable("goodsId") Integer goodsId) {
        return ResultUtils.success(statisticsService.searchStatisticsInfoByGoodsId(goodsId));
    }

}
