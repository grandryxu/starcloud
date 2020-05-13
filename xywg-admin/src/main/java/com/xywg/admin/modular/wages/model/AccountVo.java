package com.xywg.admin.modular.wages.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/6/6 10:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountVo extends Account{

    private List<Map> accountDetailList;

    private String accountDetailListStr;
}
