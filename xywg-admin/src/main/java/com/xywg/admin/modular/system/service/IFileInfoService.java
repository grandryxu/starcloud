package com.xywg.admin.modular.system.service;

import com.xywg.admin.modular.system.model.FileInfo;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 附件表 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-07
 */
public interface IFileInfoService extends IService<FileInfo> {

    /**
     * 批量插入
     *
     * @param list
     */
    void insertPatch(List<FileInfo> list);

    List<FileInfo> getByTableNameAndId(@Param("t") String tableName, @Param("r") Long relevanceId);

    /**
     * @param fileInfo
     * @return
     * @description 二维码考勤图片地址上传获取ID
     * @author chupp
     * @date 2018年6月27日
     */
    String getIdFromImagePath(FileInfo fileInfo);
    
   /**
    * 新增单个上传附件
    * @param fileInfo
    * @return
    */
    int inserFileInfo(FileInfo fileInfo);


    /**
     * 批量删除附件
     * @param ids
     */
    void deleteAccessory(List<String> ids);
}