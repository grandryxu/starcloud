package com.xywg.attendance.common.utils;

import lombok.Data;

/**
 * @Auther: wangshibo
 * @Date: 2019/2/26/026 19:12
 * @Description:
 */
@Data
public class ResultOut {

    private String error_code;

    private ResultIn result;

    private String reason;

}
