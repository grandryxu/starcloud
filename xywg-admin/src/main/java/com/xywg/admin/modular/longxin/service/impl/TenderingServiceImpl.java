package com.xywg.admin.modular.longxin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.flow.service.OrderService;
import com.xywg.admin.modular.company.dao.SubContractorMapper;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.longxin.dao.LxProjectMapper;
import com.xywg.admin.modular.longxin.dao.TenderMapper;
import com.xywg.admin.modular.longxin.model.*;
import com.xywg.admin.modular.longxin.service.LxTenderProcessRelationService;
import com.xywg.admin.modular.longxin.service.TenderingService;
import com.xywg.admin.modular.system.dao.UserMapper;
import com.xywg.admin.modular.system.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by tcw on 2019/7/9.
 */
@Service
public class TenderingServiceImpl extends ServiceImpl<TenderMapper, LxTender> implements TenderingService {

    @Autowired
    private LxTenderProcessRelationService lxTenderProcessRelationService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SubContractorMapper subContractorMapper;

    @Autowired
    private LxProjectMapper lxProjectMapper;

    @Override
    public List<TenderResultBean> selectList(Map<String, Object> map, Page<TenderResultBean> page) {
        return this.baseMapper.selectList(map, page);
    }

    @Override
    public List<TenderResultBean> selectListFix(Map<String, Object> map, Page<TenderResultBean> page) {
        return this.baseMapper.selectListFix(map, page);
    }

    @Override
    public List<TenderResultBean> selectList1(Map<String, Object> map, Page<TenderResultBean> page) {
        return this.baseMapper.selectList1(map, page);
    }


    @Override
    public void saveFile(List<LxTenderFile> fileList) {

        this.baseMapper.saveFile(fileList);

    }

    @Override
    public LxTender saveTender(LxTender td) {
        this.baseMapper.saveTender(td);
        return td;
    }

    @Override
    public TenderResultBean getById(String id) {
        return this.baseMapper.getById(id);
    }

    @Override
    public List<LxTenderFile> getFileById(String id) {
        return this.baseMapper.getFileByTenderId(id);
    }


    @Override
    public void updateStatus(LxTender tender) {
        this.baseMapper.updateStatus(tender);
    }

    @Override
    public void updateStatusFix(LxTender tender) {
        this.baseMapper.updateStatusFix(tender);
    }

