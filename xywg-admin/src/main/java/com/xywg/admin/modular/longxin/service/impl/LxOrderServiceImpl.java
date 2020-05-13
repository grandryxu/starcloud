package com.xywg.admin.modular.longxin.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.common.constant.state.ManagerStatus;
import com.xywg.admin.core.common.exception.BizExceptionEnum;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.util.MD5Util;
import com.xywg.admin.flow.entity.Order;
import com.xywg.admin.flow.service.OrderService;
import com.xywg.admin.flow.service.ProcessService;
import com.xywg.admin.modular.company.dao.SubContractorMapper;
import com.xywg.admin.modular.company.model.RegSubContractor;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.company.service.ISubContractorService;
import com.xywg.admin.modular.longxin.dao.LxOrderMapper;
import com.xywg.admin.modular.longxin.dao.LxTenderProcessRelationMapper;
import com.xywg.admin.modular.longxin.model.InviteBid;
import com.xywg.admin.modular.longxin.model.LxOrder;
import com.xywg.admin.modular.longxin.model.LxTender;
import com.xywg.admin.modular.longxin.model.LxTenderProcessRelation;
import com.xywg.admin.modular.longxin.service.LxOrderService;
import com.xywg.admin.modular.longxin.service.TenderingService;
import com.xywg.admin.modular.projectSubContractor.dao.ProjectSubContractorMapper;
import com.xywg.admin.modular.system.dao.UserMapper;
import com.xywg.admin.modular.system.model.Dept;
import com.xywg.admin.modular.system.model.User;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.system.service.IUserService;
import com.xywg.admin.modular.system.transfer.UserDto;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by tcw on 2019/7/9.
 */
@Service
public class LxOrderServiceImpl extends ServiceImpl<LxOrderMapper, LxOrder> implements LxOrderService{

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProcessService processService;

    @Autowired
    private LxTenderProcessRelationMapper relationMapper;


    @Autowired
    private ISubContractorService subContractorService;

    @Autowired
    private IUserService userService;


    @Autowired
    private LxOrderService lxOrderService;

    @Autowired
    private TenderingService tenderService;

    @Autowired
    private SubContractorMapper subContractorMapper;

    @Autowired
    private ProjectSubContractorMapper projectSubContractorMapper;

    @Autowired
    private IDeptService deptService;

    private   User getCurrentLoginUser(){
        User param = new User();
        param.setId(ShiroKit.getUser().getId());
        User user = userMapper.selectOne(param);
      return user;
    }

