package com.xywg.admin.modular.system.service.impl;

import com.xywg.admin.modular.system.model.FileInfo;
import com.xywg.admin.modular.system.dao.FileInfoMapper;
import com.xywg.admin.modular.system.service.IFileInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 附件表 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-07
 */
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements IFileInfoService {
    @Autowired
    private  FileInfoMapper  fileInfoMapper;


    @Override
    public void insertPatch(List<FileInfo> list) {
        fileInfoMapper.insertPatch(list);
    }

    @Override
    public List<FileInfo> getByTableNameAndId(String tableName, Long relevanceId) {
        return fileInfoMapper.getByTableNameAndId(tableName,relevanceId);
    }
    
    /**
     * 
     * @description 二维码考勤图片地址上传获取ID
     * @author chupp
     * @date 2018年6月27日
     * @param fileInfo
     * @return
     * @see com.xywg.admin.modular.system.service.IFileInfoService#getIdFromImagePath(com.xywg.admin.modular.system.model.FileInfo)
     *
     */
	@Override
	public String getIdFromImagePath(FileInfo fileInfo) {
		fileInfo.setType("QR");
		this.baseMapper.insert(fileInfo);
		return String.valueOf(fileInfo.getId());
	}

    @Override
    public void deleteAccessory(List<String> ids) {
        fileInfoMapper.deletePatch(ids);
    }

	@Override
	public int inserFileInfo(FileInfo fileInfo) {
		return this.baseMapper.insertFileInfo(fileInfo);
	}
}
