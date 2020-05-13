package com.xywg.admin.modular.longxin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.flow.entity.Order;
import com.xywg.admin.flow.entity.Process;
import com.xywg.admin.flow.service.ProcessService;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.longxin.dao.LxProjectMapper;
import com.xywg.admin.modular.longxin.model.LxTender;
import com.xywg.admin.modular.longxin.model.LxTenderFile;
import com.xywg.admin.modular.longxin.model.LxTenderProcessRelation;
import com.xywg.admin.modular.longxin.model.TenderResultBean;
import com.xywg.admin.modular.longxin.service.LxProjectService;
import com.xywg.admin.modular.longxin.service.LxTenderProcessRelationService;
import com.xywg.admin.modular.longxin.service.TenderingService;
import com.xywg.admin.modular.project.model.ProjectAddress;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.system.dao.UserMapper;
import com.xywg.admin.modular.system.model.User;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.system.service.UploadService;

import com.xywg.admin.oauth.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by tcw on 2019/7/9.
 */
@Service
public class LxProjectServiceImpl extends ServiceImpl<LxProjectMapper, ProjectMaster> implements LxProjectService{

    @Autowired
    private LxTenderProcessRelationService lxTenderProcessRelationService;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private UserService userService;
    @Autowired
    private ProcessService processService;
    @Autowired

    private TenderingService tenderingService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private LxProjectMapper lxProjectMapper;

    @Autowired
    private AccountProjectService accountProjectService;

    @Override
    public List<Map<String, Object>> selectList(Map<String, Object> map, Page page) {
        map.put("projectCodes", accountProjectService.getProjectCodes());
        return lxProjectMapper.list(map, page);

    }

    @Override
    public void updateStatus(Long projectMasterId) {

        lxProjectMapper.updateStatus(projectMasterId);
    }

    @Override
    public void deleteByIds(Long projectMasterId) {

     lxProjectMapper.deleteByIds(projectMasterId);
    }

    @Override
    public ProjectMaster selectById2(String id) {
        return lxProjectMapper.selectById2(id);
    }

    @Override
    public Map<String, Object> selectById(Long id) {
        return lxProjectMapper.selectById(id);
    }


    @Override
    public List<ProjectAddress>  selectAddressByProjectCode(String projectCode) {
        return this.baseMapper.selectAddressByProjectCode(projectCode);

    }



    @Override
    public List<SubContractor> selectCompanyList(Map<String, Object> map, Page<SubContractor> page,String zbId) {
        List<SubContractor> list=this.baseMapper.selectCompanyList(map,page,zbId);
        return list;
    }

    @Transactional
    @Override
    public void insertInvite(String zbId, List<String> strings) {
        for (String string : strings) {
            this.baseMapper.insertInvite(string,zbId);

        }

    }


    @Override
    public List<String> selectCompanyListId(String zbId) {
        List<String>companyIds =baseMapper.selectCompanyListId(zbId);
        return companyIds;
    }

    @Override
    public Object uploadFtp(MultipartFile[] files) {
        List<String > pathList = new ArrayList<>();
        String fileType = "doc,docx,ppt,pptx,xls,pdf,gif,png,bmp,jpeg,jpg,tiff,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,wmf,webp";
        for (MultipartFile file : files) {
            Map<String, String> resMap = uploadService.fileHandle(file,fileType,10485760);
            if(resMap.get("code").equals("200")){
                pathList.add(resMap.get("path"));
            }else{
                return resMap;
            }
        }

        return pathList;
    }