    @Override
    public   Page<LxOrder<RegSubContractor>> getAllReg( ) {
        User param = new User();
        param.setId(ShiroKit.getUser().getId());
        User user = userMapper.selectOne(param);
        List l = new ArrayList();
        l.add(Integer.parseInt(user.getRoleid()));
        //获取所有代办流程
        Page<Order> page =  orderService.findActiveOrders( new Order(),user.getId(),user.getDeptid() ,l);
        List<Order >  orderList = page.getRecords();
        List<LxOrder<RegSubContractor>>  lxOrders = new ArrayList<>();
        for (Order order : orderList) {
            LxOrder<RegSubContractor> lxod = new LxOrder<>();
            lxod.setOrder(order);
            LxTenderProcessRelation lpr = new LxTenderProcessRelation();
            lpr.setProcessId(order.getId());
            lpr.setType(5);
            LxTenderProcessRelation lxtd= relationMapper.getRegById(lpr);
            if(lxtd!=null){
                /*User  u = new User();
                u = userService.getByAccount(lxtd.getBussId());
                if(u==null){

                }else{
                    lxod.setBusiness(u);
                    lxOrders.add(lxod);
                }*/
                RegSubContractor regSubContractor = subContractorMapper.getRegSubContractorByOrganizationCode(lxtd.getBussId());
                if(regSubContractor==null){

                }else{
                    lxod.setBusiness(regSubContractor);
                    lxOrders.add(lxod);
                }
            }
        }
        Page<LxOrder<RegSubContractor>>  lxpage =new Page<>();
        lxpage.setRecords(lxOrders);
        lxpage.setTotal(lxOrders.size());
        return lxpage;
    }
    //根据登录用户获取所有待办流程
    @Override
    public   Page<LxOrder<LxTender>> getAll( ) {
        User param = new User();
        param.setId(ShiroKit.getUser().getId());
        User user = userMapper.selectOne(param);
        List l = new ArrayList();
        l.add(Integer.parseInt(user.getRoleid()));
        //获取所有代办流程
        Page<Order> page =  orderService.findActiveOrders( new Order(),user.getId(),user.getDeptid() ,l);
        List<Order>  orderList = page.getRecords();
        List<LxOrder<LxTender>>  lxOrders = new ArrayList<>();
        for (Order order : orderList) {
            LxOrder<LxTender> lxod = new LxOrder<>();
            lxod.setOrder(order);
            LxTenderProcessRelation lpr = new LxTenderProcessRelation();
            lpr.setProcessId(order.getId());
            lpr.setType(2);
            LxTender lxtd= relationMapper.getTenderById(lpr);
            if(lxtd!=null){
                lxod.setBusiness(lxtd);
                lxOrders.add(lxod);
            }
        }
        Page<LxOrder<LxTender>>  lxpage =new Page<>();
        lxpage.setRecords(lxOrders);
        lxpage.setTotal(lxOrders.size());


        return lxpage;
    }
    @Override
    public Map shenheReg(String orderId,Boolean success,String bussId, String socialCreditNumber) {
        String  message = "审核成功";
        Map map = new HashMap();
        map.put("code","200");

        User u= getCurrentLoginUser();
        List<Integer> l = new ArrayList<>();
        l.add(Integer.parseInt(u.getRoleid()));
        try {
            orderService.execute(orderId,"",success,u.getId(),u.getDeptid(),l );
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(success==false){
            message="驳回成功";
        }
        map.put("message",message);
        Order o = new Order();
        o.setId(orderId);
        o = orderService.findOne(o);
        //已拒绝
        if(o.getOrderStatus().equals(Order.STATUS_DROP) ){
            User usr = new User() ;
            usr.setAccount(socialCreditNumber);
            usr.setFlowStatus("2");
            userService.updateRegStatus(usr);

            RegSubContractor regSubContractor = new RegSubContractor();
            regSubContractor.setSocialCreditNumber(socialCreditNumber);
            regSubContractor.setFlowStatus("2");
            subContractorMapper.updateRegSubContractorStatus(regSubContractor);
            //已完成
        }else if(o.getOrderStatus().equals( Order.STATUS_FINISH )){
            User usr = new User() ;
            usr.setAccount(socialCreditNumber);
            usr.setFlowStatus("1");
            userService.updateRegStatus(usr);

            RegSubContractor regSubContractorByOrganizationCode = subContractorMapper.getRegSubContractorByOrganizationCode(socialCreditNumber);
            RegSubContractor regSubContractor = new RegSubContractor();
            regSubContractor.setSocialCreditNumber(socialCreditNumber);
            regSubContractor.setFlowStatus("1");
            subContractorMapper.updateRegSubContractorStatus(regSubContractor);

            SubContractor subContractor = new SubContractor();
            try {
                PropertyUtils.copyProperties(subContractor, regSubContractorByOrganizationCode);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            subContractor.setFlowStatus("1");
            subContractorMapper.insert(subContractor);

            // 参建单位关联
            String relsocialCreditNumber = deptService.selectById(ShiroKit.getUser().getDeptId()).getSocialCreditNumber();
            projectSubContractorMapper.addBussSubContractorConstruction(ShiroKit.getUser().getName(), relsocialCreditNumber, socialCreditNumber);
        }
        return  map;
    }



    private void addUser(String account, String companyName) {
        UserDto user = new UserDto();
        user.setAccount(account);
        user.setPassword("123456");
        user.setName(companyName);
        user.setIsEnterprise(1);
        user.setDeptid(239);
        user.setRoleid(139 + "");
        // 判断账号是否重复
        User theUser = userService.getByAccountAndId(account, "");
        if (theUser != null) {
            throw new XywgException(BizExceptionEnum.USER_ALREADY_REG);
        }
        // 完善账号信息
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(MD5Util.encrypt(user.getPassword()));
        user.setPassword(ShiroKit.md5(user.getPassword(), user.getSalt()));
        user.setStatus(ManagerStatus.OK.getCode());
        user.setUserType(1);
        user.setCreatetime(new Date());
        if(user.getStartTime() == null){
            user.setStartTime(new Date());
        }
        if(user.getEndTime() == null){
            try {
                user.setEndTime(new SimpleDateFormat("yyyy-mm-dd").parse("9999-12-30"));
            } catch (ParseException e) {
                System.out.println(e);
            }
        }
        userService.saveUserInfo(user);

    }
    @Override
    public Map shenhe(String orderId,Boolean success,String bussId) {
        String  message = "审核成功";
        Map map = new HashMap();
        map.put("code","200");

        User u= getCurrentLoginUser();
        List<Integer> l = new ArrayList<>();
                l.add(Integer.parseInt(u.getRoleid()));
        try {
            orderService.execute(orderId,"",success,u.getId(),u.getDeptid(),l );
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(success==false){

            message="驳回成功";
        }
        map.put("message",message);
        Order o = new Order();
        o.setId(orderId);
        o = orderService.findOne(o);
        //已拒绝
        if(o.getOrderStatus().equals(Order.STATUS_DROP) ){
            LxTender tender = new LxTender();
            tender.setId(bussId);
            tender.setStatus("2");
            tenderService.updateStatus(tender);
            //已完成
        }else if(o.getOrderStatus().equals( Order.STATUS_FINISH )){

            LxTender tender = new LxTender();
            tender.setId(bussId);
            tender.setStatus("1");
            tenderService.updateStatus(tender);
        }
        return  map;
    }

	@Override
	public Map shenheFix(String orderId, Boolean success, String bussId) {
        String  message = "审核成功";
        Map map = new HashMap();
        map.put("code","200");

        User u= getCurrentLoginUser();
        List<Integer> l = new ArrayList<>();
                l.add(Integer.parseInt(u.getRoleid()));
        try {
            orderService.execute(orderId,"",success,u.getId(),u.getDeptid(),l );
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(success==false){

            message="驳回成功";
        }
        map.put("message",message);
        Order o = new Order();
        o.setId(orderId);
        o = orderService.findOne(o);
        //已拒绝
        if(o.getOrderStatus().equals(Order.STATUS_DROP) ){
            LxTender tender = new LxTender();
            tender.setId(bussId);
            tender.setStatus("3");
            tender.setFlowStatus("3");
            tenderService.updateStatusFix(tender);
            //已完成
        }else if(o.getOrderStatus().equals( Order.STATUS_FINISH )){

            LxTender tender = new LxTender();
            tender.setId(bussId);
            tender.setStatus("5");
            tender.setFlowStatus("2");
            tenderService.updateStatusFix(tender);
        }else if(o.getOrderStatus().equals( Order.STATUS_ON )) {
        	LxTender tender = new LxTender();
            tender.setId(bussId);
            tender.setStatus("4");
            tender.setFlowStatus("1");
            tenderService.updateStatusFix(tender);
        }
        return  map;


    }
    @Override
    public Page<LxOrder<InviteBid>> getAllHistorySub() {
        User param = new User();
        param.setId(ShiroKit.getUser().getId());
        User user = userMapper.selectOne(param);
        List l = new ArrayList();
        l.add(Integer.parseInt(user.getRoleid()));
        //获取所有代办流程
        Page<Order> page =  orderService.findHistoryOrders( new Order(),user.getId(),user.getDeptid() ,l);
        List<Order >  orderList = page.getRecords();
        List<LxOrder<InviteBid>>  lxOrders = new ArrayList<>();
        for (Order order : orderList) {

            LxOrder<InviteBid> lxod = new LxOrder<>();
            lxod.setOrder(order);
            LxTenderProcessRelation lpr = new LxTenderProcessRelation();
            lpr.setProcessId(order.getId());
            lpr.setType(2);
            InviteBid lxtd= relationMapper.getInviteByIdHis(lpr);
            if(lxtd!=null){
                lxod.setBusiness(lxtd);
                lxOrders.add(lxod);
            }

        }
        Page<LxOrder<InviteBid>>  lxpage =new Page<>();

        lxpage.setRecords(lxOrders);
        lxpage.setTotal(lxOrders.size());
        return lxpage;
    }

    @Override
    public Page<LxOrder<RegSubContractor>> getAllHistoryReg() {
        User param = new User();
        param.setId(ShiroKit.getUser().getId());
        User user = userMapper.selectOne(param);
        List l = new ArrayList();
        l.add(Integer.parseInt(user.getRoleid()));
        //获取所有代办流程
        Page<Order> page =  orderService.findHistoryOrders( new Order(),user.getId(),user.getDeptid() ,l);
        List<Order >  orderList = page.getRecords();
        List<LxOrder<RegSubContractor>>  lxOrders = new ArrayList<>();
        for (Order order : orderList) {

            LxOrder<RegSubContractor> lxod = new LxOrder<>();
            lxod.setOrder(order);
            LxTenderProcessRelation lpr = new LxTenderProcessRelation();
            lpr.setProcessId(order.getId());
            lpr.setType(5);
            LxTenderProcessRelation lxtd= relationMapper.getRegById(lpr);
            if (lxtd != null) {
                RegSubContractor regSubContractor = subContractorMapper.getRegSubContractorByOrganizationCode(lxtd.getBussId());
                if (regSubContractor != null) {
                    lxod.setBusiness(regSubContractor);
                    lxOrders.add(lxod);
                }
            }
        }
        Page<LxOrder<RegSubContractor>>  lxpage =new Page<>();

        lxpage.setRecords(lxOrders);
        lxpage.setTotal(lxOrders.size());
        return lxpage;
    }

    @Override
    public Page<LxOrder<LxTender>> getAllHistory() {
        User param = new User();
        param.setId(ShiroKit.getUser().getId());
        User user = userMapper.selectOne(param);
        List l = new ArrayList();
        l.add(Integer.parseInt(user.getRoleid()));
        //获取所有代办流程
        Page<Order> page =  orderService.findHistoryOrders( new Order(),user.getId(),user.getDeptid() ,l);
        List<Order >  orderList = page.getRecords();
        List<LxOrder<LxTender>>  lxOrders = new ArrayList<>();
        for (Order order : orderList) {
            LxOrder<LxTender> lxod = new LxOrder<>();
            lxod.setOrder(order);
            LxTenderProcessRelation lpr = new LxTenderProcessRelation();
            lpr.setProcessId(order.getId());
            lpr.setType(2);
            LxTender lxtd= relationMapper.getTenderByIdHis(lpr);
            if(lxtd!=null){
                lxod.setBusiness(lxtd);
                lxOrders.add(lxod);
            }

        }
        Page<LxOrder<LxTender>>  lxpage =new Page<>();

        lxpage.setRecords(lxOrders);
        lxpage.setTotal(lxOrders.size());
        return lxpage;
    }


}
