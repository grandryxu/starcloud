package com.xywg.admin.modular.wages.model.dto;

import lombok.Data;

/**
 * Created by jingyun_hu on 2018/6/25.
 */
@Data
public class PictureDataDto {
    //图片名
    private String alt;
    //图片id
    private Integer pid;
    //原图地址
    private String src;
    //缩略图地址
    private String thumb;

}
