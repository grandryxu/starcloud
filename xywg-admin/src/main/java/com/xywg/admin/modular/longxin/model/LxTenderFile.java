package com.xywg.admin.modular.longxin.model;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tcw on 2019/7/9.
 */


@Data
public class LxTenderFile extends Model<LxTenderFile> {


    /**
     * id
     */
     private String id ;
    /**
     * 招标文件id
     */
     private String tenderId;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件名
     */
    private String fileName;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
