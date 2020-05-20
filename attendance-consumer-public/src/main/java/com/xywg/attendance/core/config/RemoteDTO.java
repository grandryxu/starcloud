package com.xywg.attendance.core.config;

import lombok.Data;

@Data
public class RemoteDTO<T> {
    /**
     * json字符串
     */
    private T body ;
}