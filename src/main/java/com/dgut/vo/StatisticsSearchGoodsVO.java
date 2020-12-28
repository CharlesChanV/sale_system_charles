package com.dgut.vo;

import lombok.Data;

@Data
public class StatisticsSearchGoodsVO {
    private int totalCount = 0;
    private double totalPrice = 0.00;
    private int purchaseItemCount = 0;
    private int purchaseCount = 0;
}
