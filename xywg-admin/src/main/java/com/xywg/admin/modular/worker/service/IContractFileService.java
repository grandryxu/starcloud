package com.xywg.admin.modular.worker.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.tips.Tip;
import com.xywg.admin.modular.worker.model.Contract;
import com.xywg.admin.modular.worker.model.ContractFile;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 合同附件 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-08
 */
public interface IContractFileService extends IService<ContractFile> {

    /**
     * 获取一工人的所有合同
     * @param page
     * @param map
     * @return
     */
    List<ContractFile> getList(Page<ContractFile> page, Map<String, Object> map);

    /**
     * 新增合同
     * @param contractFile
     * @return
     */
    Boolean add(ContractFile contractFile);

    /**
     * 批量修改标签
     * @param ids
     * @param value
     */
    Tip updateSetting(String ids, String value);

    /**
     * 获取所有合同
     * @param page
     * @param map
     * @return
     */
    List<Contract> getContractList(Page<Contract> page, Map<String,Object> map);

    /**
     * 上传合同
     * @param file
     * @param contractFile
     * @return
     */
    Integer addContract(MultipartFile file, ContractFile contractFile);

    /**
     * 批量审核
     * @param ids
     * @param status
     * @return
     * @author 蔡伟
     */
    Integer batchReview(String ids, Integer status);

    /**
     * 批量删除附件
     * @param ids
     */
    void deleteAccessory(List<String> ids);

    /**
     * 查询人员的合同信息
     * <p>Title: selectWorkerContractFiles</p>
     * <p>Description: </p>
     * @author duanfen
     * @date 2019年4月15日
     */
    List<Map<String, Object>> selectWorkerContractFiles(Page<Map<String, Object>> page, Map<String, Object> map);

    void addContractFile(List<Object> addList);
}
