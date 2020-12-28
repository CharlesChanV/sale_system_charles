package com.dgut.service;

import com.dgut.dto.StatisticsSearchDTO;
import com.dgut.vo.StatisticsBaseVO;
import com.dgut.vo.StatisticsSearchGoodsVO;
import com.dgut.vo.StatisticsSearchVO;

public interface StatisticsService {
    public StatisticsBaseVO getStatisticsBaseInfo();
    public StatisticsSearchVO searchStatisticsInfo(StatisticsSearchDTO searchDTO);
    public StatisticsSearchGoodsVO searchStatisticsInfoByGoodsId(Integer goodsId);
}
