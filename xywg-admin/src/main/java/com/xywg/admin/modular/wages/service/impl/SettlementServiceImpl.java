package com.xywg.admin.modular.wages.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.common.constant.Const;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.project.dao.ProjectWorkerMapper;
import com.xywg.admin.modular.project.model.ProjectWorker;
import com.xywg.admin.modular.smz.utils.Constant;
import com.xywg.admin.modular.system.model.Dict;
import com.xywg.admin.modular.system.model.FileInfo;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.system.service.IFileInfoService;
import com.xywg.admin.modular.wages.dao.PayRollDetailMapper;
import com.xywg.admin.modular.wages.dao.SettlementMapper;
import com.xywg.admin.modular.wages.model.Settlement;
import com.xywg.admin.modular.wages.model.SettlementDetail;
import com.xywg.admin.modular.wages.model.dto.PictureDataDto;
import com.xywg.admin.modular.wages.model.dto.PictureDto;
import com.xywg.admin.modular.wages.model.dto.SettlementDetailDto;
import com.xywg.admin.modular.wages.model.dto.SettlementDto;
import com.xywg.admin.modular.wages.service.IAccountService;
import com.xywg.admin.modular.wages.service.IPayRollFlowService;
import com.xywg.admin.modular.wages.service.SettlementService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 结算单 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-01
 */
@Service
@SuppressWarnings("all")
public class SettlementServiceImpl extends ServiceImpl<SettlementMapper, Settlement> implements SettlementService {

    @Autowired
    private SettlementMapper settlementMapper;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private PayRollDetailMapper payRollDetailMapper;
    @Autowired
    private IFileInfoService fileInfoService;
    @Autowired
    private IPayRollFlowService payRollFlowService;
    @Autowired
    private AccountProjectService accountProjectService;
    @Autowired
    private ProjectWorkerMapper projectWorkerMapper;
    @Override
    public List<Map<String,Object>> getList(Page<Map<String,Object>> page, Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        map.put("deptId", deptService.getUserDeptAndSubdivision());
        map.put("projectCodes", accountProjectService.getProjectCodes());
        return settlementMapper.getList(page, map ,accountProjectService.getSwitchType());
    }

