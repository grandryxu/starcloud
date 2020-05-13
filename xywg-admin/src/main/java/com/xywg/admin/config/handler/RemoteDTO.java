package com.xywg.admin.config.handler;

        import lombok.Data;

@Data
public class RemoteDTO<T> {
    /**
     * json字符串
     */
    private T body ;
}