    @Override
    public Object uploadTender(MultipartFile[] file, String id, String fileName, String resume, String proId,String priceTemp,String deadline,String startTime,String flag,String ids,String tenderCode,String tenderType) {
        List<LxTenderFile> fileList = new ArrayList<LxTenderFile>();
        String fileType = "doc,docx,ppt,pptx,xls,pdf,gif,png,bmp,jpeg,jpg,tiff,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,wmf,webp";
        Map map = new HashMap();
        for (MultipartFile multipartFile : file) {
            Map<String, String> resMap = uploadService.fileHandle(multipartFile,fileType,10485760);
            if(resMap.get("code").equals("200")){
                LxTenderFile tender = new LxTenderFile();

                tender.setFileName(multipartFile.getOriginalFilename());
                tender.setFilePath(resMap.get("path"));
                fileList.add(tender);
            }else{
                return resMap;
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LxTender td = new LxTender();
        td.setProjectId(id);
        td.setFileName(fileName);
        td.setResume(resume);
        td.setPriceTemp(priceTemp);
        td.setCreateTime(new Date());
        td.setTenderCode(tenderCode);

        try {
            td.setDeadline(sdf.parse(deadline));
            td.setStartTime(sdf.parse(startTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        td.setCreateUser(ShiroKit.getUser().getName());
        td.setTenderType(tenderType);
        td =  tenderingService.saveTender(td);
        for (LxTenderFile lxTenderFile : fileList) {
            lxTenderFile.setTenderId(td.getId());
        }
        User param = new User();
        param.setId(ShiroKit.getUser().getId());
        User user = userMapper.selectOne(param);
        List l = new ArrayList();
        l.add(Integer.parseInt(user.getRoleid()));

        tenderingService.saveFile(fileList);
        
        TenderResultBean tbp = tenderingService.getById(td.getId());
        String projectId = tbp.getProjectId();
        LxTenderProcessRelation paramL = new LxTenderProcessRelation();
        paramL.setBussId(projectId);
        paramL.setType(1);
        String processId = lxTenderProcessRelationService.getFlowRelation(paramL);
        Map ret = new HashMap();
        if(StringUtils.isEmpty(processId)) {
			ret.put("code", "500");
			ret.put("message", "请绑定项目流程");
			return ret;
		}
        
		Order order = new Order();
        //插入流程
        try {
            order=   processService.start(processId,Integer.parseInt(user.getId().toString()),Integer.parseInt(user.getDeptid().toString()),l);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LxTenderProcessRelation lpr = new LxTenderProcessRelation();
        lpr.setBussId(tbp.getId());
        lpr.setProcessId(order.getId());
        lpr.setType(2);
        lxTenderProcessRelationService.addRelation(lpr);

//        Order order = new Order();
   //插入流程
//        if("1".equals(flag)){
//            try {
//                order=   processService.start(proId,user.getId(),user.getDeptid(),l);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }else{
//            String[] ids1 = ids.split(",");
//            Process p = new Process();
//            p.setCreateTime(new Date());
//            p.setCreateUser(ShiroKit.getUser().getId());
//            p.setProcessName(UUID.randomUUID().toString());
//            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
//            p.setDisplayName("自定义流程"+sdf2.format(new Date()));
//            JSONObject jsonObject = new JSONObject();
//            JSONObject startNode = new JSONObject();
//            startNode.put("prev","");
//            startNode.put("next","node"+ids1[0] + 0);
//            JSONObject parx = new JSONObject();
//            parx.put("user",new ArrayList<>());
//            parx.put("dept",new ArrayList<>());
//            parx.put("role",new ArrayList<>());
//            startNode.put("par",parx);
//            startNode.put("type",1);
//            jsonObject.put("startNode",startNode);
//
//            for(int i=0;i<ids1.length;i++){
//                JSONObject node = new JSONObject();
//                node.put("type",1);
//
//                if(ids1.length>1){
//                    //第一个 无头有尾
//                    if(i==0){
//                        node.put("prev","startNode");
//                        node.put("next","node"+ids1[i+1] + (i+1));
//                        //最后一个 有头无尾
//                    }else if(i==ids1.length-1){
//                        node.put("prev","node"+ids1[i-1] + (i-1));
//                        node.put("next","");
//                        //其余的 有头有尾
//                    }else{
//                        node.put("prev","node"+ids1[i-1]+ (i-1));
//                        node.put("next","node"+ids1[i+1]+ (i+1));
//                    }
//                }else{
//                    node.put("prev","startNode");
//                    node.put("next","");
//                }
//
//
//
//                JSONObject par = new JSONObject();
//                List<Integer> userList = new ArrayList<>();
//                userList.add(Integer.parseInt(ids1[i]));
//                par.put("user",userList);
//                par.put("dept",new ArrayList<>());
//                par.put("role",new ArrayList<>());
//                node.put("par",par);
//                jsonObject.put("node"+ids1[i]+i,node);
//            }
//            p.setVars(jsonObject.toJSONString());
//            try {
//            processService.insert(p,user.getId(),user.getDeptid(),l);
//            order=   processService.start(p.getId(),user.getId(),user.getDeptid(),l);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        LxTenderProcessRelation lpr = new LxTenderProcessRelation();
//        lpr.setBussId(td.getId());
//        lpr.setProcessId(order.getId());
//        lxTenderProcessRelationService.addRelation(lpr);
        map.put("message","发布成功！");
        map.put("code","200");
        return map;
    }


    @Override
    public List<SubContractor> selectCompanyListAndYb(Map<String, Object> map, Page<SubContractor> page, String zbId) {

        List<SubContractor> list=   this.lxProjectMapper.selectCompanyListAndYb(map,page,zbId);
        return list;
    }


    @Override
    public List<SubContractor> companyDbList(Map<String, Object> map, Page<SubContractor> page, String projectId) {

        List<SubContractor> list=   this.lxProjectMapper.companyDbList(map,page,projectId);
        return list;
    }

    @Override
    public Long selectNumber(Long projectMasterId) {
        return this.lxProjectMapper.selectNumber(projectMasterId);
    }

	

	@Override
	public Object flowAdd(String projectId, String flag,String processId, String ids,String fixFlag,String fixProcessId,String fixIds) {
		Map map = new HashMap();
		//招标审批流程表
		addRelation(projectId, flag, processId, ids);
		//定标审批流程表
		addFixRelation(projectId, fixFlag, fixProcessId, fixIds);
	
        map.put("message","发布成功！");
        map.put("code","200");
        return map;
	}

	private void addFixRelation(String projectId, String flag, String processId, String ids) {
		User param = new User();
        param.setId(ShiroKit.getUser().getId());
        User user = userMapper.selectOne(param);
        List l = new ArrayList();
        l.add(Integer.parseInt(user.getRoleid()));
        
		Order order = new Order();
        //插入流程
        if("1".equals(flag)){
        }else{
            String disName = "";
            String[] ids1 = ids.split(",");
            Process p = new Process();
            p.setCreateTime(new Date());
            p.setCreateUser(ShiroKit.getUser().getId());
            p.setProcessName(UUID.randomUUID().toString());
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
            p.setDisplayName("自定义流程"+sdf2.format(new Date()));
            JSONObject jsonObject = new JSONObject();
            JSONObject startNode = new JSONObject();
            startNode.put("prev","");
            startNode.put("next","node"+ids1[0] + 0);
            JSONObject parx = new JSONObject();
            parx.put("user",new ArrayList<>());
            parx.put("dept",new ArrayList<>());
            parx.put("role",new ArrayList<>());
            startNode.put("par",parx);
            startNode.put("type",1);
            jsonObject.put("startNode",startNode);

            for(int i=0;i<ids1.length;i++){

                User u = new User();
                u.setId(Integer.parseInt(ids1[i]));
                u = userMapper.getById(u);
                if(i==ids1.length-1){

                    disName =disName+ u.getName() ;
                }else{
                    disName =disName+ u.getName()+"-";


                }
                JSONObject node = new JSONObject();
                node.put("type",1);

                if(ids1.length>1){
                    //第一个 无头有尾
                    if(i==0){
                        node.put("prev","startNode");
                        node.put("next","node"+ids1[i+1] + (i+1));
                        //最后一个 有头无尾
                    }else if(i==ids1.length-1){
                        node.put("prev","node"+ids1[i-1] + (i-1));
                        node.put("next","");
                        //其余的 有头有尾
                    }else{
                        node.put("prev","node"+ids1[i-1]+ (i-1));
                        node.put("next","node"+ids1[i+1]+ (i+1));
                    }
                }else{
                    node.put("prev","startNode");
                    node.put("next","");
                }



                JSONObject par = new JSONObject();
                List<Integer> userList = new ArrayList<>();
                userList.add(Integer.parseInt(ids1[i]));
                par.put("user",userList);
                par.put("dept",new ArrayList<>());
                par.put("role",new ArrayList<>());
                node.put("par",par);
                jsonObject.put("node"+ids1[i]+i,node);
            }
            p.setDisplayName(disName);
            p.setVars(jsonObject.toJSONString());
            p.setType(1);
            try {
            processService.insert(p,user.getId(),user.getDeptid(),l,projectId);
            processId = p.getId();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        LxTenderProcessRelation lpr = new LxTenderProcessRelation();
        lpr.setBussId(projectId);
        lpr.setProcessId(processId);
        lpr.setType(1);
        lxTenderProcessRelationService.addFixRelation(lpr);
	}

	private void addRelation(String projectId, String flag, String processId, String ids) {
		User param = new User();
        param.setId(ShiroKit.getUser().getId());
        User user = userMapper.selectOne(param);
        List l = new ArrayList();
        l.add(Integer.parseInt(user.getRoleid()));
        
		Order order = new Order();
        //插入流程
        if("1".equals(flag)){
        } else {
            String disName = "";
            String[] ids1 = ids.split(",");
            Process p = new Process();
            p.setCreateTime(new Date());
            p.setCreateUser(ShiroKit.getUser().getId());
            p.setProcessName(UUID.randomUUID().toString());
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
            JSONObject jsonObject = new JSONObject();
            JSONObject startNode = new JSONObject();
            startNode.put("prev","");
            startNode.put("next","node"+ids1[0] + 0);
            JSONObject parx = new JSONObject();
            parx.put("user",new ArrayList<>());
            parx.put("dept",new ArrayList<>());
            parx.put("role",new ArrayList<>());
            startNode.put("par",parx);
            startNode.put("type",1);
            jsonObject.put("startNode",startNode);

            for(int i=0;i<ids1.length;i++){
                User u = new User();
                u.setId(Integer.parseInt(ids1[i]));
                u = userMapper.getById(u);
                if(i==ids1.length-1){

                    disName =disName+ u.getName() ;
                }else{
                    disName =disName+ u.getName()+"-";


                }

                JSONObject node = new JSONObject();
                node.put("type",1);

                if(ids1.length>1){
                    //第一个 无头有尾
                    if(i==0){
                        node.put("prev","startNode");
                        node.put("next","node"+ids1[i+1] + (i+1));
                        //最后一个 有头无尾
                    }else if(i==ids1.length-1){
                        node.put("prev","node"+ids1[i-1] + (i-1));
                        node.put("next","");
                        //其余的 有头有尾
                    }else{
                        node.put("prev","node"+ids1[i-1]+ (i-1));
                        node.put("next","node"+ids1[i+1]+ (i+1));
                    }
                }else{
                    node.put("prev","startNode");
                    node.put("next","");
                }
                JSONObject par = new JSONObject();
                List<Integer> userList = new ArrayList<>();
                userList.add(Integer.parseInt(ids1[i]));
                par.put("user",userList);
                par.put("dept",new ArrayList<>());
                par.put("role",new ArrayList<>());
                node.put("par",par);
                jsonObject.put("node"+ids1[i]+i,node);



            }
            p.setDisplayName(disName);
            p.setVars(jsonObject.toJSONString());
            p.setType(1);
            try {
            processService.insert(p,user.getId(),user.getDeptid(),l,projectId);
            processId = p.getId();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        LxTenderProcessRelation lpr = new LxTenderProcessRelation();
        lpr.setBussId(projectId);
        lpr.setProcessId(processId);
        lpr.setType(1);
        lxTenderProcessRelationService.addRelation(lpr);
	}

    @Override
    public Long selectProjectManCode(String projectManCode) {
        return lxProjectMapper.selectProjectManCode(projectManCode);
    }


    @Override
    public Object getIdByProjectCode(Object projectCode) {
        return lxProjectMapper.getIdByProjectCode(projectCode);
    }

    @Override
    public Map<String, Object> selectProjectCodeByProjectId(Integer projectMasterId) {
        return lxProjectMapper.selectProjectCodeByProjectId(projectMasterId);
    }
}