    @Override
    public Map deleteTender(String id) {
        Map map = new HashMap();

        User param = new User();
        param.setId(ShiroKit.getUser().getId());
        User user = userMapper.selectOne(param);
        List l = new ArrayList();
        l.add(Integer.parseInt(user.getRoleid()));
        LxTender tender = new LxTender();
        tender.setId(id);


        //删除流程关联信息
        List<LxTenderProcessRelation> relationList = lxTenderProcessRelationService.getTenderRelationByBussId(tender);
        boolean success = true;
        for (LxTenderProcessRelation relation : relationList) {
            try {
                boolean isDel = orderService.dropByOrderId(relation.getProcessId(), user.getId(), user.getDeptid(), l);
                if (isDel == false) {
                    success = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (success) {
            //删除招标文件信息
            this.baseMapper.deleteTender(tender);
            map.put("code", "200");
            map.put("message", "删除成功");
        } else {
            map.put("code", "600");
            map.put("message", "不是创建者 不可删除");
        }
        return map;
    }

    @Override
    public Object updateManStatus(String id, String status) {
        Map map = new HashMap();
        this.baseMapper.updateManStatus(id, status);
        map.put("code", "200");
        map.put("message", "删除成功");
        return map;
    }

    @Override
    public Object updateFlowStatus(String id, String status) {
        Map map = new HashMap();
        this.baseMapper.updateFlowStatus(id, status);
        map.put("code", "200");
        map.put("message", "删除成功");
        return map;
    }



    @Override
    public List<TenderResultBean> selectListTender(Map<String, Object> map, Page<TenderResultBean> page) {
        return this.baseMapper.selectListTender(map, page);
    }

    @Override
    public Object setType(String id, String type) {
        Map map = new HashMap();
        LxTender tender = new LxTender();
        this.baseMapper.setType(tender);
        map.put("code", "200");
        map.put("message", "更新成功");
        return map;
    }

    @Override
    public Object bid(String tenderId, String account) {
        Map map = new HashMap();
        SubContractor subContractor = subContractorMapper.selectSubContractor(account);
        if (subContractor == null) {
            map.put("code", "404");
            map.put("message", "企业未审核！");
        } else {
            Long count = lxProjectMapper.inviteBidCount(subContractor.getId().toString(), tenderId);
            if (count > 0) {
                map.put("code", "302");
                map.put("message", "已参与投标！");
            } else {
                lxProjectMapper.insertTender(subContractor.getId().toString(), tenderId);
                map.put("code", "200");
                map.put("message", "参与投标成功！");
            }
        }
        return map;
    }

    @Override
    public Integer selectType(String id) {
        return baseMapper.selectType(id);
    }

    @Override
    public List<Map<String, Object>> announcementGetAllGetTenderList(Map<String, Object> map) {
        Integer pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer pageNo = (Integer.parseInt(String.valueOf(map.get("pageNo"))) - 1) * pageSize;
        List<Map<String, Object>> list = baseMapper.announcementGetAllGetTenderList(String.valueOf(map.get("account")), pageNo, pageSize);
        for (Map<String, Object> bidMap : list) {
            //0审核中，1审核完成, 2 已拒绝 -1待审核
            if ("0".equals(bidMap.get("auditStatus").toString())) {
                bidMap.put("isComplete", "1");
                bidMap.put("tenderStatus", "审核中");
                //审核完成，走正常流程
            } else if ("1".equals(bidMap.get("auditStatus").toString())) {
                //0默认，1流标，2废标
                if ("0".equals(bidMap.get("bid").toString())) {
                    //默认的时候判断标的状态
                    //null:未被邀请 1:邀请2:已接受3:评标4:定标审核5：定标
                    if (bidMap.get("status")==null) {
                        String startTime = bidMap.get("startTime").toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date date = sdf.parse(startTime);
                            long time = System.currentTimeMillis();
                            long time1 = date.getTime();
                            if (time > time1) {
                                bidMap.put("isComplete", "1");
                                bidMap.put("tenderStatus", "开标中");
                            }else {
                                bidMap.put("isComplete", "1");
                                bidMap.put("tenderStatus", "等待开标");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else if ("5".equals(bidMap.get("status").toString())) {
                        bidMap.put("isComplete", "0");
                        bidMap.put("tenderStatus", "1");
                    } else if ("1".equals(bidMap.get("status").toString())) {
                        String startTime = bidMap.get("startTime").toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date date = sdf.parse(startTime);
                            long time = System.currentTimeMillis();
                            long time1 = date.getTime();
                            if (time > time1) {
                                bidMap.put("isComplete", "1");
                                bidMap.put("tenderStatus", "开标中");
                            }else {
                                bidMap.put("isComplete", "1");
                                bidMap.put("tenderStatus", "等待开标");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }else if ("2".equals(bidMap.get("status").toString())){
                        String startTime = bidMap.get("startTime").toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date date = sdf.parse(startTime);
                            long time = System.currentTimeMillis();
                            long time1 = date.getTime();
                            if (time > time1) {
                                bidMap.put("isComplete", "1");
                                bidMap.put("tenderStatus", "开标中");
                            }else {
                                bidMap.put("isComplete", "1");
                                bidMap.put("tenderStatus", "等待开标");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else if ("3".equals(bidMap.get("status").toString())) {
                        bidMap.put("isComplete", "1");
                        bidMap.put("tenderStatus", "评标中");
                    } else if ("4".equals(bidMap.get("status").toString())) {
                        bidMap.put("isComplete", "1");
                        bidMap.put("tenderStatus", "定标中");
                    } else {
                        //企业已经投标，现在时间>开标时间  =开标中
                        String startTime = bidMap.get("startTime").toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date date = sdf.parse(startTime);
                            long time = System.currentTimeMillis();
                            long time1 = date.getTime();
                            if (time > time1) {
                                bidMap.put("isComplete", "1");
                                bidMap.put("tenderStatus", "开标中");
                            }else {
                                bidMap.put("isComplete", "1");
                                bidMap.put("tenderStatus", "等待开标");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                 /*   }else if ("2".equals(bidMap.get("status").toString())) {

                    }*/
                } else if ("1".equals(bidMap.get("bid").toString())) {
                    //废标
                    bidMap.put("isComplete", "0");
                    bidMap.put("tenderStatus", "3");
                } else if ("2".equals(bidMap.get("bid").toString())) {
                    //流标
                    bidMap.put("isComplete", "0");
                    bidMap.put("tenderStatus", "2");
                }
            } else if ("2".equals(bidMap.get("auditStatus").toString())) {
                bidMap.put("isComplete", "0");
                bidMap.put("tenderStatus", "4");

            } else if ("-1".equals(bidMap.get("auditStatus").toString())) {
                bidMap.put("isComplete", "1");
                bidMap.put("tenderStatus", "待审核");
            }

        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getTenderList(Map<String, Object> map) {
        Integer pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer pageNo = (Integer.parseInt(String.valueOf(map.get("pageNo"))) - 1) * pageSize;
        List<Map<String, Object>> list = baseMapper.getTenderList(String.valueOf(map.get("account")), pageNo, pageSize,map);
        if (list.size()>0) {
            for (Map<String, Object> bidMap : list) {
                //0审核中，1审核完成, 2 已拒绝 -1待审核
                if ("0".equals(bidMap.get("auditStatus").toString())) {
                    bidMap.put("isComplete", "1");
                    bidMap.put("tenderStatus", "审核中");
                    //审核完成，走正常流程
                } else if ("1".equals(bidMap.get("auditStatus").toString())) {
                    //0默认，2流标，1废标
                    if ("0".equals(bidMap.get("bid").toString())) {
                        //默认的时候判断标的状态
                        //null:未被邀请 1:邀请2:已接受3:评标4:定标审核5：定标
                        if (bidMap.get("status") == null) {
                            String startTime = bidMap.get("startTime").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date date = sdf.parse(startTime);
                                long time = System.currentTimeMillis();
                                long time1 = date.getTime();
                                if (time > time1) {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "开标中");
                                }else {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "等待开标");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else if ("5".equals(bidMap.get("status").toString())) {
                            bidMap.put("isComplete", "0");
                            bidMap.put("tenderStatus", "1");
                        } else if ("1".equals(bidMap.get("status").toString())) {
                            String startTime = bidMap.get("startTime").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date date = sdf.parse(startTime);
                                long time = System.currentTimeMillis();
                                long time1 = date.getTime();
                                if (time > time1) {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "开标中");
                                }else {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "等待开标");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }else if ("2".equals(bidMap.get("status").toString())){
                            String startTime = bidMap.get("startTime").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date date = sdf.parse(startTime);
                                long time = System.currentTimeMillis();
                                long time1 = date.getTime();
                                if (time > time1) {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "开标中");
                                }else {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "等待开标");
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        } else if ("3".equals(bidMap.get("status").toString())) {
                            bidMap.put("isComplete", "1");
                            bidMap.put("tenderStatus", "评标中");
                        } else if ("4".equals(bidMap.get("status").toString())) {
                            bidMap.put("isComplete", "1");
                            bidMap.put("tenderStatus", "定标中");
                        } else {
                            String startTime = bidMap.get("startTime").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date date = sdf.parse(startTime);
                                long time = System.currentTimeMillis();
                                long time1 = date.getTime();
                                if (time > time1) {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "开标中");
                                }else {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "等待开标");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }/* else if ("2".equals(bidMap.get("status").toString())) {
                        //企业已经投标，现在时间>开标时间  =开标中
                        String startTime = bidMap.get("startTime").toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date date = sdf.parse(startTime);
                            long time = System.currentTimeMillis();
                            long time1 = date.getTime();
                            if (time > time1) {
                                bidMap.put("isComplete", "1");
                                bidMap.put("tenderStatus", "开标中");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }*/
                    } else if ("1".equals(bidMap.get("bid").toString())) {
                        //废标
                        bidMap.put("isComplete", "0");
                        bidMap.put("tenderStatus", "3");
                    } else if ("2".equals(bidMap.get("bid").toString())) {
                        //流标
                        bidMap.put("isComplete", "0");
                        bidMap.put("tenderStatus", "2");
                    }
                } else if ("2".equals(bidMap.get("auditStatus").toString())) {
                    bidMap.put("isComplete", "0");
                    bidMap.put("tenderStatus", "4");

                } else if ("-1".equals(bidMap.get("auditStatus").toString())) {
                    bidMap.put("isComplete", "1");
                    bidMap.put("tenderStatus", "待审核");
                }

            }
            return list;
        }else {

            return list;
        }
    }

    @Override
    public Map<String, Object> tenderDetails(String tenderCode) {
        Map<String, Object> bidMap = baseMapper.tenderDetails(tenderCode);
        if (bidMap.get("auditStatus") == null) {
            bidMap.put("message", "请输入正确的招标编号");
            return bidMap;
        } else {
            //0审核中，1审核完成, 2 已拒绝 -1待审核
            if ("0".equals(bidMap.get("auditStatus").toString())) {
                bidMap.put("isComplete", "1");
                bidMap.put("tenderStatus", "审核中");
                //审核完成，走正常流程
            } else if ("1".equals(bidMap.get("auditStatus").toString())) {
                //0默认，1流标，2废标
                if ("0".equals(bidMap.get("bid").toString())) {
                    //默认的时候判断标的状态
                    //null:未被邀请 1:邀请2:已接受3:评标4:定标审核5：定标

                    if (bidMap.get("status") == null) {
                        String startTime = bidMap.get("startTime").toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date date = sdf.parse(startTime);
                            long time = System.currentTimeMillis();
                            long time1 = date.getTime();
                            if (time > time1) {
                                bidMap.put("isComplete", "1");
                                bidMap.put("tenderStatus", "开标中");
                            }else {
                                bidMap.put("isComplete", "1");
                                bidMap.put("tenderStatus", "等待开标");
                            }
                    } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        } else if ("5".equals(bidMap.get("status").toString())) {
                        bidMap.put("isComplete", "0");
                        bidMap.put("tenderStatus", "1");
                    } else if ("1".equals(bidMap.get("status").toString())  ) {
                        String startTime = bidMap.get("startTime").toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date date = sdf.parse(startTime);
                            long time = System.currentTimeMillis();
                            long time1 = date.getTime();
                            if (time > time1) {
                                bidMap.put("isComplete", "1");
                                bidMap.put("tenderStatus", "开标中");
                            }else {
                                bidMap.put("isComplete", "1");
                                bidMap.put("tenderStatus", "等待开标");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }else if ( "2".equals(bidMap.get("status").toString())){
                        String startTime = bidMap.get("startTime").toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date date = sdf.parse(startTime);
                            long time = System.currentTimeMillis();
                            long time1 = date.getTime();
                            if (time > time1) {
                                bidMap.put("isComplete", "1");
                                bidMap.put("tenderStatus", "开标中");
                            }else {
                                bidMap.put("isComplete", "1");
                                bidMap.put("tenderStatus", "等待开标");
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    } else if ("3".equals(bidMap.get("status").toString())) {
                        bidMap.put("isComplete", "1");
                        bidMap.put("tenderStatus", "评标中");
                    } else if ("4".equals(bidMap.get("status").toString())) {
                        bidMap.put("isComplete", "1");
                        bidMap.put("tenderStatus", "定标中");
                    }else {
                        //企业已经投标，现在时间>开标时间  =开标中
                        String startTime = bidMap.get("startTime").toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date date = sdf.parse(startTime);
                            long time = System.currentTimeMillis();
                            long time1 = date.getTime();
                            if (time > time1) {
                                bidMap.put("isComplete", "1");
                                bidMap.put("tenderStatus", "开标中");
                            }else {
                                bidMap.put("isComplete", "1");
                                bidMap.put("tenderStatus", "等待开标");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    /* else if ("2".equals(bidMap.get("status").toString())) {
                        //企业已经投标，现在时间>开标时间  =开标中
                        String startTime = bidMap.get("startTime").toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date date = sdf.parse(startTime);
                            long time = System.currentTimeMillis();
                            long time1 = date.getTime();
                            if (time > time1) {
                                bidMap.put("isComplete", "1");
                                bidMap.put("tenderStatus", "开标中");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }*/
                } else if ("1".equals(bidMap.get("bid").toString())) {
                    //废标
                    bidMap.put("isComplete", "0");
                    bidMap.put("tenderStatus", "3");
                } else if ("2".equals(bidMap.get("bid").toString())) {
                    //流标
                    bidMap.put("isComplete", "0");
                    bidMap.put("tenderStatus", "2");
                }
            } else if ("2".equals(bidMap.get("auditStatus").toString())) {
                bidMap.put("isComplete", "0");
                bidMap.put("tenderStatus", "4");

            } else if ("-1".equals(bidMap.get("auditStatus").toString())) {
                bidMap.put("isComplete", "1");
                bidMap.put("tenderStatus", "待审核");
            }
            return bidMap;
        }
    }

    @Override
    public List<Map<String, Object>> tenderFile(String tenderCode) {
        return baseMapper.tenderFile(tenderCode);
    }

    @Override
    public List<Map<String, Object>> winningBidList(Map<String, Object> map) {
        Integer pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer pageNo = (Integer.parseInt(String.valueOf(map.get("pageNo"))) - 1) * pageSize;
        return baseMapper.winningBidList(String.valueOf(map.get("account")), pageNo, pageSize);
    }

    @Override
    public Map<String, Object> winningBidDetails(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        String tenderCode = String.valueOf(map.get("tenderCode"));
        try {
            Map<String, Object> tenderMap = baseMapper.winningBidDetails(tenderCode);
 /*           Map<String, String> tenderDetailList= baseMapper.tenderDetailList(tenderCode);
            String temp = tenderDetailList.get("temp");
            String price = tenderDetailList.get("price");
            String[] priceArr = price.split("\\|");
            List<PriceTemp> priceTempList = (List<PriceTemp>) JSONArray.parseArray(temp, PriceTemp.class);
            PriceTemp [] priceList=new PriceTemp [priceTempList.size()];
            for (int i = 0; i < priceTempList.size(); i++) {
                priceList[i]= priceTempList.get(i);
            }
            for (int i = 0; i < priceList.length; i++) {
                priceList[i].setPrice(priceArr[i]);
            }
            //转换
            AppPriceTemp [] appPriceTemp=new AppPriceTemp [priceTempList.size()];
            for (int i = 0; i < appPriceTemp.length; i++) {
                appPriceTemp[i].setName(priceList[i].getName());
                appPriceTemp[i].setPrice(priceList[i].getPrice());
                appPriceTemp[i].setType(priceList[i].getType());
                appPriceTemp[i].setDescription(priceList[i].getDesc());
            }*/
            result.put("code", "200");
            result.put("success", true);
            result.put("message", "查询成功");
            result.put("data", tenderMap);
            //  result.put("tenderDetailList",appPriceTemp);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", "500");
            result.put("success", false);
            result.put("message", "查询失败");
            return result;
        }
    }

    @Override
    public Map<String, Object> selectionList(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        Integer pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer pageNo = (Integer.parseInt(String.valueOf(map.get("pageNo"))) - 1) * pageSize;
        try {
            List<Map<String, Object>> list = baseMapper.selectionList(String.valueOf(map.get("account")), pageNo, pageSize,map);
            for (Map<String, Object> bidMap : list) {
                //0审核中，1审核完成, 2 已拒绝 -1待审核
                if ("0".equals(bidMap.get("auditStatus").toString())) {
                    bidMap.put("isComplete", "1");
                    bidMap.put("tenderStatus", "审核中");
                    //审核完成，走正常流程
                } else if ("1".equals(bidMap.get("auditStatus").toString())) {
                    //0默认，1流标，2废标
                    if ("0".equals(bidMap.get("bid").toString())) {
                        //默认的时候判断标的状态
                        //null:未被邀请 1:邀请2:已接受3:评标4:定标审核5：定标
                        if (bidMap.get("status") == null) {
                            String startTime = bidMap.get("startTime").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date date = sdf.parse(startTime);
                                long time = System.currentTimeMillis();
                                long time1 = date.getTime();
                                if (time > time1) {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "开标中");
                                }else {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "等待开标");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else if ("5".equals(bidMap.get("status").toString())) {
                            bidMap.put("isComplete", "0");
                            bidMap.put("tenderStatus", "1");
                        } else if ("1".equals(bidMap.get("status").toString())) {
                            bidMap.put("isComplete", "1");
                            bidMap.put("tenderStatus", "等待开标");
                        }else if ( "2".equals(bidMap.get("status").toString())){
                            String startTime = bidMap.get("startTime").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date date = sdf.parse(startTime);
                                long time = System.currentTimeMillis();
                                long time1 = date.getTime();
                                if (time > time1) {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "开标中");
                                }else {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "等待开标");
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else if ("3".equals(bidMap.get("status").toString())) {
                            bidMap.put("isComplete", "1");
                            bidMap.put("tenderStatus", "评标中");
                        } else if ("4".equals(bidMap.get("status").toString())) {
                            bidMap.put("isComplete", "1");
                            bidMap.put("tenderStatus", "定标中");
                        } else  {
                            //企业已经投标，现在时间>开标时间  =开标中
                            String startTime = bidMap.get("startDate").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date date = sdf.parse(startTime);
                                long time = System.currentTimeMillis();
                                long time1 = date.getTime();
                                if (time > time1) {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "开标中");
                                }else {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "等待开标");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if ("1".equals(bidMap.get("bid").toString())) {
                        //废标
                        bidMap.put("isComplete", "0");
                        bidMap.put("tenderStatus", "3");
                    } else if ("2".equals(bidMap.get("bid").toString())) {
                        //流标
                        bidMap.put("isComplete", "0");
                        bidMap.put("tenderStatus", "2");
                    }
                } else if ("2".equals(bidMap.get("auditStatus").toString())) {
                    bidMap.put("isComplete", "1");
                    bidMap.put("tenderStatus", "4");

                } else if ("-1".equals(bidMap.get("auditStatus").toString())) {
                    bidMap.put("isComplete", "1");
                    bidMap.put("tenderStatus", "待审核");
                }

            }
            result.put("code", "200");
            result.put("success", true);
            result.put("message", "查询成功");
            result.put("data", list);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", "500");
            result.put("success", false);
            result.put("message", "查询失败");
            return result;
        }
    }

    @Override
    public Map<String, Object> candidateBidList(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        String tenderCode = String.valueOf(map.get("tenderCode"));
        if (tenderCode == null || tenderCode.isEmpty()) {
            result.put("code", "500");
            result.put("success", false);
            result.put("message", "没有输入招标编号");
            return result;
        }

            List<Map<String, Object>> candidateBidList = baseMapper.candidateBidList(tenderCode);

            if (candidateBidList.size()!=0){
                result.put("code", "200");
                result.put("success", true);
                result.put("message", "查询成功");
                result.put("data", candidateBidList);
                return result;
            }else {
                //招标
             /////////////
               /* Map<String, Object> bidMap = baseMapper.tenderDetails(tenderCode);
                //正常流程   1
                if ("1".equals(bidMap.get("auditStatus").toString())){
                    //没废标也没流标
                    if ("0".equals(bidMap.get("bid").toString())){
                        if (bidMap.get("status") == null) {
                            String startTime = bidMap.get("startTime").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date date = sdf.parse(startTime);
                                long time = System.currentTimeMillis();
                                long time1 = date.getTime();
                                if (time > time1) {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "开标中");
                                    List<Map<String, Object>> candidateBidListZb = baseMapper.candidateBidListZb(tenderCode);

                                    result.put("code", "200");
                                    result.put("success", true);
                                    result.put("message", "查询成功");
                                    result.put("data", candidateBidListZb);
                                    return result;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else if ("1".equals(bidMap.get("status").toString())  ) {
                            String startTime = bidMap.get("startTime").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date date = sdf.parse(startTime);
                                long time = System.currentTimeMillis();
                                long time1 = date.getTime();
                                if (time > time1) {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "开标中");
                                    List<Map<String, Object>> candidateBidListZb = baseMapper.candidateBidListZb(tenderCode);

                                    result.put("code", "200");
                                    result.put("success", true);
                                    result.put("message", "查询成功");
                                    result.put("data", candidateBidListZb);
                                    return result;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else if ( "2".equals(bidMap.get("status").toString())){
                            String startTime = bidMap.get("startTime").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date date = sdf.parse(startTime);
                                long time = System.currentTimeMillis();
                                long time1 = date.getTime();
                                if (time > time1) {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "开标中");
                                    List<Map<String, Object>> candidateBidListZb = baseMapper.candidateBidListZb(tenderCode);

                                    result.put("code", "200");
                                    result.put("success", true);
                                    result.put("message", "查询成功");
                                    result.put("data", candidateBidListZb);
                                    return result;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        } else if ("3".equals(bidMap.get("status").toString())) {
                            bidMap.put("isComplete", "1");
                            bidMap.put("tenderStatus", "评标中");
                            *//*开标中， 显示 投过标的企业 ；
                                评标中， 显示 评过标的企业；
                                定标中，显示  选择的定标的企业*//*
                            List<Map<String, Object>> candidateBidListZb = baseMapper.candidateBidListZbPingBiao1(tenderCode);
                            result.put("code", "200");
                            result.put("success", true);
                            result.put("message", "查询成功");
                            result.put("data", candidateBidListZb);
                            return result;

                        } else if ("4".equals(bidMap.get("status").toString())) {
                            bidMap.put("isComplete", "1");
                            bidMap.put("tenderStatus", "定标中");

                            List<Map<String, Object>> candidateBidListZb = baseMapper.candidateBidListZbDingBiao1(tenderCode);
                            result.put("code", "200");
                            result.put("success", true);
                            result.put("message", "查询成功");
                            result.put("data", candidateBidListZb);
                            return result;
                        }else {
                            //企业已经投标，现在时间>开标时间  =开标中
                            String startTime = bidMap.get("startTime").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date date = sdf.parse(startTime);
                                long time = System.currentTimeMillis();
                                long time1 = date.getTime();
                                if (time > time1) {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "开标中");
                                    List<Map<String, Object>> candidateBidListZb = baseMapper.candidateBidListZb(tenderCode);

                                    result.put("code", "200");
                                    result.put("success", true);
                                    result.put("message", "查询成功");
                                    result.put("data", candidateBidListZb);
                                    return result;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }*/

                ////////////

                Map<String, Object> bidMap = baseMapper.tenderDetails(tenderCode);

                if ("1".equals(bidMap.get("auditStatus").toString())){
                    if (bidMap.get("status") == null) {
                        String startTime = bidMap.get("startTime").toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date date = sdf.parse(startTime);
                            long time = System.currentTimeMillis();
                            long time1 = date.getTime();
                            if (time > time1) {
                                bidMap.put("isComplete", "1");
                                bidMap.put("tenderStatus", "开标中");
                                List<Map<String, Object>> candidateBidListZb = baseMapper.candidateBidListZb(tenderCode);

                                result.put("code", "200");
                                result.put("success", true);
                                result.put("message", "查询成功");
                                result.put("data", candidateBidListZb);
                                return result;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else if ("4".equals(bidMap.get("status").toString())) {
                        bidMap.put("isComplete", "1");
                        bidMap.put("tenderStatus", "定标中");

                        List<Map<String, Object>> candidateBidListZb = baseMapper.candidateBidListZbDingBiao1(tenderCode);
                        result.put("code", "200");
                        result.put("success", true);
                        result.put("message", "查询成功");
                        result.put("data", candidateBidListZb);
                        return result;
                    }else {
                        List<Map<String, Object>> candidateBidListZb = baseMapper.candidateBidListZb(tenderCode);
                        result.put("code", "200");
                        result.put("success", true);
                        result.put("message", "查询成功");
                        result.put("data", candidateBidListZb);
                        return result;
                    }
                }

            }
      List<Map<String, Object>> candidateBidListZb  =new ArrayList<>();
        result.put("code", "200");
        result.put("success", true);
        result.put("message", "查询成功");
        result.put("data", candidateBidListZb);
        return result;

    }

    @Override
    public Map<String, Object> tenderDetail(Map<String, Object> map) {
        HashMap<String, Object> result = new HashMap<>();
        String tenderCode = String.valueOf(map.get("tenderCode"));
        if (map.get("unitCode") == null) {
            try {
                Map<String, String> tempDetailAndPrice = baseMapper.tenderDetail(tenderCode);
                String temp = tempDetailAndPrice.get("temp");
                String price = tempDetailAndPrice.get("price");
                List<PriceTemp> tempList = (List<PriceTemp>) JSONArray.parseArray(temp, PriceTemp.class);
                String[] priceArr = null;
                PriceTemp[] priceList = new PriceTemp[tempList.size()];
                if (price != null) {
                    priceArr = price.split("\\|");
                    for (int i = 0; i < tempList.size(); i++) {
                        priceList[i] = tempList.get(i);
                    }
                    for (int i = 0; i < priceList.length; i++) {
                        priceList[i].setPrice(priceArr[i]);
                    }
                } else {
                    for (int i = 0; i < tempList.size(); i++) {
                        priceList[i] = tempList.get(i);
                    }
                }
                //转换
                AppPriceTemp[] appPriceTemp = new AppPriceTemp[tempList.size()];
                for (int i = 0; i < appPriceTemp.length; i++) {
                    AppPriceTemp appPrice = new AppPriceTemp();
                    appPrice.setName(priceList[i].getName());
                    appPrice.setPrice(priceList[i].getPrice());
                    appPrice.setType(priceList[i].getType());
                    appPrice.setDescription(priceList[i].getDesc());

                    appPriceTemp[i]=appPrice;
                }
                result.put("code", "200");
                result.put("success", true);
                result.put("message", "查询成功");
                result.put("data", appPriceTemp);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                result.put("code", "500");
                result.put("success", false);
                result.put("message", "查询失败");
                return result;
            }

        } else {
            String unitCode = String.valueOf(map.get("unitCode"));

            Map<String, String> tempDetailAndPrice = baseMapper.tenderDetailAndCompany(tenderCode, unitCode);
            if (tempDetailAndPrice == null || tempDetailAndPrice.isEmpty()) {
                AppPriceTemp apptemp = new AppPriceTemp();
                apptemp.setDescription(null);
                apptemp.setType(null);
                apptemp.setPrice(null);
                apptemp.setName(null);
                ArrayList<AppPriceTemp> arrayList = new ArrayList<>();
                arrayList.add(apptemp);
                result.put("code", "200");
                result.put("success", false);
                result.put("message", "未查到数据");
                result.put("data", arrayList);
                return result;
            } else {
                if (tempDetailAndPrice != null || !tempDetailAndPrice.isEmpty()) {
                    String temp = tempDetailAndPrice.get("temp");
                    String price = tempDetailAndPrice.get("price");
                    List<PriceTemp> tempList = (List<PriceTemp>) JSONArray.parseArray(temp, PriceTemp.class);
                    String[] priceArr = null;
                    PriceTemp[] priceList = new PriceTemp[tempList.size()];
                    if (price != null) {
                        priceArr = price.split("\\|");
                        for (int i = 0; i < tempList.size(); i++) {
                            priceList[i] = tempList.get(i);
                        }
                        for (int i = 0; i < priceList.length; i++) {
                            if (priceArr.length<i){
                                priceList[i].setPrice(null);
                            }else {
                                priceList[i].setPrice(priceArr[i]);
                            }
                        }
                    } else {
                        for (int i = 0; i < tempList.size(); i++) {
                            priceList[i] = tempList.get(i);
                        }
                    }
                    //List
                    //转换
                    AppPriceTemp[] appPriceTemp = new AppPriceTemp[tempList.size()];
                    for (int i = 0; i < tempList.size(); i++) {
                        AppPriceTemp appPrice = new AppPriceTemp();

                        appPrice.setName(priceList[i].getName());
                        appPrice.setPrice(priceList[i].getPrice());
                        appPrice.setType(priceList[i].getType());
                        appPrice.setDescription(priceList[i].getDesc());

                        appPriceTemp[i]=appPrice;


                    }

                    result.put("code", "200");
                    result.put("success", true);
                    result.put("message", "查询成功");
                    result.put("data", appPriceTemp);
                    return result;

                } else {
                    result.put("code", "200");
                    result.put("success", false);
                    result.put("message", "未查到数据");
                    return result;
                }
            }
        }
    }


    @Override
    public Map<String, Object> toDoTasks(Map<String, Object> map) {
        HashMap<String, Object> result = new HashMap<>();
        Integer pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer pageNo = (Integer.parseInt(String.valueOf(map.get("pageNo"))) - 1) * pageSize;
        // List<Map<String, Object>> list = baseMapper.toDoTasks(map.get("account").toString(), pageNo, pageSize, map.get("type").toString());
        // 1:招标审批   2：定标审批
        if ("1".equals(map.get("type").toString())) {
            //查此用户是否在招标审批节点上 审批
           // selectApprove(map.get("account").toString());
            List<Map<String, Object>> list = baseMapper.toDoTasks(map.get("account").toString(), pageNo, pageSize, map.get("type").toString());
            for (Map<String, Object> bidMap : list) {
                //0审核中，1审核完成, 2 已拒绝 -1待审核
                if ("0".equals(bidMap.get("auditStatus").toString())) {
                    bidMap.put("isComplete", "1");
                    bidMap.put("tenderStatus", "审核中");
                    //审核完成，走正常流程
                } else if ("1".equals(bidMap.get("auditStatus").toString())) {
                    //0默认，1流标，2废标
                    if ("0".equals(bidMap.get("bid").toString())) {
                        //默认的时候判断标的状态
                        //null:未被邀请 1:邀请2:已接受3:评标4:定标审核5：定标
                        if (bidMap.get("status") == null) {
                            String startTime = bidMap.get("startTime").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date date = sdf.parse(startTime);
                                long time = System.currentTimeMillis();
                                long time1 = date.getTime();
                                if (time > time1) {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "开标中");
                                }else {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "等待开标");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else if ("5".equals(bidMap.get("status").toString())) {
                            bidMap.put("isComplete", "0");
                            bidMap.put("tenderStatus", "1");
                        } else if ("1".equals(bidMap.get("status").toString())  ) {
                            bidMap.put("isComplete", "1");
                            bidMap.put("tenderStatus", "等待开标");
                        }else if ("2".equals(bidMap.get("status").toString())){
                            String startTime = bidMap.get("startTime").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date date = sdf.parse(startTime);
                                long time = System.currentTimeMillis();
                                long time1 = date.getTime();
                                if (time > time1) {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "开标中");
                                }else {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "等待开标");
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else if ("3".equals(bidMap.get("status").toString())) {
                            bidMap.put("isComplete", "1");
                            bidMap.put("tenderStatus", "评标中");
                        } else if ("4".equals(bidMap.get("status").toString())) {
                            bidMap.put("isComplete", "1");
                            bidMap.put("tenderStatus", "定标中");
                        } else  {
                            //企业已经投标，现在时间>开标时间  =开标中
                            String startTime = bidMap.get("startDate").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date date = sdf.parse(startTime);
                                long time = System.currentTimeMillis();
                                long time1 = date.getTime();
                                if (time > time1) {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "开标中");
                                }else {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "等待开标");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if ("1".equals(bidMap.get("bid").toString())) {
                        //废标
                        bidMap.put("isComplete", "0");
                        bidMap.put("tenderStatus", "3");
                    } else if ("2".equals(bidMap.get("bid").toString())) {
                        //流标
                        bidMap.put("isComplete", "0");
                        bidMap.put("tenderStatus", "2");
                    }
                } else if ("2".equals(bidMap.get("auditStatus").toString())) {
                    bidMap.put("isComplete", "0");
                    bidMap.put("tenderStatus", "4");

                } else if ("-1".equals(bidMap.get("auditStatus").toString())) {
                    bidMap.put("isComplete", "1");
                    bidMap.put("tenderStatus", "待审核");
                }
            }
            result.put("code", "200");
            result.put("success", true);
            result.put("message", "查询成功");
            result.put("data", list);
            return result;
            //2.定标审批
        } else if ("2".equals(map.get("type").toString())) {
            List<Map<String, Object>> list = baseMapper.toDoTasksD(map.get("account").toString(), pageNo, pageSize, map.get("type").toString());
            for (Map<String, Object> bidMap : list) {
                //0审核中，1审核完成, 2 已拒绝 -1待审核
                if ("0".equals(bidMap.get("auditStatus").toString())) {
                    bidMap.put("isComplete", "1");
                    bidMap.put("tenderStatus", "审核中");
                    //审核完成，走正常流程
                } else if ("1".equals(bidMap.get("auditStatus").toString())) {
                    //0默认，1流标，2废标
                    if ("0".equals(bidMap.get("bid").toString())) {
                        //默认的时候判断标的状态
                        //null:未被邀请 1:邀请2:已接受3:评标4:定标审核5：定标
                        if (bidMap.get("status") == null) {

                            String startTime = bidMap.get("startTime").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date date = sdf.parse(startTime);
                                long time = System.currentTimeMillis();
                                long time1 = date.getTime();
                                if (time > time1) {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "开标中");
                                }else {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "等待开标");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else if ("5".equals(bidMap.get("status").toString())) {
                            bidMap.put("isComplete", "0");
                            bidMap.put("tenderStatus", "1");
                        } else if ("1".equals(bidMap.get("status").toString())
                               ) {
                            bidMap.put("isComplete", "1");
                            bidMap.put("tenderStatus", "等待开标");
                        }else if ("2".equals(bidMap.get("status").toString())){
                            String startTime = bidMap.get("startTime").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date date = sdf.parse(startTime);
                                long time = System.currentTimeMillis();
                                long time1 = date.getTime();
                                if (time > time1) {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "开标中");
                                }else {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "等待开标");
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else if ("3".equals(bidMap.get("status").toString())) {
                            bidMap.put("isComplete", "1");
                            bidMap.put("tenderStatus", "评标中");
                        } else if ("4".equals(bidMap.get("status").toString())) {
                            bidMap.put("isComplete", "1");
                            bidMap.put("tenderStatus", "定标中");
                        } else if ("2".equals(bidMap.get("status").toString())) {
                            //企业已经投标，现在时间>开标时间  =开标中
                            String startTime = bidMap.get("startDate").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date date = sdf.parse(startTime);
                                long time = System.currentTimeMillis();
                                long time1 = date.getTime();
                                if (time > time1) {
                                    bidMap.put("isComplete", "1");
                                    bidMap.put("tenderStatus", "开标中");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if ("1".equals(bidMap.get("bid").toString())) {
                        //废标
                        bidMap.put("isComplete", "0");
                        bidMap.put("tenderStatus", "3");
                    } else if ("2".equals(bidMap.get("bid").toString())) {
                        //流标
                        bidMap.put("isComplete", "0");
                        bidMap.put("tenderStatus", "2");
                    }
                } else if ("2".equals(bidMap.get("auditStatus").toString())) {
                    bidMap.put("isComplete", "0");
                    bidMap.put("tenderStatus", "4");

                } else if ("-1".equals(bidMap.get("auditStatus").toString())) {
                    bidMap.put("isComplete", "1");
                    bidMap.put("tenderStatus", "待审核");
                }
            }
            result.put("code", "200");
            result.put("success", true);
            result.put("message", "查询成功");
            result.put("data", list);
            return result;
        }
        result.put("code", "500");
        result.put("success", true);
        result.put("message", "查询失败");
        return result;
    }




    @Override
    public Map<String, Object> updateEvaluationBid(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        //Integer num = null;
        String id = baseMapper.selectEvaluationBid(map.get("tenderCode").toString(), map.get("unitCode").toString());
      //String tenderId=  baseMapper.selectTenderId(map.get("tenderCode").toString());
      //String companyId=baseMapper.selectCompamyId( map.get("unitCode").toString());

        String evaluateTimes = map.get("evaluateTimes").toString();
        if (id != null) {
            if ("1".equals(evaluateTimes)){
                baseMapper.updateEvaluationBid1(map.get("evaluateContent").toString(), id);
                baseMapper.updatestatusPb(id);
            }else if ("2".equals(evaluateTimes)){
                baseMapper.updateEvaluationBid2(map.get("evaluateContent").toString(), id);
            }else if ("3".equals(evaluateTimes)){
                baseMapper.updateEvaluationBid3(map.get("evaluateContent").toString(), id);
            }
            //baseMapper.updateEvaluationBid(map.get("evaluateContent").toString(), map.get("evaluateTimes").toString(), id);
            result.put("code", "200");
            result.put("success", true);
            result.put("message", "评价成功");
            return result;
        }else {
            result.put("code", "200");
            result.put("success", true);
            result.put("message", "查不到可评价的相关信息");
            return result;
        }

     /*   if (num != null) {*/

       /* } else {*/
      /*      result.put("code", "500");
            result.put("success", false);
            result.put("message", "评价失败");
            return result;*/
     /*   }*/
    }

    @Override
    public Map<String, Object> submitbid(Map<String, Object> map) {
        String tenderCode = map.get("tenderCode").toString();
        String unitCode = map.get("unitCode").toString();
 /*     String tendId=  baseMapper.getid(tenderCode);
     String companyId= baseMapper.getCompanyId(unitCode);
     String ibId=baseMapper.getibId(tendId);*/


        Integer id = baseMapper.submitbid(tenderCode, unitCode);
        if (id != null) {
            Integer num = baseMapper.updateSubmitbid(id);
        }
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("success", true);
        result.put("message", "提交成功");
        return result;
    }

    @Override
    public Map<String, Object> reviewCalibration(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        String tenderCode = map.get("tenderCode").toString();
        String approvalResult = map.get("approvalResult").toString();
        Integer id = baseMapper.selectId(tenderCode);
        String type = map.get("type").toString();
        // 1：招标，2：定标
        if ("2".equals(type)) {


            //0不通过 1通过
            if ("0".equals(approvalResult)) {
               baseMapper.reviewCalibration(id.toString());
                result.put("code", "200");
                result.put("success", true);
                result.put("message", "提交成功");
                return result;
            } else if ("1".equals(approvalResult)) {
                baseMapper.reviewCalibrationPass(id.toString());
                result.put("code", "200");
                result.put("success", true);
                result.put("message", "提交成功");
                return result;
            }
        } else if ("1".equals(type)) {
          Integer zbid=  baseMapper.selectIdZb(tenderCode);
            //0不通过 1通过
            if ("0".equals(approvalResult)) {
                baseMapper.reviewCalibrationBid(zbid);
                result.put("code", "200");
                result.put("success", true);
                result.put("message", "提交成功");
                return result;
            } else if ("1".equals(approvalResult)) {
                baseMapper.reviewCalibrationPassBid(zbid);
                result.put("code", "200");
                result.put("success", true);
                result.put("message", "提交成功");
                return result;
            }

        }

        result.put("code", "500");
        result.put("success", false);
        result.put("message", "提交失败");
        return result;

    }


    @Override
    public Map<String, Object> missionDetails(Map<String, Object> map) {
        HashMap<String, Object> result = new HashMap<>();
        String type = map.get("type").toString();
        String account = map.get("account").toString();
        String tenderCode = map.get("tenderCode").toString();
        Map<String, Object> bidMap = baseMapper.selectDetails(account, tenderCode);
        Map<String, Object> resultMap = flowStatus(bidMap);
        //1：招标审批  2：定标审批
        //canApproval 是否有权限审批 0：没有  1：有
        String projectId = baseMapper.selectProjectId(tenderCode);
        if (projectId == null) {
            resultMap.put("canApproval", "0");
        } else {
            if ("1".equals(type)) {
                Integer num = baseMapper.canApproval(account, projectId);
                if (num == 1 || num > 1) {
                    resultMap.put("canApproval", "1");
                } else {
                    resultMap.put("canApproval", "0");
                }
            } else if ("2".equals(type)) {
                Integer number = baseMapper.canApprovalBid(account, projectId);
                if (number == 1 || number > 1) {
                    resultMap.put("canApproval", "1");
                } else {
                    resultMap.put("canApproval", "0");
                }
            }
        }

        List<Map<String, Object>> filePaths = baseMapper.filePath(tenderCode);
        try {
            result.put("code", "200");
            result.put("message", "查询成功");
            result.put("success", true);
            result.put("data", resultMap);
            result.put("startFileList", filePaths);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", "500");
            result.put("success", false);
            result.put("message", "查询失败");
            return result;
        }
    }


    @Override
    public Map<String, Object> currentBidOrRejectedBid(Map<String, Object> map) {

        Integer num = null;
        //1流标2废标
        if ("1".equals(map.get("operateResult").toString())) {
            num = baseMapper.currentBid(map.get("tenderCode").toString());
        } else if ("2".equals(map.get("operateResult").toString())) {
            num = baseMapper.RejectedBid(map.get("tenderCode").toString());
        }
        Map<String, Object> result = new HashMap<>();
        if (num != null) {
            result.put("code", "200");
            result.put("success", true);
            result.put("message", "操作成功");
            return result;
        } else {
            result.put("code", "500");
            result.put("success", false);
            result.put("message", "操作失败");
            return result;
        }


    }


    public Map<String, Object> flowStatus(Map<String, Object> bidMap) {

        //0审核中，1审核完成, 2 已拒绝 -1待审核
        if ("0".equals(bidMap.get("auditStatus").toString())) {
            bidMap.put("isComplete", "1");
            bidMap.put("tenderStatus", "审核中");
            //审核完成，走正常流程
        } else if ("1".equals(bidMap.get("auditStatus").toString())) {
            //0默认，1流标，2废标
            if ("0".equals(bidMap.get("bid").toString())) {
                //默认的时候判断标的状态
                //null:未被邀请 1:邀请2:已接受3:评标4:定标审核5：定标
                if (bidMap.get("status") == null) {
                    bidMap.put("isComplete", "1");
                    bidMap.put("tenderStatus", "等待开标");
                } else if ("5".equals(bidMap.get("status").toString())) {
                    bidMap.put("isComplete", "0");
                    bidMap.put("tenderStatus", "1");
                } else if ("1".equals(bidMap.get("status").toString()) ||
                        "2".equals(bidMap.get("status").toString()) || bidMap.get("status") == null) {
                    bidMap.put("isComplete", "1");
                    bidMap.put("tenderStatus", "等待开标");
                } else if ("3".equals(bidMap.get("status").toString())) {
                    bidMap.put("isComplete", "1");
                    bidMap.put("tenderStatus", "评标中");
                } else if ("4".equals(bidMap.get("status").toString())) {
                    bidMap.put("isComplete", "1");
                    bidMap.put("tenderStatus", "定标中");
                } else if ("2".equals(bidMap.get("status").toString())) {
                    //企业已经投标，现在时间>开标时间  =开标中
                    String startTime = bidMap.get("startTime").toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Date date = sdf.parse(startTime);
                        long time = System.currentTimeMillis();
                        long time1 = date.getTime();
                        if (time > time1) {
                            bidMap.put("isComplete", "1");
                            bidMap.put("tenderStatus", "开标中");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } else if ("1".equals(bidMap.get("bid").toString())) {
                //流标
                bidMap.put("isComplete", "0");
                bidMap.put("tenderStatus", "3");
            } else if ("2".equals(bidMap.get("bid").toString())) {
                //废标
                bidMap.put("isComplete", "0");
                bidMap.put("tenderStatus", "2");
            }
        } else if ("2".equals(bidMap.get("auditStatus").toString())) {
            bidMap.put("isComplete", "0");
            bidMap.put("tenderStatus", "4");

        } else if ("-1".equals(bidMap.get("auditStatus").toString())) {
            bidMap.put("isComplete", "1");
            bidMap.put("tenderStatus", "待审核");
        }

        return bidMap;
    }



    private void selectApprove(String account) {
       String id= baseMapper.selectUserIdByUserName(account);



    }
}