    @Override
    @Transactional
    public void deleteBatch(List<Settlement> list) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Settlement settlement : list) {
            stringBuffer.append(settlement.getPayrollDetailIds());
        }
        String[] payrollDetailIds = stringBuffer.toString().split(",");
        if (payrollDetailIds.length > 0) {
            Integer payStatus = 0;
            //删除待审核的结算单时,重新把工资单详细里的数据解除锁定
            payRollDetailMapper.updateBatchPayStatus(payStatus, Arrays.asList(payrollDetailIds), ShiroKit.getUser().getName());
        }
        //删除结算单
        settlementMapper.deleteBatch(list);
        //删除结算单时,删除结算单详细表里的数据
        settlementMapper.deleteBatchDetail(list);
    }

    @Override
    public List<Dict> getWorkerType(String workerTypeName) {
        return settlementMapper.getWorkerType(workerTypeName);
    }

    @Override
    public List<SettlementDetail> getSettlementDetail(Page<SettlementDetail> page, Map<String, Object> map) {
        return settlementMapper.getSettlementDetail(page, map);
    }

    @Override
    public List<SettlementDto> getSettlementListByProjectCodeAndTeamSysNo(String projectCode, Integer id) {
        return settlementMapper.getSettlementListByProjectCodeAndTeamSysNo(projectCode , id);
    }

    @Override
    public List<SettlementDto> v116GetSettlementListByProjectCodeAndSysTeamNo(String projectCode, Integer id,Integer pageNo , Integer pageSize) {
        return settlementMapper.v116GetSettlementListByProjectCodeAndSysTeamNo2(projectCode , id,(pageNo-1)*pageSize,pageSize);
//        return settlementMapper.v116GetSettlementListByProjectCodeAndSysTeamNo(projectCode , id,(pageNo-1)*pageSize,pageSize);
    }

    @Override
    @Transactional
    public void payrollBatch(List<Settlement> list) {
        //结算单发放工资的时候修改结算单的状态
        settlementMapper.payrollBatch(list);
        List<String> idsList = new ArrayList<>();
        List<String>  settlementCodeList=new ArrayList<>();
        for (Settlement settlement : list) {
            String[] ids = settlement.getPayrollDetailIds().split(",");
            idsList.addAll(Arrays.asList(ids));
            settlementCodeList.add(settlement.getSettlementCode());

            Map<String, Object>  codeMap=new HashMap<>();
            codeMap.put("settlementCode",settlement.getSettlementCode());
            //插入工资流水
            payRollFlowService.insertBySettlement(getSettlementDetailBySettlementCode(codeMap),ShiroKit.getUser().getName());
        }
        if(idsList.size()>0){
            //工资明细表的发放状态20 已发放
            Integer payStatus = 20;
            //修改工资明细表的发放状态
            payRollDetailMapper.updateBatchPayStatus(payStatus, idsList, ShiroKit.getUser().getName());
        }
        //修改结算单明细状态
        settlementMapper.updateSettlementDetailStatus(settlementCodeList,ShiroKit.getUser().getName());
    }


    @Override
    public void checkInfo(List<Settlement> list) {
        settlementMapper.checkInfo(list, ShiroKit.getUser().getName());
    }

    @Override
    public void review(List<Settlement> list) {
        settlementMapper.review(list, ShiroKit.getUser().getName());
    }


    @Override
    @Transactional
    public void saveSettlement(Settlement settlement, List<SettlementDetail> settlementDetailList) {
        String settlementCode = "SET" + System.currentTimeMillis();

        if (settlement == null || settlementDetailList.size() == 0) {
            throw new XywgException(600, "参数不足");
        }
        settlement.setSettlementCode(settlementCode);
        settlement.setPayStatus(1);
        if (settlement.getCreateUser() == null) {
            settlement.setCreateUser(ShiroKit.getUser().getName());
        }
        settlement.setSalaryPerson(Long.parseLong(ShiroKit.getUser().getId().toString()));
        settlement.setUpdateUser(ShiroKit.getUser().getId().toString());
        settlement.setOrganizationCode(deptService.getDept(Long.parseLong(ShiroKit.getUser().getDeptId().toString())).getSocialCreditNumber());

        List<SettlementDetail> list = new ArrayList<>();
        for (SettlementDetail settlementDetail : settlementDetailList) {
            settlementDetail.setSettlementCode(settlementCode);
            settlementDetail.setUpdateUser(ShiroKit.getUser().getName());
            settlementDetail.setCreateUser(ShiroKit.getUser().getName());
            List<ProjectWorker> pwList = projectWorkerMapper.isInProject(settlement.getProjectCode(), Integer.parseInt(settlementDetail.getIdCardType()), settlementDetail.getIdCardNumber());
            if(pwList.size() > 0) {
            	settlementDetail.setTeamSysNo(pwList.get(0).getTeamSysNo());
            }
            list.add(settlementDetail);
        }

        //存储结算单与工资明细id的关联关系
        Set<Long> rayRollDetailIdList = new HashSet<>();
        StringBuffer sb = new StringBuffer();
        for (SettlementDetail settlementDetail : list) {
            //得到每个人此次参建计算工资的工资明细id
            String rayRollDetailIdsString = settlementDetail.getPayRollDetailConcatIds();
            if(StringUtils.isNotBlank(rayRollDetailIdsString)){
                String[] rayRollDetailIds=rayRollDetailIdsString.split(",");
                for(String id:rayRollDetailIds){
                    rayRollDetailIdList.add(Long.parseLong(id));
                    sb.append(id).append(",");
                }
            }
        }

        settlement.setPayrollDetailIds(sb.toString());
        //保存结算单
        settlementMapper.saveSettlement(settlement);
        //保存结算单明细
        settlementMapper.saveSettlementDetailBatch(list);
        //当结算单中的工人有工资单信息表时才更新工资详细状态
        if(rayRollDetailIdList.size()>0){
            //修改工资单详细状态
            payRollDetailMapper.updatePayStatus(rayRollDetailIdList, ShiroKit.getUser().getName());
        }
    }

    @Override
    @Transactional
    public void updateSettlementDetailBatch(Settlement settlement, List<SettlementDetail> settlementDetailList) {
        if (settlement == null || settlementDetailList.size() == 0) {
            throw new XywgException(600, "参数不足");
        }
        settlement.setUpdateUser(ShiroKit.getUser().getName().toString());
        //保存结算单
        settlementMapper.updateSettlement(settlement);
        for (SettlementDetail settlementDetail : settlementDetailList) {
            settlementDetail.setUpdateUser(ShiroKit.getUser().getName());
            settlementMapper.updateSettlementDetail(settlementDetail);
        }
    }


    @Override
    public List<SettlementDetail> getSettlementDetailBySettlementCode(Map<String, Object> map) {
        return settlementMapper.getSettlementDetailBySettlementCode(map);
    }


    @Override
    public List<Map<String,Object>> getSettlementDetailById(Long id) {
        return settlementMapper.getSettlementDetailById(id);
    }
    
    @Override
    public List<Map<String,Object>> getSettlementDetailById2(Long id,Long uid) {
        return settlementMapper.getSettlementDetailById2(id,uid);
    }

    @Override
    public Integer settlementDetailDtoSign(SettlementDetailDto settlementDetailDto) {
        return settlementMapper.settlementDetailDtoSign(settlementDetailDto);
    }

    @Transactional
    @Override
    public Integer addSettlementDto(SettlementDto settlementDto) {
        Settlement settlement = new Settlement();
        BeanUtils.copyProperties(settlementDto, settlement);
        settlement.setPayStatus(1);
        BigDecimal totalAmount = new BigDecimal(0);
        BigDecimal actualAmount = new BigDecimal(0);
        BigDecimal payedMoney = new BigDecimal(0);
        List<SettlementDetailDto> settlementDetailDtoList = settlementDto.getDetailList();
        String payrollDetailIds = "";
        for (SettlementDetailDto settlementDetailDto : settlementDetailDtoList) {
            totalAmount = totalAmount.add(settlementDetailDto.getSettlePayAmount());
            actualAmount = actualAmount.add(settlementDetailDto.getSettleActualAmount());
            if(settlementDetailDto.getPayrollDetailIds() != null && (!("".equals(settlementDetailDto.getPayrollDetailIds())))  ){
                payrollDetailIds += settlementDetailDto.getPayrollDetailIds() + ",";
            }
            List<ProjectWorker> pwList = projectWorkerMapper.isInProject(settlementDto.getProjectCode(), 
            		Integer.parseInt(settlementDetailDto.getIdCardType()), settlementDetailDto.getIdCardNumber());
            if(pwList.size() > 0) {
            	settlementDetailDto.setWorkerType(pwList.get(0).getWorkTypeCode());
            }
            settlementDetailDto.setCreateUser(settlementDto.getCreateUser());
        }
        payedMoney = totalAmount.subtract(actualAmount);
        settlement.setTotalAmount(totalAmount);
        settlement.setActualAmount(actualAmount);
        settlement.setPayedMoney(payedMoney);
        settlement.setSettlementCode("SET" + String.valueOf(System.currentTimeMillis()));
        if(!"".equals(payrollDetailIds)){
               payrollDetailIds = payrollDetailIds.substring(0,payrollDetailIds.length()-1);
        }
        settlement.setPayrollDetailIds(payrollDetailIds);
        settlementMapper.insert(settlement);
        settlementMapper.updatePayRollDetails(settlement.getPayrollDetailIds());
        return settlementMapper.insertSettlementDetailDto2(settlementDetailDtoList, settlement.getSettlementCode(), settlement.getTeamSysNo());
//        return settlementMapper.insertSettlementDetailDto(settlementDetailDtoList, settlement.getSettlementCode());
    }

    @Override
    public List<SettlementDto> getSettlementDtoByOrganizationCode(String organizationCode) {
        return settlementMapper.getSettlementDtoByOrganizationCode(organizationCode);
    }

    @Override
    public List<SettlementDto> v116GetSettlementDtoByOrganizationCode(String organizationCode,Integer pageNo , Integer pageSize) {
        return settlementMapper.v116GetSettlementDtoByOrganizationCode(organizationCode,(pageNo-1)*pageSize,pageSize);
    }

    @Override
    @Transactional
    public Integer examineSettlementDto(SettlementDto settlementDto) {
        Integer number = settlementMapper.examineSettlementDto(settlementDto);
        if(settlementDto.getStatus() == 50){
            //更新结算单详情的发放人
            baseMapper.updateDetailsPayPerson(settlementDto);
            Map<String, Object>  codeMap=new HashMap<>();
            codeMap.put("settlementCode",settlementMapper.selectById(settlementDto.getId()).getSettlementCode());
            //插入工资流水
            payRollFlowService.insertBySettlement(getSettlementDetailBySettlementCode(codeMap),settlementDto.getAccountName());
            //
            payRollDetailMapper.updatePayStatusToTwenty(settlementDto.getId());
        }
        return number;

    }

    /**
     * 查看附件通用方法
     * @param
     * @param title
     * @param tableName
     * @return
     */
    @Override
    public PictureDto getAccessoryPicture(Long id , String title , String tableName) {
        List<FileInfo> list = fileInfoService.getByTableNameAndId(tableName, id);
        //组装展示图数据格式
        PictureDto pictureDto = new PictureDto();
        pictureDto.setId(1);
        pictureDto.setStart(0);
        pictureDto.setTitle(title);
        List<PictureDataDto> listPictureDataDto=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            PictureDataDto pictureDataDto = new PictureDataDto();
            pictureDataDto.setAlt(list.get(i).getFileName());
            pictureDataDto.setPid(list.get(i).getId());
            pictureDataDto.setSrc(Constant.FTP_URL_PATH+list.get(i).getPath());
            pictureDataDto.setThumb(Constant.FTP_URL_PATH+list.get(i).getPath());
            listPictureDataDto.add(pictureDataDto);
        }
        pictureDto.setData(listPictureDataDto);


        return pictureDto;
    }

    @Override
    public void deleteAccessoryPicture(List<String> ids) {
        fileInfoService.deleteAccessory(ids);
    }

    @Override
    public List<SettlementDetail> getDetailListNoPage(Map<String, Object> map) {
        return this.baseMapper.getDetailListNoPage(map);
    }
}
