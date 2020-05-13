package com.xywg.admin.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.system.model.FileInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 附件表 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-07
 */
public interface FileInfoMapper extends BaseMapper<FileInfo> {

	/**
	 * 
	 * @description 根据表名、ID查询上传文件
	 * @author chupp
	 * @date 2018年6月7日
	 * @param tableName
	 * @param relevanceId
	 *
	 */
	List<FileInfo> getByTableNameAndId(@Param("t") String tableName, @Param("r") Long relevanceId);

	/**
	 *
	 * @description 添加fileInfo
	 * @author xieshuaishuai
	 * @date 2018年6月7日
	 * @param tableName
	 * @param relevanceId
	 *
	 */
	int insertFileInfo(@Param("map") FileInfo info);


	void insertPatch(List<FileInfo> list);

    void deletePatch(List<String> ids);
}
