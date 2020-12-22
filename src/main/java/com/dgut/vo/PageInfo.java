package com.dgut.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author jrue
 * @version 1.0
 * @date 2019/8/16 14:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo<T> {

    private Long total;

    private List<T> data;

}
