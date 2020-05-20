package com.xywg.attendance.common.model;

import lombok.Data;

/**
 * @author hjy
 * @date 2019/3/5
 */
@Data
public class TransmissionMessageTemplateSubclass extends TransmissionMessageTemplate{
    private String insertDate;
    private String error;


}
