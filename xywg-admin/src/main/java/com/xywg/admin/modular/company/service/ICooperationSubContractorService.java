package com.xywg.admin.modular.company.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.company.model.SubContractor;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 企业表 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-22
 */
public interface ICooperationSubContractorService extends IService<SubContractor> {

    /**
     * 查询参建企业 分页
     * @param page
     * @param map
     * @return
     */
    List<Map<String,Object>> selectList(Page page, Map map);

    int changeState(Map<String, Object> map);

    List<Map<String,Object>> selectGeneralContractors(String generalContractorName);

    Integer addBussSubContractorConstruction(String constructionCode);

    List<Map<String,Object>> getList(String projectCode);

    List<Map<String,Object>> getAllOtherCooperationSubContractor(Long projectMasterId);

    /**
     * 查询参建企业 无分页
     * @param params
     * @return
     */
    List<Map<String,Object>> selectList(Map<String, Object> params);

    /**
     * 导入
     * @param excelFile
     * @param request
     * @return
     */
    String excelUpload(MultipartFile excelFile, HttpServletRequest request) throws FileNotFoundException, Exception;

   void selectCompanyById(Object id);

}
