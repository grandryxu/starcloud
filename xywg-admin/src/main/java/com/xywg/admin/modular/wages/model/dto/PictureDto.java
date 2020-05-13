package com.xywg.admin.modular.wages.model.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by jingyun_hu on 2018/6/25.
 */
@Data
public class PictureDto {
    /**
     * 相册标题
     */
    private String title;
    /**
     * 相册id
     */

    private Integer id;
    /**
     * 初始显示的图片序号，默认0
     */

    private Integer start;
    /**
     * 相册包含的图片，数组格式
     */
    private List<PictureDataDto> data;
}
